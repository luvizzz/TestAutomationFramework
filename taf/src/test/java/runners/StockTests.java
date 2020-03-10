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
        Random rand = new Random();
        int CAR_ID = rand.nextInt(12) + 1;
        int SHOP_ID = rand.nextInt(15) + 1;

        Car expectedCar =
                super.given()
                        .when()
                        .basePath(String.format("/car/%d", CAR_ID))
                        .log().all()
                        .get()
                        .then()
                        .log().all()
                        .extract()
                        .as(Car.class);

        Shop expectedShop =
                super.given()
                        .when()
                        .basePath(String.format("/shop/%d", SHOP_ID))
                        .log().all()
                        .get()
                        .then()
                        .log().all()
                        .extract()
                        .as(Shop.class);

        Response response =
                super.given()
                        .when()
                        .basePath("/stock")
                        .queryParam("car", CAR_ID)
                        .queryParam("shop", SHOP_ID)
                        .log().all()
                        .get()
                        .then()
                        .log().all()
                        .extract()
                        .response();


        Stock[] responseStockArray = response.getBody().as(Stock[].class);
        Stock responseStock = responseStockArray[0];
        assertEquals(responseStock.getId().getCarId(), CAR_ID);
        assertEquals(responseStock.getId().getShopId(), SHOP_ID);
        assertEquals(responseStock.getCar(), expectedCar);
        assertEquals(responseStock.getShop(), expectedShop);
        System.out.println("Stock amount: " + responseStock.getStock());
    }
}
