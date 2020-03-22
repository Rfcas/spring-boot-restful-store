package com.example.store.model;

import lombok.*;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Long id;

    private String email;

    private LocalDateTime creationDate;

    @Builder.Default
    Set<Product> products = new HashSet<>();

    private Double totalPrice;

}
