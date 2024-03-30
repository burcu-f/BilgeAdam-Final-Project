document.addEventListener("DOMContentLoaded", function() {
    // Fetch order details from backend API
    fetchOrderDetails();
});

function fetchOrderDetails() {
    // Replace 'orderId' with the actual order ID
    const orderId = 123; // Example order ID
    fetch(`/order-details?orderId=${orderId}`)
        .then(response => response.json())
        .then(data => {
            // Populate order details in the table
            const orderDetailsBody = document.getElementById("orderDetailsBody");
            let totalPrice = 0;

            data.forEach(orderDetail => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${orderDetail.product.productName}</td>
                    <td>${orderDetail.product.price}</td>
                    <td>${orderDetail.quantity}</td>
                    <td>${orderDetail.totalPrice}</td>
                `;
                orderDetailsBody.appendChild(row);

                // Calculate total price
                totalPrice += orderDetail.totalPrice;
            });

            // Set total price
            document.getElementById("totalPrice").textContent = totalPrice.toFixed(2);
        })
        .catch(error => console.error("Error fetching order details:", error));
}
