package runners;

import base.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class FirstTest extends BaseTest {

    @Test
    public void getAllCars() {
        Response response =
                super.given()
                        .when()
                        .basePath("/car")
                        .log().all()
                        .get()
                        .then()
                        .log().all()
                        .extract().response();

        System.out.println(response.asString());
    }

    @Test
    public void getCar1() {
        final int CAR_ID = 1;

        JsonPath response =
                super.given()
                        .when()
                        .basePath(String.format("/car/%d", CAR_ID))
                        .log().all()
                        .get()
                        .then()
                        .log().all()
                        .extract()
                        .response().jsonPath();

        assertEquals((int) response.get("id"), CAR_ID);
    }
}
