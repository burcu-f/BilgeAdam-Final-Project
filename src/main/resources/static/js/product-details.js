$(document).ready(function() {
    // Function to fetch product details
    function fetchProductDetails(productId) {
        $.ajax({
            url: '/product-management/' + productId,
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
                    // Assuming brand is a string property, not an object with a brandName property
                    $('#brand').text(response.brand.brandName); 
                    $('#productDescription').text(response.productDescription);
                    $('#price').text('Price: ' + response.price + ' TL');
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
});
