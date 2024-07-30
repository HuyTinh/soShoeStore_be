package com.personal.soshoestore_be.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "social_accounts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SocialAccount implements Serializable {

    @Id
    @Column(name = "social_account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "provider", columnDefinition = "VARCHAR(20)", nullable = false)
    String provider;

    @Column(name = "provider_id", columnDefinition = "VARCHAR(50)", nullable = false)
    String providerId;

    @Column(name = "email", columnDefinition = "VARCHAR(150)", nullable = false)
    String email;

    @Column(name = "name", columnDefinition = "VARCHAR(100)", nullable = false)
    String name;
}
