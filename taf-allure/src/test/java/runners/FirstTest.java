package runners;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import steps.CarSteps;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FirstTest {

    private CarSteps carSteps = new CarSteps();

    @Test
    public void getAllCars() {
        Response response = carSteps.getAllCars();

        System.out.println(response.asString());
    }

    @Test
    public void getCar1() {
        final int CAR_ID = 1;

        JsonPath response = carSteps.getCar(CAR_ID);

        assertEquals((int) response.get("id"), CAR_ID);
    }
}
