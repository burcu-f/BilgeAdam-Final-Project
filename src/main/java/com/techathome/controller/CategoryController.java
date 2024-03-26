package com.techathome.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techathome.config.IMapper;
import com.techathome.entities.Category;
import com.techathome.entities.CategoryForm;
import com.techathome.entities.Subcategory;
import com.techathome.services.CategoryService;

@Controller
@RequestMapping("/category")
//@PreAuthorize("hasRole('USER')")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private IMapper mapper;
    
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoryForm>> getAllCategories() {
		List<Category> categories = categoryService.getAllCategories();
		List<CategoryForm> formList = categories.stream().map(c -> mapper.fromCategoryEntity(c)).toList();
		return ResponseEntity.ok().body(formList);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryForm> getCategoryByCategoryId(@PathVariable Long categoryId) {
        Category category = categoryService.getCategoryByCategoryId(categoryId);
        if (category != null) {
            return ResponseEntity.ok().body(mapper.fromCategoryEntity(category));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{categoryId}/subcategories")
    public ResponseEntity<List<Subcategory>> getSubcategoriesByCategory(@PathVariable Long categoryId) {
        List<Subcategory> subcategories = categoryService.getSubcategoriesByCategory(categoryId);
        return ResponseEntity.ok().body(subcategories);
    }
}
