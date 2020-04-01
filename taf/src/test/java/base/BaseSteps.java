package base;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BaseSteps {
    protected final static String ROOT_URI = "http://localhost:8080";

    protected RequestSpecification given() {
        return RestAssured.given().baseUri(ROOT_URI)
                .contentType(ContentType.JSON)
                //.contentType("application/json")
                //.header("Accept","*/*")
        ;
    }
}
