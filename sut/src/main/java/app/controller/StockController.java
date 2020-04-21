package app.controller;

import app.domain.Stock;
import app.domain.StockKey;
import app.resource.CarRepository;
import app.resource.ShopRepository;
import app.resource.StockRepository;
import app.utils.ErrorMessage;
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
@Api(value = "Stock Management Endpoints", tags = "Stock", description = "Manage your Stocks")
public class StockController extends BaseController {
    private static final Logger LOG = Logger.getLogger(StockController.class.getSimpleName());
    private static final ErrorMessage STOCK_MUST_BE_POSITIVE_MSG = new ErrorMessage(HttpStatus.BAD_REQUEST, "Stock must be positive");

    @Autowired
    StockRepository repository;

    @Autowired
    CarRepository cars;

    @Autowired
    ShopRepository shops;

    private Predicate<Stock> byCarId(Long carId) {
        return s -> s.getCarId() == carId;
    }

    private Predicate<Stock> byShopId(Long shopId) {
        return s -> s.getShopId() == shopId;
    }

    public StockController(StockRepository repository) {
        this.repository = repository;
        path = "/stock";
    }

    @GetMapping
    @ApiOperation(value = "Gets stocks for specified car and shop")
    public ResponseEntity<List<Stock>> getStocks(
            @RequestParam(value = "carId", required = false) Long carId,
            @RequestParam(value = "shopId", required = false) Long shopId) {
        LOG.info(String.format("(getStocks) Input car id: %d; Input shop id: %s",
                (carId == null) ? 0 : carId,
                (shopId == null) ? 0 : shopId));
        
        Iterable<Stock> stocks = repository.findAll();

        List<Stock> filteredStocks = StreamSupport.stream(stocks.spliterator(), false)
                .filter((carId != null) ? byCarId(carId) : x -> true)
                .filter((shopId != null) ? byShopId(shopId) : x -> true)
                .collect(Collectors.toList());

        return ResponseEntity.ok(filteredStocks);
    }

    @PostMapping
    @ApiOperation(value = "Creates a stock")
    public ResponseEntity<String> postStock(
            @RequestBody Stock newStock) {
        LOG.info(String.format("(postStock) Input stock: %s", newStock));

        StockKey key = new StockKey();
        key.setCarId(newStock.getCarId());
        key.setShopId(newStock.getShopId());

        if (repository.findById(key).isPresent()) {
            return createErrorResponse(ENTITY_ALREADY_EXISTS_MSG);
        }

        if (!cars.existsById(newStock.getCarId())) {
            return createErrorResponse(INVALID_DEPENDENCY_MSG);
        }

        if (!shops.existsById(newStock.getShopId())) {
            return createErrorResponse(INVALID_DEPENDENCY_MSG);
        }

        if(newStock.getStock() < 0) {
            return createErrorResponse(STOCK_MUST_BE_POSITIVE_MSG);
        }

        newStock.setId(key);
        repository.save(newStock);

        if (repository.findById(key).isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(newStock.toString());
        } else {
            return createErrorResponse(ENTITY_COULD_NOT_BE_CREATED_MSG);
        }
    }

    @PutMapping
    @ApiOperation(value = "Updates a stock")
    public ResponseEntity<String> putStock(
            @RequestBody Stock updatedStock) {
        LOG.info(String.format("(putStock) Input stock: %s",
                updatedStock.toString()));

        StockKey key = new StockKey();
        key.setCarId(updatedStock.getCarId());
        key.setShopId(updatedStock.getShopId());

        if (!repository.findById(key).isPresent()) {
            return createErrorResponse(ENTITY_DOES_NOT_EXIST_MSG);
        }

        if (!cars.existsById(updatedStock.getCarId())) {
            return createErrorResponse(INVALID_DEPENDENCY_MSG);
        }

        if (!shops.existsById(updatedStock.getShopId())) {
            return createErrorResponse(INVALID_DEPENDENCY_MSG);
        }

        if(updatedStock.getStock() < 0) {
            return createErrorResponse(STOCK_MUST_BE_POSITIVE_MSG);
        }

        updatedStock.setId(key);
        repository.save(updatedStock);

        if (repository.findById(key).isPresent()) {
            return ResponseEntity.accepted().body(updatedStock.toString());
        } else {
            return createErrorResponse(ENTITY_COULD_NOT_BE_UPDATED_MSG);
        }
    }

    @DeleteMapping
    @ApiOperation(value = "Deletes a stock")
    public ResponseEntity<Object> deleteStock(
            @RequestParam(value = "carId", required = true) Long carId,
            @RequestParam(value = "shopId", required = true) Long shopId) {
        LOG.info(String.format("(deleteStock) Input car id: %d; Input shop id: %s",
                (carId == null) ? 0 : carId,
                (shopId == null) ? 0 : shopId));

        StockKey key = new StockKey();
        key.setShopId(shopId);
        key.setCarId(carId);

        Optional<Stock> maybeStock = repository.findById(key);

        repository.deleteById(key);
        return ResponseEntity.noContent().build();
    }
}
