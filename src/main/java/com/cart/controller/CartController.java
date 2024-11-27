package com.cart.controller;

import com.cart.dto.Cart;
import com.cart.dto.CartItem;
import com.cart.dto.CustomCart;
import com.cart.service.CartService;
import com.cart.service.CustomCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

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
    @GetMapping("/items")
    public ResponseEntity<Cart> getCart(HttpServletRequest request) {
        Cart cart = cartService.getCart(request);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    // 장바구니에 상품 추가
    @PostMapping("/items")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Cart> addItemToCart(@RequestBody CartItem item, HttpServletRequest request) {
        Cart cart = cartService.addItemToCart(request, item);
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }

    // 장바구니에서 상품 삭제
    @DeleteMapping("/items/{productCode}")
    public ResponseEntity<Cart> removeItemFromCart(@PathVariable String productCode, HttpServletRequest request) {
        Cart cart = cartService.removeItemFromCart(request, productCode);
        return new ResponseEntity<>(cart, HttpStatus.NO_CONTENT);
    }

    // 커스텀 장바구니 조회
    @GetMapping("/custom/items")
    public ResponseEntity<CustomCart> getCustomCart(HttpServletRequest request) {
        CustomCart customCart = customCartService.getCustomCart(request);
        return new ResponseEntity<>(customCart, HttpStatus.OK);
    }

    // 커스텀 장바구니 등록
    @PostMapping("/custom")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<CustomCart> createCustomCart(@RequestBody CustomCart customCart, HttpServletRequest request) {
        CustomCart createdCart = customCartService.createCustomCart(customCart, request);
        return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
    }

    // 커스텀 장바구니 제목 수정
    @PatchMapping("/custom/updateTitle")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<CustomCart> updateCustomCartTitle(@RequestBody String newTitle, HttpServletRequest request) {
        CustomCart updatedCart = customCartService.updateCustomCartTitle(newTitle, request);
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }

    // 커스텀 장바구니에서 상품 삭제
    @DeleteMapping("/custom/items/{productCode}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<CustomCart> removeItemFromCustomCart(@PathVariable String productCode, HttpServletRequest request) {
        CustomCart updatedCustomCart = customCartService.removeItemFromCustomCart(productCode, request);
        return new ResponseEntity<>(updatedCustomCart, HttpStatus.NO_CONTENT);
    }
}
