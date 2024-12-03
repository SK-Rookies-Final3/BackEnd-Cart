package com.cart.controller;

import com.cart.dto.CartDto;
import com.cart.dto.CartItemDto;
import com.cart.dto.CartItemPositionUpdateDto;
import com.cart.dto.CustomCartDto;
import com.cart.exception.CustomCartNotFoundException;
import com.cart.exception.ItemNotFoundException;
import com.cart.service.CartService;
import com.cart.service.CustomCartService;
import jakarta.servlet.http.HttpServletRequest;
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

    @GetMapping("/items")
    public ResponseEntity<CartDto> getCart(HttpServletRequest request) {
        try {
            CartDto cart = cartService.getCart(request);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/items")
    public ResponseEntity<CartDto> addItemToCart(@RequestBody CartItemDto item, HttpServletRequest request) {
        try {
            CartDto cart = cartService.addItemToCart(request, item);
            return new ResponseEntity<>(cart, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/items/{productCode}")
    public ResponseEntity<CartDto> removeItemFromCart(@PathVariable String productCode, HttpServletRequest request) {
        try {
            CartDto cart = cartService.removeItemFromCart(request, productCode);  // Cart -> CartDto
            return new ResponseEntity<>(cart, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/custom/items")
    public ResponseEntity<CustomCartDto> getCustomCart(HttpServletRequest request) {
        try {
            CustomCartDto customCartDto = customCartService.getCustomCart(request);
            return new ResponseEntity<>(customCartDto, HttpStatus.OK);
        } catch (CustomCartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PostMapping("/update-cart-item-position")
    public void updateCartItemPosition(@RequestBody CartItemPositionUpdateDto dto) {
        customCartService.updateCartItemPosition(dto);
    }

    @PostMapping("/custom")
    public ResponseEntity<CustomCartDto> createCustomCart(@RequestBody CustomCartDto customCartDto, HttpServletRequest request) {
        try {
            CustomCartDto createdCart = customCartService.createCustomCart(customCartDto, request);
            return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/custom/title")
    public ResponseEntity<CustomCartDto> updateCustomCartTitle(@RequestBody String newTitle, HttpServletRequest request) {
        try {
            CustomCartDto updatedCart = customCartService.updateCustomCartTitle(newTitle, request);
            return new ResponseEntity<>(updatedCart, HttpStatus.OK);
        } catch (CustomCartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/custom/items/{productCode}")
    public ResponseEntity<CustomCartDto> removeItemFromCustomCart(@PathVariable String productCode, HttpServletRequest request) {
        try {
            CustomCartDto updatedCart = customCartService.removeItemFromCustomCart(productCode, request);
            return new ResponseEntity<>(updatedCart, HttpStatus.NO_CONTENT);
        } catch (ItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
