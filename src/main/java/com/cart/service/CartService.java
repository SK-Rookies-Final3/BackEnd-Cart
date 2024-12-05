package com.cart.service;

import com.cart.dto.CartItem;
import com.cart.dto.CustomCart;
import com.cart.dto.CustomCartItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class CartService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${cart.service.url}")
    private String cartServiceUrl;

    public CartService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    // 장바구니 조회
    public List<CartItem> getCartItems(String userId) {
        String url = cartServiceUrl + "/cart/{userId}";
        log.info("Fetching cart items for userId: {}", userId);

        try {
            ResponseEntity<List<CartItem>> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    getHttpEntity(userId),
                    new ParameterizedTypeReference<List<CartItem>>() {},
                    userId
            );

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return responseEntity.getBody();
            } else {
                log.error("Failed to get cart items, status code: {}", responseEntity.getStatusCode());
                throw new RuntimeException("Failed to get cart items");
            }
        } catch (Exception e) {
            log.error("Error while fetching cart items: {}", e.getMessage());
            throw new RuntimeException("Failed to get cart items", e);
        }
    }

    // 장바구니 상품 등록
    public CartItem addItemToCart(String userId, CartItem cartItem) {
        String url = cartServiceUrl + "/cart/{userId}/item";
        HttpEntity<CartItem> requestEntity = new HttpEntity<>(cartItem, getHeaders(userId));

        ResponseEntity<CartItem> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, CartItem.class, userId);

        if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
            return responseEntity.getBody();
        } else {
            throw new RuntimeException("Failed to add item to cart");
        }
    }

    // 장바구니 상품 삭제
    public void removeItemFromCart(String userId, String itemCode) {
        String url = cartServiceUrl + "/cart/{userId}/item/{itemCode}";
        HttpEntity<String> requestEntity = new HttpEntity<>(getHeaders(userId));

        ResponseEntity<Void> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Void.class, userId, itemCode);

        if (responseEntity.getStatusCode() != HttpStatus.NO_CONTENT) {
            throw new RuntimeException("Failed to remove item from cart");
        }
    }

    // 커스텀 장바구니 조회
    public List<CustomCart> getCustomCartItems(String userId) {
        String url = cartServiceUrl + "/customCart/{userId}";
        log.info("Fetching custom cart items for userId: {}", userId);

        try {
            ResponseEntity<List<CustomCart>> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    getHttpEntity(userId),
                    new ParameterizedTypeReference<List<CustomCart>>() {},
                    userId
            );

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return responseEntity.getBody();
            } else {
                log.error("Failed to get custom cart items, status code: {}", responseEntity.getStatusCode());
                throw new RuntimeException("Failed to get custom cart items");
            }
        } catch (Exception e) {
            log.error("Error while fetching custom cart items: {}", e.getMessage());
            throw new RuntimeException("Failed to get custom cart items", e);
        }
    }

    // 커스텀 장바구니 생성
    public CustomCart createCustomCart(String userId, CustomCart customCart) {
        String url = cartServiceUrl + "/custom-carts/{userId}";
        HttpEntity<CustomCart> requestEntity = new HttpEntity<>(customCart, getHeaders(userId));

        ResponseEntity<CustomCart> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, CustomCart.class, userId);

        if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
            return responseEntity.getBody();
        } else {
            throw new RuntimeException("Failed to create custom cart");
        }
    }

    // 커스텀 장바구니 상품 등록
    public CustomCartItem addItemToCustomCart(String userId, String customCartId, CustomCartItem customCartItem) {
        String url = cartServiceUrl + "/custom-carts/{userId}/{customCartId}/item";
        HttpEntity<CustomCartItem> requestEntity = new HttpEntity<>(customCartItem, getHeaders(userId));

        ResponseEntity<CustomCartItem> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, CustomCartItem.class, userId, customCartId);

        if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
            return responseEntity.getBody();
        } else {
            throw new RuntimeException("Failed to add item to custom cart");
        }
    }

    // 커스텀 장바구니 상품 삭제
    public void removeItemFromCustomCart(String userId, String customCartId, String itemCode) {
        String url = cartServiceUrl + "/custom-carts/{userId}/{customCartId}/item/{itemCode}";
        HttpEntity<String> requestEntity = new HttpEntity<>(getHeaders(userId));

        ResponseEntity<Void> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Void.class, userId, customCartId, itemCode);

        if (responseEntity.getStatusCode() != HttpStatus.NO_CONTENT) {
            throw new RuntimeException("Failed to remove item from custom cart");
        }
    }

    // JWT 인증 헤더 추가
    private HttpEntity<Void> getHttpEntity(String userId) {
        return new HttpEntity<>(getHeaders(userId));
    }

    // HTTP 헤더에 JWT 토큰 추가
    private HttpHeaders getHeaders(String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-User-Id", userId); // JWT로 유저 정보를 담은 헤더
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
