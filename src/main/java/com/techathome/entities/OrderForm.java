package com.techathome.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
public class OrderForm {
    private Long id;

    private Date orderDate;
    private double totalAmount;
    private AddressForm address;
    private AccountForm account;
    
    private List<OrderDetailForm> orderDetails = new ArrayList<>();
    
}

