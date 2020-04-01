package steps;

import base.BaseSteps;
import domain.Country;
import io.restassured.http.ContentType;

public class CountrySteps extends BaseSteps {

    public Country createCountry(Country country) {
            return super.given()
                    .body(country)
                    .when()
                    .basePath("/country")
                    .log().all()
                    .post()
                    .then()
                    .contentType(ContentType.JSON)
                    .log().all()
                    .extract().as(Country.class);
    }
}
