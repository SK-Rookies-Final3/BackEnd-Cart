package com.cart.controller;

import com.cart.dto.Cart;
import com.cart.dto.CartItem;
import com.cart.dto.CustomCart;
import com.cart.service.CartService;
import com.cart.service.CustomCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final CustomCartService customCartService;

    @Autowired
    public CartController(CartService cartService, CustomCartService customCartService) {
        this.cartService = cartService;
        this.customCartService = customCartService;
    }

    // 장바구니 조회
    @GetMapping("/items")
    public Mono<ResponseEntity<Cart>> getCart(ServerWebExchange exchange) {
        return cartService.getCart(exchange)
                .map(cart -> {
                    // HATEOAS: 장바구니 관련 링크 추가
                    cart.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CartController.class).getCart(exchange)).withSelfRel());
                    cart.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CartController.class).addItemToCart(exchange, null)).withRel("addItem"));
                    cart.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CartController.class).removeItemFromCart(exchange, null)).withRel("removeItem"));
                    return new ResponseEntity<>(cart, HttpStatus.OK);
                });
    }

    // 장바구니에 상품 추가
    @PostMapping("/items")
    @PreAuthorize("hasRole('CLIENT')") // 인증된 사용자만 접근 가능
    public Mono<ResponseEntity<Cart>> addItemToCart(ServerWebExchange exchange, @RequestBody CartItem item) {
        return cartService.addItemToCart(exchange, item)
                .map(cart -> {
                    cart.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CartController.class).getCart(exchange)).withRel("viewCart"));
                    return new ResponseEntity<>(cart, HttpStatus.CREATED);
                });
    }

    // 장바구니에서 상품 삭제
    @DeleteMapping("/items/{productCode}")
    @PreAuthorize("hasRole('CLIENT')")
    public Mono<ResponseEntity<Cart>> removeItemFromCart(ServerWebExchange exchange, @PathVariable String productCode) {
        return cartService.removeItemFromCart(exchange, productCode)
                .map(cart -> {
                    cart.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CartController.class).getCart(exchange)).withRel("viewCart"));
                    return new ResponseEntity<>(cart, HttpStatus.NO_CONTENT);
                });
    }

    // 커스텀 장바구니 조회
    @GetMapping("/custom/items")
    public Mono<ResponseEntity<CustomCart>> getCustomCart(ServerWebExchange exchange) {
        return customCartService.getCustomCart(exchange)
                .map(customCart -> {
                    customCart.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CartController.class).getCustomCart(exchange)).withSelfRel());
                    customCart.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CartController.class).createCustomCart(exchange, null)).withRel("createCustomCart"));
                    return new ResponseEntity<>(customCart, HttpStatus.OK);
                });
    }

    // 커스텀 장바구니 등록
    @PostMapping("/custom")
    @PreAuthorize("hasRole('CLIENT')")
    public Mono<ResponseEntity<CustomCart>> createCustomCart(ServerWebExchange exchange, @RequestBody CustomCart customCart) {
        return customCartService.createCustomCart(exchange, customCart)
                .map(createdCart -> {
                    createdCart.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CartController.class).getCustomCart(exchange)).withRel("viewCustomCart"));
                    return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
                });
    }

    // 커스텀 장바구니 제목 수정
    @PutMapping("/custom/updateTitle")
    @PreAuthorize("hasRole('CLIENT')")
    public Mono<ResponseEntity<CustomCart>> updateCustomCartTitle(ServerWebExchange exchange, @RequestBody String newTitle) {
        return customCartService.updateCustomCartTitle(exchange, newTitle)
                .map(updatedCart -> new ResponseEntity<>(updatedCart, HttpStatus.OK));
    }

    // 커스텀 장바구니에서 상품 삭제
    @DeleteMapping("/custom/items/{productCode}")
    @PreAuthorize("hasRole('CLIENT')")
    public Mono<ResponseEntity<CustomCart>> removeItemFromCustomCart(ServerWebExchange exchange, @PathVariable String productCode) {
        return customCartService.removeItemFromCustomCart(exchange, productCode)
                .map(updatedCustomCart -> new ResponseEntity<>(updatedCustomCart, HttpStatus.NO_CONTENT));
    }
}
