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

    public Brand getBrandById(Integer brandId) {
        return brandRepository.findById(brandId).orElse(null);
    }

    public void saveBrand(Brand brand) {
        brandRepository.save(brand);
    }

    public void deleteBrand(Integer brandId) {
        brandRepository.deleteById(brandId);
    }



}
