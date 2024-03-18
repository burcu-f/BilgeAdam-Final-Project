package com.techathome.services;

import com.techathome.entities.Subcategory;
import com.techathome.repository.SubcategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubcategoryService {

	@Autowired
    private SubcategoryRepository subcategoryRepository;

    public List<Subcategory> getAllSubcategories() {
    	return subcategoryRepository.findAll();
    	}

    public Subcategory getSubcategoryById(Long subcategoryId) {
    	return subcategoryRepository.findById(subcategoryId).orElse(null);
    	}

	public Subcategory saveSubcategory(Subcategory subcategory) {
		return subcategoryRepository.save(subcategory);
	}
	
	public Subcategory updateSubcategory(Long subcategoryId, Subcategory updatedSubcategory) {
        // Retrieve the existing subcategory from the database
        Subcategory existingSubcategory = subcategoryRepository.findById(subcategoryId).
        		orElseThrow(() -> new IllegalArgumentException("Subcategory not found"));
        		

        	// Update the subcategory with the provided information
     
            existingSubcategory.setSubcategoryName(updatedSubcategory.getSubcategoryName());
            existingSubcategory.setCategory(updatedSubcategory.getCategory());
           

            // Save the updated subcategory to the database
            return subcategoryRepository.save(existingSubcategory);
            
	}
       
	
	public void deleteSubcategory(Long subcategoryId) {
		Subcategory subcategory = subcategoryRepository.findById(subcategoryId).orElseThrow();
		subcategoryRepository.delete(subcategory);
		
		subcategoryRepository.deleteById(subcategoryId);
    }
}
