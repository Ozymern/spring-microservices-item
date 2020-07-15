package com.ozymern.springmicroservices.item.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.ozymern.spring.microservices.commons.models.entity.Product;
import com.ozymern.springmicroservices.item.domain.Item;
import com.ozymern.springmicroservices.item.service.IItemService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//RefreshScope permite actualizar los componente que injectamos configuraciones con @Value y Environment
@RefreshScope
@RestController
//@RequestMapping(ItemController.URI_BASE)
public class ItemController {

    @Value("${config.txt}")
    private String configTxt;

    private Environment env;

    public static final String URI_BASE = "/api/v1";

    private IItemService iItemService;

    public ItemController(Environment env, IItemService iItemService) {
        this.env = env;
        this.iItemService = iItemService;
    }

    @GetMapping("/config")
    public ResponseEntity<Map<String, String>> getConfig() {

        Map<String, String> json = new HashMap<>();
        json.put("text", configTxt);

        if (env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
            json.put("name", env.getProperty("config.name"));
            json.put("email", env.getProperty("config.email"));
        }

        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @GetMapping("/items")
    public List<Item> findAll() {

        return iItemService.findAll();
    }

    // El @HystrixCommandes proporcionado por una biblioteca contrib Netflix llamada " javanica " . Spring Cloud envuelve automáticamente los beans Spring con esa anotación en un proxy que está conectado al disyuntor Hystrix. El disyuntor calcula cuándo abrir y cerrar el circuito y qué hacer en caso de falla.
    @HystrixCommand(fallbackMethod = "alternativeMethod")
    @GetMapping("/items/{id}/count/{count}")
    public Item details(@PathVariable Long id, @PathVariable Integer count) {

        return iItemService.findById(id, count);
    }

    //el metodo alternativo del fallback, debe tener los mismos parametros
    public Item alternativeMethod(Long id, Integer count) {

        //devuelve item por defecto en caso del error
        Product p = new Product();
        p.setName("Test alternativeMethod");
        p.setPrice(5000.0);
        Item i = new Item();
        i.setCount(count);
        i.setProduct(p);

        return i;

    }


    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {

        return new ResponseEntity<>(iItemService.save(product), HttpStatus.CREATED);

    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable Long id) {


        return new ResponseEntity<>(iItemService.update(product, id), HttpStatus.CREATED);

    }

    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {

        iItemService.delete(id);
    }


}
