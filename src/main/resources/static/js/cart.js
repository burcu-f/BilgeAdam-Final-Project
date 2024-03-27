$(document).ready(function() {
    // Dummy data for cart items
    var cartItemsData = [
        { name: "BUZDOLABI", quantity: 1, price: "₺22000", total: "₺22000" }
        // Add more items if needed
    ];

    // Function to fill cart items dynamically
    function fillCartItems() {
        var cartItemsHtml = "";
        var orderTotal = 0;
        cartItemsData.forEach(function(item, index) {
            cartItemsHtml += `
                <li class="cart_item clearfix">
                    <div class="cart_item_info d-flex flex-md-row flex-column justify-content-between">
                        <div class="cart_item_name cart_info_col">
                            <div class="cart_item_title">Name</div>
                            <div class="cart_item_text">${item.name}</div>
                        </div>
                        <div class="cart_item_quantity cart_info_col">
                            <div class="cart_item_title">Quantity</div>
                            <div class="cart_item_text">${item.quantity}</div>
                        </div>
                        <div class="cart_item_price cart_info_col">
                            <div class="cart_item_title">Price</div>
                            <div class="cart_item_text">${item.price}</div>
                        </div>
                        <div class="cart_item_total cart_info_col">
                            <div class="cart_item_title">Total</div>
                            <div class="cart_item_text">${item.total}</div>
                        </div>
                        <div class="cart_item_remove cart_info_col">
                            <button class="btn btn-danger btn-sm remove-btn">Remove</button>
                        </div>
                    </div>
                </li>
            `;
            orderTotal += parseInt(item.total.replace("₺", "").replace(",", ""));
        });

        // Update cart items count and order total
        $("#cartItemCount").text(cartItemsData.length);
        $(".order_total_amount").text("₺" + orderTotal.toLocaleString('tr-TR'));

        // Append HTML to cart items list
        $("#cartItems").html(cartItemsHtml);
    }

    // Call function to fill cart items
    fillCartItems();

    // Event listener for remove button click
    $(document).on("click", ".remove-btn", function() {
        var index = $(this).closest(".cart_item").index();
        cartItemsData.splice(index, 1); // Remove item from cart items data
        fillCartItems(); // Re-fill cart items
    });

    // Event listener for "Continue Shopping" button click
    $(".cart_button_clear").click(function() {
        // Retrieve the previous page URL from session storage
        var previousPageUrl = sessionStorage.getItem('previousPageUrl');
        if (previousPageUrl) {
            window.location.href = previousPageUrl; // Redirect to the previous page
        } else {
            window.location.href = 'index.html'; // Redirect to a default page if previous page URL is not available
        }
    });

    // Store the current page URL in session storage
    var currentPageUrl = window.location.href;
    sessionStorage.setItem('previousPageUrl', currentPageUrl);
});
