package com.personal.soshoestore_be.controller;

import com.personal.soshoestore_be.model.Cart;
import com.personal.soshoestore_be.dto.CartDetailDTO;
import com.personal.soshoestore_be.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCart(@PathVariable("cartId") int cartId) throws Exception {
        return ResponseEntity.ok(cartService.getCart(cartId));
    }

    @PostMapping("/{cartId}")
    public ResponseEntity<Cart> addToCart(
            @PathVariable("cartId") int cartId,
            @RequestBody CartDetailDTO cartDetailDto) throws Exception {
        return ResponseEntity.ok(cartService.addToCart(cartId, cartDetailDto));
    }

    @DeleteMapping("/clear/{cartId}")
    public ResponseEntity<Cart> clearCart(@PathVariable("cartId") long cartId) throws Exception {
        return ResponseEntity.ok(cartService.clearCart(cartId));
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<Cart> updateFromCart(
            @PathVariable("cartId") int cartId,
            @RequestBody CartDetailDTO cartDetailDto) throws Exception {
        if(cartDetailDto.getQuantity() == 0){
            return  ResponseEntity.ok(cartService.removeFromCart(cartId, Math.toIntExact((cartDetailDto.getShoeId()))));
        }
        return ResponseEntity.ok(cartService.updateToCart(cartId, cartDetailDto));
    }
}

