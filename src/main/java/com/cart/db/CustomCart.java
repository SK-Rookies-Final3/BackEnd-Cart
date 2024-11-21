package com.cart.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int code;  // 커스텀 장바구니 code

    @Column(name = "user_id", nullable = false, length = 50)
    private String userId;  // 사용자 ID

    @Column(name = "custom_cart_title", length = 255)
    private String customCartTitle; // 커스텀 장바구니 제목

    @OneToMany(mappedBy = "customCart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomCartItem> items;  // 커스텀 장바구니의 상품들
}
