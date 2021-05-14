package steps;

import base.BaseSteps;
import io.restassured.response.Response;

public class ShopSteps extends BaseSteps {
    public Response getShopById(int shop_id) {
        return super.given()
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
