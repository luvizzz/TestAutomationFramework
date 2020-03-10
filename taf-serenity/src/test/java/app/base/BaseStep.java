package app.base;

import io.restassured.specification.RequestSpecification;

import static net.serenitybdd.rest.SerenityRest.rest;

public class BaseStep {
    protected final static String ROOT_URI = "http://localhost:8080";

    protected RequestSpecification given() {
        return rest().given().baseUri(ROOT_URI);
    }
}
