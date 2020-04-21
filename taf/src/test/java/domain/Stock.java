package domain;

import java.util.Objects;

public class Stock{
    private long carId;

    private long shopId;

    private int stock;

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(String.format("%d%d", getCarId(), getShopId()));
    }

    @Override
    public boolean equals(Object that) {
        if (this == that)
            return true;
        if (that == null)
            return false;
        if (getClass() != that.getClass())
            return false;
        Stock other = (Stock) that;
        return getCarId() == other.getCarId() && getShopId() == other.getShopId();
    }

    @Override
    public String toString() {
        return String.format("Car Id: %s, Shop Id: %s, Stock: %s",
                getCarId(),
                getShopId(),
                getStock());
    }
}
