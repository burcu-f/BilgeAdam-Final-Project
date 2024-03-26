package com.techathome.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techathome.config.IMapper;
import com.techathome.entities.Product;
import com.techathome.entities.ProductForm;
import com.techathome.services.ProductService;

@Controller
@RequestMapping("/product")
//@PreAuthorize("hasRole('USER')")
public class ProductController {
    @Autowired
    private ProductService productService;
    
    @Autowired
    private IMapper mapper;
    
    @GetMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductForm>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductForm> list = products.stream().map(product -> mapper.fromProductEntity(product)).toList();
        if (!list.isEmpty()) {
        	return ResponseEntity.ok().body(list);
        } else {
	        return ResponseEntity.noContent().build();
	    }
    }
    
    @GetMapping("/{productId}")
    public ResponseEntity<ProductForm> getProductById(@PathVariable("productId") Long id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            ProductForm productForm = mapper.fromProductEntity(product);
            return ResponseEntity.ok().body(productForm);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
