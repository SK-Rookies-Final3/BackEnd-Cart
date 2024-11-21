package com.cart.controller;

import com.cart.dto.Cart;
import com.cart.dto.CartItem;
import com.cart.dto.CustomCart;
import com.cart.service.CartService;
import com.cart.service.CustomCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final CustomCartService customCartService;

    @Autowired
    public CartController(CartService cartService, CustomCartService customCartService) {
        this.cartService = cartService;
        this.customCartService = customCartService;
    }

    // 장바구니 조회
    @GetMapping("/get")
    public Mono<Cart> getCart(ServerWebExchange exchange) {
        return cartService.getCart(exchange);  // ServerWebExchange를 전달하여 헤더에서 사용자 ID 추출
    }

    // 장바구니에 상품 추가
    @PostMapping("/add")
    public Mono<Cart> addItemToCart(ServerWebExchange exchange, @RequestBody CartItem item) {
        return cartService.addItemToCart(exchange, item);
    }

    // 장바구니에서 상품 삭제
    @DeleteMapping("/remove/{productCode}")
    public Mono<Cart> removeItemFromCart(ServerWebExchange exchange, @PathVariable String productCode) {
        return cartService.removeItemFromCart(exchange, productCode);
    }

    // 커스텀 장바구니 조회
    @GetMapping("/custom/get")
    public Mono<CustomCart> getCustomCart(ServerWebExchange exchange) {
        return customCartService.getCustomCart(exchange);
    }

    // 커스텀 장바구니 등록
    @PostMapping("/custom/create")
    public Mono<CustomCart> createCustomCart(ServerWebExchange exchange, @RequestBody CustomCart customCart) {
        return customCartService.createCustomCart(exchange, customCart);
    }

    // 커스텀 장바구니 제목 수정
    @PutMapping("/custom/updateTitle")
    public Mono<CustomCart> updateCustomCartTitle(ServerWebExchange exchange, @RequestBody String newTitle) {
        return customCartService.updateCustomCartTitle(exchange, newTitle);
    }

    // 커스텀 장바구니에서 상품 삭제
    @DeleteMapping("/custom/remove/{productCode}")
    public Mono<CustomCart> removeItemFromCustomCart(ServerWebExchange exchange, @PathVariable String productCode) {
        return customCartService.removeItemFromCustomCart(exchange, productCode);
    }
}
