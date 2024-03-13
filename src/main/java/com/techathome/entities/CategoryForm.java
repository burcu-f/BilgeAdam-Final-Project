package com.techathome.entities;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryForm {


    private Long categoryId;

    private String categoryName;

    private String description;
    
    private List<SubcategoryForm> subcategories;

}
