package com.example.semestrovkafoodz.repositories;

import com.example.semestrovkafoodz.entities.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    public Optional<TokenEntity> findByToken(String token);
    public void deleteById(Long id);
    // public void deleteById(int id);
}
