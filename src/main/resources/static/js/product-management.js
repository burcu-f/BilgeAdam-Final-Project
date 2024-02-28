$(document).ready(function() {
    // Function to populate the product table
    function populateProductTable(products) {
        $("#productTable tbody").empty();
        let i = 1;
        products.forEach(function(product) {
            let row = $('<tr>');
            row.append($('<td>').text(i));
            row.append($('<td>').text(product.name));
            row.append($('<td>').text(product.subcategory));
            row.append($('<td>').text(product.category));
            row.append($('<td>').text(product.description));
            row.append($('<td>').text(product.price));
            row.append($('<td>').text(product.stock));
            let actionsTd = $('<td>');
            let showDetailsBtn = $('<button>', {
                class: 'btn btn-info',
                text: 'Details',
                click: function() {
                    showDetails(product.id);
                }
            });
            actionsTd.append(showDetailsBtn);
            row.append(actionsTd);
            ++i;
            $("#productTable tbody").append(row);
        });
    }

    // Function to refresh the product list
    function refreshProductList() {
        // Assuming Common.ajax() is defined elsewhere
        Common.ajax({
            url: "/product-management/products",
            type: "GET",
            success: function(products) {
                populateProductTable(products);
            },
        });
    }

    // Function to handle addition of a new product
    $("#btnAddProductModal").click(function() {
        let productName = $("#productName").val();
        let categoryId = $("#categoryId").val();
        let description = $("#description").val();
        let price = $("#price").val();
        let stock = $("#stock").val();

        let newProduct = {
            name: productName,
            category: categoryId,
            description: description,
            price: price,
            stock: stock
        };

        // Assuming Common.ajax() is defined elsewhere
        Common.ajax({
            url: "/product-management/create",
            type: "POST",
            data: JSON.stringify(newProduct),
            success: function(response) {
                alert("Product added successfully!");
                $("#productModal").modal("hide");
                refreshProductList(); // Refresh the product list after addition
            },
        });
    });

    // Function to show details
    function showDetails(productId) {
        alert('Details for product with ID ' + productId);
        // Implement details display functionality here
    }

    // Assuming populateCategoryCombo() function is defined elsewhere
    populateCategoryCombo();
});
