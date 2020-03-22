package com.example.store.impl.logic.converters;

import com.example.store.impl.persistence.entity.OrderEntity;
import com.example.store.impl.persistence.entity.ProductEntity;
import com.example.store.model.Order;
import com.example.store.model.Product;
import com.example.store.utils.TestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;

@SpringBootTest
class OrderConverterTest {

    @Autowired
    private OrderConverter classUnderTest;

    private LocalDateTime now;
    private Product product;
    private ProductEntity productEntity;

    private Order order;
    private OrderEntity orderEntity;

    @BeforeEach
    private void setUp() {
        now = LocalDateTime.now();
        product = Product.builder().sku(1L).name("test").price(1.0).creationDate(now).build();
        productEntity = ProductEntity.builder().sku(1L).name("test").price(1.0).creationDate(now).build();

        order = Order.builder().id(1L).email(TestUtils.VALID_EMAIL).creationDate(now).products(new HashSet<>(Collections.singletonList(product))).totalPrice(1.0).build();
        orderEntity = OrderEntity.builder().id(1L).email(TestUtils.VALID_EMAIL).creationDate(now).products(new HashSet<>(Collections.singletonList(productEntity))).build();
    }

    @Test
    void convert_from_order_entity() {

        Order res = classUnderTest.fromOrderEntity(orderEntity);

        Assertions.assertThat(res).isEqualToIgnoringGivenFields(order, "products");
        Assertions.assertThat(res.getProducts().size()).isEqualTo(1);
    }

    @Test
    void convert_from_order_entity_null_id() {

        order.setId(null);
        orderEntity.setId(null);

        Order res = classUnderTest.fromOrderEntity(orderEntity);

        Assertions.assertThat(res).isEqualToIgnoringGivenFields(order, "products");
        Assertions.assertThat(res.getProducts().size()).isEqualTo(1);
    }

    @Test
    void convert_from_null_order_entity() {

        Order res = classUnderTest.fromOrderEntity(null);

        Assertions.assertThat(res).isNull();
    }
}