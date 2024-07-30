package com.personal.soshoestore_be.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailDTO {

    @JsonProperty("order_id")
    @Min(value = 1, message = "Order id must be greater than 0")
    Long orderId;

    @JsonProperty("shoe_id")
    @Min(value = 1, message = "Product id must be greater than 0")
    Long shoeId;

    @JsonProperty("price")
    @Min(value = 0, message = "Price must be greater than 0")
    Double price;

    @JsonProperty("number_of_product")
    @Min(value = 1, message = "Number of product must be greater than 0")
    Integer numberOfProduct;

    @JsonProperty("total_money")
    @Min(value = 0, message = "Total money must be greater than 0")
    Double totalMoney;


    @JsonProperty("size_id")
    @Min(value = 1, message = "Size id must be greater than 0")
    Long sizeId;
}
