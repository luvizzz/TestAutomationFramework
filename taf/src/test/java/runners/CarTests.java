package runners;

import base.BaseTest;
import domain.Car;
import domain.Country;
import domain.Manufacturer;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CarTests extends BaseTest {
    private final static Logger LOG = Logger.getLogger(CarTests.class.getSimpleName());
    final int MAX_COUNTRY_CODE_LENGTH = 2;
    long CAR_ID;
    long MANUFACTURER_ID;

    @BeforeAll
    static void setupAll() {
        LOG.info("- setupAll (@BeforeAll)");
    }

    @BeforeEach
    private void setup() {
        LOG.info("--- setup (BeforeEach)");

        Country country = new Country();
        country.setCode(newRandomString(MAX_COUNTRY_CODE_LENGTH));
        country.setName(UUID.randomUUID().toString());
        Country createdCountry = countrySteps.create(country);

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(UUID.randomUUID().toString());
        manufacturer.setCountryCode(createdCountry.getCode());
        Manufacturer createdManufacturer = manufacturerSteps.create(manufacturer);
        MANUFACTURER_ID = createdManufacturer.getId();

        Car car = new Car();
        car.setManufacturerId((long) createdManufacturer.getId());
        car.setName(UUID.randomUUID().toString());
        CAR_ID = carSteps.create(car).getId();
    }

    @AfterEach
    private void teardown() {
        LOG.info("--- teardown (AfterEach)");

    }

    @AfterAll
    static void teardownAll() {
        LOG.info("- teardownAll (@AfterAll)");

    }

    @Test
    public void getAllCars_fails() {
        //WHEN
        Response response = carSteps.readResponse(new HashMap<>());

        //THEN
        assertEquals(response.getStatusCode(), SC_OK);
        List<Long> allCarIds = response.jsonPath().getList("id");
        LOG.info("Searching list expecting " + CAR_ID);
        LOG.info(allCarIds.toString());
        assertTrue(allCarIds.contains(CAR_ID));
    }

    @Test
    public void getAllCars_passes() {
        //WHEN
        Response response = carSteps.readResponse(new HashMap<>());

        //THEN
        assertEquals(response.getStatusCode(), SC_OK);
        List<Long> allCarIds = response.jsonPath().getList("id", Long.class);
        LOG.info("Searching list expecting " + CAR_ID);
        LOG.info(allCarIds.toString());
        assertTrue(allCarIds.contains(CAR_ID));
    }

    @Test
    public void getCar1() {
        //WHEN
        JsonPath response = carSteps.readByIdResponse(CAR_ID).jsonPath();

        //THEN
        assertEquals((int) response.get("id"), CAR_ID);
    }

    @Test
    public void getCar1_expectManufacturer1() {
        //WHEN
        Manufacturer manufacturer = manufacturerSteps.readById(MANUFACTURER_ID);
        Response response = carSteps.readByIdResponse(CAR_ID);

        //THEN
        assertEquals((int) response.jsonPath().get("id"), CAR_ID);
        Car responseCar = response.getBody().as(Car.class);
        assertEquals(responseCar.getManufacturerId(), manufacturer.getId());
    }
}
