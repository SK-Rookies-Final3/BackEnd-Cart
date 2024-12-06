package com.cart.service;

import com.cart.dto.CartItem;
import com.cart.dto.CustomCart;
import com.cart.dto.CustomCartItem;
import com.cart.repository.CartItemRepository;
import com.cart.repository.CustomCartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final CustomCartRepository customCartRepository;

    public CartService(CartItemRepository cartItemRepository, CustomCartRepository customCartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.customCartRepository = customCartRepository;
    }

    // 장바구니 항목 추가
    public CartItem addItemToCart(String userId, CartItem cartItem) {
        log.info("Adding productCode {} to cart for userId: {}", cartItem.getProductCode(), userId);
        return cartItemRepository.save(cartItem);
    }

    // 장바구니 항목 삭제
    public void removeItemFromCart(String userId, Long id) {
        log.info("Removing productCode {} from cart for userId: {}", id, userId);
        cartItemRepository.deleteById(id);
    }

    // 장바구니 항목 조회
    public List<CartItem> getCartItems(String userId) {
        log.info("Fetching cart items for userId: {}", userId);
        return cartItemRepository.findAll();
    }

    // 커스텀 장바구니 항목 조회
    public List<CustomCart> getCustomCartItems(String userId) {
        log.info("Fetching custom cart items for userId: {}", userId);
        return customCartRepository.findByUserId(userId);
    }

    // 커스텀 장바구니 생성 (자동 제목 처리 및 중복 처리)
    public CustomCart createCustomCart(String userId, CustomCart customCart) {
        log.info("Creating custom cart for userId: {}", userId);

        // 제목이 없으면 자동 생성
        if (customCart.getTitle() == null || customCart.getTitle().isEmpty()) {
            customCart.setTitle(generateUniqueTitle(userId));
        }

        // 제목 중복 처리
        Optional<CustomCart> existingCart = customCartRepository.findByUserIdAndTitle(userId, customCart.getTitle());
        int count = 1;
        while (existingCart.isPresent()) {
            customCart.setTitle(customCart.getTitle() + " (" + count + ")");
            count++;
            existingCart = customCartRepository.findByUserIdAndTitle(userId, customCart.getTitle());
        }

        return customCartRepository.save(customCart);
    }

    // 커스텀 장바구니 제목 중복 방지를 위한 유니크 제목 생성
    private String generateUniqueTitle(String userId) {
        return "Custom Cart for " + userId;
    }

    // 커스텀 장바구니 아이템 추가
    public CustomCartItem addItemToCustomCart(String userId, String customCartId, CustomCartItem customCartItem) {
        log.info("Adding item {} to custom cart {} for userId: {}", customCartItem.getItemCode(), customCartId, userId);
        CustomCart customCart = customCartRepository.findById(Long.parseLong(customCartId))
                .orElseThrow(() -> new IllegalArgumentException("Custom cart not found"));

        customCartItem.setId(null); // 기존 ID를 설정하지 않음
        customCart.getItems().add(customCartItem);
        customCartRepository.save(customCart); // 아이템을 추가하고 저장

        return customCartItem;
    }

    // 커스텀 장바구니 아이템 삭제
    public void removeItemFromCustomCart(String userId, String customCartId, String itemCode) {
        log.info("Removing item {} from custom cart {} for userId: {}", itemCode, customCartId, userId);
        CustomCart customCart = customCartRepository.findById(Long.parseLong(customCartId))
                .orElseThrow(() -> new IllegalArgumentException("Custom cart not found"));

        customCart.getItems().removeIf(item -> item.getItemCode().equals(itemCode));
        customCartRepository.save(customCart);
    }
}
