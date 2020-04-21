package runners;

import base.BaseTest;
import domain.Car;
import domain.Shop;
import domain.Stock;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StockTests extends BaseTest {

    @Test
    public void getStock() {
        //GIVEN
        Random rand = new Random();
        long CAR_ID = rand.nextInt(12) + 1;
        long SHOP_ID = rand.nextInt(15) + 1;

        Car expectedCar = carSteps.readById(CAR_ID);
        Shop expectedShop = shopSteps.readById(SHOP_ID);

        //WHEN
        Map<String, Long> params = new HashMap<>();
        params.put("carId", CAR_ID);
        params.put("shopId", SHOP_ID);
        Response response = stockSteps.readResponse(params);

        //THEN
        Stock[] responseStockArray = response.getBody().as(Stock[].class);
        Stock responseStock = responseStockArray[0];
        assertEquals(responseStock.getCarId(), expectedCar.getId());
        assertEquals(responseStock.getShopId(), expectedShop.getId());
    }
}
