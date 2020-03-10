package runners;

import base.BaseTest;
import domain.Car;
import domain.Manufacturer;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FirstTest extends BaseTest {

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
}
