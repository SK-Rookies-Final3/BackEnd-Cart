package com.cart.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CartService {

    private static final String MEMBER_SERVICE_URL = "http://member-service/api/v1/member";
    private static final String PRODUCT_SERVICE_URL = "http://product-service/api/v1/products";
    private static final String CART_SERVICE_URL = "http://cart-service/api/v1/cart";

    private final RestTemplate restTemplate;

    public CartService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 장바구니에 상품 추가
    public String addProductToCart(String memberId, String productCode, int quantity) {
        // 상품 정보 조회
        String productUrl = PRODUCT_SERVICE_URL + "/" + productCode;
        ResponseEntity<Map> productResponse = restTemplate.exchange(productUrl, HttpMethod.GET, null, Map.class);
        Map<String, Object> productData = productResponse.getBody();

        // 장바구니에 상품 추가
        String cartUrl = CART_SERVICE_URL + "/add";
        Map<String, Object> cartItem = Map.of(
                "memberId", memberId,
                "productCode", productCode,
                "quantity", quantity,
                "productName", productData.get("name"),
                "productImage", productData.get("image"),
                "price", productData.get("price")
        );

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(cartItem);
        ResponseEntity<String> response = restTemplate.postForEntity(cartUrl, requestEntity, String.class);

        return response.getBody();
    }

    // 장바구니 상품 목록 조회
    public List<Map<String, Object>> getCartItems(String memberId) {
        String url = CART_SERVICE_URL + "/items?memberId=" + memberId;
        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, null, List.class);
        return response.getBody();
    }

    // 장바구니에서 상품 삭제
    public String removeProductFromCart(String memberId, String productCode) {
        String url = CART_SERVICE_URL + "/remove";
        Map<String, Object> cartItem = Map.of("memberId", memberId, "productCode", productCode);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(cartItem);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        return response.getBody();
    }

    // 커스텀 장바구니 생성
    public String createCustomCart(String memberId, String cartName, List<String> productCodes) {
        List<Map<String, Object>> customCartItems = new ArrayList<>();

        for (String productCode : productCodes) {
            // 상품 정보 조회
            String productUrl = PRODUCT_SERVICE_URL + "/" + productCode;
            ResponseEntity<Map> productResponse = restTemplate.exchange(productUrl, HttpMethod.GET, null, Map.class);
            Map<String, Object> productData = productResponse.getBody();

            // 커스텀 장바구니에 상품 추가
            Map<String, Object> cartItem = Map.of(
                    "productCode", productCode,
                    "productName", productData.get("name"),
                    "productImage", productData.get("image"),
                    "price", productData.get("price"),
                    "xCoordinate", 0,  // 기본값
                    "yCoordinate", 0   // 기본값
            );
            customCartItems.add(cartItem);
        }

        // 커스텀 장바구니 생성 API 호출
        String customCartUrl = CART_SERVICE_URL + "/custom";
        Map<String, Object> customCart = Map.of(
                "memberId", memberId,
                "cartName", cartName,
                "items", customCartItems
        );

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(customCart);
        ResponseEntity<String> response = restTemplate.postForEntity(customCartUrl, requestEntity, String.class);

        return response.getBody();
    }

    // 커스텀 장바구니 수정
    public String updateCustomCart(String memberId, String cartName, List<String> productCodes) {
        return createCustomCart(memberId, cartName, productCodes);  // 이름 수정과 내용 수정 로직은 유사
    }
}
