package steps;

import base.BaseSteps;
import domain.Country;
import io.restassured.response.Response;

import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class CountrySteps extends BaseSteps {

    public Response createCountryResponse(String body) {
        return super.given()
                .body(body)
                .when()
                .basePath("/country")
                .log()
                .all()
                .post()
                .then()
                .log().all()
                .extract().response();
    }

    public Response createCountryResponse(Country country) {
        return super.given()
                .body(country)
                .when()
                .basePath("/country")
                .log()
                .all()
                .post()
                .then()
                .log().all()
                .extract().response();
    }

    public Country createCountry(Country country) {
        Response response = createCountryResponse(country);
        assertEquals(SC_CREATED, response.getStatusCode());
        return response.as(Country.class);
    }

    public Response getAllAsResponse() {
        return super.given()
                .when()
                .basePath("/country")
                .log()
                .all()
                .get()
                .then()
                .log().all()
                .extract().response();
    }

    public Response getAllFilteringByCodeAsResponse(String code) {
        return super.given()
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
    public Response getAllFilteringByNameAsResponse(String name) {
        return super.given()
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
    public Response getAllFilteringByCodeAndNameAsResponse(String code, String name) {
        return super.given()
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

    public Response getByIdAsResponse(String id) {
        return super.given()
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
