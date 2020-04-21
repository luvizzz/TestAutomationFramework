package steps;

import base.BaseSteps;
import domain.Car;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

public class CarSteps extends BaseSteps {

    public CarSteps() {
        basePath = "/car";
    }

    public Car create(Car car) {
        return createResponse(car).as(Car.class);
    }

    public Response createResponse(Car car) {
        return given()
                .when()
                .basePath(basePath)
                .body(car)
                .log().all()
                .post()
                .then()
                .log().all()
                .extract().response();
    }

    public Car readById(long id) {
        return readByIdResponse(id).as(Car.class);
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

    public List<Car> read(Map<String, String> queryParams) {
        return readResponse(queryParams).jsonPath().getList(".", Car.class);
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

    public Car update(Car car) {
        return updateResponse(car).as(Car.class);
    }

    public Response updateResponse(Car car) {
        Car updatedCar = new Car();
        updatedCar.setName(car.getName());
        updatedCar.setManufacturerId(car.getManufacturerId());
        return given()
                .when()
                .basePath(basePath)
                .body(updatedCar)
                .log().all()
                .put("/{id}", car.getId())
                .then()
                .log().all()
                .extract().response();
    }

    public Response delete(Car car) {
        return deleteResponse(car.getId());
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
