package com.cart.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CustomCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tabId;

    private String userId;
    private String title;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomCartItem> items;  // 커스텀 장바구니에 포함된 아이템들
}
