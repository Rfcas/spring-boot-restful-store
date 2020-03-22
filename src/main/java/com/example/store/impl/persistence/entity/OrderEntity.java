package com.example.store.impl.persistence.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ORDERS")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    @Id
    @SequenceGenerator(name = "ORDER_ID_GENERATOR", sequenceName = "ORDER_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ORDER_ID_GENERATOR")
    @Column(name = "ID")
    private Long id;

    @Column(name = "EMAIl")
    private String email;

    @Column(name = "CREATION_DATE")
    private LocalDateTime creationDate;


    @ManyToMany
    @JoinTable(
            name = "ORDERS_PRODUCTS",
            joinColumns = @JoinColumn(name = "orders_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_sku", referencedColumnName = "sku"))
    @Builder.Default
    Set<ProductEntity> products = new HashSet<>();

}
