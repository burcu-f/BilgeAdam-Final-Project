package com.techathome.services;

import com.techathome.entities.Product;
import com.techathome.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, Product updatedProduct) {
        Product existingProduct = productRepository.findById(productId)
        		.orElseThrow(() -> new IllegalArgumentException("Product not found"));;
        if (existingProduct != null) {
            // Update existing product fields with fields from updatedProduct
            existingProduct.setProductName(updatedProduct.getProductName());
            existingProduct.setCategory(updatedProduct.getCategory());
            existingProduct.setBrand(updatedProduct.getBrand());
            existingProduct.setProductDescription(updatedProduct.getProductDescription());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setStock(updatedProduct.getStock());
            existingProduct.setImage(updatedProduct.getImage());

            // Save the updated product
            productRepository.save(existingProduct);
        }
		return existingProduct;
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}