package com.techathome.repository;

import com.techathome.entities.Subcategory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

	public List<Subcategory> findByCategoryCategoryId(Long categoryId);
	
}
