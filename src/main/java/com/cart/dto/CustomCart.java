package com.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomCart {
    private String id;
    private String userId;
    private String title;
    private List<CustomCartItem> items;

    // getters and setters
}