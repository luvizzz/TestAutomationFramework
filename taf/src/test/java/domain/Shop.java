package domain;

import java.util.Objects;

public class Shop {
    private long id;

    private String name;

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
        Shop other = (Shop) that;
        return id == other.getId();
    }

    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s",
                getId(),
                getName());
    }
}
