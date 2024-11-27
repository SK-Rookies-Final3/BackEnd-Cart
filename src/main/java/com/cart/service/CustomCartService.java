package com.cart.service;

import com.cart.dto.CustomCart;
import com.cart.util.HeaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpServletRequest;

@Service
public class CustomCartService {

    private final RestTemplate restTemplate;
    private static final String CUSTOM_CART_API_URL = "/api/cart/custom";

    @Autowired
    public CustomCartService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 커스텀 장바구니 조회
    public CustomCart getCustomCart(HttpServletRequest request) {
        String userId = HeaderUtils.getUserIdFromHeaders(request);  // HttpServletRequest로 사용자 ID 추출
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

    // 커스텀 장바구니 생성
    public CustomCart createCustomCart(CustomCart customCart, HttpServletRequest request) {
        String userId = HeaderUtils.getUserIdFromHeaders(request);  // HttpServletRequest로 사용자 ID 추출
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

    // 커스텀 장바구니 제목 수정
    public CustomCart updateCustomCartTitle(String newTitle, HttpServletRequest request) {
        String userId = HeaderUtils.getUserIdFromHeaders(request);  // HttpServletRequest로 사용자 ID 추출
        String url = CUSTOM_CART_API_URL + "/updateTitle";
        try {
            restTemplate.put(url, newTitle, userId);
            return getCustomCart(request); // 제목 업데이트 후 커스텀 장바구니를 다시 불러옴
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while updating custom cart title", e);
        }
    }

    // 커스텀 장바구니에서 상품 제거
    public CustomCart removeItemFromCustomCart(String productCode, HttpServletRequest request) {
        String userId = HeaderUtils.getUserIdFromHeaders(request);  // HttpServletRequest로 사용자 ID 추출
        String url = CUSTOM_CART_API_URL + "/items/{productCode}";
        try {
            restTemplate.delete(url, userId, productCode);
            return getCustomCart(request); // 상품 제거 후 커스텀 장바구니를 다시 불러옴
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while removing item from custom cart", e);
        }
    }
}
