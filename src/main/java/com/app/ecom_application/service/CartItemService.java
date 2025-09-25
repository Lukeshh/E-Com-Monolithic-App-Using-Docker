package com.app.ecom_application.service;

import com.app.ecom_application.dto.CartItemDtoRequest;
import com.app.ecom_application.model.entity.CartItemEntity;
import com.app.ecom_application.model.entity.OrderEntity;
import com.app.ecom_application.model.entity.ProductsEntity;
import com.app.ecom_application.model.entity.UserEntity;
import com.app.ecom_application.repository.CartItemRepository;
import com.app.ecom_application.repository.ProductsRepository;
import com.app.ecom_application.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductsRepository productsRepository;

    public CartItemService(CartItemRepository cartItemRepository, UserRepository userRepository, ProductsRepository productsRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productsRepository = productsRepository;
    }

//    public boolean addToCart(Long userId, CartItemDtoRequest request) {
//        Optional<ProductsEntity> productOpt = productsRepository.findById(request.getProductId());
//        if (productOpt.isEmpty()) {
//            return false;
//        }
//        ProductsEntity productsEntity = productOpt.get();
//        if (productsEntity.getStockQuantity() < request.getStockQuantity()) {
//            return false;
//        }
//        //check for the user
//        Optional<UserEntity> userOpt = userRepository.findById(userId);
//        if (userOpt.isEmpty()) {
//            return false;
//        }
//        UserEntity user = userOpt.get();
//        //if cart item exist in the cart then update the cart
//
//        CartItemEntity existingCartItem = cartItemRepository.findByUserAndProduct(user, productsEntity);
//
//        if (existingCartItem != null) {
//            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getStockQuantity());
//            existingCartItem.setPrice(productsEntity.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
//            // update the   quantity
//        } else {
//            // create a new cart item
//            CartItemEntity itemEntity = new CartItemEntity();
//            itemEntity.setQuantity(request.getStockQuantity());
//            itemEntity.setUserEntity(user);
//            itemEntity.setProductsEntity(productsEntity);
//            itemEntity.setPrice(productsEntity.getPrice().multiply(BigDecimal.valueOf(request.getStockQuantity())));
//            cartItemRepository.save(itemEntity);
//        }
//        return  true;

//    }

    public boolean addToCart(Long userId, CartItemDtoRequest request) {
        Optional<ProductsEntity> productOpt = productsRepository.findById(request.getProductId());
        if (productOpt.isEmpty()) {
            return false;
        }

        ProductsEntity product = productOpt.get();
        if (product.getStockQuantity() < request.getStockQuantity()) {
            return false;
        }
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return false;
        }
        UserEntity user = userOpt.get();
        // check if the  item already exist if exist then  update
        CartItemEntity existingEntity = new CartItemEntity();
        existingEntity = cartItemRepository.findByUserEntityAndProductsEntity(user, product);
        if (existingEntity != null) {
            existingEntity.setStockQuantity(existingEntity.getStockQuantity() + request.getStockQuantity());
            existingEntity.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingEntity.getStockQuantity())));
            cartItemRepository.save(existingEntity);
        }
            CartItemEntity cartItem = new CartItemEntity();
        cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getStockQuantity())));
        cartItem.setUserEntity(user);
        cartItem.setStockQuantity(request.getStockQuantity());
        cartItem.setProductsEntity(product);
        cartItemRepository.save(cartItem);
        return true;
    }

    @Transactional
    public boolean deleteItemFromCart(Long productId, Long userId) {
        Optional<ProductsEntity> productOpt = productsRepository.findById(productId);
        if(productOpt.isEmpty()){
            return  false;
        }
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if(userOpt.isEmpty()){
            return  false;
        }
        ProductsEntity productsEntity = productOpt.get();
        UserEntity user  = userOpt.get();
//        CartItemEntity cartItem  = new CartItemEntity();
//        cartItem.setProductsEntity(productsEntity);
//        cartItem.setUserEntity(user);
        cartItemRepository.deleteByUserEntityAndProductsEntity(user,productsEntity);
        return  true;
    }
    public List<CartItemEntity> fetchCart(Long userId) {
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            throw new IllegalArgumentException("User not found ");
        }
        UserEntity user = userOpt.get();
       return   cartItemRepository.findByUserEntity(user);


    }

    public void clearCart(Long userId) {
       Optional<UserEntity> user= userRepository.findById(userId);
        if(user.isPresent()){
            cartItemRepository.deleteByUserEntity(user);
        }
    }
}