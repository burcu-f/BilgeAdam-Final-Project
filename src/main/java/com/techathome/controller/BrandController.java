package com.techathome.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techathome.config.IMapper;
import com.techathome.entities.Brand;
import com.techathome.entities.BrandForm;
import com.techathome.services.BrandService;

@Controller
@RequestMapping("/brand")
//@PreAuthorize("hasRole('USER')")
public class BrandController {

    @Autowired
    private BrandService brandService;
    
    @Autowired
    private IMapper mapper;
    
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
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

}
