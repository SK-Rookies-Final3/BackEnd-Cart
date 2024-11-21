package com.cart.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int code;  // 기본 키

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")  // 장바구니와의 관계 설정
    private Cart cart;  // 어느 장바구니에 속하는지

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
