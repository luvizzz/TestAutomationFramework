package app.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Arrays;

@Entity
public class Country {

    @Id
    private char[] code;

    private String name;

    public char[] getCode() {
        return code;
    }

    public void setCode(char[] code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%n- CountryCode: %s%n- CountryName: %s", Arrays.toString(this.getCode()), this.getName());
    }
}
