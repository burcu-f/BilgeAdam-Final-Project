package com.techathome.services;

import com.techathome.entities.Subcategory;
import com.techathome.repository.SubcategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubcategoryService {

	@Autowired
    private SubcategoryRepository subcategoryRepository;

    public List<Subcategory> getAllSubcategories() {return subcategoryRepository.findAll();}

    public Subcategory getSubcategoryById(Long subcategoryId) {return subcategoryRepository.findById(subcategoryId).orElse(null);}

	public Subcategory saveSubcategory(Subcategory subcategory) {
		return subcategoryRepository.save(subcategory);
	}
}
