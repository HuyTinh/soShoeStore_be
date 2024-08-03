package com.personal.soshoestore_be.model;

import com.personal.soshoestore_be.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name =  "orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    Long id;

    @Column(name = "first_name", columnDefinition = "VARCHAR(100)")
    String firstName;

    @Column(name = "last_name", columnDefinition = "VARCHAR(100)")
    String lastName;

    @Column(name = "email", columnDefinition = "VARCHAR(100)", nullable = false)
    String email;

    @Column(name = "phone_number", columnDefinition = "VARCHAR(20)", nullable = false)
    String phoneNumber;

    @Column(name = "address", columnDefinition = "VARCHAR(200)", nullable = false)
    String address;

    @Column(name = "note", columnDefinition = "VARCHAR(100)")
    String note;

    @Column(name = "order_date")
    LocalDateTime orderDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    OrderStatus status;

    @Column(name = "total_money")
    Double totalMoney;

    @Column(name = "shipping_method", columnDefinition = "VARCHAR(100)")
    String shippingMethod;

    @Column(name = "shipping_address", columnDefinition = "VARCHAR(200)")
    String shippingAddress;

    @Column(name = "shipping_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date shippingDate;

    @Column(name = "tracking_number", columnDefinition = "VARCHAR(100)")
    String trackingNumber;

    @Column(name = "payment_method", columnDefinition = "VARCHAR(100)")
    String paymentMethod;

    @Column(name = "active")
    Boolean active;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
