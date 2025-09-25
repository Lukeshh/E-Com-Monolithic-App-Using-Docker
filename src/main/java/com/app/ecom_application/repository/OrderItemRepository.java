package com.app.ecom_application.repository;

import com.app.ecom_application.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository  extends JpaRepository<OrderEntity,Long> {
}
