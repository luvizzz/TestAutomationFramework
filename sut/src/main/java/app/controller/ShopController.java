package app.controller;

import app.domain.Shop;
import app.resource.ShopRepository;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shop")
@Api(value="Shop Management Endpoints", tags = "Shop")
public class ShopController {

    private final ShopRepository repository;

    public ShopController(ShopRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Iterable<Shop> getShops() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Shop getShop(@PathVariable("id") long id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping
    public void postShop(@RequestBody Shop shop) {
        repository.save(shop);
    }

    @GetMapping("/")
    public List<Shop> getShopByName(@RequestParam String name) {
        return repository.findByName(name);
    }
}
