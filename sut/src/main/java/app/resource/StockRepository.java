package app.resource;

import app.domain.Car;
import app.domain.Stock;
import app.domain.StockKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends CrudRepository<Stock, StockKey> {
    List<Stock> findByCar(Car carKey);
}
