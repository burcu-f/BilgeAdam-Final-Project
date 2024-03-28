package com.techathome.entities;



import lombok.Data;

@Data
public class CartDetailForm {

    private Long cartDetailId;

    private CartForm cart;

    private ProductForm product;

    private int quantity;
    private double itemPrice;
    private double totalPrice;
    
    
}
