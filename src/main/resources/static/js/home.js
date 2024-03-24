$(document).ready(function() {
    // Function to fetch categories via Ajax
    function fetchCategories() {
        $.ajax({
            url: '/category-management/categories', // Endpoint to fetch categories
            type: 'GET',
            dataType: 'json',
            success: function(response) {
                // Clear existing categories
                //$('#categoriesList').empty();

                // Append fetched categories
                response.forEach(function(category) {
                    $('#categoriesList').append('<li class="list-group-item">' + category.categoryName + '</li>');
            	});
            },
            error: function(xhr, status, error) {
                console.error('Error fetching categories:', error);
            }
        });
    }
    
    // Function to fetch products
    function fetchProducts() {
        $.ajax({
            url: '/product-management/products',
            type: 'GET',
            contentType: "application/json",
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
                        '<a href="/product-details/' + product.productId + '" class="btn btn-primary">View Product</a>' +
                        '</div>' +
                        '</div>' +
                        '</div>';
                 
                 // Append the card to the products row
                    $('#productsRow').append(card);
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

    /*Function to fetch products based on category via Ajax
    function fetchProducts(categoryId) {
        $.ajax({
            url: '/product-management/products?category=' + categoryId, // Endpoint to fetch products based on category
            type: 'GET',
            success: function(response) {
                // Clear existing products
                $('#productsContainer').empty();

                // Append fetched products
                response.forEach(function(product) {
                    $('#productsContainer').append('<div class="card" style="margin-top: 20px"><div class="row no-gutters"><div class="col-sm-5 d-flex justify-content-center"><img class="" height="150px" width="150px" src="' + product.image + '" alt="' + product.productName + '"></div><div class="col-sm-7 d-flex justify-content-center"><div class="card-body"><h5 class="card-title">' + product.productName + '</h5><h4>TL ' + product.price + '</h4><p>' + product.productDescription + '</p><a href="/shop/viewproduct/' + product.productId + '" class="btn btn-primary">View Product</a></div></div></div></div>');
                });
            },
            error: function(xhr, status, error) {
                console.error('Error fetching products:', error);
            }
        });
    }*/

    // Fetch categories when the page loads
    fetchCategories();
    // Fetch products when the page is loaded
    fetchProducts();

    // Click event listener for categories
    $(document).on('click', '#categoriesList a', function(e) {
        e.preventDefault();
        var categoryId = $(this).data('category');
        fetchProducts(categoryId);
    });
});
