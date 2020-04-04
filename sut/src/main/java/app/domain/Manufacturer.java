package app.domain;


import app.utils.JsonProperty;
import app.utils.Utils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name="manufacturer", schema="sut")
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToOne
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

    public String getCountryCode() {
        return originCountry != null ? originCountry.getCode() : null;
    }

    public void setCountryCode(String countryCode) {
        this.originCountry = new Country();
        this.originCountry.setCode(countryCode);
    }

    @Override
    public String toString() {
        return Utils.toJson(List.of(
                new JsonProperty("id", getId()),
                new JsonProperty("name", getName()),
                new JsonProperty("countryCode", getCountryCode())
        ));
    }
}
