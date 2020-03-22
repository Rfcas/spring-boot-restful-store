package com.example.store.impl.logic.converters;

import com.example.store.impl.persistence.entity.OrderEntity;
import com.example.store.impl.persistence.entity.ProductEntity;
import com.example.store.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderConverter {

    private ProductConverter productConverter;

    @Autowired
    public OrderConverter(ProductConverter productConverter) {
        this.productConverter = productConverter;
    }

    public Order fromOrderEntity(OrderEntity productEntity) {
        return productEntity != null ?
                Order.builder()
                        .id(productEntity.getId())
                        .email(productEntity.getEmail())
                        .creationDate(productEntity.getCreationDate())
                        .products(productEntity.getProducts().stream().map(productConverter::fromProductEntity).collect(Collectors.toSet()))
                        .totalPrice(productEntity.getProducts().stream().mapToDouble(ProductEntity::getPrice).sum())
                        .build() :
                null;
    }
}
