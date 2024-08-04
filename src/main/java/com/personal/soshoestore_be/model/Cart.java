package com.personal.soshoestore_be.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.personal.soshoestore_be.dto.CartDetailDTO;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("carts")
public class Cart implements Serializable {
    private Long id;
    @Builder.Default
    @JsonProperty("cart_details")
    private List<CartDetailDTO> cartDetailsDto = new ArrayList<>();
}
