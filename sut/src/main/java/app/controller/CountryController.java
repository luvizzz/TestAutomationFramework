package app.controller;

import app.domain.Country;
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
@RequestMapping("/country")
@SuppressWarnings("deprecation")
@Api(value = "Country Management Endpoints", tags = "Country", description = "Manage your Countries")
public class CountryController {
    private final static Logger LOG = Logger.getLogger(CountryController.class.getSimpleName());
    private final static String EXISTS_IN_MANUFACTURER_MSG = "Country with code \"%s\" is present in manufacturer. Please remove this dependency first.";
    private final static String INVALID_CODE_MSG = "Please provide a code of minimum %d and maximum %d characters.";
    private final static int MIN_CODE_LENGTH = 2;
    private final static int MAX_CODE_LENGTH = 2;

    @Autowired
    CountryRepository repository;

    @Autowired
    ManufacturerRepository manufacturer;

    private Predicate<Country> byCode(String code) {
        return c -> code.equals(c.getCode());
    }

    private Predicate<Country> byName(String name) {
        return c -> name.equals(c.getName());
    }

    private Boolean existsInManufacturer(Country country) {
        return manufacturer.findByOriginCountry(country).size() > 0;
    }

    private Boolean expectedCodeLength(String code) {
        return code.length() >= MIN_CODE_LENGTH && code.length() <= MAX_CODE_LENGTH;
    }

    public CountryController(CountryRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @ApiOperation(value = "Gets countries for specified codes and names")
    public ResponseEntity<List<Country>> getCountries(
            @SuppressWarnings("OptionalUsedAsFieldOrParameterType") @RequestParam("code") Optional<String> maybeCode,
            @SuppressWarnings("OptionalUsedAsFieldOrParameterType") @RequestParam("name") Optional<String> maybeName) {
        LOG.info(String.format("(getCountries) Input code: %s; Input name: %s",
                maybeCode.orElse("null"),
                maybeName.orElse("null")));

        Iterable<Country> countries = repository.findAll();

        List<Country> filteredCountries = StreamSupport.stream(countries.spliterator(), false)
                .filter(maybeCode.isPresent() ? byCode(maybeCode.get()) : x -> true)
                .filter(maybeName.<Predicate<? super Country>>map(this::byName).orElseGet(() -> x -> true))
                .collect(Collectors.toList());

        if (filteredCountries.size() > 0) {
            return ResponseEntity.ok(filteredCountries);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{code}")
    @ApiOperation(value = "Gets country by code")
    public ResponseEntity<Country> getCountryByCode(@PathVariable("code") String code) {
        LOG.info(String.format("(getCountryByCode) Input code: %s", code));
        Optional<Country> country = repository.findById(code);
        return country.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }


    @PostMapping
    @ApiOperation(value = "Creates a country")
    public ResponseEntity<String> postCountry(@RequestBody Country country) {
        LOG.info(String.format("(postCountry) Input country: %s", country.toString()));

        if (country.getCode().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide a valid code.");
        }

        if (!expectedCodeLength(country.getCode())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format(INVALID_CODE_MSG, MIN_CODE_LENGTH, MAX_CODE_LENGTH));
        }

        if (repository.existsById(country.getCode())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("Country with code \"%s\" already exists    .", country.getCode()));
        }

        repository.save(country);

        if (repository.existsById(country.getCode())) {
            URI location = URI.create(String.format("/country/%s", country.getCode()));
            return ResponseEntity.created(location).body(String.format("Country with code \"%s\" created.", country.getCode()));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Country could not be created.");
        }
    }

    @PutMapping(value = "/{code}")
    @ApiOperation(value = "Updates country with specified code")
    public ResponseEntity<String> putCountryByCode(@PathVariable("code") String code, @RequestBody Country newCountry) {
        LOG.info(String.format("(putCountryByCode) Input code: %s", code));

        if (newCountry.getCode() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please do not provide country code in body.");
        }
        if (!repository.existsById(code)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to update country which does not exist.");
        }

        Optional<Country> maybeCountry = repository.findById(code)
                .map(currentCountry -> {
                    currentCountry.setName(newCountry.getName());
                    return repository.save(currentCountry);
                });

        return maybeCountry.map(country -> ResponseEntity.ok(String.format("Country with code \"%s\" updated.", country.getCode())))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Country could not be updated."));
    }

    @DeleteMapping(value = "/{code}")
    @ApiOperation(value = "Deletes a country")
    public ResponseEntity<String> deleteCountry(@PathVariable("code") String code) {
        LOG.info(String.format("(deleteCountry) Input code: %s", code));

        Optional<Country> maybeCountry = repository.findById(code);

        if (maybeCountry.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Country with code \"%s\" does not exist.", code));
        } else {
            if (existsInManufacturer(maybeCountry.get())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format(EXISTS_IN_MANUFACTURER_MSG, code));
            }
            repository.deleteById(code);
            return ResponseEntity.noContent().build();
        }
    }
}
