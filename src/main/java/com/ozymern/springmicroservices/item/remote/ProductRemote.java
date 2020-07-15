package com.ozymern.springmicroservices.item.remote;



import com.ozymern.spring.microservices.commons.models.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//@FeignClient(name = "product", url = "http://localhost:8083/api/v1") con ribbon se desacopla la url
//con ribbon
@FeignClient(name = "product")
public interface ProductRemote {

    @GetMapping("/products")
    List<Product> finAll();

    @GetMapping("/products/{id}")
    Product showProduct(@PathVariable Long id);

    @PostMapping("/products")
    Product createProduct(@RequestBody Product product);

    @PutMapping("/products/{id}")
    Product updateProduct(@RequestBody Product product, @PathVariable Long id);


    @DeleteMapping("/products/{id}")
    void deleteProduct(@PathVariable Long id);


}
