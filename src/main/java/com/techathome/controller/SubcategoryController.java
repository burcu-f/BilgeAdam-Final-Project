package com.techathome.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.techathome.entities.Subcategory;
import com.techathome.services.SubcategoryService;

@Controller
@RequestMapping("/subcategory-management")
public class SubcategoryController {

	@Autowired
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
    
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Subcategory> createSubcategory(@RequestBody Subcategory subcategory) {
    	Subcategory savedSubcategory = subcategoryService.saveSubcategory(subcategory);
    	return ResponseEntity.ok().body(savedSubcategory);
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
