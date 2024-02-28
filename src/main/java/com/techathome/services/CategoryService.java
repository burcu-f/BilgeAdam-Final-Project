package com.techathome.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techathome.entities.Category;
import com.techathome.repository.CategoryRepository;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findByCategoryId(categoryId).orElse(null);
    }
    
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long categoryId, Category updatedCategory) {
        // Find the category in the database by categoryId
        Category categoryToUpdate = categoryRepository.findByCategoryId(categoryId)
        		.orElseThrow(() -> new IllegalArgumentException("Category not found"));
        
   
            // Update the category with the provided information
            categoryToUpdate.setCategoryName(updatedCategory.getCategoryName());
            categoryToUpdate.setDescription(updatedCategory.getDescription());
          
            
            // Return the updated category
            return categoryRepository.save(categoryToUpdate);
        }
    }

   