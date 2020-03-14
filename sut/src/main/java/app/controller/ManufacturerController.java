package app.controller;

import app.domain.Manufacturer;
import app.resource.ManufacturerRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/manufacturer")
@SuppressWarnings("deprecation")
@Api(value="Manufacturer Management Endpoints", tags = "Manufacturer", description="Manage your Manufacturers")
public class ManufacturerController {

    private final ManufacturerRepository repository;

    public ManufacturerController(ManufacturerRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @ApiOperation(value = "Get all manufacturers")
    public Iterable<Manufacturer> getManufacturers() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get manufacturers by specified id")
    public Manufacturer getManufacturer(@PathVariable("id") long id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping
    @ApiOperation(value = "Create a new manufacturer")
    public void postManufacturer(@RequestBody Manufacturer manufacturer) {
        repository.save(manufacturer);
    }

    @GetMapping("/")
    @ApiOperation(value = "Get manufacturers by specified names")
    public List<Manufacturer> getManufacturerByName(@RequestParam String name) {
        return repository.findByName(name);
    }
}
