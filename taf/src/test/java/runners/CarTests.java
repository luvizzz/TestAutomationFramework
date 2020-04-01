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

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CarTests extends BaseTest {
    private final static Logger LOG = Logger.getLogger(CarTests.class.getSimpleName());
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
        country.setCode(newCountryCode());
        country.setName(UUID.randomUUID().toString());
        Country createdCountry = countrySteps.createCountry(country);

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(UUID.randomUUID().toString());
        manufacturer.setOriginCountry(createdCountry);
        Manufacturer createdManufacturer = manufacturerSteps.createManufacturer(manufacturer);
        MANUFACTURER_ID = createdManufacturer.getId();

        Car car = new Car();
        car.setManufacturer(createdManufacturer);
        car.setName(UUID.randomUUID().toString());
        CAR_ID = carSteps.createCar(car).getId();
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
    public void getAllCars() {
        //WHEN
        Response response = carSteps.getAllCars();

        //THEN
        assertEquals(response.getStatusCode(), SC_OK);
        List<Long> allCarIds = response.jsonPath().getList("id");
        assertTrue(allCarIds.contains(CAR_ID));
    }

    @Test
    public void getCar1() {
        //WHEN
        JsonPath response = carSteps.getCarById(CAR_ID).jsonPath();

        //THEN
        assertEquals((long) response.get("id"), CAR_ID);
    }

    @Test
    public void getCar1_expectManufacturer1() {
        //WHEN
        Manufacturer manufacturer = manufacturerSteps.getManufacturerById(MANUFACTURER_ID).as(Manufacturer.class);
        Response response = carSteps.getCarById(CAR_ID);

        //THEN
        assertEquals((long) response.jsonPath().get("id"), CAR_ID);
        Car responseCar = response.getBody().as(Car.class);
        assertEquals(responseCar.getManufacturer(), manufacturer);
    }
}
