package steps;

import base.BaseSteps;
import domain.Shop;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

public class ShopSteps extends BaseSteps {

    public ShopSteps() {
        basePath = "/shop";
    }

    public Shop create(Shop shop) {
        return createResponse(shop).as(Shop.class);
    }

    public Response createResponse(Shop shop) {
        return given()
                .when()
                .basePath(basePath)
                .body(shop)
                .log().all()
                .post()
                .then()
                .log().all()
                .extract().response();
    }

    public Shop readById(long id) {
        return readByIdResponse(id).as(Shop.class);
    }

    public Response readByIdResponse(long id) {
        return super.given()
                .when()
                .basePath(basePath)
                .log().all()
                .get("/{id}", id)
                .then()
                .log().all()
                .extract().response();
    }

    public List<Shop> read(Map<String, String> queryParams) {
        return readResponse(queryParams).jsonPath().getList(".", Shop.class);
    }

    public Response readResponse(Map<String, String> queryParams) {
        return super.given()
                .when()
                .basePath(basePath)
                .queryParams(queryParams)
                .log().all()
                .get()
                .then()
                .log().all()
                .extract().response();
    }

    public Shop update(Shop shop) {
        return updateResponse(shop).as(Shop.class);
    }

    public Response updateResponse(Shop shop) {
        Shop updatedShop = new Shop();
        updatedShop.setName(shop.getName());
        return given()
                .when()
                .basePath(basePath)
                .body(updatedShop)
                .log().all()
                .put("/{id}", shop.getId())
                .then()
                .log().all()
                .extract().response();
    }

    public Response delete(Shop shop) {
        return deleteResponse(shop.getId());
    }

    public Response deleteResponse(long id) {
        return super.given()
                .when()
                .basePath(basePath)
                .log().all()
                .delete("/{id}", id)
                .then()
                .log().all()
                .extract().response();
    }
}
