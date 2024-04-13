package com.example.semestrovkafoodz.controllers;

import com.example.semestrovkafoodz.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductsController {

    private final ProductsService productsService;
    @GetMapping("/favoritesProducts")
    public ResponseEntity<?> getFavoritesProducts() {
        return productsService.getFavoritesProducts();
    }

    @GetMapping("/search")
    public ResponseEntity<?> getSearchProducts(@RequestBody String name) {
        return productsService.getSearchProducts(name);
    }
}