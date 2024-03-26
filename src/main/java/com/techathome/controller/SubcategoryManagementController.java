package com.techathome.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.ModelAndView;

import com.techathome.config.IMapper;
import com.techathome.entities.Subcategory;
import com.techathome.entities.SubcategoryForm;
import com.techathome.services.SubcategoryService;

@Controller
@RequestMapping("/admin/subcategory-management")
//@PreAuthorize("hasRole('ADMIN')")
public class SubcategoryManagementController {

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

    //Method to ADD a new subcategory
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubcategoryForm> createSubcategory(@RequestBody Subcategory subcategory) {
    	Subcategory savedSubcategory = subcategoryService.saveSubcategory(subcategory);
    	return ResponseEntity.ok().body(mapper.fromSubcategoryEntity(savedSubcategory));
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

