package com.cart.repository;

import com.cart.dto.CustomCart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface CustomCartRepository extends JpaRepository<CustomCart, Long> {
    Optional<CustomCart> findByUserIdAndTitle(String userId, String title);
    List<CustomCart> findByUserId(String userId); // userId로 커스텀 장바구니를 조회
}
