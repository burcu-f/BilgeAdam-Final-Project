package com.techathome.config;

import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.techathome.entities.Account;
import com.techathome.entities.AccountForm;
import com.techathome.entities.Address;
import com.techathome.entities.AddressForm;
import com.techathome.entities.Brand;
import com.techathome.entities.BrandForm;
import com.techathome.entities.Cart;
import com.techathome.entities.CartDetail;
import com.techathome.entities.CartDetailForm;
import com.techathome.entities.CartForm;
import com.techathome.entities.Category;
import com.techathome.entities.CategoryForm;
import com.techathome.entities.Order;
import com.techathome.entities.OrderForm;
import com.techathome.entities.Product;
import com.techathome.entities.ProductForm;
import com.techathome.entities.Subcategory;
import com.techathome.entities.SubcategoryForm;

@org.mapstruct.Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IMapper {
	
	@Mapping(target = "subcategories.category", ignore = true)
	CategoryForm fromCategoryEntity(Category source);
	@Mapping(target = "subcategories.category", ignore = true)
	Category toCategoryEntity(CategoryForm source);

	@Mapping(target = "category.subcategories", ignore = true)
	SubcategoryForm fromSubcategoryEntity(Subcategory source);
	@Mapping(target = "category.subcategories", ignore = true)
	Subcategory toSubcategoryEntity(SubcategoryForm source);
	
	// Method to convert from ProductForm to Product
//    @Mapping(target = "category.subcategories", ignore = true)
//    @Mapping(target = "subcategories.category", ignore = true)
	@Mapping(target = "brand.products", ignore = true)
    Product toProductEntity(ProductForm source);

    // Method to convert from Product to ProductDTO
//    @Mapping(target = "categoryId", expression = "java(source.getSubcategory() != null ? source.getCategory().getCategoryId() : null)")
//    @Mapping(target = "subcategoryId", expression = "java(source.getSubcategory() != null ? source.getSubcategory().getSubcategoryId() : null)")
    @Mapping(target = "brand.products", ignore = true)
    ProductForm fromProductEntity(Product source);
    
    @Mapping(target = "products.brand", ignore = true)
    Brand toBrandEntity(BrandForm source);
    @Mapping(target = "products.brand", ignore = true)
    BrandForm fromBrandEntity(Brand source);

    @Mapping(target = "cartDetails.cart", ignore = true)
    Cart toCartEntity(CartForm source);
    @Mapping(target = "cartDetails.cart", ignore = true)
    CartForm fromCartEntity(Cart source);

    @Mapping(target = "cart.cartDetails", ignore = true)
    CartDetail toCartDetailEntity(CartDetailForm source);
    @Mapping(target = "cart.cartDetails", ignore = true)
    CartDetailForm fromCartDetailEntity(CartDetail source);

    Address toAddressEntity(AddressForm source);
    AddressForm fromAddressEntity(Address source);

    Order toOrderEntity(OrderForm source);
    OrderForm fromOrderEntity(Order source);

    Account toAccountEntity(AccountForm source);
    AccountForm fromAccountEntity(Account source);
    
}
