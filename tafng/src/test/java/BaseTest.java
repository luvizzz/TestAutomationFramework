import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.util.logging.Logger;

public class BaseTest {
    protected final static Logger LOGGER = Logger.getLogger(BaseTest.class.getName());
    protected final static String ROOT_URI = "http://localhost:8080";

    @BeforeClass
    public void onBeforeClass() {
        LOGGER.info("Running tests from --> " + getClass().getName() + "class.");
    }

    @BeforeMethod
    public void onBeforeMethod(Method method) {
        LOGGER.info("Starting --> " + method.getName() + " test method");
    }

    protected static RequestSpecification given() {
        return RestAssured.given().baseUri((ROOT_URI));
    }

    protected Response retrieveGetResponse(String path) {
        LOGGER.info("GET REQUEST --> ");
        return given()
                .when()
                .basePath(path)
                .get()
                .then()
                .log().all()
                .extract().response();
    }
}
