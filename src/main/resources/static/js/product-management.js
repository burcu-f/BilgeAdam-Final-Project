$(document).ready(function() {
    // Function to populate the product table
    function populateProductTable(products) {
        $("#productTable tbody").empty();
        let i = 1;
        products.forEach(function(product) {
            let row = $('<tr>');
            row.append(Common.createTd(i));
            row.append(Common.createTd(product.name));
            row.append(Common.createTd(product.subcategory));
            row.append(Common.createTd(product.category));
            row.append(Common.createTd(product.description));
            row.append(Common.createTd(product.price));
            row.append(Common.createTd(product.stock));
            let actionsTd = Common.createTd();
            let showDetailsBtn = $('<button>', {
                class: 'btn btn-info',
                text: 'Details',
                click: function() {
                    showDetails(product.id);
                }
            });
            actionsTd.append(showDetailsBtn);

            // Update button
            let updateBtn = $('<button>', {
                class: 'btn btn-warning',
                text: 'Update',
                click: function() {
                    updateProduct(product.id);
                }
            });
            actionsTd.append(updateBtn);

            // Delete button
            let deleteBtn = $('<button>', {
                class: 'btn btn-danger',
                text: 'Delete',
                click: function() {
                    deleteProduct(product.id);
                }
            });
            actionsTd.append(deleteBtn);

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
        });
    }

    // Function to handle addition of a new product
    $("#btnAddProductModal").click(function() {
        let productName = $("#productName").val();
        let categoryId = $("#categoryId").val();
        let description = $("#description").val();
        let price = $("#price").val();
        let stock = $("#stock").val();

        let newProduct = {
            name: productName,
            category: categoryId,
            description: description,
            price: price,
            stock: stock
        };

        Common.ajax({
            url: "/product-management/create",
            type: "POST",
            data: JSON.stringify(newProduct),
            success: function(response) {
                alert("Product added successfully!");
                $("#productModal").modal("hide");
                refreshProductList(); // Refresh the product list after addition
            },
        });
    });

    // Function to update a product
    function updateProduct(productId) {
        // Retrieve product details via AJAX request
        Common.ajax({
            url: "/product-management/" + productId,
            type: "GET",
            success: function(product) {
                // Populate the modal with product details
                $("#updatedProductName").val(product.name);
                $("#updatedCategoryId").val(product.category);
                $("#updatedDescription").val(product.description);
                $("#updatedPrice").val(product.price);
                $("#updatedStock").val(product.stock);
                
                // Show the update modal
                $("#updateProductModal").modal("show");
            },
            error: function(xhr, status, error) {
                console.error("Error retrieving product details: " + error);
            }
        });
    }

    // Function to delete a product
    function deleteProduct(productId) {
        // Confirm with the user before proceeding with the deletion
        if (confirm("Are you sure you want to delete this product?")) {
            // Send AJAX request to delete the product
            Common.ajax({
                url: "/product-management/delete/" + productId,
                type: "DELETE",
                success: function(response) {
                    alert("Product deleted successfully!");
                    refreshProductList(); // Refresh the product list after deletion
                },
                error: function(xhr, status, error) {
                    alert("Error deleting product: " + error);
                }
            });
        }
    }

    // Function to show details
    function showDetails(productId) {
        alert('Details for product with ID ' + productId);
        // Implement details display functionality here
    }
    
    function populateCategoryCombo() {
        Common.ajax({
            url: "/category-management",
            type: "GET",
            success: function(categories) {
                if (categories && categories.length > 0) {
                    categories.forEach(function(category) {
                        let option = $('<option>', {
                            value: category.categoryId,
                            text: category.categoryName
                        });
                        $('select#categoryId').append(option);
                    });
                }
            }
        });
    }
    
    function populateSubcategoryCombo() {
    Common.ajax({
        url: "/subcategory-management/subcategories",
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
        }
    });
}

    populateCategoryCombo();
    populateSubcategoryCombo();
});
