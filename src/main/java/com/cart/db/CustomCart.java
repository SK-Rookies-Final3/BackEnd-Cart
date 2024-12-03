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
    private int code;

    @Column(name = "cart_code", nullable = false)
    private Long cartCode;  // Long 타입으로 변경 (여기서는 cartCode를 Long으로 가정)``    *`**```````````                                     ``````**************

    @Column(name = "user_id", nullable = false, length = 50)
    private String userId;

    @Column(name = "custom_cart_title", length = 255)
    private String customCartTitle;

    @OneToMany(mappedBy = "customCart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomCartItem> items;

    // Getter and Setter for userId and other fields
}
