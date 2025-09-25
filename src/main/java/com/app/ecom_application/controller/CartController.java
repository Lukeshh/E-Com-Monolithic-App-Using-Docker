package com.app.ecom_application.controller;

import com.app.ecom_application.dto.CartItemDtoRequest;
import com.app.ecom_application.model.entity.CartItemEntity;
import com.app.ecom_application.repository.CartItemRepository;
import com.app.ecom_application.service.CartItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartItemService cartItemService;
    public CartController( CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

@PostMapping
    public ResponseEntity<String> addTocart(@RequestHeader("X-User-ID") Long userId , @RequestBody CartItemDtoRequest cartItemDtoRequest) {
    if (!cartItemService.addToCart(userId, cartItemDtoRequest)) {
        return ResponseEntity.badRequest().body("Product of stock or User not found");
    }
    return ResponseEntity.status(HttpStatus.CREATED).build();

}

@DeleteMapping("/items/{productId}")
    public ResponseEntity<Boolean> removeFromCart( @RequestHeader("X-User-Id")   Long userId , @PathVariable Long productId){
         boolean deleted = cartItemService.deleteItemFromCart(productId, userId);
          return deleted ? ResponseEntity.noContent().build(): ResponseEntity.notFound().build();
}
@GetMapping
    public ResponseEntity<List<CartItemEntity>> fetchCartItems(@RequestHeader("X-User-Id")Long userId)
{
    return  new ResponseEntity<>(cartItemService.fetchCart(userId),HttpStatus.OK);
}
}