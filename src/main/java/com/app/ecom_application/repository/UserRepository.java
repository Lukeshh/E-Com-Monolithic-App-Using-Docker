package com.app.ecom_application.repository;

import com.app.ecom_application.model.entity.CartItemEntity;
import com.app.ecom_application.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
