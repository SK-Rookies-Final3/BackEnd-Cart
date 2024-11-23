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
    public ResponseEntity<Cart> getCart() {
        Cart cart = cartService.getCart();
        // HATEOAS: 장바구니 관련 링크 추가
        cart.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CartController.class).getCart()).withSelfRel());
        cart.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CartController.class).addItemToCart(null)).withRel("addItem"));
        cart.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CartController.class).removeItemFromCart(null)).withRel("removeItem"));
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    // 장바구니에 상품 추가
    @PostMapping("/items")
    @PreAuthorize("hasRole('CLIENT')") // 인증된 사용자만 접근 가능
    public ResponseEntity<Cart> addItemToCart(@RequestBody CartItem item) {
        Cart cart = cartService.addItemToCart(item);
        cart.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CartController.class).getCart()).withRel("viewCart"));
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }

    // 장바구니에서 상품 삭제
    @DeleteMapping("/items/{productCode}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Cart> removeItemFromCart(@PathVariable String productCode) {
        Cart cart = cartService.removeItemFromCart(productCode);
        cart.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CartController.class).getCart()).withRel("viewCart"));
        return new ResponseEntity<>(cart, HttpStatus.NO_CONTENT);
    }

    // 커스텀 장바구니 조회
    @GetMapping("/custom/items")
    public ResponseEntity<CustomCart> getCustomCart() {
        CustomCart customCart = customCartService.getCustomCart();
        customCart.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CartController.class).getCustomCart()).withSelfRel());
        customCart.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CartController.class).createCustomCart(null)).withRel("createCustomCart"));
        return new ResponseEntity<>(customCart, HttpStatus.OK);
    }

    // 커스텀 장바구니 등록
    @PostMapping("/custom")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<CustomCart> createCustomCart(@RequestBody CustomCart customCart) {
        CustomCart createdCart = customCartService.createCustomCart(customCart);
        createdCart.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CartController.class).getCustomCart()).withRel("viewCustomCart"));
        return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
    }

    // 커스텀 장바구니 제목 수정
    @PatchMapping("/custom/updateTitle")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<CustomCart> updateCustomCartTitle(@RequestBody String newTitle) {
        CustomCart updatedCart = customCartService.updateCustomCartTitle(newTitle);
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }

    // 커스텀 장바구니에서 상품 삭제
    @DeleteMapping("/custom/items/{productCode}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<CustomCart> removeItemFromCustomCart(@PathVariable String productCode) {
        CustomCart updatedCustomCart = customCartService.removeItemFromCustomCart(productCode);
        return new ResponseEntity<>(updatedCustomCart, HttpStatus.NO_CONTENT);
    }
}
