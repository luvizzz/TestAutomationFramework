package app.runners;

import app.steps.CarSteps;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
@Narrative(text={"Car tests."})
public class WhenTestingCars {

    @Steps
    CarSteps carSteps;

    @Test
    public void testGetAllCars() {
        Response response = carSteps.getAllCars();

        System.out.println(response.asString());
    }

    @Test
    public void testGetCar1_expectId1() {
        final int CAR_ID = 1;

        Response response = carSteps.getCar(CAR_ID);

        carSteps.assertCardId(response.jsonPath(), CAR_ID);
    }

    @Test
    public void testGetCar1MultipleTimes_expectAllSuccess() {
        final int CAR_ID = 1;

        Response response = carSteps.getCar(CAR_ID);

        carSteps.assertCardId(response.jsonPath(), CAR_ID);

        response = carSteps.getCar(CAR_ID);

        carSteps.assertCardId(response.jsonPath(), CAR_ID);

        response = carSteps.getCar(CAR_ID);

        carSteps.assertCardId(response.jsonPath(), CAR_ID);
    }

    @Test
    public void testGetCar1000_expectId1000() { //to show test failure
        final int CAR_ID = 1000;

        Response response = carSteps.getCar(CAR_ID);

        carSteps.assertCardId(response.jsonPath(), CAR_ID);
    }

    @Test
    public void nestedStepsExample_expectSuccess() {
        carSteps.step1(1);
    }
}