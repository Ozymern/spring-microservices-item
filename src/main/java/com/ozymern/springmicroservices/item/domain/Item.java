package com.ozymern.springmicroservices.item.domain;

import com.ozymern.spring.microservices.commons.models.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private Product product;

    private Integer count;

    public Double getTotal(){
        return product.getPrice() *count.doubleValue();
    }
}
