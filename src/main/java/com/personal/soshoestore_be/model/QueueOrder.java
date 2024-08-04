package com.personal.soshoestore_be.model;

import com.personal.soshoestore_be.dto.OrderDTO;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("queue_orders")
public class QueueOrder {
    private Long id;
    @Builder.Default
    private OrderDTO queueOrders = OrderDTO.builder().build();
}
