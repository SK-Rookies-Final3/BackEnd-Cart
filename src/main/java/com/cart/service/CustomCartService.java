package com.cart.service;

import com.cart.dto.CustomCart;
import com.cart.dto.CustomCartItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Service
public class CustomCartService {

    private final RestTemplate restTemplate;
    private final String CUSTOM_CART_API_URL = "http://api-gateway/customcart";  // 커스텀 장바구니 API 엔드포인트

    @Autowired
    public CustomCartService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 커스텀 장바구니 조회
    public Mono<CustomCart> getCustomCart(ServerWebExchange exchange) {
        String userId = getUserIdFromHeaders(exchange);  // 헤더에서 사용자 ID 추출
        String url = CUSTOM_CART_API_URL + "/{userId}";
        return Mono.defer(() -> {
            ResponseEntity<CustomCart> responseEntity = restTemplate.getForEntity(url, CustomCart.class, userId);
            return Mono.justOrEmpty(responseEntity.getBody());  // 바디를 Mono로 감싸서 반환
        });
    }

    // 커스텀 장바구니 등록
    public Mono<CustomCart> createCustomCart(ServerWebExchange exchange, CustomCart customCart) {
        String userId = getUserIdFromHeaders(exchange);  // 헤더에서 사용자 ID 추출
        String url = CUSTOM_CART_API_URL + "/{userId}/create";
        return Mono.defer(() -> {
            ResponseEntity<CustomCart> responseEntity = restTemplate.postForEntity(url, customCart, CustomCart.class, userId);
            return Mono.justOrEmpty(responseEntity.getBody());  // 바디를 Mono로 감싸서 반환
        });
    }

    // 커스텀 장바구니 제목 수정
    public Mono<CustomCart> updateCustomCartTitle(ServerWebExchange exchange, String newTitle) {
        String userId = getUserIdFromHeaders(exchange);  // 헤더에서 사용자 ID 추출
        String url = CUSTOM_CART_API_URL + "/{userId}/updateTitle";
        restTemplate.put(url, newTitle, userId);
        return getCustomCart(exchange);  // 제목 수정 후 커스텀 장바구니 조회
    }

    // 커스텀 장바구니에서 상품 삭제
    public Mono<CustomCart> removeItemFromCustomCart(ServerWebExchange exchange, String productCode) {
        String userId = getUserIdFromHeaders(exchange);  // 헤더에서 사용자 ID 추출
        String url = CUSTOM_CART_API_URL + "/{userId}/remove/{productCode}";
        restTemplate.delete(url, userId, productCode);



        return getCustomCart(exchange);  // 상품 삭제 후 커스텀 장바구니 조회
    }

    private String getUserIdFromHeaders(ServerWebExchange exchange) {
        return exchange.getRequest().getHeaders().getFirst("X-User-Id");  // JWT 인증 필터에서 추가된 X-User-Id 헤더
    }
}
