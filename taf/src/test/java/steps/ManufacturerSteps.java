package steps;

import base.BaseSteps;
import domain.Manufacturer;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

public class ManufacturerSteps extends BaseSteps {

    public ManufacturerSteps() {
        basePath = "/manufacturer";
    }

    public Manufacturer create(Manufacturer manufacturer) {
        return createResponse(manufacturer).as(Manufacturer.class);
    }

    public Response createResponse(Manufacturer manufacturer) {
        return given()
                .when()
                .basePath(basePath)
                .body(manufacturer)
                .log().all()
                .post()
                .then()
                .log().all()
                .extract().response();
    }

    public Manufacturer readById(long id) {
        return readByIdResponse(id).as(Manufacturer.class);
    }

    public Response readByIdResponse(long id) {
        return super.given()
                .when()
                .basePath(basePath)
                .log().all()
                .get("/{id}", id)
                .then()
                .log().all()
                .extract().response();
    }

    public List<Manufacturer> read(Map<String, String> queryParams) {
        return readResponse(queryParams).jsonPath().getList(".", Manufacturer.class);
    }

    public Response readResponse(Map<String, String> queryParams) {
        return super.given()
                .when()
                .basePath(basePath)
                .queryParams(queryParams)
                .log().all()
                .get()
                .then()
                .log().all()
                .extract().response();
    }

    public Manufacturer update(Manufacturer manufacturer) {
        return updateResponse(manufacturer).as(Manufacturer.class);
    }

    public Response updateResponse(Manufacturer manufacturer) {
        Manufacturer updatedManufacturer = new Manufacturer();
        updatedManufacturer.setName(manufacturer.getName());
        updatedManufacturer.setCountryCode(manufacturer.getCountryCode());
        return given()
                .when()
                .basePath(basePath)
                .body(updatedManufacturer)
                .log().all()
                .put("/{id}", manufacturer.getId())
                .then()
                .log().all()
                .extract().response();
    }

    public Response delete(Manufacturer manufacturer) {
        return deleteResponse(manufacturer.getId());
    }

    public Response deleteResponse(long id) {
        return super.given()
                .when()
                .basePath(basePath)
                .log().all()
                .delete("/{id}", id)
                .then()
                .log().all()
                .extract().response();
    }
}
