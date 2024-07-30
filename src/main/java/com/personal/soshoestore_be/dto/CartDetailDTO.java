package com.personal.soshoestore_be.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.personal.soshoestore_be.model.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartDetailDTO implements Serializable {

    @JsonProperty("shoe_id")
    Long shoeId;
    @JsonProperty("image_url")
    String imageUrl;
    String name;
    Double price;
    @Builder.Default
    Integer quantity = 1;
    Size size;
}
