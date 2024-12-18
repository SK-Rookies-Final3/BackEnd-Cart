package com.cart.repository;

import com.cart.dto.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUserId(int userId);

    Optional<CartItem> findByUserIdAndProductCode(int userId, int productCode);
}
