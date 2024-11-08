package com.cart.controller;

import com.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // 장바구니 조회
    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable String userId) {
        return cartService.getCartByUser(userId);
    }

    // 장바구니에 상품 추가
    @PostMapping("/{userId}/add")
    public void addItemToCart(@PathVariable String userId, @RequestBody CartItem cartItem) {
        cartService.addItemToCart(userId, cartItem);
    }

    // 장바구니에서 상품 삭제
    @DeleteMapping("/{userId}/remove/{productCode}")
    public void removeItemFromCart(@PathVariable String userId, @PathVariable String productCode) {
        cartService.removeItemFromCart(userId, productCode);
    }

    // 커스텀 장바구니 만들기
    @PostMapping("/{userId}/custom")
    public void createCustomCart(@PathVariable String userId, @RequestBody CustomCart customCart) {
        cartService.createCustomCart(userId, customCart);
    }
}
