package com.personal.soshoestore_be.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse{
    Long id;
    @JsonProperty("user_id")
    Long userId;
    @JsonProperty("firstname")
    String firstName;
    @JsonProperty("lastname")
    String lastName;
    @JsonProperty("phone_number")
    String phoneNumber;
    String address;
    String note;
    @JsonProperty("order_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime orderDate;
    String status;
    @JsonProperty("total_money")
    Double totalMoney;
    @JsonProperty("shipping_method")
    String shippingMethod;
    @JsonProperty("shipping_address")
    String shippingAddress;
    @JsonProperty("shipping_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date shippingDate;
    @JsonProperty("tracking_number")
    String trackingNumber;
    @JsonProperty("payment_method")
    String paymentMethod;
    @JsonProperty("active")
    Boolean active;
    @JsonProperty("order_details")
    List<SubOrderResponse> orderDetails;
}
