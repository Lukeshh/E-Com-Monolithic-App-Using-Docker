package com.app.ecom_application.controller;

import com.app.ecom_application.dto.ProductRequestDto;
import com.app.ecom_application.dto.ProductResponseDto;
import com.app.ecom_application.model.entity.ProductsEntity;
import com.app.ecom_application.repository.ProductsRepository;
import com.app.ecom_application.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final ProductsRepository productsRepository;

    public ProductController(ProductService productService, ProductsRepository productsRepository) {
        this.productService = productService;
        this.productsRepository = productsRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productRequestDto) {
        return new ResponseEntity<>(productService.createProduct(productRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDto productRequestDto) {
        return new ResponseEntity<>(productService.updateProduct(productRequestDto, id), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProductResponseDto>> getAllProduct() {
        return new ResponseEntity<>(productService.getAllProduct(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getProduct")
    public ResponseEntity<List<ProductResponseDto>> searchProduct( @RequestParam String keyword) {
        return new ResponseEntity<>(productService.searchProduct(keyword), HttpStatus.OK);

    }
}