package app.controller;

import app.domain.Shop;
import app.resource.ShopRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@SuppressWarnings("deprecation")
@Api(value="Shop Management Endpoints", tags = "Shop", description="Manage your Shops")
public class ShopController {

    private final ShopRepository repository;

    public ShopController(ShopRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @ApiOperation(value = "Get all shops")
    public Iterable<Shop> getShops() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get shops by specified id")
    public Shop getShop(@PathVariable("id") long id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping
    @ApiOperation(value = "Create a new shop")
    public void postShop(@RequestBody Shop shop) {
        repository.save(shop);
    }

    @GetMapping("/")
    @ApiOperation(value = "Get shops by specified names")
    public List<Shop> getShopByName(@RequestParam String name) {
        return repository.findByName(name);
    }
}
