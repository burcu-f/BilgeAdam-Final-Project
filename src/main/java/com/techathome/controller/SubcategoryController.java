package com.techathome.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.techathome.config.IMapper;
import com.techathome.entities.Subcategory;
import com.techathome.entities.SubcategoryForm;
import com.techathome.services.SubcategoryService;

@Controller
@RequestMapping("/subcategory")
//@PreAuthorize("hasRole('USER')")
public class SubcategoryController {

	@Autowired
    private SubcategoryService subcategoryService;
	
	@Autowired
	private IMapper mapper;

    //Method to GET ALL subcategories
    @GetMapping("/list")
    public ResponseEntity<List<SubcategoryForm>> getAllSubcategories(@RequestParam(required = false) Long categoryId) {
        List<Subcategory> subcategories = null;
        if (categoryId != null) {
        	subcategories = subcategoryService.getSubcategoriesByCategoryId(categoryId);
        } else {
        	subcategories = subcategoryService.getAllSubcategories();
        }
        List<SubcategoryForm> list = subcategories.stream().map(s -> mapper.fromSubcategoryEntity(s)).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
    //Method to GET a subcategory by ID
    @GetMapping("/{subcategoryId}")
    public ResponseEntity<SubcategoryForm> getSubcategoryById(@PathVariable Long subcategoryId) {
        Subcategory subcategory = subcategoryService.getSubcategoryById(subcategoryId);
        if (subcategory != null) {
            return ResponseEntity.ok().body(mapper.fromSubcategoryEntity(subcategory));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
}

