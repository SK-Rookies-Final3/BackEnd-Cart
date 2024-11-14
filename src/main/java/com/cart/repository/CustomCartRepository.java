package com.cart.repository;

import com.cart.dto.CustomCart;
import org.springframework.data.jpa.repository.JpaRepository;

// 커스텀 장바구니 Repository
public interface CustomCartRepository extends JpaRepository<CustomCart, Long> {
    CustomCart findByUserIdAndCustomCartName(String userId, String customCartName);
}