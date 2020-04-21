package runners.country;

import base.BaseTest;
import domain.Country;
import domain.Manufacturer;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_METHOD_NOT_ALLOWED;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteTests extends BaseTest {
    private final static Logger LOG = Logger.getLogger(DeleteTests.class.getSimpleName());
    private String USED_COUNTRY_CODE;
    private long USED_MANUFACTURER_ID;

    @BeforeEach
    public void setup() {
    }

    @AfterEach
    public void teardown() {
        if(USED_MANUFACTURER_ID != 0) manufacturerSteps.deleteResponse(USED_MANUFACTURER_ID);
        if(USED_COUNTRY_CODE != null) countrySteps.deleteResponse(USED_COUNTRY_CODE);
    }

    @Test
    public void deleteValidCountry_expect204() {
        //GIVEN
        USED_COUNTRY_CODE = countrySteps.findInexistantCode();
        Country country = new Country();
        country.setCode(USED_COUNTRY_CODE);
        country.setName(countrySteps.randomName());
        Country expectedCountry = countrySteps.create(country);
        Assumptions.assumeTrue(expectedCountry.getCode().equals(country.getCode()));

        //WHEN
        Response response = countrySteps.delete(expectedCountry);

        //THEN
        assertEquals(response.getStatusCode(), SC_NO_CONTENT);

        //TODO:following steps shouldnt be here
        //WHEN
        response = countrySteps.readByCodeResponse(expectedCountry.getCode());

        //THEN
        assertEquals(response.getStatusCode(), SC_NO_CONTENT);
    }

    @Test
    public void deleteBoundedCountry_expect400() {
        //GIVEN
        USED_COUNTRY_CODE = countrySteps.findInexistantCode();
        Country country = new Country();
        country.setCode(USED_COUNTRY_CODE);
        country.setName(countrySteps.randomName());
        Country expectedCountry = countrySteps.create(country);
        Assumptions.assumeTrue(expectedCountry.getCode().equals(country.getCode()));

        //AND
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setCountryCode(expectedCountry.getCode());
        manufacturer.setName(manufacturerSteps.randomName());
        Manufacturer expectedManufacturer = manufacturerSteps.create(manufacturer);
        USED_MANUFACTURER_ID = expectedManufacturer.getId();
        Assumptions.assumeTrue(expectedCountry.getCode().equals(expectedManufacturer.getCountryCode()));

        //WHEN
        Response response = countrySteps.delete(expectedCountry);

        //THEN
        assertEquals(response.getStatusCode(), SC_BAD_REQUEST);

        //TODO:following steps shouldnt be here
        //WHEN
        response = countrySteps.readByCodeResponse(expectedCountry.getCode());

        //THEN
        assertEquals(response.getStatusCode(), SC_OK);
    }

    @Test
    public void deleteInexistantCountry_expect404() {
        //GIVEN
        String invalidId = countrySteps.findInexistantCode();

        //WHEN
        Response response = countrySteps.deleteResponse(invalidId);

        //THEN
        assertEquals(response.getStatusCode(), SC_NOT_FOUND);
    }

    @Test
    public void deleteUsingBlankId_expect405() {
        //WHEN
        Response response = countrySteps.deleteResponse("");

        //THEN
        assertEquals(response.getStatusCode(), SC_METHOD_NOT_ALLOWED);
    }
}
