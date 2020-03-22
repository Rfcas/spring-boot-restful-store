package com.example.store.impl.persistence.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "PRODUCTS")
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @SequenceGenerator(name = "PRODUCT_ID_GENERATOR", sequenceName = "PRODUCT_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "PRODUCT_ID_GENERATOR")
    @Column(name = "SKU")
    private Long sku;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "CREATION_DATE")
    private LocalDateTime creationDate;

    @Builder.Default()
    @Column(name = "IS_ACTIVE")
    private Boolean isActive = true;

}
