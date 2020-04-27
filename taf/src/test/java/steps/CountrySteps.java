package steps;

import base.BaseSteps;
import domain.Country;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class CountrySteps extends BaseSteps {

    public Country createCountry(Country country) {
            return super.given()
                    .body(country)
                    .when()
                    .basePath("/country")
                    .log().all()
                    .post()
                    .then()
                    .statusCode(201)
                    .contentType(ContentType.JSON)
                    .log().all()
                    .extract().as(Country.class);
    }

    public Response createCountryResponse(Country country) {
        return super.given()
                .body(country)
                .when()
                .basePath("/country")
                .log()
                .all()
                .post();
    }
}
