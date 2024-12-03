package com.cart.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomCartItemRepository extends CrudRepository<CustomCartItem, Long> {
    List<CustomCartItem> findByCustomCart_CartCode(Long customCartCode);
}
