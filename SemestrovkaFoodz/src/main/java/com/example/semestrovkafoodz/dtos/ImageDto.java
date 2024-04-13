package com.example.semestrovkafoodz.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ImageDto {
    private String image;
    private Long imageProductId;
}
