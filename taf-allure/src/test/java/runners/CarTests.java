package runners;

import base.BaseTest;
import domain.Car;
import domain.Country;
import domain.Manufacturer;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
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
        country.setCode(Utils.newCountryCode());
        country.setName(UUID.randomUUID().toString());
        Country createdCountry = countrySteps.createCountry(country);
        Assumptions.assumeTrue(createdCountry.getCode() != null, "Country was created");

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(UUID.randomUUID().toString());
        manufacturer.setCountryCode(createdCountry.getCode());
        Manufacturer createdManufacturer = manufacturerSteps.createManufacturer(manufacturer);
        MANUFACTURER_ID = createdManufacturer.getId();
        Assumptions.assumeTrue(createdManufacturer.getId() != 0);

        Car car = new Car();
        car.setManufacturerId(createdManufacturer.getId());
        car.setName(UUID.randomUUID().toString());
        Car createdCar = carSteps.createCar(car);
        CAR_ID = createdCar.getId();
        Assumptions.assumeTrue(CAR_ID != 0);
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
        //GIVEN
        Response setUp = carSteps.getAllCars();
        Assumptions.assumeTrue(setUp.getStatusCode() == SC_BAD_REQUEST, "GET all cars failed");

        //WHEN
        Response response = carSteps.getAllCars();

        //THEN
        assertEquals(response.getStatusCode(), SC_OK);
        List<Long> allCarIds = response.jsonPath().getList("id", Long.class);


        LOG.info("Valor de CAR_ID: " + CAR_ID);
        LOG.info("Lista de CAR_ID: " + allCarIds);

        assertTrue(allCarIds.contains(CAR_ID));
    }

    @Test
    public void getCar1() {
        //WHEN
        Response response = carSteps.getCarById(CAR_ID);

        //THEN
        assertEquals((int) response.jsonPath().get("id"), CAR_ID);
    }

    @Test
    public void getCar1_expectManufacturer1() {
        //WHEN
        Manufacturer manufacturer = manufacturerSteps.getManufacturerById(MANUFACTURER_ID).as(Manufacturer.class);
        Response response = carSteps.getCarById(CAR_ID);

        //THEN
        assertEquals((int) response.jsonPath().get("id"), CAR_ID);
        Car responseCar = response.getBody().as(Car.class);
        assertEquals(responseCar.getManufacturerId(), manufacturer.getId());
    }
}
