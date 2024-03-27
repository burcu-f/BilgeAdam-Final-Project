package com.techathome.entities;

import java.util.Set;

import jakarta.persistence.*;
import lombok.Data;



@Data
@Entity
public class Cart {

    @Id
    @GeneratedValue(generator = "cart_id_generator")
    @SequenceGenerator(name = "cart_id_generator", sequenceName = "cart_id_seq", allocationSize = 1)
    private Long cartId;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;
    
    private Long productId;
    
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartDetail> cartDetails;
    
    private double totalPrice; 

    
}
