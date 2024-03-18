document.addEventListener("DOMContentLoaded", function () {
    // Get the orders link element
    var ordersLink = document.getElementById("ordersLink");

    // Add click event listener to the orders link
    ordersLink.addEventListener("click", function (event) {
        event.preventDefault(); // Prevent default link behavior

        // Fetch orders data
        fetchOrdersData();
    });

    // Function to fetch orders data
    function fetchOrdersData() {
        // Get accountId of the logged-in user (You should replace this with your actual logic to retrieve accountId)
        var accountId = 18; // Replace 1 with actual accountId or logic to get accountId

        // You can replace this with your actual endpoint to fetch orders by accountId
        var ordersEndpoint = "/order-management/search-order?account=" + accountId;

        // Fetch orders data from the endpoint
        fetch(ordersEndpoint)
            .then(function (response) {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error("Failed to fetch orders");
                }
            })
            .then(function (order) {
                // Call function to display order
                displayOrder(order);
            })
            .catch(function (error) {
                console.error("Error fetching orders:", error);
            });
    }

    // Function to display order
    function displayOrder(order) {
        // Get the main content element
        var mainContent = document.getElementById("mainContent");

        // Clear previous content
        mainContent.innerHTML = "";

        // Create a div to hold the order details
        var orderDiv = document.createElement("div");
        orderDiv.classList.add("order");

        // Assuming orderId is the identifier
        var orderNumber = document.createElement("div");
        orderNumber.textContent = "Order #" + order.orderId;
        orderDiv.appendChild(orderNumber);

        var orderDate = document.createElement("div");
        orderDate.textContent = "Order Date: " + order.orderDate;
        orderDiv.appendChild(orderDate);

        var orderStatus = document.createElement("div");
        orderStatus.textContent = "Status: " + order.status;
        orderDiv.appendChild(orderStatus);

        // Append order details to main content
        mainContent.appendChild(orderDiv);
    }
});
