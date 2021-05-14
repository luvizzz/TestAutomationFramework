package app.domain;

import app.utils.JsonProperty;
import app.utils.Utils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToOne
    private Manufacturer manufacturer;

    @OneToMany(mappedBy = "car")
    Set<Stock> stock;

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

    public long getManufacturerId() {
        return manufacturer != null ? manufacturer.getId() : null;
    }

    public void setManufacturerId(long manufacturerId) {
        this.manufacturer = new Manufacturer();
        this.manufacturer.setId(manufacturerId);
    }

    @Override
    public String toString() {
        return Utils.toJson(List.of(
                new JsonProperty("id", getId()),
                new JsonProperty("name", getName()),
                new JsonProperty("manufacturerId", getManufacturerId())
        ));
    }
}
