$(document).ready(function() {
    // Function to populate the product table
    function populateProductTable(products) {
        $("#productTable tbody").empty();
        
        if (!products || products.length == 0) {
			return;
		}
        let i = 1;
        products.forEach(function(product) {
            let row = $('<tr>', {
                "row-id": product.productId
            });
            row.append(Common.createTd(i));
            row.append(Common.createTd(product.productName));
            row.append(Common.createTd(product.brand ? product.brand.brandName : 'N/A')); // Add null check for brand
            row.append(Common.createTd(product.subcategory ? product.subcategory.subcategoryName : 'N/A')); // Add null check for subcategory
            row.append(Common.createTd(product.category ? product.category.categoryName : 'N/A')); // Add null check for category
            row.append(Common.createTd(product.productDescription));
            row.append(Common.createTd(product.price));
            row.append(Common.createTd(product.stock));
            row.append(Common.createTd(product.image));
            
            // Actions column containing update and delete buttons
            let actionsTd = Common.createTd();
           
            // Update button
            let updateBtn = $("<button>", {
                class: "btn btn-warning",
                text: "Edit",
                click: function() {
                    var productId = $(this).closest("tr").attr("row-id");
                    updateProduct(productId);
                }
            });
            actionsTd.append(updateBtn);

            // Delete button
            let deleteBtn = $("<button>", {
                class: "btn btn-danger",
                text: "Delete",
                click: function() {
                    deleteProduct(product.productId);
                }
            });
            actionsTd.append(deleteBtn);
            
            // Add actions column to the row
            row.append(actionsTd);
            ++i;
            $("#productTable tbody").append(row);
        });
    }

    // Function to refresh the product list
    function refreshProductList() {
        Common.ajax({
            url: "/product-management/products",
            type: "GET",
            success: function(products) {
                populateProductTable(products);
            },
            error: function(xhr, status, error) {
                console.error("Error refreshing product list: " + error);
            }
        });
    }
    
    // Populate the product table when the page is loaded
    refreshProductList();
    
    populateBrandCombo();
    
    populateCategoryCombo();
    
    populateSubcategoryCombo();
    
    $("#btnAddProduct").click(function() {
        $("#productModal").modal('show');
    });

    // Function to handle addition of a new product
    $("#btnAddProductModal").click(function() {
        let productName = $("#productName").val();
        let brand = $("#brand").val();
        let categoryId = $("#categoryId").val();
        let subcategoryId = $("#subcategoryId").val();
        let productDescription = $("#productDescription").val();
        let price = $("#price").val();
        let stock = $("#stock").val();
        let image = $("#image").val();
        
		let newProduct = {
            productName: productName,
            brand: {
                brandId: brand
            },
            category: {
                categoryId: categoryId
            },
            subcategory: {
                subcategoryId: subcategoryId
            },
            productDescription: productDescription,
            price: price,
            stock: stock,
            image: image
        };
	
        // AJAX request to add the new product
        Common.ajax({
            url: "/product-management/create",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(newProduct),
            success: function(response) {
                alert("Product added successfully!");
                $("#productModal").modal("hide");
                refreshProductList(); // Refresh the product list after addition
            },
            error: function(xhr, status, error) {
                alert("Error adding product: " + error);
            }
        });
});
    

    
     // Function to delete a product
    function deleteProduct(productId) {
		// Show the delete confirmation modal
		$("#deleteProductModal").modal("show");
		// Set up the delete confirmation button click event
		$("#btnConfirmDelete").off("click").on("click", function() {
        // Send AJAX request to delete the product
            Common.ajax({
                url: "/product-management/" + productId,
                type: "DELETE",
                headers: {
                    Accept: "application/json"
                },
                success: function(response) {
                    alert("Product deleted successfully!");
                    $("#deleteProductModal").modal("hide");
                    refreshProductList(); // Refresh the product list after deletion
                },
                error: function(xhr, status, error) {
                    alert("Error deleting product: " + error);
                }
            });
        });
    }


 // Function to update a product
function updateProduct(productId) {
    // Retrieve product details via AJAX request
    $.ajax({
        url: "/product-management/" + productId,
        type: "GET",
        headers: {
            Accept: "application/json",
        },
        success: function(product) {
            console.log("Retrieved product details:", product); // Debugging
            // Populate the modal with product details
            $("#updatedProductId").val(product.productId);
            $("#updatedProductName").val(product.productName);
            $("#updatedBrand").val(product.brand.brandId); // Use brandId for selection
            $("#updatedCategoryId").val(product.category.categoryId);
            $("#updatedSubcategoryId").val(product.subcategory.subcategoryId);
            $("#updatedProductDescription").val(product.productDescription);
            $("#updatedPrice").val(product.price);
            $("#updatedStock").val(product.stock);
            $("#updatedImage").val(product.image);
            
            // Show the update modal
            $("#updateProductModal").modal("show");
        },
        error: function(xhr, status, error) {
            console.error("Error retrieving product details: " + error);
        }
    });
}

// Add event handler for update button click
$("#btnUpdateProductModal").click(function() {
    var productId = $("#updatedProductId").val();
    var productName = $("#updatedProductName").val();
    var brand = $("#updatedBrand").val();
    var categoryId = $("#updatedCategoryId").val();
    var subcategoryId = $("#updatedSubcategoryId").val();
    var productDescription = $("#updatedProductDescription").val();
    var price = $("#updatedPrice").val();
    var stock = $("#updatedStock").val();
    var image = $("#updatedImage").val();

    var updatedProduct = {
        productId: productId,
        productName: productName,
        brand: {
            brandId: brand
        },
        category: {
            categoryId: categoryId
        },
        subcategory: {
            subcategoryId: subcategoryId
        },
        productDescription: productDescription,
        price: price,
        stock: stock,
        image: image
    };
    
    // Make AJAX request to update product
    $.ajax({
        url: "/product-management/" + productId,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(updatedProduct),
        success: function(response) {
            console.log("Product information updated successfully!"); // Debugging
            alert("Product information updated successfully!");
            $("#updateProductModal").modal("hide");
            refreshProductList();
        },
        error: function(xhr, status, error) {
            console.error("Error updating product information: " + error);
            alert("Error updating product information: " + error);
        }
    });
});

   function populateBrandCombo() {
    Common.ajax({
        url: "/brand-management/brands",
        type: "GET",
        success: function(brands) {
			
            if (brands && brands.length > 0) {
                brands.forEach(function(brand) {
                    let option = $('<option>', {
                        value: brand.brandId,
                        text: brand.brandName
                    });
                    $('select#brand').append(option);
                });
            }
        },
        error: function(xhr, status, error) {
            console.error("Error populating brand combo box: " + error);
        }
    });
}

    
    function populateCategoryCombo() {
        Common.ajax({
            url: "/category-management/categories",
            type: "GET",
            success: function(categories) {
                if (categories && categories.length > 0 && Array.isArray(categories)) {
                    categories.forEach(function(category) {
                        let option = $('<option>', {
                            value: category.categoryId,
                            text: category.categoryName
                        });
                        $('select#updatedCategoryId, select#categoryId').append(option);
                    });
                } else {
                    console.error("Invalid response format: ", categories);
                }
            },
            error: function(xhr, status, error) {
                console.error("Error populating category combo box: " + error);
            }
        });
    }
    
    // Function to populate the subcategory select box based on the selected category
function populateSubcategoryCombo(categoryId) {
	
    $('select#subcategoryId').empty(); // Clear the subcategory select box first
    if (categoryId) { // If categoryId exists, populate subcategories
        Common.ajax({
            url: "/subcategory-management/subcategories?categoryId=" + categoryId,
            type: "GET",
            success: function(subcategories) {
                if (subcategories && subcategories.length > 0) {
                    subcategories.forEach(function(subcategory) {
                        let option = $('<option>', {
                            value: subcategory.subcategoryId,
                            text: subcategory.subcategoryName
                        });
                        $('select#subcategoryId').append(option);
                    });
                }
            },
            error: function(xhr, status, error) {
                console.error("Error populating subcategory combo box: " + error);
            }
        });
    }
}   
    

    populateCategoryCombo();
//    $('select#updatedCategoryId, select#categoryId').change(function(event) {
//		let categoryId = $(event.currentTarget).val();
//	    populateSubcategoryCombo(categoryId);
//	});
	
	// Kategori seçimi değiştiğinde alt kategorileri yükle
	$('select#updatedCategoryId, select#categoryId').change(function() {
		
	    let selectedCategoryId = $(this).val();
	    populateSubcategoryCombo(selectedCategoryId);
	});
});

