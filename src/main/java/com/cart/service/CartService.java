package com.cart.service;

import com.cart.dto.CartItem;
import com.cart.dto.CustomCart;
import com.cart.dto.CustomCartItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CartService {

    private List<CartItem> cartItems = new ArrayList<>();
    private List<CustomCart> customCarts = new ArrayList<>();

    // 장바구니 항목 추가
    public CartItem addItemToCart(String userId, CartItem cartItem) {
        log.info("Adding item {} to cart for userId: {}", cartItem.getItemCode(), userId);
        cartItems.add(cartItem);
        return cartItem;
    }

    // 장바구니 항목 삭제
    public void removeItemFromCart(String userId, String itemCode) {
        log.info("Removing item {} from cart for userId: {}", itemCode, userId);
        cartItems.removeIf(item -> item.getItemCode().equals(itemCode));
    }

    // 장바구니 항목 조회
    public List<CartItem> getCartItems(String userId) {
        log.info("Fetching cart items for userId: {}", userId);
        return cartItems;
    }

    // 커스텀 장바구니 항목 조회
    public List<CustomCart> getCustomCartItems(String userId) {
        log.info("Fetching custom cart items for userId: {}", userId);
        return customCarts;
    }

    // 커스텀 장바구니 생성
    public CustomCart createCustomCart(String userId, CustomCart customCart) {
        log.info("Creating custom cart for userId: {}", userId);
        customCarts.add(customCart);
        return customCart;
    }

    // 커스텀 장바구니 항목 추가
    public CustomCartItem addItemToCustomCart(String userId, String customCartId, CustomCartItem customCartItem) {
        log.info("Adding item {} to custom cart {} for userId: {}", customCartItem.getItemCode(), customCartId, userId);
        // 커스텀 장바구니에 아이템 추가하는 로직
        return customCartItem;
    }

    // 커스텀 장바구니 항목 삭제
    public void removeItemFromCustomCart(String userId, String customCartId, String itemCode) {
        log.info("Removing item {} from custom cart {} for userId: {}", itemCode, customCartId, userId);
        // 커스텀 장바구니에서 아이템 제거하는 로직
    }
}
