package com.techathome.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Sale {
    @Id
    @GeneratedValue(generator = "sale_id_generator")
    @SequenceGenerator(name = "sale_id_generator", sequenceName = "sale_id_seq", allocationSize = 1)
    private Long saleId;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private LocalDateTime saleDate;
    private double totalAmount;

    @ManyToOne
    @JoinColumn(name = "shipping_address_id")
    private Address shippingAddress;


}

