package com.cart.service;

import com.cart.dto.CustomCart;
import com.cart.dto.CustomCartItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ServerWebExchange;

@Service  // Spring에서 이 클래스를 서비스 컴포넌트로 사용함
public class CustomCartService {

    private final RestTemplate restTemplate;  // HTTP 요청을 보낼 때 사용할 RestTemplate 객체
    private final String CUSTOM_CART_API_URL = "/api/cart";  // 사용자 정의 장바구니 API의 기본 URL

    @Autowired  // Spring이 RestTemplate 객체를 자동으로 주입하도록 설정
    public CustomCartService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 사용자 정의 장바구니를 가져오는 메서드
    public CustomCart getCustomCart(ServerWebExchange exchange) {
        // 요청 헤더에서 사용자 ID를 추출
        String userId = getUserIdFromHeaders(exchange);
        // 사용자 ID를 포함하여 API URL을 생성
        String url = CUSTOM_CART_API_URL + "/custom/items";
        // GET 요청을 보내고, 반환된 장바구니 데이터를 ResponseEntity로 받음
        ResponseEntity<CustomCart> responseEntity = restTemplate.getForEntity(url, CustomCart.class, userId);
        // 응답에서 사용자 정의 장바구니 객체를 반환
        return responseEntity.getBody();
    }

    // 사용자 정의 장바구니를 생성하는 메서드
    public CustomCart createCustomCart(ServerWebExchange exchange, CustomCart customCart) {
        // 요청 헤더에서 사용자 ID를 추출
        String userId = getUserIdFromHeaders(exchange);
        // 장바구니를 생성하는 API URL 생성
        String url = CUSTOM_CART_API_URL + "/custom";
        // POST 요청을 보내고, 반환된 사용자 정의 장바구니 데이터를 ResponseEntity로 받음
        ResponseEntity<CustomCart> responseEntity = restTemplate.postForEntity(url, customCart, CustomCart.class, userId);
        // 응답에서 생성된 사용자 정의 장바구니 객체를 반환
        return responseEntity.getBody();
    }

    // 사용자 정의 장바구니의 제목을 업데이트하는 메서드
    public CustomCart updateCustomCartTitle(ServerWebExchange exchange, String newTitle) {
        // 요청 헤더에서 사용자 ID를 추출
        String userId = getUserIdFromHeaders(exchange);
        // 제목을 업데이트하는 API URL 생성
        String url = CUSTOM_CART_API_URL + "/custom/updateTitle";
        // PUT 요청을 보내어 장바구니 제목을 업데이트
        restTemplate.put(url, newTitle, userId);
        // 제목 업데이트 후, 최신 사용자 정의 장바구니 데이터를 반환
        return getCustomCart(exchange);
    }

    // 사용자 정의 장바구니에서 특정 상품을 제거하는 메서드
    public CustomCart removeItemFromCustomCart(ServerWebExchange exchange, String productCode) {
        // 요청 헤더에서 사용자 ID를 추출
        String userId = getUserIdFromHeaders(exchange);
        // 상품을 제거하는 API URL 생성
        String url = CUSTOM_CART_API_URL + "/custom/items/{productCode}";
        // DELETE 요청을 보내어 상품을 장바구니에서 제거
        restTemplate.delete(url, userId, productCode);
        // 상품을 제거한 후, 최신 사용자 정의 장바구니 데이터를 반환
        return getCustomCart(exchange);
    }

    // 요청 헤더에서 사용자 ID를 추출하는 도우미 메서드
    private String getUserIdFromHeaders(ServerWebExchange exchange) {
        // 요청에서 헤더 정보를 가져오고, "X-User-Id" 헤더의 값을 추출하여 반환
        return exchange.getRequest().getHeaders().getFirst("X-User-Id");
    }
}
