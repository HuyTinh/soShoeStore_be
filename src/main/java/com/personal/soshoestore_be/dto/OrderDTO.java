package com.personal.soshoestore_be.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.logging.Level;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDTO {

    @JsonProperty("user_id")
    @Min(value = 1, message = "User id must be greater than 0")
    Long userId;

    @JsonProperty("firstname")
    String firstName;

    @JsonProperty("lastname")
    String lastName;

    @JsonProperty("email")
    String email;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required")
    @Size(min = 5, message = "Phone number must be at least 5 characters")
    String phoneNumber;

    @JsonProperty("address")
    String address;

    @JsonProperty("note")
    String note;

    @JsonProperty("total_money")
    @Min(value = 0, message = "Total money must be greater than 0")
    Double totalMoney;

    @JsonProperty("shipping_method")
    String shippingMethod;

    @JsonProperty("shipping_address")
    String shippingAddress;

    @JsonProperty("shipping_date")
    Date shippingDate;

    @JsonProperty("payment_method")
    String paymentMethod;
}
