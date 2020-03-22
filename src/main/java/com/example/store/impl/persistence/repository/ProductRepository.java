package com.example.store.impl.persistence.repository;

import com.example.store.impl.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
