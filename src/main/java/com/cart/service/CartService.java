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

    private List<CustomCart> customCarts = new ArrayList<>();
    private final CartItemRepository cartItemRepository;
    private final CustomCartRepository customCartRepository;

    public CartService(CartItemRepository cartItemRepository, CustomCartRepository customCartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.customCartRepository = customCartRepository;
    }

    // 장바구니 항목 추가
    public CartItem addItemToCart(String userId, CartItem cartItem) {
        log.info("Adding productCode {} to cart for userId: {}", cartItem.getProductCode(), userId);

        // 동일한 userId와 productCode를 가진 장바구니 항목이 이미 존재하는지 확인
        Optional<CartItem> existingCartItem = cartItemRepository.findAll()
                .stream()
                .filter(item -> item.getProductCode().equals(cartItem.getProductCode()))
                .findFirst();

        // 중복된 항목이 있다면, quantity만 증가시키기
        if (existingCartItem.isPresent()) {
            CartItem existingItem = existingCartItem.get();
            existingItem.setQuantity(existingItem.getQuantity() + cartItem.getQuantity()); // 수량을 더함
            cartItemRepository.save(existingItem);  // 기존 항목 업데이트
            log.info("Updated quantity for productCode {} in cart for userId: {}", cartItem.getProductCode(), userId);
            return existingItem;  // 업데이트된 항목 반환
        }

        // 중복된 항목이 없다면 새 항목을 추가
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

    // 선택된 장바구니 항목으로 커스텀 장바구니 생성
    public CustomCart createCustomCartFromCart(String userId, List<Long> cartItemIds, String customCartTitle) {
        log.info("Creating custom cart for userId: {} from cart items", userId);

        // 장바구니 항목 ID로 항목들을 가져옵니다.
        List<CartItem> cartItems = cartItemRepository.findAllById(cartItemIds);

        // 제목이 없으면 기본 제목을 생성
        if (customCartTitle == null || customCartTitle.isEmpty()) {
            customCartTitle = generateUniqueTitle(userId);
        }

        // 제목 중복 방지 처리
        Optional<CustomCart> existingCart = customCartRepository.findByUserIdAndTitle(userId, customCartTitle);
        int count = 1;
        while (existingCart.isPresent()) {
            customCartTitle = customCartTitle + " (" + count + ")";
            count++;
            existingCart = customCartRepository.findByUserIdAndTitle(userId, customCartTitle);
        }

        // 커스텀 장바구니 생성
        List<CustomCartItem> customCartItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            customCartItems.add(new CustomCartItem(
                    null, cartItem.getProductCode(), cartItem.getProductName(),
                    cartItem.getProductImage(), cartItem.getQuantity(),
                    cartItem.getSize(), cartItem.getColor(), 0, 0 // 기본 좌표 값
            ));
        }

        CustomCart customCart = new CustomCart(null, userId, customCartTitle, customCartItems);
        return customCartRepository.save(customCart);
    }

    // 커스텀 장바구니 제목 수정
    public CustomCart updateCustomCartTitle(Long customCartId, String newTitle) {
        log.info("Updating custom cart title to {}", newTitle);

        CustomCart customCart = customCartRepository.findById(customCartId)
                .orElseThrow(() -> new IllegalArgumentException("Custom cart not found"));

        // 제목 중복 처리
        Optional<CustomCart> existingCart = customCartRepository.findByUserIdAndTitle(customCart.getUserId(), newTitle);
        int count = 1;
        while (existingCart.isPresent()) {
            newTitle = newTitle + " (" + count + ")";
            count++;
            existingCart = customCartRepository.findByUserIdAndTitle(customCart.getUserId(), newTitle);
        }

        customCart.setTitle(newTitle);
        return customCartRepository.save(customCart);
    }

    // 커스텀 장바구니 제목 중복 방지를 위한 유니크 제목 생성
    private String generateUniqueTitle(String userId) {
        return "Custom Cart for " + userId;
    }

    // 커스텀 장바구니 삭제
    public void deleteCustomCart(String userId, Long tabid) {
        log.info("Deleting custom cart {} for userId: {}", tabid, userId);
        CustomCart customCart = customCartRepository.findById(tabid)
                .orElseThrow(() -> new IllegalArgumentException("Custom cart not found"));

        if (!customCart.getUserId().equals(userId)) {
            throw new IllegalArgumentException("User does not own this custom cart");
        }

        customCartRepository.delete(customCart);
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


}
