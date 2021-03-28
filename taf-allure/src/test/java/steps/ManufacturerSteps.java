package steps;

import base.BaseSteps;
import domain.Manufacturer;
import io.restassured.response.Response;

public class ManufacturerSteps extends BaseSteps {
    public Response getManufacturerById(long manufacturerId) {
        return super.given()
                .when()
                .basePath(String.format("/manufacturer/%d", manufacturerId))
                .log().all()
                .get()
                .then()
                .log().all()
                .extract()
                .response();
    }

    public Manufacturer createManufacturer(Manufacturer manufacturer) {
        return super.given()
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
