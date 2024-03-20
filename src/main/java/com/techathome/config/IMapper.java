package com.techathome.config;

import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.techathome.entities.Category;
import com.techathome.entities.CategoryForm;
import com.techathome.entities.Product;
import com.techathome.entities.ProductForm;
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
	
	// Method to convert from ProductForm to Product
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "subcategory", ignore = true)
    Product toProductEntity(ProductForm source);

    // Method to convert from Product to ProductDTO
    @Mapping(target = "categoryId", expression = "java(source.getCategory().getCategoryId())")
    @Mapping(target = "subcategoryId", expression = "java(source.getSubcategory().getSubcategoryId())")
    ProductForm fromProductEntity(Product source);
}
