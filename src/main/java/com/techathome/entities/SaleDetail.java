package com.techathome.entities;

import jakarta.persistence.*;

@Entity
public class SaleDetail {
    @Id
    @GeneratedValue(generator = "sale_detail_id_generator")
    @SequenceGenerator(name = "sale_detail_id_generator", sequenceName = "sale_detail_id_seq", allocationSize = 1)
    private Long saleDetailId;

    @ManyToOne
    @JoinColumn(name = "saleId")
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    private int quantity;
    private double itemPrice;
    private double totalPrice;

}
