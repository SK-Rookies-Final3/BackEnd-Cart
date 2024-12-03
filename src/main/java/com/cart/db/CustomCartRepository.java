package com.cart.db;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CustomCartRepository extends JpaRepository<CustomCart, Long> {
    Optional<CustomCart> findByUserId(String userId);
    Optional<CustomCart> findByCustomCartTitle(String customCartTitle);
}