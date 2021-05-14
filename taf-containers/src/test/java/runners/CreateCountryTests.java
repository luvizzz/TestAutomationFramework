package runners;

import base.BaseTest;
import domain.Country;
import io.qameta.allure.Issue;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.util.UUID;
import java.util.logging.Logger;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class CreateCountryTests extends BaseTest {
    private final static Logger LOG = Logger.getLogger(CreateCountryTests.class.getSimpleName());

    @Test
    public void postCountry() {
        //GIVEN
        Country country = new Country();
        country.setCode(Utils.newCountryCode());
        country.setName(UUID.randomUUID().toString());

        //WHEN
        Response response = countrySteps.createCountryResponse(country);

        //THEN
        countrySteps.assertResponseCode(SC_CREATED, response.getStatusCode());
    }

    @Test
    public void postCountryNameWithoutUUIDFormat() {
        //GIVEN
        Country country = new Country();

        String countryCode = Utils.newCountryCode();
        country.setCode(countryCode);

        String countryName = "test";
        country.setName(countryName);

        //WHEN
        Response response = countrySteps.createCountryResponse(country);

        //THEN
        countrySteps.assertResponseCode(SC_CREATED, response.getStatusCode());
        assertEquals(countryCode, response.jsonPath().get("code"));
        assertEquals(countryName, response.jsonPath().get("name"));
    }

    @Test
    public void postCountryDuplicated() {
        //GIVEN
        Country country = new Country();

        String countryCode = Utils.newCountryCode();
        country.setCode(countryCode);

        String countryName = "test";
        country.setName(countryName);
        Response firstCall = countrySteps.createCountryResponse(country);
        Assumptions.assumeTrue(firstCall.statusCode() == SC_CREATED, "Country not created with success.");

        //WHEN
        Response response = countrySteps.createCountryResponse(country);

        //THEN
        countrySteps.assertResponseCode(SC_BAD_REQUEST, response.getStatusCode());
        assertEquals("Entity already exists", response.jsonPath().get("message"));
    }

    @Test
    public void postCountryWithCodeMoreThan2Digits() {
        Country country = new Country();
        country.setCode("AAA");
        country.setName(UUID.randomUUID().toString());

        Response response = countrySteps.createCountryResponse(country);
        countrySteps.assertResponseCode(SC_BAD_REQUEST, response.getStatusCode());
    }

    @Test //Bug, we should see 400 but got 500
    public void postCountryWitNullCode() {
        Country country = new Country();
        country.setCode(null);
        country.setName(UUID.randomUUID().toString());

        Response response = countrySteps.createCountryResponse(country);
        countrySteps.assertResponseCode(SC_BAD_REQUEST, response.getStatusCode());
    }

    @Test //Bug, we should see 400 but got 500
    @Issue("Jira-123")
    public void postCountryWithoutCode() {
        String body = "{\n" +
                "\"name\": \"da3fd399-16ac-4cff-b82a-6ed093ebcc01\"\n" +
                "}";

        Response response = countrySteps.createCountryResponse(body);
        countrySteps.assertResponseCode(SC_BAD_REQUEST, response.getStatusCode());
    }

    @Test //Bug, we should see 400 but got 500
    public void postCountryWithoutName() {
        Country country = new Country();
        country.setCode(Utils.newCountryCode());
        country.setName(null);

        Response response = countrySteps.createCountryResponse(country);
        countrySteps.assertResponseCode(SC_BAD_REQUEST, response.getStatusCode());
    }

    @Test //Bug, we should see 400 but got 500
    public void postCountryWithoutMandatoryFields() {
        Country country = new Country();
        country.setCode(null);
        country.setName(null);

        Response response = countrySteps.createCountryResponse(country);
        countrySteps.assertResponseCode(SC_BAD_REQUEST, response.getStatusCode());
    }
}
