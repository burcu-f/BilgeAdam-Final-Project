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


// Function to handle saving/updating user's address
function saveUserAddress() {
    let city = $('#city').val();
    let district = $('#district').val();
    let addressLine = $('#addressLine').val();

    let addressData = {
        id: $('#addressId').val(),
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
			debugger;
			alertify.success('Adres başarıyla kaydedildi.');
			populateAddress(response);
        },
        error: function (xhr, status, error) {
            console.error('Failed to save user address:', error);
            alert('Failed to save user address. Please try again.');
        }
    });
}

function populateAddress(address) {
	debugger;
	$("#addressWarning").hide();
	$('#addressId').val(address.id);	
	$('#city').val(address.city);	
	$('#district').val(address.district);	
	$('#addressLine').val(address.addressLine);	
}

// Event listener for the Save button in the address form
$(document).ready(function () {
     Cart.fetchCartData((response) => {
		Cart.populateCart(response.cartDetails, false);
	});

    $('#saveAddressBtn').click(function () {
        saveUserAddress(); // Save/update user's address when Save button is clicked
    });

	Common.ajax({
        url: '/address/getByAccountId', // Endpoint to fetch user's address
        type: 'GET',
        contentType: 'application/json',
        success: function (response) {
			debugger;
			if (!response) {
				$('#addressWarning').show();
				return; 
			}
			populateAddress(response);
        },
        error: function (xhr, status, error) {
            console.error('Failed to fetch user address:', error);
            alert('Failed to fetch user address. Please try again.');
        }
    });
    
    $('#completeOrderBtn').click(function() {
		if (!$('#addressId').val()) {
			alertify.error("There is no registered address, please add one!");
			return;
		}
		Common.ajax({
	        url: '/order/createOrder', // Endpoint to fetch user's address
	        type: 'GET',
	        contentType: 'application/json',
	        success: function (response) {
				window.location = '/order/order-completed/' + response.id;
	        },
	        error: function (xhr, status, error) {
	            console.error('Failed to fetch user address:', error);
	            alert('Failed to fetch user address. Please try again.');
	        }
	    });
	});    
    
});
