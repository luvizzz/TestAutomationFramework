package domain;

import java.util.Objects;

public class Car {
    private long id;

    private String name;

    private Long manufacturerId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that)
            return true;
        if (that == null)
            return false;
        if (getClass() != that.getClass())
            return false;
        Car other = (Car) that;
        return id == other.getId();
    }

    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s, Manufacturer: %s",
                getId(),
                getName(),
                getManufacturerId());
    }
}
