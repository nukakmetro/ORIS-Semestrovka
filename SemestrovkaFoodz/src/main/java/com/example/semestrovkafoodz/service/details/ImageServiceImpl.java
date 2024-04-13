package com.example.semestrovkafoodz.service.details;

import com.example.semestrovkafoodz.dtos.ImageDto;
import com.example.semestrovkafoodz.entities.ImageEntity;
import com.example.semestrovkafoodz.entities.ProductEntity;
import com.example.semestrovkafoodz.repositories.ImageRepository;
import com.example.semestrovkafoodz.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    @Override
    public ImageDto addNewImage(ImageDto imageDto) {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(imageDto.getImageProductId());
        if(productEntityOptional.isPresent()) {
            ProductEntity productEntity = productEntityOptional.get();
            ImageEntity imageEntity = ImageEntity.builder()
                    .imageCode(imageDto.getImage())
                    .productEntity(productEntity)
                    .build();
            imageRepository.save(imageEntity);
        }
        return  imageDto;
    }

    public List<ImageDto> addNewImages(List<ImageDto> imageDtos) {
        imageDtos.stream().map(it -> addNewImage(it));
        return imageDtos;
    }

    @Override
    public List<ImageDto> getAllImages() {
        return null;
    }
}
