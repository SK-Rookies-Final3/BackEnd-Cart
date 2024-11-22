package com.cart.service;

import com.cart.dto.Cart;
import com.cart.dto.CartItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

@Service  // Spring에서 이 클래스가 서비스 컴포넌트임을 나타냄
public class CartService {

    private final RestTemplate restTemplate;  // HTTP 요청을 보내기 위한 RestTemplate 객체
    private final String CART_API_URL = "/api/cart";  // 장바구니 관련 API의 기본 URL

    @Autowired  // Spring이 RestTemplate 객체를 자동으로 주입하도록 설정
    public CartService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 특정 사용자의 장바구니 정보를 가져오는 메서드
    public Cart getCart(ServerWebExchange exchange) {
        // 요청 헤더에서 사용자 ID를 추출
        String userId = getUserIdFromHeaders(exchange);
        // 사용자 ID를 포함하여 API URL을 생성
        String url = CART_API_URL + "/items";
        // GET 요청을 보내고, 반환된 장바구니 데이터를 ResponseEntity로 받음
        ResponseEntity<Cart> responseEntity = restTemplate.getForEntity(url, Cart.class, userId);
        // 응답에서 장바구니 객체를 반환
        return responseEntity.getBody();
    }

    // 사용자의 장바구니에 아이템을 추가하는 메서드
    public Cart addItemToCart(ServerWebExchange exchange, CartItem item) {
        // 요청 헤더에서 사용자 ID를 추출
        String userId = getUserIdFromHeaders(exchange);
        // 장바구니에 아이템을 추가하는 API URL 생성
        String url = CART_API_URL + "/items";
        // POST 요청을 보내고, 반환된 장바구니 데이터를 ResponseEntity로 받음
        ResponseEntity<Cart> responseEntity = restTemplate.postForEntity(url, item, Cart.class, userId);
        // 응답에서 업데이트된 장바구니 객체를 반환
        return responseEntity.getBody();
    }

    // 장바구니에서 특정 상품을 제거하는 메서드
    public Cart removeItemFromCart(ServerWebExchange exchange, String productCode) {
        // 요청 헤더에서 사용자 ID를 추출
        String userId = getUserIdFromHeaders(exchange);
        // 장바구니에서 상품을 제거하는 API URL 생성
        String url = CART_API_URL + "/items/{productCode}";
        // DELETE 요청을 보내어 상품을 제거
        restTemplate.delete(url, userId, productCode);
        // 상품을 제거한 후, 업데이트된 장바구니 데이터를 반환
        return getCart(exchange);
    }

    // 요청 헤더에서 사용자 ID를 추출하는 도우미 메서드
    private String getUserIdFromHeaders(ServerWebExchange exchange) {
        // 요청에서 헤더 정보를 가져옴
        HttpHeaders headers = exchange.getRequest().getHeaders();
        // "X-User-Id" 헤더의 값을 추출하여 반환
        return headers.getFirst("X-User-Id");
    }
}
