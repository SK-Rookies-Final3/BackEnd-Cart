package com.cart.dto;

public class CustomCartItemDto {
    private String productCode;  // 상품 코드
    private String productName;  // 상품명
    private String thumbnail_url; // 상품 이미지 URL
    private int quantity;        // 수량
    private String size;         // 사이즈
    private String color;        // 색상
    private int xCoordinate;     // x좌표
    private int yCoordinate;     // y좌표

    // Getters and Setters
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(int YCoordinate) {
        this.yCoordinate = YCoordinate;
    }
}
