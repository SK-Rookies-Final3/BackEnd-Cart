package com.cart.controller;

import com.cart.dto.CartDto;
import com.cart.dto.CartItemDto;
import com.cart.dto.CartItemPositionUpdateDto;
import com.cart.dto.CustomCartDto;
import com.cart.exception.CustomCartNotFoundException;
import com.cart.exception.ItemNotFoundException;
import com.cart.service.CartService;
import com.cart.service.CustomCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // Get cart items using @RequestHeader instead of HttpServletRequest
    @GetMapping("/items")
    public ResponseEntity<CartDto> getCart(@RequestHeader("X-User-Id") String userId) {
        try {
            CartDto cart = cartService.getCart(userId);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Add item to cart using @RequestHeader instead of HttpServletRequest
    @PostMapping("/items")
    public ResponseEntity<CartDto> addItemToCart(@RequestBody CartItemDto item, @RequestHeader("X-User-Id") String userId) {
        try {
            CartDto cart = cartService.addItemToCart(userId, item);
            return new ResponseEntity<>(cart, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Remove item from cart using @RequestHeader instead of HttpServletRequest
    @DeleteMapping("/items/{productCode}")
    public ResponseEntity<CartDto> removeItemFromCart(@PathVariable String productCode, @RequestHeader("X-User-Id") String userId) {
        try {
            CartDto cart = cartService.removeItemFromCart(userId, productCode);
            return new ResponseEntity<>(cart, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Get custom cart using @RequestHeader instead of HttpServletRequest
    @GetMapping("/custom/items")
    public ResponseEntity<CustomCartDto> getCustomCart(@RequestHeader("X-User-Id") String userId) {
        try {
            CustomCartDto customCartDto = customCartService.getCustomCart(userId);
            return new ResponseEntity<>(customCartDto, HttpStatus.OK);
        } catch (CustomCartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Update cart item position
    @PostMapping("/update-cart-item-position")
    public void updateCartItemPosition(@RequestBody CartItemPositionUpdateDto dto) {
        customCartService.updateCartItemPosition(dto);
    }

    // Create custom cart using @RequestHeader instead of HttpServletRequest
    @PostMapping("/custom")
    public ResponseEntity<CustomCartDto> createCustomCart(@RequestBody CustomCartDto customCartDto, @RequestHeader("X-User-Id") String userId) {
        try {
            CustomCartDto createdCart = customCartService.createCustomCart(customCartDto, userId);
            return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Update custom cart title using @RequestHeader instead of HttpServletRequest
    @PutMapping("/custom/title")
    public ResponseEntity<CustomCartDto> updateCustomCartTitle(@RequestBody String newTitle, @RequestHeader("X-User-Id") String userId) {
        try {
            CustomCartDto updatedCart = customCartService.updateCustomCartTitle(newTitle, userId);
            return new ResponseEntity<>(updatedCart, HttpStatus.OK);
        } catch (CustomCartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Remove item from custom cart using @RequestHeader instead of HttpServletRequest
    @DeleteMapping("/custom/items/{productCode}")
    public ResponseEntity<CustomCartDto> removeItemFromCustomCart(@PathVariable String productCode, @RequestHeader("X-User-Id") String userId) {
        try {
            CustomCartDto updatedCart = customCartService.removeItemFromCustomCart(productCode, userId);
            return new ResponseEntity<>(updatedCart, HttpStatus.NO_CONTENT);
        } catch (ItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
