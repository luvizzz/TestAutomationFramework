package base;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import java.util.Random;

public class BaseSteps {
    protected final static String ROOT_URI = "http://localhost:8080";
    protected String basePath = "/";
    final int MAX_NAME_LENGTH = 10;

    public String randomName() {
        return BaseTest.newRandomString(MAX_NAME_LENGTH);
    }

    public int randomId(int maxId) {
        Random rand = new Random();
        return rand.nextInt(maxId) + 1;
    }

    protected static RequestSpecification given() {
        return RestAssured.given().baseUri(ROOT_URI)
                .contentType("application/json")
                .header("content-type", "application/json; charset=utf-8");
    }
}
