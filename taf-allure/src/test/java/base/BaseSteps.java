package base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BaseSteps {
    protected final static String ROOT_URI = "http://localhost:8080";

    protected RequestSpecification given() {
        RequestSpecification spec = new RequestSpecBuilder()
                .addFilter(new AllureRestAssured())
                .build();

        return RestAssured.given().baseUri(ROOT_URI)
                .contentType(ContentType.JSON)
                .spec(spec)
        ;
    }
}
