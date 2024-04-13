package com.example.semestrovkafoodz.repositories;

import com.example.semestrovkafoodz.entities.ProductEntity;
import com.example.semestrovkafoodz.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByproductName(String productName);
    // Optional<ProductEntity> findByNameContaining(String partialName);
}