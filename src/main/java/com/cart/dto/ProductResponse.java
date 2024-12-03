package com.cart.dto;

import java.util.List;

public class ProductResponse {
    private int code; // 상품 코드
    private String name; // 상품명
    private int price; // 가격
    private List<String> thumbnailUrl; // 썸네일 이미지 리스트

    // Default constructor
    public ProductResponse() {}

    // Constructor with all fields
    public ProductResponse(int code, String name, int price, List<String> thumbnailUrl) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.thumbnailUrl = thumbnailUrl;
    }

    // Getter for code
    public int getCode() {
        return code;
    }

    // Setter for code
    public void setCode(int code) {
        this.code = code;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for price
    public int getPrice() {
        return price;
    }

    // Setter for price
    public void setPrice(int price) {
        this.price = price;
    }

    // Getter for thumbnailUrl
    public List<String> getThumbnailUrl() {
        return thumbnailUrl;
    }

    // Setter for thumbnailUrl
    public void setThumbnailUrl(List<String> thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
