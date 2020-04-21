package app.controller;

import app.domain.Country;
import app.resource.CountryRepository;
import app.resource.ManufacturerRepository;
import app.utils.ErrorMessage;
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

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@RequestMapping("/country")
@SuppressWarnings("deprecation")
@Api(value = "Country Management Endpoints", tags = "Country", description = "Manage your Countries")
public class CountryController extends BaseController {
    private static final Logger LOG = Logger.getLogger(CountryController.class.getSimpleName());

    private static final int MIN_CODE_LENGTH = 2;
    private static final int MAX_CODE_LENGTH = 2;
    private static final ErrorMessage INVALID_CODE_MSG = new ErrorMessage(
            BAD_REQUEST,
            String.format("Please provide a code of minimum %d and maximum %d characters",
                    MIN_CODE_LENGTH,
                    MAX_CODE_LENGTH)
            );

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
        path = "/country";
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

        return ResponseEntity.ok(filteredCountries);
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

        if (country.getCode().isEmpty() || !expectedCodeLength(country.getCode())) {
            return createErrorResponse(INVALID_CODE_MSG);
        }

        if (repository.existsById(country.getCode())) {
            return createErrorResponse(ENTITY_ALREADY_EXISTS_MSG);
        }

        repository.save(country);

        URI location = URI.create(String.format("/country/%s", country.getCode()));
        Optional<Country> maybeCountry = repository.findById(country.getCode());

        return maybeCountry.map(c -> ResponseEntity.created(location).contentType(APPLICATION_JSON).body(c.toString()))
                .orElseGet(() -> createErrorResponse(ENTITY_COULD_NOT_BE_CREATED_MSG));
    }

    @PutMapping(value = "/{code}")
    @ApiOperation(value = "Updates country with specified code")
    public ResponseEntity<String> putCountryByCode(@PathVariable("code") String code, @RequestBody Country newCountry) {
        LOG.info(String.format("(putCountryByCode) Input code: %s", code));

        if (newCountry.getCode() != null) {
            return createErrorResponse(COUNTRY_CODE_PROVIDED_IN_BODY_MSG);
        }
        if (!repository.existsById(code)) {
            return createErrorResponse(ENTITY_DOES_NOT_EXIST_MSG);
        }

        Optional<Country> maybeCountry = repository.findById(code)
                .map(currentCountry -> {
                    currentCountry.setName(newCountry.getName());
                    return repository.save(currentCountry);
                });

        return maybeCountry.map(c -> ResponseEntity.ok(c.toString()))
                .orElseGet(() -> createErrorResponse(ENTITY_COULD_NOT_BE_UPDATED_MSG));
    }

    @DeleteMapping(value = "/{code}")
    @ApiOperation(value = "Deletes a country")
    public ResponseEntity<String> deleteCountry(@PathVariable("code") String code) {
        LOG.info(String.format("(deleteCountry) Input code: %s", code));

        Optional<Country> maybeCountry = repository.findById(code);

        if (maybeCountry.isEmpty()) {
            return createErrorResponse(ENTITY_DOES_NOT_EXIST_MSG);
        } else {
            if (existsInManufacturer(maybeCountry.get())) {
                return createErrorResponse(DEPENDENCY_EXISTS_MSG);
            }
            repository.deleteById(code);
            return ResponseEntity.noContent().build();
        }
    }
}
