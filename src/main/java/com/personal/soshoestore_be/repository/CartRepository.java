package com.personal.soshoestore_be.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.soshoestore_be.model.Cart;
import com.personal.soshoestore_be.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class CartRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    private final ObjectMapper objectMapper;

    private final String CART_STORE = "cartStore:";

    public void save(Cart cart) {
        String key = CART_STORE + cart.getId();
        redisTemplate.opsForValue().set(key, cart);
        redisTemplate.expire(CART_STORE + cart.getId(), Duration.ofDays(7));
    }

    public Cart findById(long id) {
        try {
            Cart cart = objectMapper.convertValue(redisTemplate.opsForValue().get(CART_STORE + id), Cart.class);
            if (cart != null) {
                return cart;
            }
        } catch (Exception e) {
            throw new DataNotFoundException(String.format("Cart with (id = %d) is not found", id));
        }
        return Cart.builder().build();
   }
}
