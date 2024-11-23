package com.cart.service;

import com.cart.dto.CustomCart;
import com.cart.dto.CustomCartItem;
import com.cart.util.HeaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;

@Service
public class CustomCartService {

    private final RestTemplate restTemplate;
    private static final String CUSTOM_CART_API_URL = "/api/cart/custom";

    @Autowired
    public CustomCartService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 장바구니 커스텀 담기
    public CustomCart getCustomCart(ServerWebExchange exchange) {
        String userId = HeaderUtils.getUserIdFromHeaders(exchange);
        String url = CUSTOM_CART_API_URL + "/items";
        try {
            ResponseEntity<CustomCart> responseEntity = restTemplate.getForEntity(url, CustomCart.class, userId);
            if (responseEntity.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Failed to fetch custom cart");
            }
            return responseEntity.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while accessing custom cart API", e);
        }
    }

    // 장바구니 상품 커스텀 담기
    public CustomCart createCustomCart(ServerWebExchange exchange, CustomCart customCart) {
        String userId = HeaderUtils.getUserIdFromHeaders(exchange);
        String url = CUSTOM_CART_API_URL;
        try {
            ResponseEntity<CustomCart> responseEntity = restTemplate.postForEntity(url, customCart, CustomCart.class, userId);
            if (responseEntity.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Failed to create custom cart");
            }
            return responseEntity.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while creating custom cart", e);
        }
    }

    // 장바구니 커스텀 제목 업데이트
    public CustomCart updateCustomCartTitle(ServerWebExchange exchange, String newTitle) {
        String userId = HeaderUtils.getUserIdFromHeaders(exchange);
        String url = CUSTOM_CART_API_URL + "/updateTitle";
        try {
            restTemplate.put(url, newTitle, userId);
            return getCustomCart(exchange); // 제목 업데이트 후 장바구니를 다시 불러옴
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while updating custom cart title", e);
        }
    }

    // 장바구니 커스텀 상품 제거
    public CustomCart removeItemFromCustomCart(ServerWebExchange exchange, String productCode) {
        String userId = HeaderUtils.getUserIdFromHeaders(exchange);
        String url = CUSTOM_CART_API_URL + "/items/{productCode}";
        try {
            restTemplate.delete(url, userId, productCode);
            return getCustomCart(exchange); // 상품 제거 후 장바구니를 다시 불러옴
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while removing item from custom cart", e);
        }
    }
}
