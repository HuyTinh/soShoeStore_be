package com.personal.soshoestore_be.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "roles")
public class Role implements Serializable {

    @Id
    @Column(name = "role_id")
    @JsonProperty("id")
    private Integer id;

    @Column(name = "name", columnDefinition = "VARCHAR(20)")
    @JsonProperty("name")
    private String name;

    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";
}
