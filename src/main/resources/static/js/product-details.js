$(document).ready(function() {
    // Function to fetch product details
    function fetchProductDetails() {
        $.ajax({
            url: '/product/' + $('#productId').val(),
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
                        // Call addToCart function directly
                        Cart.addToCart($('#productId').val());
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
//    var urlParams = new URLSearchParams(window.location.search);
//    var productId = urlParams.get('productId');

    // Call fetchProductDetails function with productId
    fetchProductDetails($('#productId').val());
});

