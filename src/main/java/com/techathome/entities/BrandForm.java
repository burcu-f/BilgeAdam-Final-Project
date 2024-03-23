package com.techathome.entities;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandForm {
	    private Long brandId;
	    private String brandName;
	    private List<ProductForm> products;
}
