package com.techathome.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.techathome.entities.Brand;
import com.techathome.services.BrandService;

@Controller
@RequestMapping("/admin/brand-management")
@PreAuthorize("hasRole('ADMIN')")
public class BrandManagementController {

    @Autowired
    private BrandService brandService;
    
    @Autowired
    private IMapper mapper;
    
    @GetMapping("")
    public ModelAndView brandManagementPage() {
        ModelAndView modelAndView = new ModelAndView("brand-management");
        modelAndView.addObject("pageTitle", "Brand Management");
        List<Brand> brands = brandService.getAllBrands();
        modelAndView.addObject("brands", brands); // Add brands to the model
        return modelAndView;
    }

    // CREATE a new brand
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Brand> createBrand(@RequestBody Brand brand) {
        brandService.saveBrand(brand);
        return new ResponseEntity<>(brand, HttpStatus.CREATED);
    }

    // UPDATE an existing brand
    @PutMapping("/{brandId}")
    public ResponseEntity<Brand> updateBrand(@PathVariable("brandId") Long brandId, @RequestBody Brand updatedBrand) {
        Brand existingBrand = brandService.getBrandById(brandId);
        if (existingBrand != null) {
        	 Brand brand = brandService.updateBrand(brandId, updatedBrand);
             return ResponseEntity.ok(brand);
        }else {
        	return ResponseEntity.notFound().build();
        }
    }
       
    // DELETE a brand
    @DeleteMapping("/{brandId}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long brandId) {
        Brand brand = brandService.getBrandById(brandId);
        if (brand != null) {
            brandService.deleteBrand(brandId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
