package com.app.ecom_application.repository;


import com.app.ecom_application.entity.CartItemEntity;
import com.app.ecom_application.entity.ProductsEntity;
import com.app.ecom_application.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity,Long> {
CartItemEntity findByUserEntityAndProductsEntity(UserEntity user , ProductsEntity entity);

    void deleteByUserEntityAndProductsEntity(UserEntity user, ProductsEntity productsEntity);

    List<CartItemEntity> findByUserEntity(UserEntity user);

    void deleteByUserEntity(Optional<UserEntity> user);
}
