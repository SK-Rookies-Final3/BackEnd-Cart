package com.cart.dto;

public class CustomCartItem {

    private String productCode;
    private String productName;
    private String productImage;
    private int price;
    private int xCoordinate;
    private int yCoordinate;

    // Constructor, Getters, Setters
    public CustomCartItem(String productCode, String productName, String productImage, int price, int xCoordinate, int yCoordinate) {
        this.productCode = productCode;
        this.productName = productName;
        this.productImage = productImage;
        this.price = price;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

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

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
}
