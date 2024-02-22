package com.techathome.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Cart {

    @Id
    @GeneratedValue(generator = "cart_id_generator")
    @SequenceGenerator(name = "cart_id_generator", sequenceName = "cart_id_seq", allocationSize = 1)
    private Long cartId;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;

    private LocalDateTime cartDate;
}
