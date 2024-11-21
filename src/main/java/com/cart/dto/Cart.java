package com.cart.dto;

import java.util.List;

public class Cart {
    private String userId;  // 사용자 ID
    private List<CartItem> items;  // 장바구니에 담긴 상품들

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }
}
