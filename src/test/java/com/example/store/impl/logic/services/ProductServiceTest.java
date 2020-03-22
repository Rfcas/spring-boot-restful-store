package com.example.store.impl.logic.services;

import com.example.store.impl.logic.converters.ProductConverter;
import com.example.store.impl.logic.exceptions.ProductBadRequestException;
import com.example.store.impl.logic.exceptions.ProductNotFoundException;
import com.example.store.impl.persistence.entity.ProductEntity;
import com.example.store.impl.persistence.repository.ProductRepository;
import com.example.store.model.Product;
import com.example.store.model.requests.CreateProductRequest;
import com.google.common.collect.Lists;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService classUnderTest;

    @MockBean
    private ProductConverter productConverter;

    @MockBean
    private ProductRepository productRepository;

    private long productId;
    private LocalDateTime now;
    private Product product;
    private ProductEntity productEntity;
    private CreateProductRequest createProductRequest;

    @BeforeEach
    void setUp() {
        productId = 1L;
        now = LocalDateTime.now();
        product = Product.builder()
                .sku(productId)
                .name("test")
                .price(1.0)
                .creationDate(now)
                .build();
        productEntity = ProductEntity.builder()
                .sku(productId)
                .name("test")
                .price(1.0)
                .creationDate(now)
                .build();
        createProductRequest = CreateProductRequest.builder()
                .name("test")
                .price(1.0)
                .creationDate(now)
                .build();
    }

    @Test
    void create_product_exception_no_request() {
        assertThrows(ProductBadRequestException.class, () -> classUnderTest.create(null));
    }

    @Test
    void create_product_success() {
        ArgumentCaptor<ProductEntity> productEntityArgumentCaptor = ArgumentCaptor.forClass(ProductEntity.class);

        ProductEntity expected = ProductEntity.builder()
                .sku(productId)
                .name("test")
                .price(1.0)
                .creationDate(now)
                .build();

        when(productConverter.productEntityfromProductRequest(createProductRequest)).thenReturn(productEntity);
        when(productRepository.save(productEntityArgumentCaptor.capture())).thenReturn(productEntity);
        when(productConverter.fromProductEntity(productEntity)).thenReturn(product);

        Product res = classUnderTest.create(createProductRequest);

        ProductEntity realProductEntity = productEntityArgumentCaptor.getValue();
        Assertions.assertThat(realProductEntity.getSku()).isEqualTo(productId);
        Assertions.assertThat(realProductEntity.getName()).isEqualTo("test");
        Assertions.assertThat(realProductEntity.getPrice()).isEqualTo(1.0);
        Assertions.assertThat(realProductEntity.getCreationDate()).isEqualTo(now);
        Assertions.assertThat(res).isEqualToComparingFieldByField(product);

    }

    @Test()
    void create_product_negative_price_throws_exception() {
        createProductRequest.setPrice(-1.0);

        assertThrows(ProductBadRequestException.class, () -> classUnderTest.create(createProductRequest));

    }

    @Test()
    void create_product_price_zero_throws_exception() {
        createProductRequest.setPrice(0.0);

        assertThrows(ProductBadRequestException.class, () -> classUnderTest.create(createProductRequest));

    }


    @Test
    void list_empty_products() {
        List<Product> products = classUnderTest.list();
        Assertions.assertThat(products).isNotNull();
    }

    @Test
    void list_products() {
        List<ProductEntity> productEntities = Lists.newArrayList(productEntity);

        when(productRepository.findAll()).thenReturn(productEntities);
        when(productConverter.fromProductEntity(productEntity)).thenReturn(product);

        List<Product> products = classUnderTest.list();

        Assertions.assertThat(products.size()).isEqualTo(1);
        Assertions.assertThat(products.get(0)).isEqualTo(product);
    }

    @Test
    void update_product_exception_missing_params() {
        assertThrows(ProductBadRequestException.class, () -> classUnderTest.update(productId, null));
        assertThrows(ProductBadRequestException.class, () -> classUnderTest.update(null, product));
        assertThrows(ProductBadRequestException.class, () -> classUnderTest.update(null, null));
    }

    @Test
    void update_product_success() {
        ArgumentCaptor<ProductEntity> productEntityArgumentCaptor = ArgumentCaptor.forClass(ProductEntity.class);

        Product newProduct = Product.builder()
                .sku(productId)
                .name("test")
                .price(2.0)
                .creationDate(now)
                .build();

        ProductEntity expected = ProductEntity.builder()
                .sku(productId)
                .name("test")
                .price(2.0)
                .creationDate(now)
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));
        when(productConverter.toProductEntity(newProduct)).thenReturn(expected);
        when(productRepository.save(productEntityArgumentCaptor.capture())).thenReturn(expected);
        when(productConverter.fromProductEntity(expected)).thenReturn(newProduct);

        Product res = classUnderTest.update(productId, newProduct);

        ProductEntity realProductEntity = productEntityArgumentCaptor.getValue();

        Assertions.assertThat(realProductEntity.getSku()).isEqualTo(productId);
        Assertions.assertThat(realProductEntity.getName()).isEqualTo("test");
        Assertions.assertThat(realProductEntity.getPrice()).isEqualTo(2.0);
        Assertions.assertThat(realProductEntity.getCreationDate()).isEqualTo(now);
        Assertions.assertThat(res).isEqualToComparingFieldByField(expected);
    }

    @Test()
    void update_product_negative_price_throws_exception() {
        product.setPrice(-1.0);

        assertThrows(ProductBadRequestException.class, () -> classUnderTest.update(productId, product));

    }

    @Test()
    void update_product_price_zero_throws_exception() {
        product.setPrice(0.0);

        assertThrows(ProductBadRequestException.class, () -> classUnderTest.update(productId, product));

    }

    @Test
    void delete_null_product_id_throw_exception() {
        assertThrows(ProductNotFoundException.class, () -> classUnderTest.delete(null));
    }

    @Test
    void delete_not_existent_product_id_throw_exception() {
        assertThrows(ProductNotFoundException.class, () -> classUnderTest.delete(productId));
    }

    @Test
    void delete_success() {
        ArgumentCaptor<ProductEntity> productEntityArgumentCaptor = ArgumentCaptor.forClass(ProductEntity.class);

        Product deletedProduct = Product.builder()
                .sku(productId)
                .name("test")
                .price(1.0)
                .creationDate(now)
                .isActive(false)
                .build();

        ProductEntity expected = ProductEntity.builder()
                .sku(productId)
                .name("test")
                .price(1.0)
                .creationDate(now)
                .isActive(false)
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));
        when(productRepository.save(productEntityArgumentCaptor.capture())).thenReturn(expected);
        when(productConverter.fromProductEntity(expected)).thenReturn(deletedProduct);

        Product res = classUnderTest.delete(productId);

        ProductEntity realProductEntity = productEntityArgumentCaptor.getValue();

        Assertions.assertThat(realProductEntity.getSku()).isEqualTo(productId);
        Assertions.assertThat(realProductEntity.getName()).isEqualTo("test");
        Assertions.assertThat(realProductEntity.getPrice()).isEqualTo(1.0);
        Assertions.assertThat(realProductEntity.getCreationDate()).isEqualTo(now);
        Assertions.assertThat(realProductEntity.getIsActive()).isEqualTo(false);
        Assertions.assertThat(res).isEqualToComparingFieldByField(expected);
    }
}