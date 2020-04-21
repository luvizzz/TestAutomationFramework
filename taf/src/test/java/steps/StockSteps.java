package steps;

import base.BaseSteps;
import domain.Stock;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockSteps extends BaseSteps {

    public StockSteps() {
        basePath = "/stock";
    }

    public Stock create(Stock stock) {
        return createResponse(stock).as(Stock.class);
    }

    public Response createResponse(Stock stock) {
        return given()
                .when()
                .basePath(basePath)
                .body(stock)
                .log().all()
                .post()
                .then()
                .log().all()
                .extract().response();
    }

    public List<Stock> read(Map<String, Long> queryParams) {
        return readResponse(queryParams).jsonPath().getList(".", Stock.class);
    }

    public Response readResponse(Map<String, Long> queryParams) {
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

    public Stock update(Stock stock) {
        return updateResponse(stock).as(Stock.class);
    }

    public Response updateResponse(Stock stock) {
        return given()
                .when()
                .basePath(basePath)
                .body(stock)
                .log().all()
                .put()
                .then()
                .log().all()
                .extract().response();
    }

    public Response delete(Stock stock) {
        Map<String, Long> params = new HashMap<>();
        params.put("shopId", stock.getShopId());
        params.put("carId", stock.getCarId());
        return deleteResponse(params);
    }

    public Response deleteResponse(Map<String, Long> queryParams) {
        return super.given()
                .when()
                .queryParams(queryParams)
                .basePath(basePath)
                .log().all()
                .delete()
                .then()
                .log().all()
                .extract().response();
    }
}
