package base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class BaseSteps {
    protected final static String ROOT_URI = "http://localhost:8080";

    protected RequestSpecification given() {
        return RestAssured.given()
                .filter(new AllureRestAssured())
                .baseUri(ROOT_URI);
    }
}
