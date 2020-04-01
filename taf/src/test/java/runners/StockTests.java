package runners;

import base.BaseTest;
import domain.Car;
import domain.Shop;
import domain.Stock;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StockTests extends BaseTest {

    @Test
    public void getStock() {
        //GIVEN
        Random rand = new Random();
        int CAR_ID = rand.nextInt(12) + 1;
        int SHOP_ID = rand.nextInt(15) + 1;

        Car expectedCar = carSteps.getCarById(CAR_ID).as(Car.class);
        Shop expectedShop = shopSteps.getShopById(SHOP_ID).as(Shop.class);

        //WHEN
        Response response = stockSteps.getStockByCarAndShop(CAR_ID, SHOP_ID);

        //THEN
        Stock[] responseStockArray = response.getBody().as(Stock[].class);
        Stock responseStock = responseStockArray[0];
        assertEquals(responseStock.getId().getCarId(), CAR_ID);
        assertEquals(responseStock.getId().getShopId(), SHOP_ID);
        assertEquals(responseStock.getCar(), expectedCar);
        assertEquals(responseStock.getShop(), expectedShop);
    }
}
