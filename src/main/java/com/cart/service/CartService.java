package com.cart.service;

import com.cart.dto.CartDto;
import com.cart.dto.CartItemDto;
import com.cart.dto.ProductResponse;
import com.cart.utils.HeaderUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CartService {

    private final RestTemplate restTemplate;
    private static final String CART_API_URL = "/api/cart";
    private static final String PRODUCT_API_URL = "/open-api/brand/product/";

    @Autowired
    public CartService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 장바구니 조회
    public CartDto getCart(HttpServletRequest request) {
        String userId = HeaderUtils.getUserId(request);  // HttpServletRequest로 사용자 ID 추출
        String url = CART_API_URL + "/items";
        try {
            ResponseEntity<CartDto> responseEntity = restTemplate.getForEntity(url, CartDto.class, userId);
            if (responseEntity.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Failed to fetch cart");
            }
            return responseEntity.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while accessing cart API", e);
        }
    }

    // 장바구니 상품 추가
    public CartDto addItemToCart(HttpServletRequest request, CartItemDto cartItemDto) {
        String userId = HeaderUtils.getUserId(request);
        String productUrl = PRODUCT_API_URL + cartItemDto.getProductCode();

        try {
            // 상품 상세 정보 조회
            ProductResponse product = restTemplate.getForObject(productUrl, ProductResponse.class);

            if (product == null) {
                throw new RuntimeException("Product not found");
            }

            // CartItem에 상품 정보 세팅
            cartItemDto.setProductName(product.getName());
            cartItemDto.setThumbnail_url(product.getThumbnailUrl().isEmpty() ? null : product.getThumbnailUrl().get(0));

            // 장바구니에 추가
            String url = CART_API_URL + "/items";
            ResponseEntity<CartDto> responseEntity = restTemplate.postForEntity(url, cartItemDto, CartDto.class, userId);

            if (responseEntity.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Failed to add item to cart");
            }

            return responseEntity.getBody();

        } catch (Exception e) {
            throw new RuntimeException("Error occurred while adding item to cart", e);
        }
    }


    // 장바구니 상품 제거
    public CartDto removeItemFromCart(HttpServletRequest request, String productCode) {
        String userId = HeaderUtils.getUserId(request);
        String url = CART_API_URL + "/items/{productCode}";
        try {
            restTemplate.delete(url, userId, productCode);
            return getCart(request);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while removing item from cart", e);
        }
    }
}
