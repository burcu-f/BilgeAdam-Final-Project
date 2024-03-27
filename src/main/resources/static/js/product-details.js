$(document).ready(function() {
    // Function to fetch product details
    function fetchProductDetails(productId) {
        $.ajax({
            url: '/product/' + productId,
            type: 'GET',
            contentType: "application/json",
            headers: {
                Accept: "application/json",
            },
            success: function(response) {
                console.log(response);
                // Check if response is not empty
                if (response) {
                    $('#image').attr('src', response.image);
                    $('#productName').text(response.productName);
                    $('#brand').text(response.brand.brandName); 
                    $('#productDescription').text(response.productDescription);
                    $('#price').text('Price: ' + response.price + ' TL');

                    // Add event listener for "Add to cart" button
                    $('.custom-btn').click(function() {
                        addToCart(productId);
                    });
                } else {
                    console.log("No product found.");
                }
            },
            error: function(xhr, status, error) {
                console.error("Error fetching product details: " + error);
            }
        });
    }

    // Get the productId from the URL
    var urlParams = new URLSearchParams(window.location.search);
    var productId = urlParams.get('productId');

    // Call fetchProductDetails function with productId
    fetchProductDetails(productId);

    // Function to add product to cart with optional quantity
    function addToCart(productId, quantity = 1) {
        // Retrieve CSRF token from the HTML page
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");

        // Include CSRF token in AJAX request headers
        var headers = {};
        headers[csrfHeader] = csrfToken;

        $.ajax({
            url: '/carts/add',
            type: 'POST',
            contentType: 'application/json',
            headers: headers, // Include CSRF token in headers
            data: JSON.stringify({ productId: productId, quantity: quantity }),
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
});
