$(document).ready(function() {
    // Function to fetch categories via Ajax
    function fetchCategories() {
        $.ajax({
            url: '/category/list', // Endpoint to fetch categories
            type: 'GET',
            dataType: 'json',
            success: function(response) {
                // Clear existing categories
                $('#categoriesList').empty();

                // Append fetched categories
                response.forEach(function(category) {
                    let cat = $("<a>", {
                        class: "dropdown-item",
                        text: category.categoryName,
                        href: "#",
                        "data-category-id": category.categoryId // Add data attribute for category ID
                    });
                    $('#categoriesList').append(cat);
                    if (category.subcategories) {
                        category.subcategories.forEach(function(subcategory) {
                            let subCat = $("<a>", {
                                class: "dropdown-item",
                                style: "padding-left: 50px;",
                                text: subcategory.subcategoryName,
                                href: "#",
                                "data-subcategory-id": subcategory.subcategoryId // Add data attribute for subcategory ID
                            });
                            $('#categoriesList').append(subCat);
                        });
                    }
                    let divider = $("<div>", {
                        class: "dropdown-divider"
                    });
                    $('#categoriesList').append(divider);
                });
            },
            error: function(xhr, status, error) {
                console.error('Error fetching categories:', error);
            }
        });
    }

    // Function to fetch all products
    function fetchAllProducts() {
        $.ajax({
            url: '/product/list',
            type: 'GET',
            contentType: "application/json",
            headers: {
                Accept: "application/json",
            },
            success: function(response) {
                displayProducts(response);
            },
            error: function(xhr, status, error) {
                console.error("Error fetching products: " + error);
            }
        });
    }

    // Fetch categories when the page loads
    fetchCategories();

    // Fetch all products and display them when the page loads
    fetchAllProducts();

   // Click event listener for categories and subcategories
$(document).on('click', '#categoriesList a', function(e) {
    e.preventDefault();
    let categoryId = $(this).data('category-id');
    let subcategoryId = $(this).data('subcategory-id');

    // Check if categoryId is a valid number
    if (!isNaN(categoryId)) {
        if (subcategoryId) {
            // Check if subcategoryId is a valid number
            if (!isNaN(subcategoryId)) {
                fetchProductsBySubcategory(subcategoryId);
            } else {
                console.error('Invalid subcategory ID:', subcategoryId);
            }
        } else {
            fetchProductsByCategory(categoryId);
        }
    } else {
        console.error('Invalid category ID:', categoryId);
    }
});


    // Function to fetch products by category ID
    function fetchProductsByCategory(categoryId) {
        $.ajax({
            url: '/product/list?category=' + categoryId, // Endpoint to fetch products by category
            type: 'GET',
            dataType: 'json',
            success: function(response) {
                displayProducts(response);
            },
            error: function(xhr, status, error) {
                console.error('Error fetching products by category:', error);
            }
        });
    }

    // Function to fetch products by subcategory ID
    function fetchProductsBySubcategory(subcategoryId) {
        $.ajax({
            url: '/product/list?subcategory=' + subcategoryId, // Endpoint to fetch products by subcategory
            type: 'GET',
            dataType: 'json',
            success: function(response) {
                displayProducts(response);
            },
            error: function(xhr, status, error) {
                console.error('Error fetching products by subcategory:', error);
            }
        });
    }

    // Function to display products
    function displayProducts(products) {
        if (products && products.length > 0) {
            $('#productsRow').empty(); // Clear existing products
            products.forEach(function(product) {
                let card = '<div class="col-md-4 mb-4">' +
                    '<div class="card">' +
                    '<img src="' + product.image + '" class="card-img-top" alt="' + product.productName + '">' +
                    '<div class="card-body">' +
                    '<h5 class="card-title">' + product.productName + '</h5>' +
                    '<p class="card-text">Price: ' + product.price + '</p>' +
                    '<a href="/product-details.html?productId=' + product.productId + '" class="btn btn-primary">View Product</a>' +
                    '<button class="btn btn-success addToCartBtn" data-productId="' + product.productId + '">Add to Cart</button>' +
                    '</div>' +
                    '</div>' +
                    '</div>';
                $('#productsRow').append(card);
            });
            // Attach event handler for Add to Cart buttons
            $('.addToCartBtn').click(function() {
                let productId = $(this).data('productid');
                Cart.addToCart(productId);
            });
        } else {
            console.log("No products found.");
        }
    }
});
