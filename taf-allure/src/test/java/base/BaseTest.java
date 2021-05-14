package base;

import steps.CarSteps;
import steps.CountrySteps;
import steps.ManufacturerSteps;
import steps.ShopSteps;
import steps.StockSteps;

public class BaseTest {
    protected CountrySteps countrySteps = new CountrySteps();

    protected CarSteps carSteps = new CarSteps();

    protected ManufacturerSteps manufacturerSteps = new ManufacturerSteps();

    protected ShopSteps shopSteps = new ShopSteps();

    protected StockSteps stockSteps = new StockSteps();
}
