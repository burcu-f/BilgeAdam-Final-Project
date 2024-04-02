package com.techathome.repository;

import com.techathome.entities.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository <Product, Long> {
	 List<Product> findByCategoryCategoryId(Long categoryId);
	 List<Product> findBySubcategorySubcategoryId(Long subcategoryId);
}
