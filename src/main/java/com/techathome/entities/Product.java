package com.techathome.entities;

import org.hibernate.annotations.Type;

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

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    
    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private Subcategory subcategory;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    private String productDescription;

    private Double price;

    private Integer stock;
    
    @Column(columnDefinition = "CLOB")
    private String image;
    
 // Method to decrement stock by a specified quantity
    public void decrementStock(int quantity) {
        if (quantity > 0) {
            this.stock -= quantity;
        }
    }


}
