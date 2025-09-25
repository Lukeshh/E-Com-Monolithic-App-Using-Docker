package com.app.ecom_application.service;

import com.app.ecom_application.dto.ProductRequestDto;
import com.app.ecom_application.dto.ProductResponseDto;
import com.app.ecom_application.model.entity.ProductsEntity;
import org.springframework.stereotype.Component;

@Component
public class MapToRequest {
    public ProductsEntity mapProductDtoTOEntity(ProductRequestDto productRequestDto) {
        ProductsEntity entity = new ProductsEntity();
        entity.setCategory(productRequestDto.getCategory());
        entity.setName(productRequestDto.getName());
        entity.setPrice(productRequestDto.getPrice());
        entity.setDescription(productRequestDto.getDescription());
        entity.setStockQuantity(productRequestDto.getStockQuantity());
        entity.setImageUrl(productRequestDto.getImageUrl());
        return entity;
    }

    public ProductResponseDto mapEnityToProductResponseDto(ProductsEntity productsEntity) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(productsEntity.getId());
        productResponseDto.setCategory(productsEntity.getCategory());
        productResponseDto.setName(productsEntity.getName());
        productResponseDto.setPrice(productsEntity.getPrice());
        productResponseDto.setDescription(productsEntity.getDescription());
        productResponseDto.setStockQuantity(productsEntity.getStockQuantity());
        productResponseDto.setImageUrl(productsEntity.getImageUrl());
        return productResponseDto;
    }
}
