package com.personal.soshoestore_be.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {

    @JsonProperty("image_url")
    @Size(min = 5, max = 200, message = "Image URL must be between 5 and 200 characters")
    private String imageUrl;

    @JsonProperty("shoe_id")
    @Min(value = 1, message = "Shoe id must be greater than 0")
    private Long shoeId;
}
