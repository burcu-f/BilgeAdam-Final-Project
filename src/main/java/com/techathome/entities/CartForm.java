package com.techathome.entities;

import java.util.List;

import lombok.Data;



@Data
public class CartForm {

    private Long cartId;

    private List<CartDetailForm> cartDetails;
    
}
