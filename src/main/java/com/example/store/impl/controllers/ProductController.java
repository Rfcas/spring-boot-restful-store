package com.example.store.impl.controllers;

import com.example.store.impl.logic.services.ProductService;
import com.example.store.model.Product;
import com.example.store.model.requests.CreateProductRequest;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(@RequestBody ProductService productService) {
        this.productService = productService;
    }

    @PostMapping

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Bad Request")})
    public Product createProduct(@RequestBody CreateProductRequest product) {
        return productService.create(product);
    }

    @GetMapping(value = "/list")
    public List<Product> listProducts() {
        return productService.list();
    }

    @PutMapping
    public Product updateProduct(@RequestParam Long id, @RequestBody Product product) {
        return productService.update(id, product);
    }

    @DeleteMapping
    public Product deleteProduct(@RequestParam Long id) {
        return productService.delete(id);
    }

}
