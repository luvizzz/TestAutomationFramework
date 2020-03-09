package app.controller;

import app.domain.Stock;
import app.resource.StockRepository;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/stock")
@Api(value="Stock Management Endpoints", tags = "Stock")
public class StockController {

    private final StockRepository repository;
    private Predicate<Stock> byCarId(Optional<Long> car_id) {
        return car_id.<Predicate<Stock>>map(aLong -> s -> aLong == s.getCar().getId()).orElseGet(() -> x -> true);
    };
    private Predicate<Stock> byShopId(Optional<Long> shop_id) {
        return shop_id.<Predicate<Stock>>map(aLong -> s -> aLong == s.getShop().getId()).orElseGet(() -> x -> true);
    };

    public StockController(StockRepository repository) {
        this.repository = repository;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @GetMapping
    public Iterable<Stock> getShopsByCarAndShop(
            @RequestParam("car") Optional<Long> car_id,
            @RequestParam("shop") Optional<Long> shop_id) {
        Iterable<Stock> stocks = repository.findAll();
        return () -> StreamSupport.stream(stocks.spliterator(), false)
                .filter(byCarId(car_id))
                .filter(byShopId(shop_id))
                .iterator();
    }
}
