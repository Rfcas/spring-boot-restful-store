package com.example.store.impl.logic.converters;

import com.example.store.impl.persistence.entity.ProductEntity;
import com.example.store.model.Product;
import com.example.store.model.requests.CreateProductRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootTest
class ProductConverterTest {

    @Autowired
    private ProductConverter classUnderTest;

    private LocalDateTime now;
    private Product product;
    private ProductEntity productEntity;
    private CreateProductRequest createProductRequest;

    @BeforeEach
    private void setUp() {
        now = LocalDateTime.now();
        product = Product.builder().sku(1L).name("test").price(1.0).creationDate(now).build();
        productEntity = ProductEntity.builder().sku(1L).name("test").price(1.0).creationDate(now).build();
        createProductRequest = CreateProductRequest.builder().name("test").price(1.0).creationDate(now).build();
    }

    @Test
    void convert_to_product_entity() {

        ProductEntity res = classUnderTest.toProductEntity(product);

        Assertions.assertThat(res).isEqualToComparingFieldByField(productEntity);
    }

    @Test
    void convert_to_product_entity_null_id() {

        product.setSku(null);
        productEntity.setSku(null);

        ProductEntity res = classUnderTest.toProductEntity(product);

        Assertions.assertThat(res).isEqualToComparingFieldByField(productEntity);
    }

    @Test
    void convert_to_product_entity_from_null_entity() {

        ProductEntity res = classUnderTest.toProductEntity(null);

        Assertions.assertThat(res).isNull();
    }

    @Test
    void convert_from_product_entity() {

        Product res = classUnderTest.fromProductEntity(productEntity);

        Assertions.assertThat(res).isEqualToComparingFieldByField(product);
    }

    @Test
    void convert_from_product_entity_null_id() {

        product.setSku(null);
        productEntity.setSku(null);

        Product res = classUnderTest.fromProductEntity(productEntity);

        Assertions.assertThat(res).isEqualToComparingFieldByField(product);
    }

    @Test
    void convert_from_null_product_entity() {

        Product res = classUnderTest.fromProductEntity(null);

        Assertions.assertThat(res).isNull();
    }

    @Test
    void convert_product_entity_from_create_product_request() {

        product.setSku(null);

        ProductEntity res = classUnderTest.productEntityfromProductRequest(createProductRequest);

        Assertions.assertThat(res).isEqualToComparingFieldByField(product);
    }

    @Test
    void convert_product_entity_from_create_product_request_null_price() {

        product.setSku(null);
        product.setPrice(null);
        createProductRequest.setPrice(null);

        ProductEntity res = classUnderTest.productEntityfromProductRequest(createProductRequest);

        Assertions.assertThat(res).isEqualToComparingFieldByField(product);
    }

    @Test
    void convert_product_entity_from_null_create_product_request() {

        ProductEntity res = classUnderTest.productEntityfromProductRequest(null);

        Assertions.assertThat(res).isNull();
    }


}