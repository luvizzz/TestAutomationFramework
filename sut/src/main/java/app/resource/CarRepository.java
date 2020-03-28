package app.resource;

import app.domain.Car;
import app.domain.Manufacturer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends CrudRepository<Car, Long> {

    List<Car> findByName(String name);

    List<Car> findByManufacturer(Manufacturer manufacturer);
}
