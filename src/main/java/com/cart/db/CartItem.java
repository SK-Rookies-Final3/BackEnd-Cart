package com.cart.db;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_code")
    private Cart cart;

    @Column(name = "product_code", nullable = false, length = 50)
    private String productCode;

    @Column(name = "product_name", nullable = false, length = 255)
    private String productName;

    @Column(name = "thumbnail_url", length = 300, nullable = false)
    private String thumbnail_url;

    @Column(nullable = false)
    private int quantity;

    @Column(length = 20)
    private String size;

    @Column(length = 20)
    private String color;
}