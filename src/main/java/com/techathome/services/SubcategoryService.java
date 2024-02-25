package com.techathome.services;

import com.techathome.entities.Subcategory;
import com.techathome.repository.SubcategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubcategoryService {

    private SubcategoryRepository subcategoryRepository;

    public List<Subcategory> getAllSubcategories() {return subcategoryRepository.findAll();}

    public Subcategory getSubcategoryById(Long subcategoryId) {return subcategoryRepository.findById(subcategoryId).orElse(null);}
}
