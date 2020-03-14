package app.controller;

import app.domain.Stock;
import app.resource.StockRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/stock")
@SuppressWarnings("deprecation")
@Api(value="Stock Management Endpoints", tags = "Stock", description="Manage your Stocks")
public class StockController {

    private final StockRepository repository;
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
    @ApiOperation(value = "Get stock amounts for specified cars and shops")
    public Iterable<Stock> getShopsByCarAndShop(
            @SuppressWarnings("OptionalUsedAsFieldOrParameterType") @RequestParam("car") Optional<Long> maybeCarId,
            @SuppressWarnings("OptionalUsedAsFieldOrParameterType") @RequestParam("shop") Optional<Long> maybeShopId) {
        Iterable<Stock> stocks = repository.findAll();
        return () -> StreamSupport.stream(stocks.spliterator(), false)
                .filter(maybeCarId.<Predicate<? super Stock>>map(this::byCarId).orElseGet(() -> x -> true))
                .filter((maybeShopId.isPresent()) ? byShopId(maybeShopId.get()) : x -> true)
                .iterator();
    }
}
