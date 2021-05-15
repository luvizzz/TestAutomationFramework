package steps;

import base.BaseSteps;
import domain.Manufacturer;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class ManufacturerSteps extends BaseSteps {
    @Step
    public Response getManufacturerById(long manufacturerId) {
        return given()
                .baseUri(ROOT_URI)
                .when()
                .basePath(String.format("/manufacturer/%d", manufacturerId))
                .log().all()
                .get()
                .then()
                .log().all()
                .extract()
                .response();
    }

    @Step
    public Manufacturer createManufacturer(Manufacturer manufacturer) {
        return given()
                .baseUri(ROOT_URI)
                .body(manufacturer)
                .when()
                .basePath("/manufacturer")
                .log().all()
                .post()
                .then()
                .log().all()
                .extract().as(Manufacturer.class);
    }
}
