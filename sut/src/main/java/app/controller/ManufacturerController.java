package app.controller;

import app.domain.Country;
import app.domain.Manufacturer;
import app.resource.CarRepository;
import app.resource.CountryRepository;
import app.resource.ManufacturerRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
public class ManufacturerController {
    private final static Logger LOG = Logger.getLogger(ManufacturerController.class.getSimpleName());
    private final static String EXISTS_IN_CAR_MSG = "Manufacturer with id \"%d\" is present in cars. Please remove this dependency first.";

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

    private Boolean existsInCar(Manufacturer manufacturer) {
        return cars.findByManufacturer(manufacturer).size() > 0;
    }

    public ManufacturerController(ManufacturerRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @ApiOperation(value = "Gets manufacturers for specified ids and names")
    public ResponseEntity<List<Manufacturer>> getManufacturers(
            @SuppressWarnings("OptionalUsedAsFieldOrParameterType") @RequestParam("id") Optional<Long> maybeId,
            @SuppressWarnings("OptionalUsedAsFieldOrParameterType") @RequestParam("name") Optional<String> maybeName) {
        LOG.info(String.format("(getManufacturers) Input id: %d; Input name: %s",
                maybeId.orElse((long) 0),
                maybeName.orElse("null")));
        Iterable<Manufacturer> manufacturers = repository.findAll();

        List<Manufacturer> filteredManufacturers = StreamSupport.stream(manufacturers.spliterator(), false)
                .filter(maybeId.isPresent() ? byId(maybeId.get()) : x -> true)
                .filter(maybeName.<Predicate<? super Manufacturer>> map (this::byName).orElseGet(() -> x -> true))
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please do not provide id when creating a manufacturer.");
        }

        repository.save(manufacturer);

        if(repository.existsById(manufacturer.getId())) {
            URI location = URI.create(String.format("/manufacturer/%d", manufacturer.getId()));
            return ResponseEntity.created(location).body(String.format("Manufacturer with id \"%d\" created.", manufacturer.getId()));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Manufacturer could not be created.");
        }
    }

    @PutMapping(value="/{id}")
    @ApiOperation(value = "Updates manufacturer with specified id")
    public ResponseEntity<String> putManufacturerById(@PathVariable("id") Long id, @RequestBody Manufacturer newManufacturer) {
        LOG.info(String.format("(putManufacturerById) Input id: %d", id));
        if(newManufacturer.getId() != 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please do not provide id in body.");
        }
        if(!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to update manufacturer which does not exist.");
        }
        Country currentCountry = newManufacturer.getOriginCountry();
        if(!countries.existsById(currentCountry.getCode())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide valid country.");
        }

        Optional<Manufacturer> maybeManufacturer = repository.findById(id)
                .map(currentManufacturer -> {
                    currentManufacturer.setName(newManufacturer.getName());
                    currentManufacturer.setOriginCountry(newManufacturer.getOriginCountry());
                    return repository.save(currentManufacturer);
                });

        return maybeManufacturer.map(manufacturer -> ResponseEntity.ok(String.format("Manufacturer with id \"%d\" updated.", manufacturer.getId())))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Manufacturer could not be updated."));
    }

    @DeleteMapping(value="/{id}")
    @ApiOperation(value = "Deletes a manufacturer")
    public ResponseEntity<String> deleteManufacturer(@PathVariable("id") Long id) {
        LOG.info(String.format("(deleteManufacturer) Input id: %d", id));

        Optional<Manufacturer> maybeManufacturer = repository.findById(id);

        if(maybeManufacturer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Manufacturer with id \"%d\" does not exist.", id));
        } else {
            if(existsInCar(maybeManufacturer.get())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format(EXISTS_IN_CAR_MSG, id));
            }
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
    }
}
