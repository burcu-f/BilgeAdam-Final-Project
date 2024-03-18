package com.techathome.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class OrderDetail {
    @Id
    @GeneratedValue(generator = "order_detail_id_generator")
    @SequenceGenerator(name = "order_detail_id_generator", sequenceName = "order_detail_id_seq", allocationSize = 1)
    private Long orderDetailId;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private TOrder order;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    private int quantity;
    private double itemPrice;
    private double totalPrice;

}
