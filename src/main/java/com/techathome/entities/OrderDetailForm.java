package com.techathome.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderDetailForm {
    private Long orderDetailId;

    private ProductForm product;

    private Integer quantity;
    private Double itemPrice;
}
