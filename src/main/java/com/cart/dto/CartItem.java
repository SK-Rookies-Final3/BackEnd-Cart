package com.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private String itemCode;
    private String productCode;
    private String productName;
    private String productImage;
    private int quantity;
    private String size;
    private String color;
    private double price;

}
