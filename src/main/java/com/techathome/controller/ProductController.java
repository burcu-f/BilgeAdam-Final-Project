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
    public ResponseEntity<List<ProductForm>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductForm> list = products.stream().map(product -> mapper.fromProductEntity(product)).toList();
        if (!list.isEmpty()) {
        	return ResponseEntity.ok().body(list);
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


    
    @PutMapping(value= "/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductForm> updateProduct(@PathVariable("productId") Long productId,
            @RequestBody ProductForm updatedProductDTO) {
        // Convert ProductDTO to Product entity using Mapper
        Product updatedProduct = mapper.toProductEntity(updatedProductDTO);
        // Update the product in the database
        Product product = productService.updateProduct(productId, updatedProduct);
        // Convert the updated entity back to DTO using Mapper
        ProductForm updatedProductForm = mapper.fromProductEntity(product);
        // Return the updated DTO
        return ResponseEntity.ok().body(updatedProductForm);
    }

    
 // Method to delete a product by ID
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
