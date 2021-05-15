package steps;

import base.BaseSteps;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class StockSteps extends BaseSteps {
    @Step
    public Response getStockByCarAndShop(int carId, int shopId) {
        return given()
                .baseUri(ROOT_URI)
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
