package com.example.store.impl.persistence.repository;

import com.example.store.impl.persistence.entity.ProductEntity;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void givenProduct_whenSave_thenGetOk() {
        ProductEntity product = ProductEntity.builder().name("Test").creationDate(LocalDateTime.now()).price(1.0).build();

        productRepository.save(product);

        ProductEntity newProduct = productRepository.findById(1L).get();

        Assertions.assertThat(newProduct.getSku()).isNotNull();
        Assertions.assertThat(newProduct).isEqualToIgnoringGivenFields(product, "sku");

    }
}