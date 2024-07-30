package com.personal.soshoestore_be.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRegisterDTO {
    @JsonProperty("first_name")
    String firstName;

    @JsonProperty("last_name")
    String lastName;

    @JsonProperty("phone_number")
    String phoneNumber;

    @JsonProperty("email")
    @NotBlank(message = "Email is required")
    String email;

    @JsonProperty("address")
    String address;

    @NotBlank(message = "Password is required")
    String password;

    @JsonProperty("retype_password")
    String retypePassword;

    @JsonProperty("date_of_birth")
    Date dateOfBirth;

    @JsonProperty("facebook_account_id")
    @Builder.Default
    Boolean facebookAccountId = false;

    @JsonProperty("google_account_id")
    @Builder.Default
    Boolean googleAccountId = false;

    @JsonProperty("role_id")
    @Builder.Default
    Long roleId = 2L;
}
