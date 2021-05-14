package base;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaseSteps {
    private final static Logger LOG = Logger.getLogger(MockedDb.class.getSimpleName());

    protected String ROOT_URI;

    public void setRootUri(String uri) {
        LOG.info(String.format("Configuring %s using uri='%s'", this.getClass().getSimpleName(), uri));

        ROOT_URI = uri;
    }

    protected RequestSpecification given() {
        RequestSpecification spec = new RequestSpecBuilder()
                .addFilter(new AllureRestAssured())
                .build();

        return RestAssured.given()
                .contentType(ContentType.JSON)
                .spec(spec);
    }

    @Step("Asserting that response status code is {0}")
    public void assertResponseCode(int expected, int actual) {
        assertEquals(expected, actual);
    }
}
