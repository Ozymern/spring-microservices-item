package com.ozymern.springmicroservices.item.service;

import com.ozymern.spring.microservices.commons.models.entity.Product;
import com.ozymern.springmicroservices.item.domain.Item;
import com.ozymern.springmicroservices.item.remote.ProductRemote;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
//@Primary
public class ItemServiceFeignImpl implements IItemService {

    private ProductRemote productRemote;

    public ItemServiceFeignImpl(ProductRemote productRemote) {
        this.productRemote = productRemote;
    }

    @Override
    public List<Item> findAll() {
        return productRemote.finAll().stream().map(x -> new Item(x, 1)).collect(Collectors.toList());
    }


    @Override
    public Item findById(Long id, Integer count) {
        return new Item(productRemote.showProduct(id), count);
    }


    @Override
    public Product save(Product product) {
        return productRemote.createProduct(product);
    }

    @Override
    public Product update(Product product, Long id) {
        return productRemote.updateProduct(product, id);
    }

    @Override
    public void delete(Long id) {
        productRemote.deleteProduct(id);
    }
}
