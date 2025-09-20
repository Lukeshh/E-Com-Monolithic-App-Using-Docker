package com.app.ecom_application.service;

import com.app.ecom_application.dto.ProductRequestDto;
import com.app.ecom_application.dto.ProductResponseDto;
import com.app.ecom_application.model.entity.ProductsEntity;
import com.app.ecom_application.repository.ProductsRepository;
import com.app.ecom_application.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductsRepository productsRepository;
    private final UserRepository userRepository;

    public ProductService(ProductsRepository productsRepository, UserRepository userRepository) {
        this.productsRepository = productsRepository;
        this.userRepository = userRepository;
    }

    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        ProductsEntity entity = new ProductsEntity();
        entity = mapProductDtoTOEntity(productRequestDto);
        ProductsEntity saveToDb = productsRepository.save(entity);
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto = mapEnityToProductResponseDto(entity);

        return productResponseDto;
    }

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

    public ProductResponseDto updateProduct(ProductRequestDto productRequestDto, Long id) {

        try {
            ProductsEntity productInEntity = productsRepository.findById(id).orElse(null);
            ProductResponseDto productResponseDto = new ProductResponseDto();
            if (productInEntity != null) {
                checkEntry(productRequestDto, productInEntity);
                ProductsEntity saveToDb = productsRepository.save(productInEntity);
                productResponseDto = mapEnityToProductResponseDto(saveToDb);
                return productResponseDto;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void checkEntry(ProductRequestDto productRequestDto, ProductsEntity productInEntity) {
        if (productRequestDto.getCategory() != null) {
            productInEntity.setCategory(productInEntity.getCategory());
        } else if (productRequestDto.getName() != null) {
            productInEntity.setName(productRequestDto.getName());

        } else if (productRequestDto.getPrice() != null) {
            productInEntity.setPrice(productRequestDto.getPrice());
        } else if (productRequestDto.getDescription() != null) {
            productInEntity.setDescription(productRequestDto.getDescription());
        } else if (productRequestDto.getStockQuantity() != null) {
            productInEntity.setStockQuantity(productRequestDto.getStockQuantity());
        } else if (productRequestDto.getImageUrl() != null) {
            productInEntity.setImageUrl(productRequestDto.getImageUrl());
        }
    }

    public List<ProductResponseDto> getAllProduct() {
        List<ProductsEntity> productsEntities = productsRepository.findAll();
        List<ProductResponseDto> responseList = new ArrayList<>();
        for (ProductsEntity entity : productsEntities) {
            ProductResponseDto responseDto = mapEnityToProductResponseDto(entity);
            responseList.add(responseDto);
        }
        return responseList;

    }

    public void deleteProduct(Long id) {
        ProductsEntity entity = productsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        entity.setActive(false);
        productsRepository.save(entity);
    }

    public List<ProductResponseDto> searchProduct(String keyword) {
        // return productsRepository.searchProducts(keyword).stream().map(this:: mapEnityToProductResponseDto).collect(Collectors.toList());
        List<ProductsEntity> entities = productsRepository.searchProducts(keyword);
        List<ProductResponseDto> responseDtosList = new ArrayList<>();
        for (ProductsEntity entity : entities) {
            ProductResponseDto responseDto = mapEnityToProductResponseDto(entity);
            responseDtosList.add(responseDto);
        }
        return responseDtosList;
    }


}


