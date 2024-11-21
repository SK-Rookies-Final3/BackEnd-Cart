package com.cart.db;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(String userId);  // 사용자 ID로 장바구니 조회
}
