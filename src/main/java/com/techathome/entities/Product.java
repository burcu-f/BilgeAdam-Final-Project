package com.techathome.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(generator = "product_id_generator")
    @SequenceGenerator(name = "product_id_generator", sequenceName = "product_id_seq", allocationSize = 1)
    private Long productId;
    private String productName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subcategory_id")
    private Subcategory subcategory;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    private String productDescription;

    private double price;

    private int stock;

    private String image;


}
