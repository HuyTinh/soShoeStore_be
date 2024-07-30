package com.personal.soshoestore_be.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.soshoestore_be.dto.CartDTO;
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

    public void save(CartDTO cartDto) {
        String key = CART_STORE + cartDto.getId();
        redisTemplate.opsForValue().set(key, cartDto);
        redisTemplate.expire(CART_STORE + cartDto.getId(), Duration.ofDays(7));
    }

    public CartDTO findById(long id) throws Exception {
        try {
            CartDTO cartDto = objectMapper.convertValue(redisTemplate.opsForValue().get(CART_STORE + id), CartDTO.class);
            if (cartDto != null) {
                return cartDto;
            }
        } catch (Exception e) {
            throw new DataNotFoundException(String.format("Cart with (id = %d) is not found", id));
        }
        return CartDTO.builder().build();
   }
}
