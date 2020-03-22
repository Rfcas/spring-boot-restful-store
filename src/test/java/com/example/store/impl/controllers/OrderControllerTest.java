package com.example.store.impl.controllers;

import com.example.store.impl.logic.exceptions.OrderBadRequestException;
import com.example.store.impl.logic.services.OrderService;
import com.example.store.model.Order;
import com.example.store.model.Product;
import com.example.store.model.requests.CreateOrderRequest;
import com.example.store.utils.TestUtils;
import com.google.common.collect.Lists;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    @MockBean
    private OrderService orderService;

    private LocalDateTime now;
    private Product product;
    private Order order;
    private CreateOrderRequest createOrderRequest;

    private final String validEmail = "valid@email.com";

    @BeforeEach
    public void setUp() {
        now = LocalDateTime.now();
        product = Product.builder().sku(1L).name("test").price(1.0).creationDate(now).build();

        order = Order.builder().id(1L).email(validEmail).creationDate(now).products(new HashSet<>(Collections.singletonList(product))).build();
        createOrderRequest = CreateOrderRequest.builder().email(validEmail).creationDate(now).products(new HashSet<>(Collections.singletonList(1L))).build();

    }

    @Test
    void create_product_return_ok() throws Exception {

        when(orderService.create(any())).thenReturn(order);

        mockMvc.perform(post("/api/v1/orders")
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtils.convertObjectToJsonBytes(createOrderRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.equalTo(validEmail)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products.[0].sku", Matchers.equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products.[0].name", Matchers.equalTo(product.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products.[0].price", Matchers.equalTo(product.getPrice())));
    }

    @Test
    void create_product_return_bad_request() throws Exception {

        when(orderService.create(null)).thenThrow(OrderBadRequestException.class);

        mockMvc.perform(post("/api/v1/orders")
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtils.convertObjectToJsonBytes(null))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void list_product_return_ok() throws Exception {

        List<Order> orders = Lists.newArrayList(order);
        when(orderService.listOrders()).thenReturn(orders);

        mockMvc.perform(get("/api/v1/orders/list")
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtils.convertObjectToJsonBytes(createOrderRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].email", Matchers.equalTo(validEmail)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].products.[0].sku", Matchers.equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].products.[0].name", Matchers.equalTo(product.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].products.[0].price", Matchers.equalTo(product.getPrice())));

    }

}