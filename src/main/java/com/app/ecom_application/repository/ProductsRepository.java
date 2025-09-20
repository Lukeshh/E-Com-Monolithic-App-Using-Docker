package com.app.ecom_application.repository;

import com.app.ecom_application.model.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductsRepository extends JpaRepository<ProductsEntity, Long> {
    List<ProductsEntity> findByActiveTrue();

    @Query("Select p from ProductsEntity p  where p.active= true AND p.stockQuantity > 0 AND LOWER(p.name) LIKE  LOWER(CONCAT('%', :keyword, '%') )")
    List<ProductsEntity> searchProducts(@Param("keyword") String keyword);
}
