package com.techathome.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.techathome.config.IMapper;
import com.techathome.entities.Subcategory;
import com.techathome.entities.SubcategoryForm;
import com.techathome.services.SubcategoryService;

@Controller
@RequestMapping("/subcategory-management")
public class SubcategoryController {

	@Autowired
    private SubcategoryService subcategoryService;
	
	@Autowired
	private IMapper mapper;

    @GetMapping("")
    public ModelAndView subcategoryManagementPage() {
        ModelAndView modelAndView = new ModelAndView("subcategory-management");
        modelAndView.addObject("pageTitle", "Subcategory Management");
        List<Subcategory> subcategories = subcategoryService.getAllSubcategories();
        List<SubcategoryForm> list = subcategories.stream().map(s -> mapper.fromSubcategoryEntity(s)).toList();
        modelAndView.addObject("subcategories", list);
        return modelAndView;
    }

    //Method to GET ALL subcategories
    @GetMapping("/subcategories")
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
    
    
    //Method to ADD a new subcategory
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubcategoryForm> createSubcategory(@RequestBody Subcategory subcategory) {
    	Subcategory savedSubcategory = subcategoryService.saveSubcategory(subcategory);
    	return ResponseEntity.ok().body(mapper.fromSubcategoryEntity(savedSubcategory));
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
    
 // PUT mapping for updating a subcategory
    @PutMapping(value= "/{subcategoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubcategoryForm> updateSubcategory(@PathVariable ("subcategoryId") Long subcategoryId, @RequestBody Subcategory updatedSubcategory) {
        Subcategory existingSubcategory = subcategoryService.getSubcategoryById(subcategoryId);
        if (existingSubcategory == null) {
        	return ResponseEntity.notFound().build(); // Return 404 Not Found response
        }
        Subcategory subcategory = subcategoryService.updateSubcategory(subcategoryId, updatedSubcategory);
        return ResponseEntity.ok().body(mapper.fromSubcategoryEntity(subcategory));
    }
    
 // Method to delete a subcategory by ID
    @DeleteMapping("/{subcategoryId}")
    public ResponseEntity<Void> deleteSubcategory(@PathVariable Long subcategoryId) {
        subcategoryService.deleteSubcategory(subcategoryId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}

