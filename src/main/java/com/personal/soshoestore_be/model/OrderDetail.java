package com.personal.soshoestore_be.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "order_details")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;

    @ManyToOne
    @JoinColumn(name = "shoe_id")
    Shoe shoe;

    @Column(name = "price")
    Double price;

    @Column(name = "number_of_product", nullable = false)
    Integer numberOfProduct;

    @Column(name = "total_money", nullable = false)
    Double totalMoney;

    @ManyToOne
    @JoinColumn(name = "size_id")
    Size size;

    @ManyToOne
    @JoinColumn(name = "colour_id")
    ShoeColour colour;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;
}
