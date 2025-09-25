package com.app.ecom_application.service;

import com.app.ecom_application.dto.ProductRequestDto;
import com.app.ecom_application.dto.ProductResponseDto;
import com.app.ecom_application.model.entity.ProductsEntity;
import com.app.ecom_application.repository.ProductsRepository;
import com.app.ecom_application.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final ProductsRepository productsRepository;
    private final UserRepository userRepository;
    private  final MapToRequest mapToRequest;
    public ProductService(ProductsRepository productsRepository, UserRepository userRepository, MapToRequest mapToRequest) {
        this.productsRepository = productsRepository;
        this.userRepository = userRepository;
        this.mapToRequest = mapToRequest;
    }

    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        ProductsEntity entity  = mapToRequest.mapProductDtoTOEntity(productRequestDto);
        ProductsEntity saveToDb = productsRepository.save(entity);
        ProductResponseDto productResponseDto  = mapToRequest.mapEnityToProductResponseDto(entity);
        return productResponseDto;
    }



    public ProductResponseDto updateProduct(ProductRequestDto productRequestDto, Long id) {

        try {
            ProductsEntity productInEntity = productsRepository.findById(id).orElse(null);
            ProductResponseDto productResponseDto = new ProductResponseDto();
            if (productInEntity != null) {
                checkEntry(productRequestDto, productInEntity);
                ProductsEntity saveToDb = productsRepository.save(productInEntity);
                productResponseDto = mapToRequest.mapEnityToProductResponseDto(saveToDb);
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
            ProductResponseDto responseDto = mapToRequest.mapEnityToProductResponseDto(entity);
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
            ProductResponseDto responseDto = mapToRequest.mapEnityToProductResponseDto(entity);
            responseDtosList.add(responseDto);
        }
        return responseDtosList;
    }


}


