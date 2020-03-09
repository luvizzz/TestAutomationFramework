package app.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
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

    public StockKey getId() {
        return id;
    }

    public void setId(StockKey id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
