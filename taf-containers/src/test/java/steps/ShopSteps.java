package steps;

import base.BaseSteps;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class ShopSteps extends BaseSteps {
    @Step
    public Response getShopById(int shop_id) {
        return given()
                .baseUri(ROOT_URI)
                .when()
                .basePath(String.format("/shop/%d", shop_id))
                .log().all()
                .get()
                .then()
                .log().all()
                .extract()
                .response();
    }
}
