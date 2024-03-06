package com.techathome.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.techathome.entities.Category;
import com.techathome.entities.Subcategory;
import com.techathome.services.CategoryService;

@Controller
@RequestMapping("/category-management")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public ModelAndView categoryManagementPage() {
        ModelAndView modelAndView = new ModelAndView("category-management");
        modelAndView.addObject("pageTitle", "Category Management");
        List<Category> categories = categoryService.getAllCategories();
        modelAndView.addObject("categories", categories); // Add categories to the model
        return modelAndView;
    }


    @GetMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok().body(categories);
    }


    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> createAccount(@RequestBody Category category) {
    	Category savedCategory = categoryService.saveCategory(category);
    	return ResponseEntity.ok().body(savedCategory);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryByCategoryId(@PathVariable Long categoryId) {
        Category category = categoryService.getCategoryByCategoryId(categoryId);
        if (category != null) {
            return ResponseEntity.ok().body(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
 // Method to update an existing category
    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") Long categoryId, @RequestBody Category updatedCategory) {
        Category category = categoryService.updateCategory(categoryId, updatedCategory);
        return ResponseEntity.ok().body(category);
    }
    
    @GetMapping("/{categoryId}/subcategories")
    public ResponseEntity<List<Subcategory>> getSubcategoriesByCategory(@PathVariable Long categoryId) {
        List<Subcategory> subcategories = categoryService.getSubcategoriesByCategory(categoryId);
        return ResponseEntity.ok().body(subcategories);
    }

}
