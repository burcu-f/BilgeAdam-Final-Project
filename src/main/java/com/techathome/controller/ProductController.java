package com.techathome.controller;


import com.techathome.entities.Product;

import com.techathome.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/product-management")
public class ProductController {
    @Autowired
    private ProductService productService;
    
    @GetMapping("")
    public ModelAndView productManagementPage() {
        ModelAndView modelAndView = new ModelAndView("product-management");
        modelAndView.addObject("pageTitle", "Product Management");
        return modelAndView;
    }

    @GetMapping(value = "/products", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        if (!products.isEmpty()) {
        	return ResponseEntity.ok().body(products);
        } else {
        return ResponseEntity.noContent().build();
    }
    }
    
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
    	Product savedProduct = productService.saveProduct(product);
    	return ResponseEntity.ok().body(savedProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok().body(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
 // PUT mapping for updating a product
    @PutMapping(value= "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> updateProduct(@PathVariable("productId") Long productId,
            @RequestBody Product updatedProduct) {
    	Product product = productService.updateProduct(productId, updatedProduct);
    	return ResponseEntity.ok().body(product);
}
    
 // Method to delete a product by ID
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
