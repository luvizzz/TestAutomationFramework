package runners.country;

import base.BaseTest;
import domain.Country;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateTests extends BaseTest {
    private final static Logger LOG = Logger.getLogger(CreateTests.class.getSimpleName());
    private List<String> USED_COUNTRY_CODES = new ArrayList<>();

    @BeforeEach
    public void setup() {
    }

    @AfterEach
    public void teardown() {
        USED_COUNTRY_CODES.forEach(c -> countrySteps.deleteResponse(c));
    }

    @Test
    public void createValidCountry_expect201() {
        //GIVEN
        String countryCode = countrySteps.findInexistantCode();
        USED_COUNTRY_CODES.add(countryCode);
        Country country = new Country();
        country.setCode(countryCode);
        country.setName(countrySteps.randomName());

        //WHEN
        Response response = countrySteps.createResponse(country);

        //THEN
        assertEquals(response.getStatusCode(), SC_CREATED);
        Country responseCountry = response.as(Country.class);
        assertEquals(responseCountry.getName(), country.getName());
        assertEquals(responseCountry.getCode(), country.getCode());
    }

    @Test
    public void createDuplicateCountry_expect400() {
        //GIVEN
        String countryCode = countrySteps.findInexistantCode();
        USED_COUNTRY_CODES.add(countryCode);
        Country country = new Country();
        country.setCode(countryCode);
        country.setName(countrySteps.randomName());

        //AND
        Country firstCountry = countrySteps.create(country);
        Assumptions.assumeTrue(firstCountry.equals(country));

        //WHEN
        Response response = countrySteps.createResponse(country);

        //THEN
        assertEquals(response.getStatusCode(), SC_BAD_REQUEST);
    }

    @Test
    public void createCountryUsingBlankCountryName_expect201() {
        //GIVEN
        String countryCode = countrySteps.findInexistantCode();
        USED_COUNTRY_CODES.add(countryCode);
        Country country = new Country();
        country.setCode(countryCode);
        country.setName("");

        //WHEN
        Response response = countrySteps.createResponse(country);

        //THEN
        assertEquals(response.getStatusCode(), SC_CREATED);
        Country responseCountry = response.as(Country.class);
        assertEquals(responseCountry.getName(), country.getName());
        assertEquals(responseCountry.getCode(), country.getCode());
    }

    @Test
    public void createCountryUsingShortCountryCode_expect400() {
        //GIVEN
        String countryCode = "A";
        USED_COUNTRY_CODES.add(countryCode);
        Country country = new Country();
        country.setCode(countryCode);
        country.setName(countrySteps.randomName());

        //WHEN
        Response response = countrySteps.createResponse(country);

        //THEN
        assertEquals(response.getStatusCode(), SC_BAD_REQUEST);
    }

    @Test
    public void createCountryUsingLargeCountryCode_expect400() {
        //GIVEN
        String countryCode = "AAA";
        USED_COUNTRY_CODES.add(countryCode);
        Country country = new Country();
        country.setCode(countryCode);
        country.setName(countrySteps.randomName());

        //WHEN
        Response response = countrySteps.createResponse(country);

        //THEN
        assertEquals(response.getStatusCode(), SC_BAD_REQUEST);
    }
}
