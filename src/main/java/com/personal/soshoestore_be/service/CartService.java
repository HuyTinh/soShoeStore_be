package com.personal.soshoestore_be.service;

import com.personal.soshoestore_be.model.Cart;
import com.personal.soshoestore_be.dto.CartDetailDTO;

public interface CartService {

    Cart getCart(long cartId) throws Exception;
    Cart addToCart(long cartId, CartDetailDTO cartItem) throws Exception;
    Cart updateToCart(long cartId, CartDetailDTO cartItem) throws Exception;
    Cart removeFromCart(long cartId, int shoeId) throws Exception;
    Cart clearCart(long cartId) throws Exception;
 }
