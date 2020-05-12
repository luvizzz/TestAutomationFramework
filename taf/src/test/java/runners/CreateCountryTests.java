package runners;

import base.BaseTest;
import domain.Country;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import steps.CountrySteps;

import java.util.UUID;
import java.util.logging.Logger;

import static org.apache.http.HttpStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class CreateCountryTests extends BaseTest {
    private final static Logger LOG = Logger.getLogger(CreateCountryTests.class.getSimpleName());
    private CountrySteps countrySteps = new CountrySteps();


    @Test
    public void postCountry() {
        Country country = new Country();
        country.setCode(newCountryCode());
        country.setName(UUID.randomUUID().toString());

        Response response = countrySteps.createCountryResponse(country);
        assertEquals(SC_CREATED, response.getStatusCode());
    }

    @Test
    public void postCountryNameWithoutUUIDFormat() {
        //GIVEN
        Country country = new Country();

        String countryCode = newCountryCode(); //valor esperado que seja persistido
        country.setCode(countryCode);

        String countryName = "test";
        country.setName(countryName);

        //WHEN
        Response response = countrySteps.createCountryResponse(country); //valor retornado pelo servico

        //THEN
        assertEquals(SC_CREATED, response.getStatusCode());
        assertEquals(countryCode, response.jsonPath().get("code"));
        assertEquals(countryName, response.jsonPath().get("name"));
    }

    @Test
    public void postCountryDuplicated() {
        //GIVEN
        Country country = new Country();

        String countryCode = newCountryCode(); //valor esperado que seja persistido
        country.setCode(countryCode);

        String countryName = "test";
        country.setName(countryName);
        Response firstCall = countrySteps.createCountryResponse(country);
        Assumptions.assumeTrue(firstCall.statusCode() == SC_CREATED, "Country not created with success.");

        //WHEN
        Response response = countrySteps.createCountryResponse(country);

        //THEN
        assertEquals(SC_BAD_REQUEST, response.getStatusCode());
        assertEquals("Entity already exists", response.jsonPath().get("message"));
    }

    @Test
    public void postCountryWithCodeMoreThan2Digits() {
        Country country = new Country();
        country.setCode("AAA");
        country.setName(UUID.randomUUID().toString());

        Response response = countrySteps.createCountryResponse(country);
        assertEquals(SC_BAD_REQUEST, response.getStatusCode());
    }

    @Test //Bug, we should see 400 but got 500
    public void postCountryWitNullCode() {
        Country country = new Country();
        country.setCode(null);
        country.setName(UUID.randomUUID().toString());

        Response response = countrySteps.createCountryResponse(country);
        assertEquals(SC_BAD_REQUEST, response.getStatusCode());
    }

    @Test //Bug, we should see 400 but got 500
    public void postCountryWithoutCode() {
        String body = "{\n" +
                "\"name\": \"da3fd399-16ac-4cff-b82a-6ed093ebcc01\"\n" +
                "}";

        Response response = countrySteps.createCountryResponse(body);
        assertEquals(SC_BAD_REQUEST, response.getStatusCode());
    }

    @Test //Bug, we should see 400 but got 500
    public void postCountryWithoutName() {
        Country country = new Country();
        country.setCode(newCountryCode());
        country.setName(null);

        Response response = countrySteps.createCountryResponse(country);
        assertEquals(SC_BAD_REQUEST, response.getStatusCode());
    }

    @Test //Bug, we should see 400 but got 500
    public void postCountryWithoutMandatoryFields() {
        Country country = new Country();
        country.setCode(null);
        country.setName(null);

        Response response = countrySteps.createCountryResponse(country);
        assertEquals(SC_BAD_REQUEST, response.getStatusCode());
    }
}
