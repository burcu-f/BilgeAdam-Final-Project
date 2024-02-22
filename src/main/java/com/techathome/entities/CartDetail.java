package com.techathome.entities;

import jakarta.persistence.*;

@Entity
public class CartDetail {

    @Id
    @GeneratedValue(generator = "cart_detail_id_generator")
    @SequenceGenerator(name = "cart_detail_id_generator", sequenceName = "cart_detail_id_seq", allocationSize = 1)
    private Long cartDetailId;

    @ManyToOne
    @JoinColumn(name = "cartId")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    private int quantity;
    private double itemPrice;
    private double totalPrice;
}
