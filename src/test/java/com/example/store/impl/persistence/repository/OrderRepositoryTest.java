package com.example.store.impl.persistence.repository;

import com.example.store.impl.persistence.entity.OrderEntity;
import com.example.store.impl.persistence.entity.ProductEntity;
import com.example.store.utils.TestUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void givenOrder_whenSave_thenGetOk() {
        ProductEntity product = ProductEntity.builder().name("Test").creationDate(LocalDateTime.now()).price(1.0).build();
        Set<ProductEntity> productEntities = new HashSet<>(Collections.singletonList(product));

        OrderEntity order = OrderEntity.builder().email(TestUtils.VALID_EMAIL).creationDate(LocalDateTime.now()).products(productEntities).build();

        orderRepository.save(order);

        OrderEntity newOrder = orderRepository.findById(1L).get();

        Assertions.assertThat(newOrder.getId()).isNotNull();
        Assertions.assertThat(newOrder).isEqualToIgnoringGivenFields(order, "sku", "product");

    }
}