package com.example.semestrovkafoodz.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class SectionDto {
    private Long id;
    private String title;
    private int type;
    private List<ProductDto> products;
}
