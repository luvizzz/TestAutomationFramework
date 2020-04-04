package app.controller;

import app.domain.Manufacturer;
import app.resource.CarRepository;
import app.resource.CountryRepository;
import app.resource.ManufacturerRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/manufacturer")
@SuppressWarnings("deprecation")
@Api(value="Manufacturer Management Endpoints", tags = "Manufacturer", description="Manage your Manufacturers")
public class ManufacturerController extends BaseController {
    private static final Logger LOG = Logger.getLogger(ManufacturerController.class.getSimpleName());

    @Autowired
    ManufacturerRepository repository;

    @Autowired
    CountryRepository countries;

    @Autowired
    CarRepository cars;

    private Predicate<Manufacturer> byId(long id) {
        return c -> c.getId() == id;
    }

    private Predicate<Manufacturer> byName(String name) {
        return c -> c.getName().equals(name);
    }

    private Predicate<Manufacturer> byCountryCode(String countryCode) {
        return c -> c.getCountryCode().equals(countryCode);
    }

    private Boolean existsInCar(Manufacturer manufacturer) {
        return cars.findByManufacturer(manufacturer).size() > 0;
    }

    public ManufacturerController(ManufacturerRepository repository) {
        this.repository = repository;
        path = "/manufacturer";
    }

    @GetMapping
    @ApiOperation(value = "Gets manufacturers for specified ids and names")
    public ResponseEntity<List<Manufacturer>> getManufacturers(
            @SuppressWarnings("OptionalUsedAsFieldOrParameterType") @RequestParam("id") Optional<Long> maybeId,
            @SuppressWarnings("OptionalUsedAsFieldOrParameterType") @RequestParam("name") Optional<String> maybeName,
            @SuppressWarnings("OptionalUsedAsFieldOrParameterType") @RequestParam("countryCode") Optional<String> maybeCountryCode) {
        LOG.info(String.format("(getManufacturers) Input id: %d; Input name: %s; Input Country Code: %s",
                maybeId.orElse((long) 0),
                maybeName.orElse("null"),
                maybeCountryCode.orElse("null")));
        
        Iterable<Manufacturer> manufacturers = repository.findAll();

        List<Manufacturer> filteredManufacturers = StreamSupport.stream(manufacturers.spliterator(), false)
                .filter(maybeId.isPresent() ? byId(maybeId.get()) : x -> true)
                .filter(maybeName.<Predicate<? super Manufacturer>> map (this::byName).orElseGet(() -> x -> true))
                .filter(maybeCountryCode.<Predicate<? super Manufacturer>> map (this::byCountryCode).orElseGet(() -> x -> true))
                .collect(Collectors.toList());

        if(filteredManufacturers.size() > 0) {
            return ResponseEntity.ok(filteredManufacturers);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value="/{id}")
    @ApiOperation(value = "Gets manufacturer by id")
    public ResponseEntity<Manufacturer> getManufacturerById(@PathVariable("id") Long id) {
        LOG.info(String.format("(getManufacturerById) Input id: %d", id));
        Optional<Manufacturer> manufacturer = repository.findById(id);
        return manufacturer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    @ApiOperation(value = "Creates a manufacturer")
    public ResponseEntity<String> postManufacturer(@RequestBody Manufacturer manufacturer) {
        LOG.info(String.format("(postManufacturer) Input manufacturer: %s", manufacturer.toString()));

        if(manufacturer.getId() != 0) {
            return createErrorResponse(ID_PROVIDED_IN_BODY_MSG);
        }
        if (!countries.existsById(manufacturer.getCountryCode())) {
            return createErrorResponse(INVALID_DEPENDENCY_MSG);
        }

        repository.save(manufacturer);

        URI location = URI.create(String.format("/manufacturer/%d", manufacturer.getId()));
        return ResponseEntity.created(location).body(manufacturer.toString());
    }

    @PutMapping(value="/{id}")
    @ApiOperation(value = "Updates manufacturer with specified id")
    public ResponseEntity<String> putManufacturerById(@PathVariable("id") Long id, @RequestBody Manufacturer updatedManufacturer) {
        LOG.info(String.format("(putManufacturerById) Input id: %d", id));
        
        if(updatedManufacturer.getId() != 0) {
            return createErrorResponse(ID_PROVIDED_IN_BODY_MSG);
        }
        if(!repository.existsById(id)) {
            return createErrorResponse(ENTITY_DOES_NOT_EXIST_MSG);
        }
        if(!countries.existsById(updatedManufacturer.getCountryCode())) {
            return createErrorResponse(INVALID_DEPENDENCY_MSG);
        }

        Optional<Manufacturer> maybeManufacturer = repository.findById(id)
                .map(currentManufacturer -> {
                    currentManufacturer.setName(updatedManufacturer.getName());
                    currentManufacturer.setCountryCode(updatedManufacturer.getCountryCode());
                    return repository.save(currentManufacturer);
                });

        return maybeManufacturer.map(manufacturer -> {
            return ResponseEntity.accepted().body(manufacturer.toString());
        })
                .orElseGet(() -> createErrorResponse(ENTITY_COULD_NOT_BE_UPDATED_MSG));
    }

    @DeleteMapping(value="/{id}")
    @ApiOperation(value = "Deletes a manufacturer")
    public ResponseEntity<String> deleteManufacturer(@PathVariable("id") Long id) {
        LOG.info(String.format("(deleteManufacturer) Input id: %d", id));

        Optional<Manufacturer> maybeManufacturer = repository.findById(id);

        if(maybeManufacturer.isEmpty()) {
            return createErrorResponse(ENTITY_DOES_NOT_EXIST_MSG);
        } else {
            if(existsInCar(maybeManufacturer.get())) {
                return createErrorResponse(DEPENDENCY_EXISTS_MSG);
            }
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
    }
}
