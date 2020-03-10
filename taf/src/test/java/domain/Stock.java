package domain;

public class Stock {
    private Car car;

    private Shop shop;

    private int stock;

    private StockKey id;

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
