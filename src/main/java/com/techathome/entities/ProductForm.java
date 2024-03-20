package com.techathome.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductForm {
	private Long productId;
    private String productName;
    private CategoryForm category;
    private SubcategoryForm subcategory;
    private Long brandId;
    private String productDescription;
    private double price;
    private int stock;
    private String image;

}
