package com.ozymern.springmicroservices.item.service;

import com.ozymern.spring.microservices.commons.models.entity.Product;
import com.ozymern.springmicroservices.item.domain.Item;


import java.util.List;

public interface IItemService {

    List<Item> findAll();
    Item findById(Long id, Integer count);

    Product save(Product product);

    Product update(Product product,Long id);

    void delete(Long id);

}
