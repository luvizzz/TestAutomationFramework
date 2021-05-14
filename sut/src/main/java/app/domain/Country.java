package app.domain;

import app.utils.JsonProperty;
import app.utils.Utils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name="country")
public class Country {

    @Id
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
    public String toString() {
        return Utils.toJson(List.of(
                new JsonProperty("code", getCode()),
                new JsonProperty("name", getName())
        ));
    }
}
