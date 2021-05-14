package base;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaseSteps {
    protected final static String ROOT_URI = "http://localhost:8080";

    protected RequestSpecification given() {
        RequestSpecification spec = new RequestSpecBuilder()
                .addFilter(new AllureRestAssured())
                .build();

        return RestAssured.given()
                .baseUri(ROOT_URI)
                .contentType(ContentType.JSON)
                .spec(spec)
        ;
    }

    @Step("Asserting that response status code is {0}")
    public void assertResponseCode(int expected, int actual) {
        assertEquals(expected, actual);
    }
}
