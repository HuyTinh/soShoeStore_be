package com.personal.soshoestore_be.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoeColourDTO {
    @JsonProperty("vamp_id")
    private Long vamp;
    @JsonProperty("quarter_id")
    private Long quarter;
    @JsonProperty("sole_id")
    private Long sole;
}
