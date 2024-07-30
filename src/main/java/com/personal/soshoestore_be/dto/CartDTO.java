package com.personal.soshoestore_be.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("cart")
public class CartDTO implements Serializable {
    private Long id;

    @Builder.Default
    @JsonProperty("cart_details")
    private List<CartDetailDTO> cartDetailsDto = new ArrayList<>();
}
