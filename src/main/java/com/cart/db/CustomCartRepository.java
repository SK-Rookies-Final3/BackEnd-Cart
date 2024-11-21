package com.cart.db;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomCartRepository extends JpaRepository<CustomCart, Long> {
    CustomCart findByUserId(String userId);  // 사용자 ID로 커스텀 장바구니 조회
}
