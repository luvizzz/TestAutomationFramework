package steps;

import base.BaseSteps;
import domain.Car;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class CarSteps extends BaseSteps {

    @Step
    public Response getAllCars() {
        return given()
                .baseUri(ROOT_URI)
                .when()
                .basePath("/car")
                .log().all()
                .get()
                .then()
                .log().all()
                .extract().response();
    }

    @Step
    public Response getCarById(long carId) {
        return given()
                .baseUri(ROOT_URI)
                .when()
                .basePath(String.format("/car/%d", carId))
                .log().all()
                .get()
                .then()
                .log().all()
                .extract()
                .response();
    }

    @Step
    public Car createCar(Car car) {
        return given()
                .baseUri(ROOT_URI)
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
