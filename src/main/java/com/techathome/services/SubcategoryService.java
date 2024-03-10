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

    public List<Subcategory> getAllSubcategories() {return subcategoryRepository.findAll();}

    public Subcategory getSubcategoryById(Long subcategoryId) {return subcategoryRepository.findById(subcategoryId).orElse(null);}

	public Subcategory saveSubcategory(Subcategory subcategory) {
		return subcategoryRepository.save(subcategory);
	}
	
	public Subcategory updateSubcategory(Long subcategoryId, Subcategory updatedSubcategory) {
        // Retrieve the existing subcategory from the database
        Subcategory existingSubcategory = subcategoryRepository.findById(subcategoryId).orElse(null);

        // If the subcategory exists, update its fields with the provided details
        if (existingSubcategory != null) {
            existingSubcategory.setSubcategoryName(updatedSubcategory.getSubcategoryName());
            existingSubcategory.setCategory(updatedSubcategory.getCategory());
            // Add more fields to update as needed

            // Save the updated subcategory to the database
            return subcategoryRepository.save(existingSubcategory);
        } else {
            // If the subcategory is not found, return null or throw an exception
            return null;
        }
    }
	
	public boolean deleteSubcategory(Long subcategoryId) {
        Optional<Subcategory> optionalSubcategory = subcategoryRepository.findById(subcategoryId);
        if (optionalSubcategory.isPresent()) {
            subcategoryRepository.deleteById(subcategoryId);
            return true; // Deletion successful
        } else {
            return false; // Subcategory not found
        }
    }
}
