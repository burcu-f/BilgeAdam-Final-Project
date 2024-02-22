package com.techathome.controller;

import com.techathome.entities.Brand;
import com.techathome.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    public ResponseEntity<List<Brand>> getAllBrands() {
        List<Brand> brands = brandService.getAllBrands();
        return new ResponseEntity<>(brands, HttpStatus.OK);
    }

    // GET a single brand by ID
    @GetMapping("/{brandId}")
    public ResponseEntity<Brand> getBrandById(@PathVariable Integer brandId) {
        Brand brand = brandService.getBrandById(brandId);
        if (brand != null) {
            return new ResponseEntity<>(brand, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // CREATE a new brand
    @PostMapping
    public ResponseEntity<Brand> createBrand(@RequestBody Brand brand) {
        brandService.saveBrand(brand);
        return new ResponseEntity<>(brand, HttpStatus.CREATED);
    }

    // UPDATE an existing brand
    @PutMapping("/{brandId}")
    public ResponseEntity<Brand> updateBrand(@PathVariable Integer brandId, @RequestBody Brand updatedBrand) {
        Brand existingBrand = brandService.getBrandById(brandId);
        if (existingBrand != null) {
            existingBrand.setBrandName(updatedBrand.getBrandName());
            // Set other properties if needed
            brandService.saveBrand(existingBrand);
            return new ResponseEntity<>(existingBrand, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE a brand
    @DeleteMapping("/{brandId}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Integer brandId) {
        Brand brand = brandService.getBrandById(brandId);
        if (brand != null) {
            brandService.deleteBrand(brandId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
