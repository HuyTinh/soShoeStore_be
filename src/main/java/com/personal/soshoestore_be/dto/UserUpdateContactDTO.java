package com.personal.soshoestore_be.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserUpdateContactDTO {
    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is empty, please check again")
    @Size(min=10, max=11, message = "Phone number must be between 10 and 11 characters")
    private String phoneNumber;

    @JsonProperty("address")
    @NotBlank(message = "Address is empty, please check again")
    private String address;
}
