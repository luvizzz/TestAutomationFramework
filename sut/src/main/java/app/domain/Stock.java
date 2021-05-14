package app.domain;

import app.utils.JsonProperty;
import app.utils.Utils;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name="stock")
public class Stock {

    @EmbeddedId
    StockKey id;

    @ManyToOne
    @MapsId("car_id")
    @JoinColumn(name = "car_id")
    Car car;

    @ManyToOne
    @MapsId("shop_id")
    @JoinColumn(name = "shop_id")
    Shop shop;

    int stock;

    public long getCarId() {
        return car.getId();
    }

    public void setCarId(long carId) {
        this.car = new Car();
        this.car.setId(carId);
    }
    public long getShopId() {
        return shop.getId();
    }

    public void setShopId(long shopId) {
        this.shop = new Shop();
        this.shop.setId(shopId);
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }


    public void setId(StockKey key) {
        this.id = key;
    }

    @Override
    public String toString() {
        return Utils.toJson(List.of(
                new JsonProperty("carId", getCarId()),
                new JsonProperty("shopId", getShopId()),
                new JsonProperty("stock", getStock())
        ));
    }
}
