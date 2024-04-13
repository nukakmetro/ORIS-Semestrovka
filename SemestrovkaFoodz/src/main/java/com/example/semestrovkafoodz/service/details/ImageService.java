package com.example.semestrovkafoodz.service.details;

import com.example.semestrovkafoodz.dtos.ImageDto;
import com.example.semestrovkafoodz.dtos.ProductDto;

import java.util.List;

public interface ImageService {
    ImageDto addNewImage(ImageDto imageDto);
    List<ImageDto> getAllImages();
}
