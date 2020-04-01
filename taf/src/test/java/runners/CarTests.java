package runners;

import base.BaseTest;
import domain.Car;
import domain.Manufacturer;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.apache.http.HttpStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class CarTests extends BaseTest {

    @Test
    public void getAllCars() {
        Response response =
                super.given()
                        .when()
                        .basePath("/car")
                        .log().all()
                        .get()
                        .then()
                        .log().all()
                        .extract().response();

        System.out.println(response.asString());
    }

    @Test
    public void getCar1() {
        final int CAR_ID = 1;

        JsonPath response =
                super.given()
                        .when()
                        .basePath(String.format("/car/%d", CAR_ID))
                        .log().all()
                        .get()
                        .then()
                        .log().all()
                        .extract()
                        .response().jsonPath();

        assertEquals((int) response.get("id"), CAR_ID);
    }

    @Test
    public void getCar1_expectManufacturer1() {
        final int CAR_ID = 1;
        final int MANUFACTURER_ID = 1;

        Manufacturer manufacturer =
                super.given()
                        .when()
                        .basePath(String.format("/manufacturer/%d", MANUFACTURER_ID))
                        .log().all()
                        .get()
                        .then()
                        .log().all()
                        .extract()
                        .as(Manufacturer.class);

        Response response =
                super.given()
                        .when()
                        .basePath(String.format("/car/%d", CAR_ID))
                        .log().all()
                        .get()
                        .then()
                        .log().all()
                        .extract()
                        .response();

        assertEquals((int) response.jsonPath().get("id"), CAR_ID);
        Car responseCar = response.getBody().as(Car.class);
        System.out.println(String.format("Manufacturer 1: %n%s", responseCar.getManufacturer().toString()));
        System.out.println(String.format("Manufacturer 2: %n%s", manufacturer.toString()));
        assertEquals(responseCar.getManufacturer(), manufacturer);
    }

    @Test
    public void getCar2() {
        final int CAR_ID = 2;

        JsonPath response =
                super.given()
                        .when()
                        .basePath(String.format("/car/%d", CAR_ID))
                        .log().all()
                        .get()
                        .then()
                        .log().all()
                        .extract()
                        .response().jsonPath();

        assertEquals((int) response.get("id"), CAR_ID);
    }

    @Test
    public void getCar3() {
        final int CAR_ID = 3;

        JsonPath response =
                super.given()
                        .when()
                        .basePath(String.format("/car/%d", CAR_ID))
                        .log().all()
                        .get()
                        .then()
                        .log().all()
                        .extract()
                        .response().jsonPath();

        assertEquals((int) response.get("id"), CAR_ID);
    }

    @Test
    public void getCarWithLetters_expectFailure() {
        String CAR_ID = "abc";
        String EXPECTED_MESSAGE = "Failed to convert value of type 'java.lang.String' to required type 'long'; nested exception is java.lang.NumberFormatException: For input string: \"%s\"";

        Response response =
                super.given()
                        .when()
                        .basePath(String.format("/car/%s", CAR_ID))
                        .log().all()
                        .get()
                        .then()
                        .log().all()
                        .extract()
                        .response();

        assertEquals(SC_BAD_REQUEST, response.getStatusCode());
        assertEquals(String.format(EXPECTED_MESSAGE, CAR_ID), response.jsonPath().getString("message"));
    }

    @Test
    public void postCar_ExpectSuccess() {

        Random rand = new Random();
        int CAR_ID = rand.nextInt(500) + 1;
        int MANUFACTURER_ID = 1;

        Manufacturer responseMan =
                super.given()
                        .when()
                        .basePath(String.format("/manufacturer/%d", MANUFACTURER_ID))
                        .log().all()
                        .get()
                        .then()
                        .log().all()
                        .extract().as(Manufacturer.class);

        Car carro = new Car();
        carro.setId(CAR_ID);
        carro.setName("Vectra");
        carro.setManufacturer(responseMan);

        Response response =
                super.given()
                        .contentType("application/json")
                        .when()
                            .body(carro)
                            .basePath("/car")
                            .log().all()
                            .post()
                        .then()
                            .log().all()
                            .extract()
                            .response();

        assertEquals(SC_OK, response.getStatusCode());
    }
}
