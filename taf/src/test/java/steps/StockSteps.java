package steps;

import base.BaseSteps;
import io.restassured.response.Response;

public class StockSteps extends BaseSteps {
    public Response getStockByCarAndShop(int carId, int shopId) {
        return super.given()
                .when()
                .basePath("/stock")
                .queryParam("carId", carId)
                .queryParam("shopId", shopId)
                .log().all()
                .get()
                .then()
                .log().all()
                .extract()
                .response();
    }
}
