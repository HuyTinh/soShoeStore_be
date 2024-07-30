package com.personal.soshoestore_be.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoeDifferentColourResponse {
    private Long id;
    private String colour;
    @JsonProperty("image_url")
    private String imageUrl;
}
