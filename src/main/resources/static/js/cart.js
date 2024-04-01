Cart = window.Cart || {};
window.Cart = Cart;

Cart.addToCart = (productId, quantity = 1) => {
    Common.ajax({
        url: '/cart/add',
        type: 'POST',
        data: JSON.stringify({ productId: productId, quantity: quantity }),
        contentType: 'application/json',
        success: function(response) {
			
			let totalCartItems = 0;
			if (!response?.cartDetails) {
				return;
			}
			response?.cartDetails.forEach(function(cartDetail) {
                totalCartItems += cartDetail.quantity;
            });
            $('#cartCount').html(totalCartItems);
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

// Function to populate the cart table
function populateCart(cartItems) {
    let tbody = $('#cartTable tbody');
    tbody.empty(); // Clear existing rows

    // Loop through each cart item and append a row to the table
    cartItems.forEach(function(cartItem, index) {
        let row = $('<tr>').appendTo(tbody);
        row.append('<td>' + (index + 1) + '</td>');
        row.append('<td>' + cartItem.product.productName + '</td>');
        row.append('<td>' + cartItem.quantity + '</td>');
        row.append('<td>$' + cartItem.product.price.toFixed(2) + '</td>');
        row.append('<td>$' + (cartItem.quantity * cartItem.product.price).toFixed(2) + '</td>');

        // Add a data attribute to store the cart detail ID
        row.attr('data-cart-detail-id', cartItem.cartDetailId);

        // Add a Remove button for each item
        let actionsCell = $('<td>').appendTo(row);
        actionsCell.append('<button class="btn btn-danger remove-item-btn">Remove</button>');
    });
}

// Function to fetch cart data from the server
function fetchCartData() {
    $.ajax({
        url: '/cart/getCartByAccount', // Fetch cart data associated with the currently authenticated user
        method: 'GET',
        success: function(response) {
            if (response && response.cartDetails) {
                populateCart(response.cartDetails);
            }
        },
        error: function(xhr, status, error) {
            console.error('Failed to fetch cart data:', error);
            alert('Failed to fetch cart data. Please try again.');
        }
    });
}

// Function to remove an item from the cart
function removeItemFromCart(cartDetailId) {
    $.ajax({
        url: '/cart/remove/' + cartDetailId,
        method: 'DELETE',
        success: function(response) {
            fetchCartData(); // Fetch updated cart data after removal
        },
        error: function(xhr, status, error) {
            console.error('Failed to remove item from cart:', error);
            alert('Failed to remove item from cart. Please try again.');
        }
    });
}

// Event listener for the Remove button
$(document).ready(function() {
    $('#cartTable').on('click', '.remove-item-btn', function() {
        let cartDetailId = $(this).closest('tr').data('cart-detail-id');
        removeItemFromCart(cartDetailId);
    });

    fetchCartData(); // Fetch cart data when the page is loaded
});
