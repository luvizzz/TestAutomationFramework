package app.steps;

import app.base.BaseStep;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;

import static junit.framework.TestCase.assertEquals;

public class CarSteps extends BaseStep {

    @Step("Get /car/{0}")
    public Response getCar(int id) {
        return super.given()
                        .when()
                        .basePath(String.format("/car/%d", id))
                        .log().all()
                        .get()
                        .then()
                        .log().all()
                        .extract()
                        .response();
    }

    @Step("Get all cars")
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

    @Step("Assert id is {1}")
    public void assertCardId(JsonPath jsonPath, int car_id) {
        assertEquals((int) jsonPath.get("id"), car_id);
    }

    @Step("Step1 using id {0}")
    public void step1(int id) {
        step2(id);
    }

    @Step("Step2 using id {0}")
    public void step2(int id) {
        step3(id);
    }

    @Step("Step3 using id {0}")
    public void step3(int id) {
        getCar(id);
    }
}