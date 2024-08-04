package com.personal.soshoestore_be.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.soshoestore_be.exception.DataNotFoundException;
import com.personal.soshoestore_be.model.QueueOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class QueueOrderRepository {
    private final RedisTemplate<String, Object> redisTemplate;

    private final ObjectMapper objectMapper;

    private final String QUEUE_ORDER_STORE = "queueOrderStore:";

    public void save(QueueOrder queueOrder) {
        String key = QUEUE_ORDER_STORE + queueOrder.getId();
        redisTemplate.opsForValue().set(key, queueOrder);
        redisTemplate.expire(QUEUE_ORDER_STORE + queueOrder.getId(), Duration.ofMinutes(30));
    }

    public QueueOrder findById(long id) {
        try {
            QueueOrder queueOrder = objectMapper.convertValue(redisTemplate.opsForValue().get(QUEUE_ORDER_STORE + id), QueueOrder.class);
            if (queueOrder != null) {
                return queueOrder;
            }
        } catch (Exception e) {
            throw new DataNotFoundException(String.format("Queue Order with (id = %d) is not found", id));
        }
        return QueueOrder.builder().build();
    }
}
