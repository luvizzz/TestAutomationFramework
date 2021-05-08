package runners;

import base.BaseTest;
import domain.Country;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.logging.Logger;

import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetCountryTests extends BaseTest {
    private static final Logger LOG = Logger.getLogger(GetCountryTests.class.getSimpleName());

    @Test
    public void getAllCountries() {
        //GIVEN
        Country country = new Country();

        String countryCode = newCountryCode();
        country.setCode(countryCode);

        String countryName = "test2";
        country.setName(countryName);

        //AND
        Country expectedCountry = countrySteps.createCountry(country);

        //WHEN
        Response response = countrySteps.getAllAsResponse();

        //THEN
        assertEquals(SC_OK, response.getStatusCode());
        List<Country> retrievedCountries = response.jsonPath().getList(".", Country.class);

        boolean countryExists = false;
        for(Country item : retrievedCountries) {
            if(item.getCode().equals(expectedCountry.getCode())) {
                assertEquals(item.getName(), expectedCountry.getName());
                countryExists = true;
            }
        }
        assertTrue(countryExists,
                String.format("Country with code %s and name %s not found in list.",
                        expectedCountry.getCode(),
                        expectedCountry.getName()));
    }

    @Test
    public void getAllCountriesFilteringByName() {
        //WHEN
        Response response = countrySteps.getAllFilteringByNameAsResponse("test");

        //THEN
        assertEquals(SC_OK, response.getStatusCode());
        List<Country> retrievedCountries = response.jsonPath().getList(".", Country.class);

        LOG.info("all retrieved data: ");
        retrievedCountries.forEach(c -> LOG.info(String.format("code: %s, name: %s", c.getCode(), c.getName())));
    }


    @Test
    public void getAllCountriesByCode() {
        //WHEN
        Response response = countrySteps.getByIdAsResponse("BR");

        //THEN
        assertEquals(SC_OK, response.getStatusCode());
        Country retrievedCountry = response.as(Country.class);

        LOG.info(String.format("First code: %s, name: %s", retrievedCountry.getCode(), retrievedCountry.getName()));

        //WHEN
        response = countrySteps.getAllFilteringByCodeAsResponse("BR");

        //THEN
        assertEquals(SC_OK, response.getStatusCode());
        List<Country> retrievedCountries = response.jsonPath().getList(".", Country.class);

        LOG.info("all retrieved data: ");
        retrievedCountries.forEach(c -> LOG.info(String.format("code: %s, name: %s", c.getCode(), c.getName())));
    }

    @Test
    public void getAllCountriesByCodeAndName() {
        //WHEN
        Response response = countrySteps.getAllFilteringByCodeAndNameAsResponse("BR", "Brazil");

        //THEN
        assertEquals(SC_OK, response.getStatusCode());
        List<Country> retrievedCountries = response.jsonPath().getList(".", Country.class);

        LOG.info("all retrieved data: ");
        retrievedCountries.forEach(c -> LOG.info(String.format("code: %s, name: %s", c.getCode(), c.getName())));
    }

    @Test
    public void getCountriesNotFoundByCodeAndNameInvalidName() {
        //WHEN
        Response response = countrySteps.getAllFilteringByCodeAndNameAsResponse("BR", "XXXXXX");

        //THEN
        assertEquals(SC_NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void getCountriesNotFoundByCodeAndNameInvalidCode() {
        //WHEN
        Response response = countrySteps.getAllFilteringByCodeAndNameAsResponse("XX", "Brazil");

        //THEN
        assertEquals(SC_NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void getCountriesNotFoundByCode() {
        //WHEN
        Response response = countrySteps.getAllFilteringByCodeAsResponse("XX");

        //THEN
        assertEquals(SC_NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void getCountriesNotFoundByName() {
        //WHEN
        Response response = countrySteps.getAllFilteringByNameAsResponse("XXXXXXX");

        //THEN
        assertEquals(SC_NO_CONTENT, response.getStatusCode());
    }
}
