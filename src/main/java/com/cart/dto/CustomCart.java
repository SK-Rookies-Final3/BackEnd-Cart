package com.cart.dto;

import java.util.List;

// 커스텀 장바구니 DTO
public class CustomCart {
    private String userId;          // 사용자 ID
    private String customCartName;  // 커스텀 장바구니 이름
    private List<CustomCartItem> items;  // 커스텀 장바구니 아이템 리스트

    // getters and setters
}