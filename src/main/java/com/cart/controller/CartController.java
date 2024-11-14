package com.cart.controller;

import com.cart.dto.Cart;
import com.cart.dto.CartItem;
import com.cart.dto.CustomCart;
import com.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // 장바구니 조회
    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable String userId) {
        return cartService.getCart(userId);
    }

    // 장바구니에 상품 추가
    @PostMapping("/{userId}")
    public Cart addToCart(@PathVariable String userId, @RequestBody CartItem cartItem) {
        return cartService.addToCart(userId, cartItem);
    }

    // 장바구니에서 상품 제거
    @DeleteMapping("/{userId}/items/{productCode}")
    public Cart removeFromCart(@PathVariable String userId, @PathVariable String productCode) {
        return cartService.removeFromCart(userId, productCode);
    }

    // 커스텀 장바구니 조회
    @GetMapping("/custom/{userId}/{customCartId}")
    public CustomCart getCustomCart(@PathVariable String userId, @PathVariable String customCartId) {
        return cartService.getCustomCart(userId, customCartId);
    }

    // 커스텀 장바구니 생성
    @PostMapping("/custom/{userId}")
    public CustomCart createCustomCart(@PathVariable String userId, @RequestBody CustomCart customCart) {
        return cartService.createCustomCart(userId, customCart);
    }

    // 커스텀 장바구니 삭제
    @DeleteMapping("/custom/{userId}/{customCartId}")
    public void deleteCustomCart(@PathVariable String userId, @PathVariable String customCartId) {
        cartService.deleteCustomCart(userId, customCartId);
    }

    // 커스텀 장바구니 수정
    @PutMapping("/custom/{userId}/{customCartId}")
    public CustomCart updateCustomCart(@PathVariable String userId, @PathVariable String customCartId, @RequestBody CustomCart customCart) {
        return cartService.updateCustomCart(userId, customCartId, customCart);
    }
}
