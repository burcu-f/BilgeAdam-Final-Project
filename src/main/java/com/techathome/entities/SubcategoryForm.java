package com.techathome.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubcategoryForm {

    private Long subcategoryId;
    private String subcategoryName;

    private CategoryForm category;

}
