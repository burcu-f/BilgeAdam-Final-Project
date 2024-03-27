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
//<div class="dropdown-menu">
//  <a class="dropdown-item" href="#">Action</a>
//  <a class="dropdown-item" href="#">Another action</a>
//  <a class="dropdown-item" href="#">Something else here</a>
//  <div class="dropdown-divider"></div>
//  <a class="dropdown-item" href="#">Separated link</a>
//</div>
//				<div class="dropdown-divider"></div>
//  <a class="dropdown-item" href="#">Separated link</a>

                // Append fetched categories
                response.forEach(function(category) {
					
					let cat = $("<a>", {
		                class: "dropdown-item",
		                text: category.categoryName,
		                href: "/",
		            });
                    $('#categoriesList').append(cat);
		            if (category.subcategories) {
			            category.subcategories.forEach(function(subcategory) {
							let subCat = $("<a>", {
				                class: "dropdown-item",
				                style: "padding-left: 50px;",
				                text: subcategory.subcategoryName,
				                href: "/",
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
//                console.error('Error fetching categories:', error);
            }
        });
    }
    
    // Function to fetch subcategories via Ajax
    function fetchSubcategories() {
        $.ajax({
            url: '/subcategory/list', // Endpoint to fetch subcategories
            type: 'GET',
            dataType: 'json',
            success: function(response) {
                // Clear existing subcategories
                $('#subcategoriesList').empty();

                // Append fetched subcategories
                response.forEach(function(subcategory) {
                    $('#subcategoriesList').append('<li class="list-group-item">' + subcategory.subcategoryName + '</li>');
                });
            },
            error: function(xhr, status, error) {
                console.error('Error fetching subcategories:', error);
            }
        });
    }
    
    // Function to fetch products
    function fetchProducts() {
        $.ajax({
            url: '/product/list',
            type: 'GET',
            contentType: "application/json",
            headers: {
				Accept: "application/json",
			},
            success: function(response) {
				// Check if response is not empty
            if (response && response.length > 0) {
                // Clear existing products
                $('#productsRow').empty();
                
                // Iterate over each product in the response
                response.forEach(function(product) {
                    // Create a card for each product
                    var card = '<div class="col-md-4 mb-4">' +
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
                 
                 // Append the card to the products row
                    $('#productsRow').append(card);
                });
                // Attach event handler for Add to Cart buttons
                $('.addToCartBtn').click(function() {
                    var productId = $(this).data('productId');
                    addToCart(productId);
                });
            } else {
                console.log("No products found.");
            }
        },
        error: function(xhr, status, error) {
            console.error("Error fetching products: " + error);
        }
    });
}

//Add to cart function

function addToCart(productId, quantity = 1) {
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");

    var headers = {};
    headers[csrfHeader] = csrfToken;

    $.ajax({
        url: '/carts/add',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({ productId: productId, quantity: quantity }),
        headers: headers,
        success: function(response) {
            alert('Product added to cart successfully!');
            updateCartCount();
        },
        error: function(xhr, status, error) {
            if (xhr.status === 401) { // Unauthorized status
                window.location.href = '/login'; // Redirect to login page
            } else {
                console.error('Error adding product to cart:', error);
                alert('Error adding product to cart. Please try again later.');
            }
        }
    });
}


// Function to update the cart count on the navbar
function updateCartCount() {
    // Perform an AJAX request to fetch the current cart count
    $.ajax({
        url: '/carts/count',
        type: 'GET',
        success: function(response) {
            // Update the cart count badge
            $('.cart-count').text(response.count);
            // Show the badge if the count is greater than zero
            if (response.count > 0) {
                $('.cart-count').show();
            } else {
                $('.cart-count').hide();
            }
        },
        error: function(xhr, status, error) {
            console.error('Error fetching cart count:', error);
        }
    });
}


    // Fetch categories when the page loads
    fetchCategories();
    fetchSubcategories();
    fetchProducts();

    // Click event listener for categories
    $(document).on('click', '#categoriesList li', function(e) {
        e.preventDefault();
        var categoryId = $(this).data('category-id');
        fetchSubcategoriesByCategory(categoryId);
        fetchProductsBySubcategory(subcategoryId);
    });

    // Function to fetch subcategories by category ID
    function fetchSubcategoriesByCategory(categoryId) {
        $.ajax({
            url: '/subcategory/list?category=' + categoryId, // Endpoint to fetch subcategories by category
            type: 'GET',
            dataType: 'json',
            success: function(response) {
                // Clear existing subcategories
                $('#subcategoriesList').empty();

                // Append fetched subcategories
                response.forEach(function(subcategory) {
                    $('#subcategoriesList').append('<li class="list-group-item" data-subcategory-id="' + subcategory.subcategoryId + '">' + subcategory.subcategoryName + '</li>');
                });
            },
            error: function(xhr, status, error) {
                console.error('Error fetching subcategories:', error);
            }
        });
    }

    // Function to fetch products by category ID
    function fetchProductsBySubcategory(subcategoryId) {
        $.ajax({
            url: '/product/list?subcategory=' + subcategoryId, // Endpoint to fetch products by category
            type: 'GET',
            dataType: 'json',
            success: function(response) {
                // Clear existing products
                $('#productsRow').empty();

                // Append fetched products
                response.forEach(function(product) {
                    var card = '<div class="col-md-4 mb-4">' +
                        '<div class="card">' +
                        '<img src="' + product.image + '" class="card-img-top" alt="' + product.productName + '">' +
                        '<div class="card-body">' +
                        '<h5 class="card-title">' + product.productName + '</h5>' +
                        '<p class="card-text">Price: ' + product.price + '</p>' +
                        '<a href="/product-details/' + product.productId + '" class="btn btn-primary">View Product</a>' +
                        '</div>' +
                        '</div>' +
                        '</div>';
                    $('#productsRow').append(card);
                });
            },
            error: function(xhr, status, error) {
                console.error('Error fetching products:', error);
            }
        });
    }
});

