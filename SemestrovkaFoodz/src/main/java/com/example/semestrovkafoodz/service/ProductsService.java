package com.example.semestrovkafoodz.service;

import com.example.semestrovkafoodz.dtos.SectionDto;
import com.example.semestrovkafoodz.service.details.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductServiceImpl productService;

    public ResponseEntity<?> getFavoritesProducts() {
        SectionDto sectionDto = SectionDto.builder()
                .products(productService.getAllProducts())
                .id(0L)
                .title("Popular")
                .type(0)
                .build();
        List<SectionDto> sectionDtoList = new ArrayList<>();
        sectionDtoList.add(sectionDto);
        return  ResponseEntity.ok(sectionDtoList);
    }

    public ResponseEntity<?> getSearchProducts(@RequestBody String name) {
//        SectionDto sectionDto = SectionDto.builder()
//                .products(productService.getSearchProducts(name))
//                .id(0L)
//                .title("Popular")
//                .type(0)
//                .build();
//        List<SectionDto> sectionDtoList = new ArrayList<>();
//        sectionDtoList.add(sectionDto);
//        return  ResponseEntity.ok(sectionDtoList);
        return ResponseEntity.ok("Ok");
    }
}
