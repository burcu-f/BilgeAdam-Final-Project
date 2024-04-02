// Function to populate the order table
function populateOrder(orderItems) {
    let tbody = $('#orderTable tbody');
    tbody.empty(); // Clear existing rows

    let totalAmount = 0;

    // Loop through each order item and append a row to the table
    orderItems.forEach(function (orderItem, index) {
        let row = $('<tr>').appendTo(tbody);
        row.append('<td>' + (index + 1) + '</td>');
        row.append('<td>' + orderItem.product.productName + '</td>');
        row.append('<td>' + orderItem.quantity + '</td>');
        row.append('<td>$' + orderItem.itemPrice.toFixed(2) + '</td>');
        let total = orderItem.quantity * orderItem.itemPrice;
        totalAmount += total;
        row.append('<td>$' + total.toFixed(2) + '</td>');
    });

    // Display the total amount
    $('#totalAmount').text('$' + totalAmount.toFixed(2));
}

// Function to fetch cart data from the server and populate the order table
function fetchCartData() {
    Common.ajax({
        url: '/cart/getCartByAccount',
        method: 'GET',
        success: function (response) {
            if (response && response.cartDetails) {
                populateOrder(response.cartDetails);
            }
        },
        error: function (xhr, status, error) {
            console.error('Failed to fetch cart data:', error);
            alert('Failed to fetch cart data. Please try again.');
        }
    });
}

// Function to fetch and populate the user's saved address
function fetchUserAddress() {
    Common.ajax({
        url: '/account/getAddress', // Endpoint to fetch user's address
        method: 'GET',
        success: function (response) {
            if (response && response.address) {
                // Populate address form with saved data
                $('#city').val(response.address.city);
                $('#district').val(response.address.district);
                $('#addressLine').val(response.address.addressLine);
            }
        },
        error: function (xhr, status, error) {
            console.error('Failed to fetch user address:', error);
            alert('Failed to fetch user address. Please try again.');
        }
    });
}

// Function to handle saving/updating user's address
function saveUserAddress() {
    let city = $('#city').val();
    let district = $('#district').val();
    let addressLine = $('#addressLine').val();

    let addressData = {
        city: city,
        district: district,
        addressLine: addressLine
    };

    Common.ajax({
        url: '/address/saveAddress',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(addressData),
        success: function (response) {
            alert('Address saved successfully!');
        },
        error: function (xhr, status, error) {
            console.error('Failed to save user address:', error);
            alert('Failed to save user address. Please try again.');
        }
    });
}

// Event listener for the Save button in the address form
$(document).ready(function () {
    fetchCartData(); // Fetch cart data when the page is loaded
    fetchUserAddress(); // Fetch user's saved address

    $('#saveAddressBtn').click(function () {
        saveUserAddress(); // Save/update user's address when Save button is clicked
    });
});
