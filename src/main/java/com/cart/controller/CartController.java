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


     // 장바구니 항목 목록 가져오기

    @GetMapping("/items")
    public ResponseEntity<List<CartItem>> getCartItems(@RequestHeader("X-User-Id") String userId) {
        log.info("Fetching cart items for userId: {}", userId);
        List<CartItem> cartItems = cartService.getCartItems(userId);
        return ResponseEntity.ok(cartItems);
    }


     // 장바구니에 항목 추가하기

    @PostMapping("/items")
    public ResponseEntity<CartItem> addItemToCart(@RequestHeader("X-User-Id") String userId, @RequestBody CartItem cartItem) {
        log.info("Adding item to cart for userId: {}", userId);
        CartItem addedItem = cartService.addItemToCart(userId, cartItem);
        return ResponseEntity.noContent().build();
    }

    // 장바구니 항목 수량 증가
    @PutMapping("/items/increase/{id}")
    public ResponseEntity<CartItem> increaseItemQuantity(@RequestHeader("X-User-Id") String userId,
                                                         @PathVariable Long id,
                                                         @RequestParam int quantity) {
        log.info("Increasing quantity for cartItemId: {} by {}", id, quantity);
        CartItem updatedItem = cartService.increaseQuantity(userId, id, quantity);
        return ResponseEntity.ok(updatedItem);
    }



    // 장바구니에서 항목 제거하기

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> removeItemFromCart(@RequestHeader("X-User-Id") String userId, @PathVariable Long id) {
        log.info("Removing productCode {} from cart for userId: {}", id, userId);
        cartService.removeItemFromCart(userId, id);
        return ResponseEntity.noContent().build();
    }


     // 커스텀 장바구니 항목 목록 가져오기

    @GetMapping("/custom")
    public ResponseEntity<List<CustomCart>> getCustomCartItems(@RequestHeader("X-User-Id") String userId) {
        log.info("Fetching custom cart items for userId: {}", userId);
        List<CustomCart> customCarts = cartService.getCustomCartItems(userId);
        return ResponseEntity.ok(customCarts);
    }


     // 커스텀 장바구니 생성하기

    @PostMapping("/custom")
    public ResponseEntity<CustomCart> createCustomCart(@RequestHeader("X-User-Id") String userId, @RequestBody CustomCart customCart) {
        log.info("Creating custom cart for userId: {}", userId);
        CustomCart createdCart = cartService.createCustomCart(userId, customCart);
        return ResponseEntity.status(201).body(createdCart);
    }


     //커스텀 장바구니에 항목 추가하기

    @PostMapping("/custom/item")
    public ResponseEntity<CustomCartItem> addItemToCustomCart(@RequestHeader("X-User-Id") String userId, @PathVariable String customCartId, @RequestBody CustomCartItem customCartItem) {
        log.info("Adding item to custom cart {} for userId: {}", customCartId, userId);
        CustomCartItem addedItem = cartService.addItemToCustomCart(userId, customCartId, customCartItem);
        return ResponseEntity.status(201).body(addedItem);
    }


     //커스텀 장바구니 제목 수정하기
    @PutMapping("/custom/{customCartId}/title")
    public ResponseEntity<CustomCart> updateCustomCartTitle(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable Long customCartId,
            @RequestParam String newTitle) {
        log.info("Updating title for custom cart {} for userId: {}", customCartId, userId);
        CustomCart updatedCart = cartService.updateCustomCartTitle(customCartId, newTitle);
        return ResponseEntity.ok(updatedCart);
    }


    // 커스텀 장바구니 삭제하기
    @DeleteMapping("/custom/{tabid}")
    public ResponseEntity<Void> deleteCustomCart(@RequestHeader("X-User-Id") String userId, @PathVariable Long tabid) {
        log.info("Deleting custom cart {} for userId: {}", tabid, userId);
        cartService.deleteCustomCart(userId, tabid);
        return ResponseEntity.noContent().build();
    }

}
