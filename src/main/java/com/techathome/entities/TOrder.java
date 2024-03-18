package com.techathome.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@Entity
public class TOrder {
    @Id
    @GeneratedValue(generator = "order_id_generator")
    @SequenceGenerator(name = "order_id_generator", sequenceName = "order_id_seq", allocationSize = 1)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private LocalDateTime orderDate;
    private double totalAmount;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address shippingAddress;


}

