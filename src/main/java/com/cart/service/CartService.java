package com.cart.service;

import com.cart.dto.Cart;
import com.cart.dto.CartItem;
import com.cart.dto.CustomCart;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CartService {

    private static final String BASE_URL = "http://api.example.com"; // 실제 API 주소로 대체
    private final RestTemplate restTemplate;

    public CartService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 장바구니 조회
    public Cart getCart(String userId) {
        String url = BASE_URL + "/carts/" + userId;
        ResponseEntity<Cart> response = restTemplate.exchange(url, HttpMethod.GET, null, Cart.class);
        return response.getBody();
    }

    // 장바구니 아이템 등록
    public Cart addToCart(String userId, CartItem cartItem) {
        String url = BASE_URL + "/carts/" + userId;
        HttpEntity<CartItem> request = new HttpEntity<>(cartItem);
        ResponseEntity<Cart> response = restTemplate.exchange(url, HttpMethod.POST, request, Cart.class);
        return response.getBody();
    }

    // 장바구니 아이템 삭제
    public Cart removeFromCart(String userId, String productCode) {
        String url = BASE_URL + "/carts/" + userId + "/items/" + productCode;
        ResponseEntity<Cart> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Cart.class);
        return response.getBody();
    }

    // 커스텀 장바구니 조회
    public CustomCart getCustomCart(String userId, String customCartId) {
        String url = BASE_URL + "/custom-carts/" + userId + "/" + customCartId;
        ResponseEntity<CustomCart> response = restTemplate.exchange(url, HttpMethod.GET, null, CustomCart.class);
        return response.getBody();
    }

    // 커스텀 장바구니 등록
    public CustomCart createCustomCart(String userId, CustomCart customCart) {
        String url = BASE_URL + "/custom-carts/" + userId;
        HttpEntity<CustomCart> request = new HttpEntity<>(customCart);
        ResponseEntity<CustomCart> response = restTemplate.exchange(url, HttpMethod.POST, request, CustomCart.class);
        return response.getBody();
    }

    // 커스텀 장바구니 삭제
    public void deleteCustomCart(String userId, String customCartId) {
        String url = BASE_URL + "/custom-carts/" + userId + "/" + customCartId;
        restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
    }

    // 커스텀 장바구니 아이템 수정
    public CustomCart updateCustomCart(String userId, String customCartId, CustomCart customCart) {
        String url = BASE_URL + "/custom-carts/" + userId + "/" + customCartId;
        HttpEntity<CustomCart> request = new HttpEntity<>(customCart);
        ResponseEntity<CustomCart> response = restTemplate.exchange(url, HttpMethod.PUT, request, CustomCart.class);
        return response.getBody();
    }
}
