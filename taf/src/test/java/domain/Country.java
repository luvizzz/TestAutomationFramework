package domain;

import java.util.Objects;

public class Country{
    private String code;

    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that)
            return true;
        if (that == null)
            return false;
        if (getClass() != that.getClass())
            return false;
        Country other = (Country) that;
        return code.equals(other.getCode());
    }

    @Override
    public String toString() {
        return String.format("Code: %s, Name: %s",
                getCode(),
                getName());
    }
}
