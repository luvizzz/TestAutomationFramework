package app.controller;

import app.domain.Car;
import app.resource.CarRepository;
import app.resource.ManufacturerRepository;
import app.resource.StockRepository;
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

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@RequestMapping("/car")
@SuppressWarnings("deprecation")
@Api(value = "Car Management Endpoints", tags = "Car", description = "Manage your Cars")
public class CarController extends BaseController {
    private static final Logger LOG = Logger.getLogger(CarController.class.getSimpleName());

    @Autowired
    CarRepository repository;

    @Autowired
    StockRepository stock;

    @Autowired
    ManufacturerRepository manufacturers;

    private Predicate<Car> byId(long id) {
        return c -> c.getId() == id;
    }

    private Predicate<Car> byName(String name) {
        return c -> c.getName().equals(name);
    }

    private Predicate<Car> byManufacturerId(long manufacturerId) {
        return c -> c.getManufacturerId() == manufacturerId;
    }

    private Boolean existsInStock(Car car) {
        return stock.findByCar(car).size() > 0;
    }

    public CarController(CarRepository repository) {
        this.repository = repository;
        path = "/car";
    }

    @GetMapping
    @ApiOperation(value = "Gets cars for specified ids, names, and manufacturer ids")
    public ResponseEntity<List<Car>> getCars(
            @SuppressWarnings("OptionalUsedAsFieldOrParameterType") @RequestParam("id") Optional<Long> maybeId,
            @SuppressWarnings("OptionalUsedAsFieldOrParameterType") @RequestParam("name") Optional<String> maybeName,
            @SuppressWarnings("OptionalUsedAsFieldOrParameterType") @RequestParam("manufacturerId") Optional<Long> maybeManufacturerId) {
        LOG.info(String.format("(getCars) Input id: %d; Input name: %s; Input manufacturerId: %d",
                maybeId.orElse((long) 0),
                maybeName.orElse("null"),
                maybeManufacturerId.orElse((long) 0)));

        Iterable<Car> cars = repository.findAll();

        List<Car> filteredCars = StreamSupport.stream(cars.spliterator(), false)
                .filter(maybeId.isPresent() ? byId(maybeId.get()) : x -> true)
                .filter(maybeName.<Predicate<? super Car>>map(this::byName).orElseGet(() -> x -> true))
                .filter(maybeManufacturerId.<Predicate<? super Car>>map(this::byManufacturerId).orElseGet(() -> x -> true))
                .collect(Collectors.toList());


        return ResponseEntity.ok(filteredCars);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Gets car by id")
    public ResponseEntity<Car> getCarById(@PathVariable("id") Long id) {
        LOG.info(String.format("(getCarById) Input id: %d", id));

        Optional<Car> car = repository.findById(id);
        return car.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    @ApiOperation(value = "Creates a car")
    public ResponseEntity<String> postCar(@RequestBody Car car) {
        LOG.info(String.format("(postCar) Input car: %s", car.toString()));

        if (car.getId() != 0) {
            return createErrorResponse(ID_PROVIDED_IN_BODY_MSG);
        }

        if (car.getManufacturerId() == 0 || !manufacturers.existsById(car.getManufacturerId())) {
            return createErrorResponse(INVALID_DEPENDENCY_MSG);
        }

        repository.save(car);

        URI location = URI.create(String.format("/car/%d", car.getId()));
        return ResponseEntity.created(location).contentType(APPLICATION_JSON).body(car.toString());
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Updates car with specified id")
    public ResponseEntity<String> putCarById(@PathVariable("id") Long id, @RequestBody Car updatedCar) {
        LOG.info(String.format("(putCarById) Input id: %d", id));

        if (updatedCar.getId() != 0) {
            return createErrorResponse(ID_PROVIDED_IN_BODY_MSG);
        }
        if (!repository.existsById(id)) {
            return createErrorResponse(ENTITY_DOES_NOT_EXIST_MSG);
        }
        if (!manufacturers.existsById(updatedCar.getManufacturerId())) {
            return createErrorResponse(INVALID_DEPENDENCY_MSG);
        }

        Optional<Car> maybeCar = repository.findById(id)
                .map(currentCar -> {
                    currentCar.setName(updatedCar.getName());
                    currentCar.setManufacturerId(updatedCar.getManufacturerId());
                    return repository.save(currentCar);
                });

        return maybeCar.map(c -> ResponseEntity.accepted().body(c.toString()))
                .orElseGet(() -> createErrorResponse(ENTITY_COULD_NOT_BE_UPDATED_MSG));
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Deletes a car")
    public ResponseEntity<String> deleteCar(@PathVariable("id") Long id) {
        LOG.info(String.format("(deleteCar) Input id: %d", id));
        Optional<Car> maybeCar = repository.findById(id);

        if (maybeCar.isEmpty()) {
            return createErrorResponse(ENTITY_DOES_NOT_EXIST_MSG);
        } else {
            if (existsInStock(maybeCar.get())) {
                return createErrorResponse(DEPENDENCY_EXISTS_MSG);
            }
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
    }
}
