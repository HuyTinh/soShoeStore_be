package com.personal.soshoestore_be.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "shoe_colour")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShoeColour implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shoe_colour_id")
    Long id;

    @OneToOne(mappedBy = "shoeColour")
    @JsonIgnore
    Shoe shoe;

    @ManyToOne
    @JoinColumn(name = "vamp")
    Colour vamp;

    @ManyToOne
    @JoinColumn(name = "quarter")
    Colour quarter;

    @ManyToOne
    @JoinColumn(name = "sole")
    Colour sole;

}
