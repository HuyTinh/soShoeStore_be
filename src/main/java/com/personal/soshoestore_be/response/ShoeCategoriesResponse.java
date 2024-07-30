package com.personal.soshoestore_be.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoeCategoriesResponse {
    private String title;
    @JsonProperty("image_url")
    private String imageUrl;
}
