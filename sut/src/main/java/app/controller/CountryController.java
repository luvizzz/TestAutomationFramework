package app.controller;

import app.domain.Country;
import app.resource.CountryRepository;
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
@RequestMapping("/country")
@SuppressWarnings("deprecation")
@Api(value="Country Management Endpoints", tags = "Country", description="Manage your Countries")
public class CountryController {

    private final CountryRepository repository;

    public CountryController(CountryRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @ApiOperation(value = "Get all countries")
    public Iterable<Country> getCountries() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get country by id")
    public Country getCountry(@PathVariable("id") char[] id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping
    @ApiOperation(value = "Create a new country")
    public void postCountry(@RequestBody Country country) {
        repository.save(country);
    }

    @GetMapping("/")
    @ApiOperation(value = "Get countries by specified names")
    public List<Country> getCountryByName(@RequestParam String name) {
        return repository.findByName(name);
    }
}
