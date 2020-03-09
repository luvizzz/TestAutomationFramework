package app.resource;

import app.domain.Stock;
import app.domain.StockKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends CrudRepository<Stock, StockKey> {
}
