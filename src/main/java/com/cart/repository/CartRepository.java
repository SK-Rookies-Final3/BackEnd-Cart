package com.cart.repository;

import com.cart.dto.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

// 장바구니 Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(String userId);
}

