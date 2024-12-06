package com.cart.controller;

import com.cart.dto.CartItem;
import com.cart.dto.CustomCart;
import com.cart.dto.CustomCartItem;
import com.cart.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItem>> getCartItems(@RequestHeader("X-User-Id") String userId) {
        log.info("Fetching cart items for userId: {}", userId);
        List<CartItem> cartItems = cartService.getCartItems(userId);
        return ResponseEntity.ok(cartItems);
    }

    @PostMapping("/items")
    public ResponseEntity<CartItem> addItemToCart(@RequestHeader("X-User-Id") String userId, @RequestBody CartItem cartItem) {
        log.info("Adding item to cart for userId: {}", userId);
        CartItem addedItem = cartService.addItemToCart(userId, cartItem);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> removeItemFromCart(@RequestHeader("X-User-Id") String userId, @PathVariable Long id) {
        log.info("Removing productCode {} from cart for userId: {}", id, userId);
        cartService.removeItemFromCart(userId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/custom")
    public ResponseEntity<List<CustomCart>> getCustomCartItems(@RequestHeader("X-User-Id") String userId) {
        log.info("Fetching custom cart items for userId: {}", userId);
        List<CustomCart> customCarts = cartService.getCustomCartItems(userId);
        return ResponseEntity.ok(customCarts);
    }

    @PostMapping("/custom")
    public ResponseEntity<CustomCart> createCustomCart(@RequestHeader("X-User-Id") String userId, @RequestBody CustomCart customCart) {
        log.info("Creating custom cart for userId: {}", userId);
        CustomCart createdCart = cartService.createCustomCart(userId, customCart);
        return ResponseEntity.status(201).body(createdCart);
    }

    @PostMapping("/custom/item")
    public ResponseEntity<CustomCartItem> addItemToCustomCart(@RequestHeader("X-User-Id") String userId, @PathVariable String customCartId, @RequestBody CustomCartItem customCartItem) {
        log.info("Adding item to custom cart {} for userId: {}", customCartId, userId);
        CustomCartItem addedItem = cartService.addItemToCustomCart(userId, customCartId, customCartItem);
        return ResponseEntity.status(201).body(addedItem);
    }

    @DeleteMapping("/custom/item/{itemCode}")
    public ResponseEntity<Void> removeItemFromCustomCart(@RequestHeader("X-User-Id") String userId, @PathVariable String customCartId, @PathVariable String itemCode) {
        log.info("Removing item {} from custom cart {} for userId: {}", itemCode, customCartId, userId);
        cartService.removeItemFromCustomCart(userId, customCartId, itemCode);
        return ResponseEntity.noContent().build();
    }
}
