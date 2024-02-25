package com.techathome.controller;

import com.techathome.entities.Category;
import com.techathome.entities.Subcategory;
import com.techathome.services.SubcategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/subcategory-management")
public class SubcategoryController {

    private SubcategoryService subcategoryService;

    @GetMapping("")
    public ModelAndView subcategoryManagementPage() {
        ModelAndView modelAndView = new ModelAndView("subcategory-management");
        modelAndView.addObject("pageTitle", "Subcategory Management");
        return modelAndView;
    }

    @GetMapping("/subcategories")
    public ResponseEntity<List<Subcategory>> getAllSubcategories() {
        List<Subcategory> subcategories = subcategoryService.getAllSubcategories();
        return new ResponseEntity<>(subcategories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subcategory> getSubcategoryById(@PathVariable Long subcategoryId) {
        Subcategory subcategory = subcategoryService.getSubcategoryById(subcategoryId);
        if (subcategory != null) {
            return ResponseEntity.ok().body(subcategory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
