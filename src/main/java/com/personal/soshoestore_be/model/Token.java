package com.personal.soshoestore_be.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tokens")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Token implements Serializable {

    @Id
    @Column(name ="token_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "token", columnDefinition = "VARCHAR(255)", nullable = false, unique = true)
    String token;

    @Column(name = "token_type", nullable = false, columnDefinition = "VARCHAR(50)")
    String tokenType;

    @Column(name = "expiration_date")
    LocalDateTime expirationDate;

    @Column(name = "revoked", nullable = false)
    Boolean revoked;

    @Column(name = "expired", nullable = false)
    Boolean expired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
