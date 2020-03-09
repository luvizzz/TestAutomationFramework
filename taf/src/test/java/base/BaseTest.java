package base;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class BaseTest {
    protected final static String ROOT_URI = "http://localhost:8080";

    protected RequestSpecification given() {
        return RestAssured.given().baseUri(ROOT_URI);
    }
}
