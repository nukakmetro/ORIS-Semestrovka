package com.example.semestrovkafoodz.service.details;

import com.example.semestrovkafoodz.dtos.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto addNewProduct(ProductDto productDto);
    List<ProductDto> getAllProducts();
}
