package com.cart.dto;

import java.util.List;

public class Cart {

    private Long cartId;
    private String memberId;
    private String cartName;
    private List<CartItem> items;

    // Constructor, Getters, Setters
    public Cart(Long cartId, String memberId, String cartName, List<CartItem> items) {
        this.cartId = cartId;
        this.memberId = memberId;
        this.cartName = cartName;
        this.items = items;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCartName() {
        return cartName;
    }

    public void setCartName(String cartName) {
        this.cartName = cartName;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }
}
