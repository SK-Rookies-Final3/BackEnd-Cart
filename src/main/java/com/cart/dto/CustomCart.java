package com.cart.dto;

import java.util.List;

public class CustomCart {
    private String userId;          // 사용자 ID
    private String customCartTitle; // 커스텀 장바구니 제목
    private List<CustomCartItem> items; // 커스텀 장바구니의 상품들

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCustomCartTitle() {
        return customCartTitle;
    }

    public void setCustomCartTitle(String customCartTitle) {
        this.customCartTitle = customCartTitle;
    }

    public List<CustomCartItem> getItems() {
        return items;
    }

    public void setItems(List<CustomCartItem> items) {
        this.items = items;
    }
}
