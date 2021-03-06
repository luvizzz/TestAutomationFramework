package app.controller;

import app.domain.Shop;
import app.resource.ShopRepository;
import app.resource.StockRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/shop")
@SuppressWarnings("deprecation")
@Api(value="Shop Management Endpoints", tags = "Shop", description="Manage your Shops")
public class ShopController extends BaseController {
    private final static Logger LOG = Logger.getLogger(ManufacturerController.class.getSimpleName());

    @Autowired
    ShopRepository repository;

    @Autowired
    StockRepository stock;

    private Predicate<Shop> byId(long id) {
        return c -> c.getId() == id;
    }

    private Predicate<Shop> byName(String name) {
        return c -> c.getName().equals(name);
    }

    private Boolean existsInStock(Shop shop) {
        return stock.findByShop(shop).size() > 0;
    }

    public ShopController(ShopRepository repository) {
        this.repository = repository;
        path = "/shop";
    }

    @GetMapping
    @ApiOperation(value = "Gets shops for specified ids and names")
    public ResponseEntity<List<Shop>> getShops(
            @SuppressWarnings("OptionalUsedAsFieldOrParameterType") @RequestParam("id") Optional<Long> maybeId,
            @SuppressWarnings("OptionalUsedAsFieldOrParameterType") @RequestParam("name") Optional<String> maybeName) {
        LOG.info(String.format("(getShops) Input id: %d; Input name: %s",
                maybeId.orElse((long) 0),
                maybeName.orElse("null")));

        Iterable<Shop> shops = repository.findAll();

        List<Shop> filteredShops = StreamSupport.stream(shops.spliterator(), false)
                .filter(maybeId.isPresent() ? byId(maybeId.get()) : x -> true)
                .filter(maybeName.<Predicate<? super Shop>> map (this::byName).orElseGet(() -> x -> true))
                .collect(Collectors.toList());

        if(filteredShops.size() > 0) {
            return ResponseEntity.ok(filteredShops);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value="/{id}")
    @ApiOperation(value = "Gets shop by id")
    public ResponseEntity<Shop> getShopById(@PathVariable("id") Long id) {
        LOG.info(String.format("(getShopById) Input id: %d", id));
        Optional<Shop> shop = repository.findById(id);

        return shop.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    @ApiOperation(value = "Creates a shop")
    public ResponseEntity<String> postShop(@RequestBody Shop shop) {
        LOG.info(String.format("(postShop) Input shop: %s", shop.toString()));

        if(shop.getId() != 0) {
            return createErrorResponse(ID_PROVIDED_IN_BODY_MSG);
        }

        repository.save(shop);

        URI location = URI.create(String.format("/shop/%d", shop.getId()));
        return ResponseEntity.created(location).body(shop.toString());
    }

    @PutMapping(value="/{id}")
    @ApiOperation(value = "Updates shop with specified id")
    public ResponseEntity<String> putShopById(@PathVariable("id") Long id, @RequestBody Shop newShop) {
        LOG.info(String.format("(putShopById) Input id: %d", id));

        if(newShop.getId() != 0) {
            return createErrorResponse(ID_PROVIDED_IN_BODY_MSG);
        }
        if(!repository.existsById(id)) {
            return createErrorResponse(ENTITY_DOES_NOT_EXIST_MSG);
        }

        Optional<Shop> maybeShop = repository.findById(id)
                .map(currentShop -> {
                    currentShop.setName(newShop.getName());
                    return repository.save(currentShop);
                });

        return maybeShop.map(s -> ResponseEntity.ok(s.toString()))
                .orElseGet(() -> createErrorResponse(ENTITY_COULD_NOT_BE_UPDATED_MSG));
    }

    @DeleteMapping(value="/{id}")
    @ApiOperation(value = "Deletes a shop")
    public ResponseEntity<String> deleteShop(@PathVariable("id") Long id) {
        LOG.info(String.format("(deleteShop) Input id: %d", id));

        Optional<Shop> maybeShop = repository.findById(id);

        if(maybeShop.isEmpty()) {
            return createErrorResponse(ENTITY_DOES_NOT_EXIST_MSG);
        } else {
            if(existsInStock(maybeShop.get())) {
                return createErrorResponse(DEPENDENCY_EXISTS_MSG);
            }
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
    }
}
