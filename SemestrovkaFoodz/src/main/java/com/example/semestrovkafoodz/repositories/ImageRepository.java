package com.example.semestrovkafoodz.repositories;

import com.example.semestrovkafoodz.entities.ImageEntity;
import com.example.semestrovkafoodz.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {}
