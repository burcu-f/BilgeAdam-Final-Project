package com.techathome.config;

import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.techathome.entities.Category;
import com.techathome.entities.CategoryForm;
import com.techathome.entities.Subcategory;
import com.techathome.entities.SubcategoryForm;

@org.mapstruct.Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IMapper {
	
	@Mapping(target = "subcategories.category", ignore = true)
	CategoryForm fromCategoryEntity(Category source);
	Category toCategoryEntity(CategoryForm source);

	@Mapping(target = "category.subcategories", ignore = true)
	SubcategoryForm fromSubcategoryEntity(Subcategory source);
	Subcategory toSubcategoryEntity(SubcategoryForm source);
}
