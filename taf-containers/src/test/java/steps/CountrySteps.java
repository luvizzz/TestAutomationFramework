package steps;

import base.BaseSteps;
import domain.Country;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class CountrySteps extends BaseSteps {
    @Step("Create a country")
    public Response createCountryResponse(String body) {
        return given()
                .baseUri(ROOT_URI)
                .body(body)
                .when()
                .basePath("/country").log().all()
                .post()
                .then().log().all()
                .extract().response();
    }

    @Step("Create a country")
    public Response createCountryResponse(Country country) {
        return given()
                .baseUri(ROOT_URI)
                .body(country)
                .when()
                .basePath("/country").log().all()
                .post()
                .then().log().all()
                .extract().response();
    }

    @Step("Create a country")
    public Country createCountry(Country country) {
        Response response = createCountryResponse(country);
        assertEquals(SC_CREATED, response.getStatusCode());
        return response.as(Country.class);
    }

    @Step("Get all countries")
    public Response getAllAsResponse() {
        return given()
                .baseUri(ROOT_URI)
                .when()
                .basePath("/country")
                .log()
                .all()
                .get()
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Get all countries filtering by code {0}")
    public Response getAllFilteringByCodeAsResponse(String code) {
        return given()
                .baseUri(ROOT_URI)
                .when()
                .basePath("/country")
                .queryParam("code", code)
                .log()
                .all()
                .get()
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Get all countries filtering by name {0}")
    public Response getAllFilteringByNameAsResponse(String name) {
        return given()
                .baseUri(ROOT_URI)
                .when()
                .basePath("/country")
                .queryParam("name", name)
                .log()
                .all()
                .get()
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Get all countries filtering by code {0} and name {1}")
    public Response getAllFilteringByCodeAndNameAsResponse(String code, String name) {
        return given()
                .baseUri(ROOT_URI)
                .when()
                .basePath("/country")
                .queryParams("code", code, "name", name)
                .log()
                .all()
                .get()
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Get country by code {0}")
    public Response getByIdAsResponse(String id) {
        return given()
                .baseUri(ROOT_URI)
                .when()
                .basePath("/country")
                .log()
                .all()
                .get("/{id}", id)
                .then()
                .log().all()
                .extract().response();
    }
}
