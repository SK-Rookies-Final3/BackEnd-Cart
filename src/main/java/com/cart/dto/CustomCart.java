package com.cart.dto;

import java.util.List;

public class CustomCart {

    private Long id;
    private String cartName;
    private List<CustomCartItem> items;

    // Constructor, Getters, Setters
    public CustomCart(Long id, String cartName, List<CustomCartItem> items) {
        this.id = id;
        this.cartName = cartName;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCartName() {
        return cartName;
    }

    public void setCartName(String cartName) {
        this.cartName = cartName;
    }

    public List<CustomCartItem> getItems() {
        return items;
    }

    public void setItems(List<CustomCartItem> items) {
        this.items = items;
    }
}
