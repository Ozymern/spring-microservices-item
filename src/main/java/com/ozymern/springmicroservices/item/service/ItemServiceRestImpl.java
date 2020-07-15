package com.ozymern.springmicroservices.item.service;

import com.ozymern.spring.microservices.commons.models.entity.Product;
import com.ozymern.springmicroservices.item.domain.Item;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Primary
@AllArgsConstructor
public class ItemServiceRestImpl implements IItemService {


    private RestTemplate restTemplate;


    @Override
    public List<Item> findAll() {
        // List<Product> product = Arrays.asList(restTemplate.getForObject("http://localhost:8083/api/v1/products", Product[].class));
        List<Product> product = Arrays.asList(restTemplate.getForObject("http://product/products", Product[].class));
        return product.stream().map(x -> new Item(x, 1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer count) {
        Map<String, Long> pathVariable = new HashMap<>();
        pathVariable.put("id", id);
        // Product product=restTemplate.getForObject("http://localhost:8083/api/v1/products/{id}", Product.class,pathVariable);
        Product product = restTemplate.getForObject("http://product/products/{id}", Product.class, pathVariable);
        return new Item(product, count);
    }

    @Override
    public Product save(Product product) {
        HttpEntity<Product> body = new HttpEntity<>(product);
        //exchange intercambiar
        ResponseEntity<Product> response = restTemplate.exchange("http://product/products", HttpMethod.POST, body, Product.class);

        return response.getBody();
    }

    @Override
    public Product update(Product product, Long id) {
        HttpEntity<Product> body = new HttpEntity<>(product);
        Map<String, Long> pathVariable = new HashMap<>();
        pathVariable.put("id", id);
        //exchange intercambiar
        ResponseEntity<Product> response = restTemplate.exchange("http://product/products/{id}", HttpMethod.PUT, body, Product.class, pathVariable);
        return response.getBody();
    }

    @Override
    public void delete(Long id) {
        Map<String, Long> pathVariable = new HashMap<>();
        pathVariable.put("id", id);
        restTemplate.delete("http://product/products/{id}",pathVariable);
    }
}
