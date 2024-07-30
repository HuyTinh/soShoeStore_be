package com.personal.soshoestore_be.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.personal.soshoestore_be.model.Image;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShoeDTO implements Serializable {
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
    String name;

    String description;

    @Min(value = 0, message = "Price must be greater than 0")
    @Max(value = 10000000, message = "Price must be less than 1000000")
    Double price;

    @JsonProperty("image_url")
    String imageUrl;

    @JsonProperty("image_url_back")
    String imageUrlBack;

    @JsonProperty("shoe_colour")
    ShoeColourDTO shoeColourDTO;

    @JsonProperty("images")
    List<Image> images;

    @JsonProperty("size_id")
    List<@Min(value = 1, message = "Size id must be greater than 0") Long> sizeId;

}
