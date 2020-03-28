package app.controller;

import app.domain.Car;
import app.domain.Shop;
import app.domain.Stock;
import app.domain.StockKey;
import app.resource.CarRepository;
import app.resource.ShopRepository;
import app.resource.StockRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/stock")
@SuppressWarnings("deprecation")
@Api(value="Stock Management Endpoints", tags = "Stock", description="Manage your Stocks")
public class StockController {
    private final static Logger LOG = Logger.getLogger(StockController.class.getSimpleName());

    @Autowired
    StockRepository repository;

    @Autowired
    CarRepository cars;

    @Autowired
    ShopRepository shops;

    private Predicate<Stock> byCarId(Long carId) {
        return s -> s.getCar().getId() == carId;
    }

    private Predicate<Stock> byShopId(Long shopId) {
        return s -> s.getShop().getId() == shopId;
    }

    public StockController(StockRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @ApiOperation(value = "Gets stocks for specified car and shop")
    public ResponseEntity<List<Stock>> getStocks(
            @RequestParam(value = "carId", required = false) Long carId,
            @RequestParam(value = "shopId", required = false) Long shopId) {
        LOG.info(String.format("(getStocks) Input car id: %d; Input shop id: %s",
                (carId == null)? 0 : carId,
                (shopId == null)? 0 : shopId));
        Iterable<Stock> stocks = repository.findAll();

        List<Stock> filteredStocks = StreamSupport.stream(stocks.spliterator(), false)
                .filter((carId != null) ? byCarId(carId) : x -> true)
                .filter((shopId != null) ? byShopId(shopId) : x -> true)
                .collect(Collectors.toList());

        if(filteredStocks.size() > 0) {
            return ResponseEntity.ok(filteredStocks);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    @ApiOperation(value = "Creates a stock")
    public ResponseEntity<String> postStock(
            @RequestParam(value = "carId", required = true) Long carId,
            @RequestParam(value = "shopId", required = true) Long shopId,
            @RequestBody Stock newStock) {
        LOG.info(String.format("(postStock) Input car id: %d; Input shop id: %s",
                (carId == null)? 0 : carId,
                (shopId == null)? 0 : shopId));

        if(carId == null || shopId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide both car id and shop id.");
        }

        if(newStock.getCar() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please do not provide car in body.");
        }

        if(newStock.getShop() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please do not provide shop in body.");
        }

        if(newStock.getId() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please do not provide ids in body.");
        }

        StockKey key = new StockKey();
        key.setCarId(carId);
        key.setShopId(shopId);

        if(repository.findById(key).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Stock with carId \"%d\" and shopId \"%d\" already exists.", carId, shopId));
        }

        if(!cars.existsById(carId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Car with id \"%d\" has not been found.", carId));
        }

        if(!shops.existsById(shopId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Shop with id \"%d\" has not been found.", shopId));
        }

        Stock stock = new Stock();
        stock.setId(key);
        stock.setStock(newStock.getStock());
        repository.save(stock);

        if(repository.findById(key).isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(String.format("Stock with car id \"%d\" and shop id \"%d\" created.", carId, shopId));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Stock could not be created.");
        }
    }

    @PutMapping
    @ApiOperation(value = "Updates a stock")
    public ResponseEntity<String> putStock(
            @RequestParam(value = "carId", required = true) Long carId,
            @RequestParam(value = "shopId", required = true) Long shopId,
            @RequestBody Stock newStock) {
        LOG.info(String.format("(putStock) Input car id: %d; Input shop id: %s",
                (carId == null)? 0 : carId,
                (shopId == null)? 0 : shopId));

        if(carId == null || shopId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide both car id and shop id.");
        }

        if(newStock.getCar() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please do not provide car in body.");
        }

        if(newStock.getShop() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please do not provide shop in body.");
        }

        if(newStock.getId() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please do not provide ids in body.");
        }

        StockKey key = new StockKey();
        key.setCarId(carId);
        key.setShopId(shopId);

        Optional<Car> maybeCar = cars.findById(carId);
        Optional<Shop> maybeShop = shops.findById(shopId);

        if(repository.findById(key).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Stock with carId \"%d\" and shopId \"%d\" was not found.", carId, shopId));
        }

        if(maybeCar.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Car with id \"%d\" has not been found.", carId));
        }

        if(maybeShop.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Shop with id \"%d\" has not been found.", shopId));
        }

        Stock stock = new Stock();
        stock.setId(key);
        stock.setCar(maybeCar.get());
        stock.setShop(maybeShop.get());
        stock.setStock(newStock.getStock());
        repository.save(stock);

        if(repository.findById(key).isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(String.format("Stock with car id \"%d\" and shop id \"%d\" updated.", carId, shopId));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Stock could not be created.");
        }
    }

    @DeleteMapping
    @ApiOperation(value = "Deletes a stock")
    public ResponseEntity<String> deleteStock(
            @RequestParam(value = "carId", required = true) Long carId,
            @RequestParam(value = "shopId", required = true) Long shopId) {
        LOG.info(String.format("(deleteStock) Input car id: %d; Input shop id: %s",
                (carId == null)? 0 : carId,
                (shopId == null)? 0 : shopId));
        Iterable<Stock> stocks = repository.findAll();

        List<Stock> filteredStocks = StreamSupport.stream(stocks.spliterator(), false)
                .filter((carId != null) ? byCarId(carId) : x -> true)
                .filter((shopId != null) ? byShopId(shopId) : x -> true)
                .collect(Collectors.toList());

        if(filteredStocks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Stock with carId \"%d\" and shopId \"%d\" does not exist.", carId, shopId));
        } else {
            if(filteredStocks.size() == 1) {
                repository.deleteById(filteredStocks.get(0).getId());
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Stock could not be deleted.");
    }
}
