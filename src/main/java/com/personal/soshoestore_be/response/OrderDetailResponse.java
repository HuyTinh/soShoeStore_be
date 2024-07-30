package com.personal.soshoestore_be.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailResponse {
    Long id;
    @JsonProperty("order_id")
    Long orderId;
    @JsonProperty("shoe_id")
    Long shoeId;
    Double price;
    @JsonProperty("number_of_product")
    Integer numberOfProduct;
    @JsonProperty("total_money")
    Double totalMoney;
    @JsonProperty("colour_id")
    Long colourId;
    @JsonProperty("size_id")
    Long sizeId;
}
