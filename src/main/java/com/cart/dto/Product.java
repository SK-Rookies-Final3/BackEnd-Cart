package com.cart.dto;

public class Product {

    private String productCode;
    private String name;
    private String image;
    private String size;
    private String color;
    private int price;

    // Constructor, Getters, Setters
    public Product(String productCode, String name, String image, String size, String color, int price) {
        this.productCode = productCode;
        this.name = name;
        this.image = image;
        this.size = size;
        this.color = color;
        this.price = price;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
