package com.personal.soshoestore_be.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdatePasswordDTO {

    @NotBlank(message = "Password is required")
    private String password;
    @JsonProperty("confirm_password")
    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;
}
