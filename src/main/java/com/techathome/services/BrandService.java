package com.techathome.services;

import com.techathome.entities.Brand;

import com.techathome.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Brand getBrandById(Long brandId) {
        return brandRepository.findById(brandId).orElse(null);
    }

    public void saveBrand(Brand brand) {
        brandRepository.save(brand);
    }

    public void deleteBrand(Long brandId) {
        brandRepository.deleteById(brandId);
    }
    
    public Brand updateBrand(Long brandId, Brand updatedBrand) {
        // Find the brand in the database by brandId
    	Brand brandToUpdate = brandRepository.findById(brandId)
        		.orElseThrow(() -> new IllegalArgumentException("Brand not found"));
        
   
            // Update the brand with the provided information
    	brandToUpdate.setBrandName(updatedBrand.getBrandName());
            
            // Return the updated brand
            return brandRepository.save(brandToUpdate);
        }



}
