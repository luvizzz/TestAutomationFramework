package steps;

import base.BaseSteps;
import domain.Car;
import io.restassured.response.Response;

public class CarSteps extends BaseSteps {
    public Response getAllCars() {
        return super.given()
                        .when()
                        .basePath("/car")
                        .log().all()
                        .get()
                        .then()
                        .log().all()
                        .extract().response();
    }

    public Response getCarById(long carId) {
        return super.given()
                .when()
                .basePath(String.format("/car/%d", carId))
                .log().all()
                .get()
                .then()
                .log().all()
                .extract()
                .response();
    }

    public Car createCar(Car car) {
        return super.given()
                .body(car)
                .when()
                .basePath("/car")
                .log().all()
                .post()
                .then()
                .log().all()
                .extract().as(Car.class);
    }
}
