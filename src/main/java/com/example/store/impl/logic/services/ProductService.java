package com.example.store.impl.logic.services;

import com.example.store.impl.logic.converters.ProductConverter;
import com.example.store.impl.logic.exceptions.ProductBadRequestException;
import com.example.store.impl.logic.exceptions.ProductNotFoundException;
import com.example.store.impl.persistence.entity.ProductEntity;
import com.example.store.impl.persistence.repository.ProductRepository;
import com.example.store.model.Product;
import com.example.store.model.requests.CreateProductRequest;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@DynamicUpdate
@Slf4j
public class ProductService {

    private ProductConverter productConverter;

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductConverter productConverter, ProductRepository productRepository) {
        this.productConverter = productConverter;
        this.productRepository = productRepository;
    }

    public Product create(CreateProductRequest product) {
        if (product == null) {
            throw new ProductBadRequestException("Missing product");
        }
        if (isNegativeOrZero(product.getPrice())) {
            throw new ProductBadRequestException("Product price should be higher than 0");
        }

        ProductEntity productEntity = productConverter.productEntityfromProductRequest(product);

        ProductEntity createdProductEntity = productRepository.save(productEntity);

        return productConverter.fromProductEntity(createdProductEntity);
    }

    public List<Product> list() {
        return productRepository.findAll()
                .stream()
                .map(productConverter::fromProductEntity)
                .collect(Collectors.toList());
    }

    public Product update(Long sku, Product product) {

        if (sku == null || product == null) {
            throw new ProductBadRequestException("Missing product or sku");
        }
        if (!sku.equals(product.getSku())) {
            log.debug("Sku on request param and product id mismatch, using product id.");
        }
        if (isNegativeOrZero(product.getPrice())) {
            throw new ProductBadRequestException("Product price should be higher than 0");
        }

        productRepository.findById(product.getSku())
                .orElseThrow(() -> new ProductNotFoundException(product.getSku()));

        ProductEntity productToModify = productConverter.toProductEntity(product);
        ProductEntity updatedEntity = productRepository.save(productToModify);
        return productConverter.fromProductEntity(updatedEntity);
    }

    public Product delete(Long id) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        productEntity.setIsActive(false);

        ProductEntity deletedEntity = productRepository.save(productEntity);
        return productConverter.fromProductEntity(deletedEntity);
    }

    private static boolean isNegativeOrZero(double d) {
        return Double.compare(d, 0.0) <= 0;
    }
}
