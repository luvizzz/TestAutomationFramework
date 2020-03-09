package app.controller;

import app.domain.Car;
import app.resource.CarRepository;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/car")
@Api(value="Car Management Endpoints", tags = "Car")
public class CarController {

    private final CarRepository repository;

    public CarController(CarRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Iterable<Car> getCars() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Car getCar(@PathVariable("id") long id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping
    public void postCar(@RequestBody Car car) {
        repository.save(car);
    }

    @GetMapping("/")
    public List<Car> getCarByName(@RequestParam String name) {
        return repository.findByName(name);
    }
}
