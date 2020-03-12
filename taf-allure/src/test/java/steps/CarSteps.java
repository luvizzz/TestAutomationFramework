package steps;

import base.BaseSteps;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CarSteps extends BaseSteps {

    @Step("Get All cars")
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

    @Step("Get car with id {0}")
    public JsonPath getCar(int car_id) {
        return super.given()
                        .when()
                        .basePath(String.format("/car/%d", car_id))
                        .log().all()
                        .get()
                        .then()
                        .log().all()
                        .extract()
                        .response().jsonPath();
    }
}
