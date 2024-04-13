package com.example.semestrovkafoodz.service.details;

import com.example.semestrovkafoodz.dtos.ProductDto;
import com.example.semestrovkafoodz.entities.ImageEntity;
import com.example.semestrovkafoodz.entities.ProductEntity;
import com.example.semestrovkafoodz.entities.UserEntity;
import com.example.semestrovkafoodz.repositories.ImageRepository;
import com.example.semestrovkafoodz.repositories.ProductRepository;
import com.example.semestrovkafoodz.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    @Override
    public ProductDto addNewProduct(ProductDto productDto) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(productDto.getProductUserId());
        if(userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            ProductEntity productEntity = ProductEntity.builder()
                    .productCategory(productDto.getProductCategory())
                    .productPrice(productDto.getProductPrice())
                    .productName(productDto.getProductName())
                    .productRating(productDto.getProductRating())
                    .productCompound(productDto.getProductCompound())
                    .productWaitingTime(productDto.getProductWaitingTime())
                    .userEntity(userEntity)
                    .build();
            productRepository.save(productEntity);


        }
        return productDto;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(it  -> mapTo(it)).toList();
    }

//    public List<ProductDto> getSearchProducts(String name) {
//        return productRepository.findByNameContaining(name).stream()
//                .map(it -> mapTo(it)).toList();
//
//    }

    public ProductDto mapTo(ProductEntity it) {
        return ProductDto.builder()
                .productId(it.getProductId())
                .productRating(it.getProductRating())
                .productPrice(it.getProductPrice())
                .productName(it.getProductName())
                .productCategory(it.getProductCategory())
                .productCompound(it.getProductCompound())
                .productUsername(it.getUserEntity().getUsername())
                .productUserId(it.getUserEntity().getUserId())
                .productWaitingTime(it.getProductWaitingTime())
                .build();
    }
}
