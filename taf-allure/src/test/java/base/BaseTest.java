package base;

import io.qameta.allure.Step;
import steps.CarSteps;
import steps.CountrySteps;
import steps.ManufacturerSteps;
import steps.ShopSteps;
import steps.StockSteps;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaseTest {
    protected Random rand = new Random();

    protected CountrySteps countrySteps = new CountrySteps();

    protected CarSteps carSteps = new CarSteps();

    protected ManufacturerSteps manufacturerSteps = new ManufacturerSteps();

    protected ShopSteps shopSteps = new ShopSteps();

    protected StockSteps stockSteps = new StockSteps();

    protected String newCountryCode() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 2;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @Step("Asserting that response status code is {0}")
    protected void assertResponseCode(int expected, int actual) {
        assertEquals(expected, actual);
    }
}
