package com.personal.soshoestore_be.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "shoes")
public class Shoe extends BaseModel implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shoe_id")
    @JsonProperty("id")
    private Long id;
    @Column(name = "name", columnDefinition = "NVARCHAR(255)")
    private String name;
    @Column(name = "description", columnDefinition = "NVARCHAR(2000)")
    private String description;
    @Column(name = "price")
    private double price;
    @Column(name = "image_url", columnDefinition = "VARCHAR(2000)")
    @JsonProperty("image_url")
    private String imageUrl;
    @Column(name = "image_url_back", columnDefinition = "VARCHAR(2000)")
    @JsonProperty("image_url_back")
    private String imageUrlBack;

    @OneToMany(mappedBy = "shoe",cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Builder.Default
    List<Image> images = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shoe_colour_id")
    private ShoeColour shoeColour;

    @ManyToMany(fetch = FetchType.EAGER ,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "shoe_size",
            joinColumns = @JoinColumn(name = "shoe_id"),
            inverseJoinColumns = @JoinColumn(name = "size_id"))
    private List<Size> sizes;

}

