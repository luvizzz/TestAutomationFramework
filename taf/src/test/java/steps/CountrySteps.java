package steps;

import base.BaseSteps;
import domain.Country;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class CountrySteps extends BaseSteps {

    public Response createCountryResponse(Country country) {
        return super.given()
                .body(country)
                .when()
                .basePath("/country")
                .log()
                .all()
                .post();
    }

    public Country createCountry(Country country) {
        return createCountryResponse(country).as(Country.class);
    }
}
