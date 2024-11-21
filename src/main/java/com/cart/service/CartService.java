package com.cart.service;

import com.cart.dto.Cart;
import com.cart.dto.CartItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Service
public class CartService {

    private final RestTemplate restTemplate;
    private final String CART_API_URL = "http://api-gateway/cart";  // 장바구니 API 엔드포인트

    @Autowired
    public CartService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 장바구니 조회
    public Mono<Cart> getCart(ServerWebExchange exchange) {
        String userId = getUserIdFromHeaders(exchange);  // 헤더에서 사용자 ID 추출
        String url = CART_API_URL + "/{userId}";
        return Mono.defer(() -> {
            ResponseEntity<Cart> responseEntity = restTemplate.getForEntity(url, Cart.class, userId);
            return Mono.justOrEmpty(responseEntity.getBody());  // 바디를 Mono로 감싸서 반환
        });
    }

    // 장바구니에 상품 등록
    public Mono<Cart> addItemToCart(ServerWebExchange exchange, CartItem item) {
        String userId = getUserIdFromHeaders(exchange);  // 헤더에서 사용자 ID 추출
        String url = CART_API_URL + "/{userId}/add";
        return Mono.defer(() -> {
            ResponseEntity<Cart> responseEntity = restTemplate.postForEntity(url, item, Cart.class, userId);
            return Mono.justOrEmpty(responseEntity.getBody());  // 바디를 Mono로 감싸서 반환
        });
    }

    // 장바구니에서 상품 삭제
    public Mono<Cart> removeItemFromCart(ServerWebExchange exchange, String productCode) {
        String userId = getUserIdFromHeaders(exchange);  // 헤더에서 사용자 ID 추출
        String url = CART_API_URL + "/{userId}/remove/{productCode}";
        restTemplate.delete(url, userId, productCode);
        return getCart(exchange);  // 상품 삭제 후 장바구니 조회
    }

    private String getUserIdFromHeaders(ServerWebExchange exchange) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        return headers.getFirst("X-User-Id");  // JWT 인증 필터에서 추가된 X-User-Id 헤더
    }
}
