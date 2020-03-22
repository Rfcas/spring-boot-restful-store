package com.example.store.model.requests;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {

    private String name;

    private Double price;

    private LocalDateTime creationDate;
}
