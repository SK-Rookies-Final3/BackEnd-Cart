package com.cart.dto;

public class CartItem {
    private String productCode;
    private String productName;
    private String productImage;
    private int quantity;
    private String size;
    private String color;
    private double price;

    // 기본 생성자 추가
    public CartItem() {}

    // 파라미터를 받는 생성자 추가
    public CartItem(String productCode, String productName, int quantity) {
        this.productCode = productCode;
        this.productName = productName;
        this.quantity = quantity;
    }

    // Getter 추가
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
