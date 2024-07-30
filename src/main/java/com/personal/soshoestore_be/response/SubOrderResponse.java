package com.personal.soshoestore_be.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.personal.soshoestore_be.model.ShoeColour;
import com.personal.soshoestore_be.model.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubOrderResponse {
    Long id;

    @JsonProperty("shoe_name")
    String shoeName;
    @JsonProperty("image_url")
    String imageUrl;
    ShoeColour colour;
    Size size;
    Double price;
    @JsonProperty("number_of_product")
    Integer numberOfProduct;
    @JsonProperty("total_money")
    Double totalMoney;
}
