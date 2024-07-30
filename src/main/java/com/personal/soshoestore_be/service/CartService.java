package com.personal.soshoestore_be.service;

import com.personal.soshoestore_be.dto.CartDTO;
import com.personal.soshoestore_be.dto.CartDetailDTO;

public interface CartService {

    CartDTO getCart(long cartId) throws Exception;
    CartDTO addToCart(long cartId, CartDetailDTO cartItem) throws Exception;
    CartDTO updateToCart(long cartId, CartDetailDTO cartItem) throws Exception;
    CartDTO removeFromCart(long cartId, int shoeId) throws Exception;
    CartDTO clearCart(long cartId) throws Exception;
}
