package com.example.store.impl.logic.services;

import com.example.store.impl.logic.converters.OrderConverter;
import com.example.store.impl.logic.exceptions.OrderBadRequestException;
import com.example.store.impl.logic.exceptions.ProductNotFoundException;
import com.example.store.impl.persistence.entity.OrderEntity;
import com.example.store.impl.persistence.entity.ProductEntity;
import com.example.store.impl.persistence.repository.OrderRepository;
import com.example.store.impl.persistence.repository.ProductRepository;
import com.example.store.model.Order;
import com.example.store.model.Product;
import com.example.store.model.requests.CreateOrderRequest;
import com.example.store.utils.TestUtils;
import com.google.common.collect.Lists;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService classUnderTest;

    @MockBean
    private OrderConverter orderConverter;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private ProductRepository productRepository;

    private long productId;
    private LocalDateTime now;
    private Product product;
    private ProductEntity productEntity;
    private Order order;
    private OrderEntity orderEntity;
    private CreateOrderRequest createOrderRequest;

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

        order = Order.builder()
                .id(1L)
                .email(TestUtils.VALID_EMAIL)
                .creationDate(now)
                .products(new HashSet<>(Collections.singletonList(product)))
                .build();

        orderEntity = OrderEntity.builder()
                .id(1L)
                .email(TestUtils.VALID_EMAIL)
                .creationDate(now).products(new HashSet<>(Collections.singletonList(productEntity)))
                .build();

        createOrderRequest = CreateOrderRequest.builder()
                .email(TestUtils.VALID_EMAIL)
                .creationDate(now)
                .products(new HashSet<>(Collections.singletonList(productId)))
                .build();
    }

    @Test
    void create_product_exception_no_request() {
        assertThrows(OrderBadRequestException.class, () -> classUnderTest.create(null));
    }

    @Test
    void create_product_success() {
        ArgumentCaptor<OrderEntity> orderEntityArgumentCaptor = ArgumentCaptor.forClass(OrderEntity.class);

        Order expected = Order.builder()
                .id(productId)
                .email(TestUtils.VALID_EMAIL)
                .products(new HashSet<>(Collections.singletonList(product)))
                .creationDate(now)
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));
        when(orderRepository.save(orderEntityArgumentCaptor.capture())).thenReturn(orderEntity);
        when(orderConverter.fromOrderEntity(orderEntity)).thenReturn(order);

        Order res = classUnderTest.create(createOrderRequest);

        OrderEntity realProductEntity = orderEntityArgumentCaptor.getValue();
        Assertions.assertThat(realProductEntity.getEmail()).isEqualTo(TestUtils.VALID_EMAIL);
        Assertions.assertThat(realProductEntity.getProducts()).isEqualTo(new HashSet<>(Collections.singletonList(productEntity)));
        Assertions.assertThat(realProductEntity.getCreationDate()).isEqualTo(now);
        Assertions.assertThat(res).isEqualToComparingFieldByField(expected);

    }

    @Test()
    void create_product_negative_price_throws_exception() {
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> classUnderTest.create(createOrderRequest));
    }

    @Test()
    void create_product_price_zero_throws_exception() {
        createOrderRequest.setEmail(TestUtils.INVALID_EMAIL);

        assertThrows(OrderBadRequestException.class, () -> classUnderTest.create(createOrderRequest));
    }

    @Test
    void list_empty_products() {
        List<Order> orders = classUnderTest.listOrders();
        Assertions.assertThat(orders).isNotNull();
    }

    @Test
    void list_products() {
        List<OrderEntity> productEntities = Lists.newArrayList(orderEntity);

        when(orderRepository.findAll()).thenReturn(productEntities);
        when(orderConverter.fromOrderEntity(orderEntity)).thenReturn(order);

        List<Order> orders = classUnderTest.listOrders();

        Assertions.assertThat(orders.size()).isEqualTo(1);
        Assertions.assertThat(orders.get(0)).isEqualTo(order);
    }
}