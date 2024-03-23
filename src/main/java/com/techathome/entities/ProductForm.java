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
    private BrandForm brand;
    private String productDescription;
    private Double price;
    private Integer stock;
    private String image;
}
