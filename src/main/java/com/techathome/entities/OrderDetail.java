package com.techathome.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "t_order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(generator = "order_detail_id_generator")
    @SequenceGenerator(name = "order_detail_id_generator", sequenceName = "order_detail_id_seq", allocationSize = 1)
    private Long orderDetailId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    private Integer quantity;
    private Double itemPrice;
    //private double totalPrice;

}
