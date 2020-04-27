package runners;

import base.BaseTest;
import domain.Country;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import steps.CountrySteps;

import java.util.UUID;
import java.util.logging.Logger;

import static org.apache.http.HttpStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class CountryTests extends BaseTest {
    private final static Logger LOG = Logger.getLogger(CountryTests.class.getSimpleName());
    private Country country = new Country();
    private CountrySteps countrySteps = new CountrySteps();


    @Test
    public void postCountry() {
        country.setCode(newCountryCode());
        country.setName(UUID.randomUUID().toString());

        Response response = countrySteps.createCountryResponse(country);
        assertEquals(SC_CREATED, response.getStatusCode());
    }

    @Test
    public void postCountryNameWithoutUUIDFormat() {
        country.setCode(newCountryCode());
        country.setName("test");

        Response response = countrySteps.createCountryResponse(country);
        assertEquals(SC_CREATED, response.getStatusCode());
    }

    @Test
    public void postCountryDuplicated() {
        country.setCode("ke");
        country.setName("57e2750a-f5e2-4d1e-9673-54eb28d44b36");

        Response response = countrySteps.createCountryResponse(country);
        assertEquals(SC_BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void postCountryWithCodeMoreThan2Digits() {
        country.setCode("AAA");
        country.setName(UUID.randomUUID().toString());

        Response response = countrySteps.createCountryResponse(country);
        assertEquals(SC_BAD_REQUEST, response.getStatusCode());
    }

    @Test //Bug, we should see 400 but got 500
    public void postCountryWithoutCode() {
        country.setCode(null);
        country.setName(UUID.randomUUID().toString());

        Response response = countrySteps.createCountryResponse(country);
        assertEquals(SC_BAD_REQUEST, response.getStatusCode());
    }

    @Test //Bug, we should see 400 but got 500
    public void postCountryWithoutName() {
        country.setCode(newCountryCode());
        country.setName(null);

        Response response = countrySteps.createCountryResponse(country);
        assertEquals(SC_BAD_REQUEST, response.getStatusCode());
    }

    @Test //Bug, we should see 400 but got 500
    public void postCountryWithoutMandatoryFields() {
        country.setCode(null);
        country.setName(null);

        Response response = countrySteps.createCountryResponse(country);
        assertEquals(SC_BAD_REQUEST, response.getStatusCode());
    }
}
