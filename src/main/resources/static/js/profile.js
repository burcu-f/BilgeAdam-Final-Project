//document.getElementById("ordersLink").addEventListener("click", function(event) {
//    event.preventDefault(); // Prevent the default link behavior
//    fetchOrders();
//});
function fetchOrders() {
    // Replace accountId with the actual ID of the logged-in user
    const accountId = 18; // Example ID, replace with actual value

    fetch(`/admin/order-management/orders/${accountId}`)
    .then(response => response.json())
    .then(data => displayOrders(data))
    .catch(error => console.error('Error fetching orders:', error));
}

function displayOrders(orders) {
    const ordersContainer = document.getElementById("mainContent");
    ordersContainer.innerHTML = ""; // Clear previous content

    orders.forEach(order => {
        const orderElement = document.createElement("div");
        orderElement.innerHTML = `
            <div class="order">
                <div>Order ID: ${order.orderId}</div>
                <div>Order Date: ${order.orderDate}</div>
                <div>Total Amount: ${order.totalAmount}</div>
                <div>Shipping Address: ${order.shippingAddress}</div>
                <div class="order-details">
                    <h4>Order Details</h4>
                    <ul>
                        ${order.orderDetails.map(detail => `
                            <li>
                                Product: ${detail.product.name} <br>
                                Quantity: ${detail.quantity} <br>
                                Price: ${detail.totalPrice} <br>
                            </li>
                        `).join("")}
                    </ul>
                </div>
            </div>
        `;
        ordersContainer.appendChild(orderElement);
    });
}
