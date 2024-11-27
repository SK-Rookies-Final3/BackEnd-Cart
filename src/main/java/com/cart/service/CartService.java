package com.cart.service;

import com.cart.dto.Cart;
import com.cart.dto.CartItem;
import com.cart.util.HeaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpServletRequest;


@Service
public class CartService {

    private final RestTemplate restTemplate;
    private static final String CART_API_URL = "/api/cart";

    @Autowired
    public CartService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 장바구니 조회
    public Cart getCart(HttpServletRequest request) {
        String userId = HeaderUtils.getUserIdFromHeaders(request);  // HttpServletRequest로 사용자 ID 추출
        String url = CART_API_URL + "/items";
        try {
            ResponseEntity<Cart> responseEntity = restTemplate.getForEntity(url, Cart.class, userId);
            if (responseEntity.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Failed to fetch cart");
            }
            return responseEntity.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while accessing cart API", e);
        }
    }

    // 장바구니 상품 추가
    public Cart addItemToCart(HttpServletRequest request, CartItem item) {
        String userId = HeaderUtils.getUserIdFromHeaders(request);  // HttpServletRequest로 사용자 ID 추출
        String url = CART_API_URL + "/items";
        try {
            ResponseEntity<Cart> responseEntity = restTemplate.postForEntity(url, item, Cart.class, userId);
            if (responseEntity.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Failed to add item to cart");
            }
            return responseEntity.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while adding item to cart", e);
        }
    }

    // 장바구니 상품 제거
    public Cart removeItemFromCart(HttpServletRequest request, String productCode) {
        String userId = HeaderUtils.getUserIdFromHeaders(request);  // HttpServletRequest로 사용자 ID 추출
        String url = CART_API_URL + "/items/{productCode}";
        try {
            restTemplate.delete(url, userId, productCode);
            return getCart(request); // 상품 제거 후 장바구니를 다시 불러옴
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while removing item from cart", e);
        }
    }
}
