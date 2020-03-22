package com.example.store.impl.controllers;

import com.example.store.impl.logic.exceptions.ProductBadRequestException;
import com.example.store.impl.logic.exceptions.ProductNotFoundException;
import com.example.store.impl.logic.services.ProductService;
import com.example.store.model.Product;
import com.example.store.model.requests.CreateProductRequest;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    @MockBean
    private ProductService productService;

    private LocalDateTime now;
    private Product product;
    private CreateProductRequest createProductRequest;

    @BeforeEach
    public void setUp() {
        now = LocalDateTime.now();
        product = Product.builder().sku(1L).name("test").price(1.0).creationDate(now).build();
        createProductRequest = CreateProductRequest.builder().name("test").price(1.0).creationDate(now).build();
    }

    @Test
    void create_product_return_ok() throws Exception {

        when(productService.create(any())).thenReturn(product);

        mockMvc.perform(post("/api/v1/products")
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtils.convertObjectToJsonBytes(createProductRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.sku", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo("test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isActive", Matchers.equalTo(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price", Matchers.equalTo(1.0)))
                .andReturn();
    }

    @Test
    void create_product_return_bad_request() throws Exception {

        when(productService.create(null)).thenThrow(ProductBadRequestException.class);

        mockMvc.perform(post("/api/v1/products")
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtils.convertObjectToJsonBytes(null))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void list_product_return_ok() throws Exception {

        List<Product> products = Lists.newArrayList(product);
        when(productService.list()).thenReturn(products);

        mockMvc.perform(get("/api/v1/products/list")
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtils.convertObjectToJsonBytes(product))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].sku", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name", Matchers.equalTo("test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].isActive", Matchers.equalTo(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].price", Matchers.equalTo(1.0)))
                .andReturn();
    }

    @Test
    void update_product_return_ok() throws Exception {

        when(productService.update(any(), any())).thenReturn(product);

        mockMvc.perform(put("/api/v1/products?id=" + product.getSku())
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtils.convertObjectToJsonBytes(createProductRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.sku", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo("test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isActive", Matchers.equalTo(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price", Matchers.equalTo(1.0)));
    }

    @Test
    void update_product_return_bad_request() throws Exception {

        when(productService.update(any(), any())).thenThrow(ProductBadRequestException.class);

        mockMvc.perform(put("/api/v1/products?id=" + product.getSku())
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtils.convertObjectToJsonBytes(createProductRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_product_return_not_found() throws Exception {

        when(productService.update(any(), any())).thenThrow(ProductNotFoundException.class);

        mockMvc.perform(put("/api/v1/products?id=" + product.getSku())
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtils.convertObjectToJsonBytes(createProductRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_product_return_ok() throws Exception {

        when(productService.delete(any())).thenReturn(product);

        mockMvc.perform(delete("/api/v1/products?id=" + product.getSku())
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtils.convertObjectToJsonBytes(createProductRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.sku", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo("test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isActive", Matchers.equalTo(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price", Matchers.equalTo(1.0)));
    }

    @Test
    void delete_product_return_bad_request() throws Exception {

        when(productService.delete((any()))).thenThrow(ProductBadRequestException.class);

        mockMvc.perform(delete("/api/v1/products?id=" + product.getSku())
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtils.convertObjectToJsonBytes(createProductRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}