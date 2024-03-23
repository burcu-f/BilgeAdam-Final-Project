package com.techathome.controller;

import com.techathome.config.IMapper;
import com.techathome.entities.Brand;
import com.techathome.entities.BrandForm;
import com.techathome.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/brand-management")
public class BrandController {

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

    @GetMapping(value = "/brands", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BrandForm>> getAllBrands() {
        List<Brand> brands = brandService.getAllBrands();
        List<BrandForm> list = brands.stream().map(brand -> mapper.fromBrandEntity(brand)).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // GET a single brand by ID
    @GetMapping("/{brandId}")
    public ResponseEntity<Brand> getBrandById(@PathVariable Long brandId) {
        Brand brand = brandService.getBrandById(brandId);
        if (brand != null) {
            return new ResponseEntity<>(brand, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
