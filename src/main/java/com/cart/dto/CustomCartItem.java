package com.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomCartItem {
    private String itemCode;
    private String productName;
    private String productImage;
    private int quantity;
    private String size;
    private String color;
    private int xCoordinate;
    private int yCoordinate;

    // getters and setters
}