package com.example.semestrovkafoodz.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private Long productPrice;
    private Long productWaitingTime;
    private Long productRating;
    private String productCategory;
    private String productCompound;

    @ManyToOne()
    private UserEntity userEntity;

    @OneToMany(mappedBy = "imageId")
    private List<ImageEntity> imageEntities;
}
