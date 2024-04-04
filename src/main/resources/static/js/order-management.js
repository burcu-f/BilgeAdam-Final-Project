$(document).ready(function() {
	Common.ajax({
        url: "/admin/order-management/orders",
        type: "GET",
        success: function(response) {
			debugger;
			populateOrderTable(response);
        },
        error: function(xhr, status, error) {
            console.error("Error retrieving product details: " + error);
        }
    });
    
    // Function to populate the product table
    function populateOrderTable(orders) {
		debugger;
        $("#orderTable tbody").empty();
        
        if (!orders || orders.length == 0) {
			return;
		}
        let i = 1;
        orders.forEach(function(order) {
            let row = $('<tr>', {
                "row-id": order.orderId
            });
            row.append(Common.createTd(i));
            row.append(Common.createTd(order?.account?.fullName));
            row.append(Common.createTd(order.orderDate));
            let totalAmount = 0;
            order.orderDetails.forEach(function (orderItem, index) {
		        let total = orderItem.quantity * orderItem.itemPrice;
		        totalAmount += total;
		    });
            row.append(totalAmount);
			let fullAddress = '';
			fullAddress += order?.address?.city;          
			fullAddress += order?.address?.district;          
			fullAddress += order?.address?.addressLine;          
            
            row.append(Common.createTd(fullAddress));
            ++i;
            $("#orderTable tbody").append(row);
        });
    }
})

//document.addEventListener("DOMContentLoaded", function() {
//    // Fetch and display orders when the page loads
//    fetchOrders();
//
//    // Add event listener for the "Add Order" button
//    document.getElementById("btnAddOrder").addEventListener("click", function() {
//        // Clear form fields
//        document.getElementById("account").value = "";
//        document.getElementById("totalAmount").value = "";
//        document.getElementById("address").value = "";
//
//        // Show the modal
//        $('#orderModal').modal('show');
//    });
//
//    // Add event listener for the "Save" button in the "Add Order" modal
//    document.getElementById("btnSaveOrder").addEventListener("click", function() {
//        // Retrieve data from form fields
//        var account = document.getElementById("account").value;
//        var totalAmount = document.getElementById("totalAmount").value;
//        var address = document.getElementById("address").value;
//
//        // Prepare data to send to server
//        var data = {
//            account: account,
//            totalAmount: totalAmount,
//            shippingAddress: address
//        };
//
//        // Send data to server
//        fetch("/admin/order-management/create", {
//            method: "POST",
//            headers: {
//                "Content-Type": "application/json"
//            },
//            body: JSON.stringify(data)
//        })
//        .then(response => response.json())
//        .then(order => {
//            // Close the modal
//            $('#orderModal').modal('hide');
//
//            // Reload orders
//            fetchOrders();
//        })
//        .catch(error => {
//            console.error("Error:", error);
//        });
//    });
//});
//
//// Function to fetch and display orders
//function fetchOrders() {
//    fetch("/admin/order-management/orders")
//    .then(response => response.json())
//    .then(data => {
//        if (Array.isArray(data)) {
//            var orderTableBody = document.getElementById("orderTable").getElementsByTagName("tbody")[0];
//            orderTableBody.innerHTML = "";
//
//            data.forEach(function(order) {
//                var row = orderTableBody.insertRow();
//                row.innerHTML = `
//                    <td>${order.orderId}</td>
//                    <td>${order.account.name}</td>
//                    <td>${order.totalAmount}</td>
//                    <td>${order.shippingAddress}</td>
//                    <td>
//                        <button class="btn btn-info btn-sm" onclick="showOrderDetails(${order.orderId})">Details</button>
//                        <button class="btn btn-danger btn-sm" onclick="confirmDeleteOrder(${order.orderId})">Delete</button>
//                    </td>
//                `;
//            });
//        } else {
//            console.error("Error: Unexpected response format - not an array");
//            console.log(data); // Log the response for debugging
//        }
//    })
//    .catch(error => {
//        console.error("Error:", error);
//    });
//}
//
//
//// Function to show order details modal
//function showOrderDetails(orderId) {
//    // Fetch order details from server
//    fetch(`/admin/order-management/${orderId}`)
//    .then(response => response.json())
//    .then(order => {
//        // Populate order details in the modal
//        document.getElementById("orderDetailsProducts").textContent = order.products; // Replace with actual field
//        document.getElementById("orderDetailsQuantity").textContent = order.quantity; // Replace with actual field
//        document.getElementById("orderDetailsItemPrice").textContent = order.itemPrice; // Replace with actual field
//        document.getElementById("orderDetailsTotalPrice").textContent = order.totalPrice; // Replace with actual field
//
//        // Show the modal
//        $('#orderDetailsModal').modal('show');
//    })
//    .catch(error => {
//        console.error("Error:", error);
//    });
//}
//
//// Function to show delete order confirmation modal
//function confirmDeleteOrder(orderId) {
//    // Show delete confirmation modal
//    $('#deleteOrderModal').modal('show');
//
//    // Add event listener for the confirmation button
//    document.getElementById("btnConfirmDelete").addEventListener("click", function() {
//        // Send delete request to server
//        fetch(`/admin/order-management/${orderId}`, {
//            method: "DELETE"
//        })
//        .then(response => {
//            // Close the modal
//            $('#deleteOrderModal').modal('hide');
//
//            // Reload orders
//            fetchOrders();
//        })
//        .catch(error => {
//            console.error("Error:", error);
//        });
//    });
//}
