CartPage = window.CartPage || {};
window.CartPage = CartPage;

// Function to remove an item from the cart
CartPage.removeItemFromCart = (cartDetailId) => {
    Common.ajax({
        url: '/cart/remove/' + cartDetailId,
        type: 'DELETE',
        success: function(response) {
            // Fetch updated cart data after removal
            Cart.fetchCartData((response) => {
                // Update the UI with the latest cart data
                Cart.populateCart(response.cartDetails, true);
            });
            alert('Item successfully removed from cart.');
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
        CartPage.removeItemFromCart(cartDetailId);
    });
    
    Cart.fetchCartData((response) => {
		Cart.populateCart(response.cartDetails, true);
	});
});
