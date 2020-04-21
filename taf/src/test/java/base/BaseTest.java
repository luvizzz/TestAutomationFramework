package base;

import steps.CarSteps;
import steps.CountrySteps;
import steps.ManufacturerSteps;
import steps.ShopSteps;
import steps.StockSteps;

import java.util.Random;

import static java.util.UUID.randomUUID;

public class BaseTest {
    protected Random rand = new Random();

    protected CountrySteps countrySteps = new CountrySteps();

    protected CarSteps carSteps = new CarSteps();

    protected ManufacturerSteps manufacturerSteps = new ManufacturerSteps();

    protected ShopSteps shopSteps = new ShopSteps();

    protected StockSteps stockSteps = new StockSteps();

    public static String newRandomString(int targetStringLength) {
        String candidate = randomUUID().toString();

        if (candidate.length() < targetStringLength) {
            return candidate;
        }
        return candidate.substring(candidate.length() - 1 - targetStringLength, candidate.length() - 1);
    }
}
