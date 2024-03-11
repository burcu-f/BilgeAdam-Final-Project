package com.techathome.repository;

import com.techathome.entities.Category;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository <Category, Integer> {

	//@Query("SELECT c FROM Category c LEFT JOIN FETCH c.subcategories WHERE c.categoryId = :categoryId")
	Optional <Category> findByCategoryId(Long categoryId);
	
	void deleteByCategoryId(Long categoryId);
}
