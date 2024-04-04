package com.techathome.entities;

import java.util.ArrayList;

import java.util.List;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Entity
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(generator = "cart_id_generator")
    @SequenceGenerator(name = "cart_id_generator", sequenceName = "cart_id_seq", allocationSize = 1)
    private Long cartId;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;
    
    @OneToMany(mappedBy = "cart")
    private List<CartDetail> cartDetails = new ArrayList<>();
    
}
