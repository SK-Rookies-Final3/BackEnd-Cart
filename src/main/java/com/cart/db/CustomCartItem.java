package com.cart.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomCartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_cart_code") //
    private CustomCart customCart;

    @Column(name = "product_code", nullable = false, length = 50)
    private String productCode;

    @Column(name = "product_name", nullable = false, length = 255)
    private String productName;

    @Column(name = "thumbnail_url", length = 300, nullable = false)
    private String thumbnail_url;

    @Column(nullable = false)
    private int quantity;

    @Column(length = 10)
    private String size;

    @Column(length = 10)
    private String color;

    @Column(name = "x_coordinate", nullable = false)
    private int xCoordinate;

    @Column(name = "y_coordinate", nullable = false)
    private int yCoordinate;
}