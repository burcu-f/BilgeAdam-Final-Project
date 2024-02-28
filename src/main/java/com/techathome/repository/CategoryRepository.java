package com.techathome.repository;

import com.techathome.entities.Category;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository <Category, Integer> {

	Optional <Category> findByCategoryId(Long categoryId);
	
	void deleteByCategoryId(Long categoryId);
}
