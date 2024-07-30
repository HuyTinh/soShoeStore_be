package com.personal.soshoestore_be.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.personal.soshoestore_be.model.Image;
import com.personal.soshoestore_be.model.ShoeColour;
import com.personal.soshoestore_be.model.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShoeResponse implements Serializable{

    Long id;

    String name;

    String description;

    Double price;

    @JsonProperty("image_url")
    String imageUrl;

    @JsonProperty("image_url_back")
    String imageUrlBack;

    @JsonProperty("colour")
    ShoeColour colour;

    @JsonProperty("shoe_different_colour")
    List<ShoeDifferentColourResponse> shoeDifferentColour;

    @JsonProperty("sizes")
    List<Size> sizes;

    List<Image> images;

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt;

    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updatedAt;
}
