package steps;

import base.BaseSteps;
import base.BaseTest;
import domain.Country;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CountrySteps extends BaseSteps {
    final int MAX_COUNTRY_CODE_LENGTH = 2;

    public CountrySteps() {
        basePath = "/country";
    }

    public Country create(Country country) {
        return createResponse(country).as(Country.class);
    }

    public Response createResponse(Country country) {
        return given()
                .when()
                .basePath(basePath)
                .body(country)
                .log().all()
                .post()
                .then()
                .log().all()
                .extract().response();
    }

    public Country readByCode(String code) {
        return readByCodeResponse(code).as(Country.class);
    }

    public Response readByCodeResponse(String code) {
        return super.given()
                .when()
                .basePath(basePath)
                .log().all()
                .get("/{code}", code)
                .then()
                .log().all()
                .extract().response();
    }

    public List<Country> read(Map<String, String> queryParams) {
        return readResponse(queryParams).jsonPath().getList(".", Country.class);
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

    public Country update(Country country) {
        return updateResponse(country).as(Country.class);
    }

    public Response updateResponse(Country country) {
        Country updatedCountry = new Country();
        updatedCountry.setName(country.getName());
        return given()
                .when()
                .basePath(basePath)
                .body(updatedCountry)
                .log().all()
                .put("/{code}", country.getCode())
                .then()
                .log().all()
                .extract().response();
    }

    public Response delete(Country country) {
        return deleteResponse(country.getCode());
    }

    public Response deleteResponse(String code) {
        return super.given()
                .when()
                .basePath(basePath)
                .log().all()
                .delete("/{code}", code)
                .then()
                .log().all()
                .extract().response();
    }

    public String findInexistantCode() {
        List<String> existingIds = read(new HashMap<String, String>()).stream()
                .map(Country::getCode)
                .collect(Collectors.toList());

        String randomId = "";
        do{
           randomId = BaseTest.newRandomString(MAX_COUNTRY_CODE_LENGTH);
        } while(existingIds.contains(randomId));
        return randomId;
    }
}
