package com.example.store.impl.logic.services;

import com.example.store.impl.logic.converters.OrderConverter;
import com.example.store.impl.logic.converters.ProductConverter;
import com.example.store.impl.logic.exceptions.OrderBadRequestException;
import com.example.store.impl.logic.exceptions.ProductNotFoundException;
import com.example.store.impl.persistence.entity.OrderEntity;
import com.example.store.impl.persistence.repository.OrderRepository;
import com.example.store.impl.persistence.repository.ProductRepository;
import com.example.store.model.Order;
import com.example.store.model.requests.CreateOrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class OrderService {

    private OrderConverter orderConverter;

    private OrderRepository orderRepository;

    private ProductRepository productRepository;

    @Autowired
    public OrderService(ProductService productService, OrderConverter orderConverter, OrderRepository orderRepository, ProductConverter productConverter, ProductRepository productRepository) {
        this.orderConverter = orderConverter;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public Order create(CreateOrderRequest orderRequest) {

        if (orderRequest == null) {
            throw new OrderBadRequestException("Missing order");
        }

        if (!EmailValidator.getInstance().isValid(orderRequest.getEmail())) {
            throw new OrderBadRequestException("Field Email");
        }

        OrderEntity orderEntity = OrderEntity.builder()
                .email(orderRequest.getEmail())
                .creationDate(orderRequest.getCreationDate())
                .build();

        orderRequest.getProducts().forEach(productId ->
                orderEntity.getProducts().add(
                        productRepository.findById(productId)
                                .orElseThrow(() -> new ProductNotFoundException(productId))));

        OrderEntity newOrder = orderRepository.save(orderEntity);

        return orderConverter.fromOrderEntity(newOrder);
    }

    public List<Order> listOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderConverter::fromOrderEntity)
                .collect(Collectors.toList());
    }

}
