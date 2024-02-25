package com.techathome.controller;

import com.techathome.entities.Category;
import com.techathome.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/category-management")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public ModelAndView categoryManagementPage() {
        ModelAndView modelAndView = new ModelAndView("category-management");
        modelAndView.addObject("pageTitle", "Category Management");
        return modelAndView;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Integer categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        if (category != null) {
            return ResponseEntity.ok().body(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
