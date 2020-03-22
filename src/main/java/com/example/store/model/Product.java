package com.example.store.model;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Long sku;

    private String name;

    private Double price;

    private LocalDateTime creationDate;

    @Builder.Default()
    private Boolean isActive = true;

}
