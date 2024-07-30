package com.personal.soshoestore_be.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseModel implements Serializable {

    @Id
    @Column(name ="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "first_name", columnDefinition = "NVARCHAR(100)")
    String firstName;

    @Column(name = "last_name", columnDefinition = "NVARCHAR(100)")
    String lastName;

    @Column(name = "email", columnDefinition = "VARCHAR(100)")
    String email;

    @Column(name = "date_of_birth")
    Date dateOfBirth;

    @Column(name = "phone_number", columnDefinition = "VARCHAR(10)", nullable = false)
    String phoneNumber;

    @Column(name = "address", columnDefinition = "NVARCHAR(200)")
    String address;

    @Column(name ="password", columnDefinition = "VARCHAR(200)")
    String password;

    @Column(name = "is_active")
    @Builder.Default
    Boolean isActive = true;

    @Column(name = "facebook_account_id")
    @Builder.Default
    Boolean facebookAccountId = false;

    @Column(name = "google_account_id")
    @Builder.Default
    Boolean googleAccountId = false;

    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;
}
