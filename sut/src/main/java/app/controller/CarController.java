package app.controller;

import app.domain.Car;
import app.domain.Manufacturer;
import app.resource.CarRepository;
import app.resource.ManufacturerRepository;
import app.resource.StockRepository;
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
@RequestMapping("/car")
@SuppressWarnings("deprecation")
@Api(value="Car Management Endpoints", tags = "Car", description="Manage your Cars")
public class CarController {
    private final static Logger LOG = Logger.getLogger(CarController.class.getSimpleName());
    private final static String EXISTS_IN_STOCK_MSG = "Car with id \"%d\" is present in stock. Please remove this dependency first.";

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

    private Boolean existsInStock(Car car) {
        return stock.findByCar(car).size() > 0;
    }

    public CarController(CarRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @ApiOperation(value = "Gets cars for specified ids and names")
    public ResponseEntity<List<Car>> getCars(
            @SuppressWarnings("OptionalUsedAsFieldOrParameterType") @RequestParam("id") Optional<Long> maybeId,
            @SuppressWarnings("OptionalUsedAsFieldOrParameterType") @RequestParam("name") Optional<String> maybeName) {
        LOG.info(String.format("(getCars) Input id: %d; Input name: %s",
                maybeId.orElse((long) 0),
                maybeName.orElse("null")));
        Iterable<Car> cars = repository.findAll();

        List<Car> filteredCars = StreamSupport.stream(cars.spliterator(), false)
                .filter(maybeId.isPresent() ? byId(maybeId.get()) : x -> true)
                .filter(maybeName.<Predicate<? super Car>> map (this::byName).orElseGet(() -> x -> true))
                .collect(Collectors.toList());

        if(filteredCars.size() > 0) {
            return ResponseEntity.ok(filteredCars);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value="/{id}")
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

        if(car.getId() != 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please do not provide id when creating a car.");
        }

        repository.save(car);

        if(repository.existsById(car.getId())) {
            URI location = URI.create(String.format("/car/%d", car.getId()));
            return ResponseEntity.created(location).body(String.format("Car with id \"%d\" created.", car.getId()));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Car could not be created.");
        }
    }

    @PutMapping(value="/{id}")
    @ApiOperation(value = "Updates car with specified id")
    public ResponseEntity<String> putCarById(@PathVariable("id") Long id, @RequestBody Car newCar) {
        LOG.info(String.format("(putCarById) Input id: %d", id));
        if(newCar.getId() != 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please do not provide id in body.");
        }
        if(!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to update car which does not exist.");
        }
        Manufacturer currentManufacturer = newCar.getManufacturer();
        if(!manufacturers.existsById(currentManufacturer.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide valid manufacturer.");
        }

        Optional<Car> maybeCar = repository.findById(id)
                .map(currentCar -> {
                    currentCar.setName(newCar.getName());
                    currentCar.setManufacturer(newCar.getManufacturer());
                    return repository.save(currentCar);
                });

        return maybeCar.map(car -> ResponseEntity.ok(String.format("Car with id \"%d\" updated.", car.getId())))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Car could not be updated."));
    }

    @DeleteMapping(value="/{id}")
    @ApiOperation(value = "Deletes a car")
    public ResponseEntity<String> deleteCar(@PathVariable("id") Long id) {

        LOG.info(String.format("(deleteCar) Input id: %d", id));
        Optional<Car> maybeCar = repository.findById(id);

        if(maybeCar.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Car with id \"%d\" does not exist.", id));
        } else {
            if(existsInStock(maybeCar.get())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format(EXISTS_IN_STOCK_MSG, id));
            }
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
    }
}
