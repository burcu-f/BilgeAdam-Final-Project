package com.techathome.controller;


import com.techathome.config.IMapper;
import com.techathome.entities.Product;
import com.techathome.entities.ProductForm;
import com.techathome.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/product-management")
public class ProductController {
    @Autowired
    private ProductService productService;
    
    @Autowired
    private IMapper mapper;
    
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
    public ResponseEntity<ProductForm> createProduct(@RequestBody ProductForm productDTO) {
        // Convert ProductDTO to Product entity using Mapper
        Product product = mapper.toProductEntity(productDTO);
        // Save the product and get the saved entity
        Product savedProduct = productService.saveProduct(product);
        // Convert the saved entity back to DTO using Mapper
        ProductForm savedProductDTO = mapper.fromProductEntity(savedProduct);
        // Return the saved DTO
        return ResponseEntity.ok().body(savedProductDTO);
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
