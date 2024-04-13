package com.example.semestrovkafoodz.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ProductDto {
    private Long productId;
    private Long productPrice;
    private Long productWaitingTime;
    private Long productRating;
    private Long productUserId;
    private String productName;
    private String productCategory;
    private String productCompound;
    private String productUsername;
    private List<String> productImages;
}
