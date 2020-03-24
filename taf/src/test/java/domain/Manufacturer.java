package domain;

import java.util.Objects;

public class Manufacturer {
    private long id;

    private String name;

    private Country originCountry;

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

    public Country getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(Country originCountry) {
        this.originCountry = originCountry;
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
        Manufacturer other = (Manufacturer) that;
        return id == other.getId();
    }

    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s, Country: %s",
                getId(),
                getName(),
                getOriginCountry().getName());
    }
}
