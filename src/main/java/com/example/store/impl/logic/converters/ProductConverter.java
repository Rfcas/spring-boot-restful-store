package com.example.store.impl.logic.converters;

import com.example.store.impl.persistence.entity.ProductEntity;
import com.example.store.model.Product;
import com.example.store.model.requests.CreateProductRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {

    public Product fromProductEntity(ProductEntity productEntity) {
        return productEntity != null ?
                Product.builder()
                        .sku(productEntity.getSku())
                        .name(productEntity.getName())
                        .price(productEntity.getPrice())
                        .creationDate(productEntity.getCreationDate())
                        .build() :
                null;
    }

    public ProductEntity toProductEntity(Product product) {
        return product != null ?
                ProductEntity.builder()
                        .sku(product.getSku())
                        .name(product.getName())
                        .price(product.getPrice())
                        .creationDate(product.getCreationDate())
                        .build() :
                null;

    }

    public ProductEntity productEntityfromProductRequest(CreateProductRequest createProductRequest) {
        return createProductRequest != null ?
                ProductEntity.builder()
                        .sku(null)
                        .name(createProductRequest.getName())
                        .price(createProductRequest.getPrice())
                        .creationDate(createProductRequest.getCreationDate())
                        .build() :
                null;
    }

}
