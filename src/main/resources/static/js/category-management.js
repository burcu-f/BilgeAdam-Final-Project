$(document).ready(function() {
    // Function to populate the category table
function populateCategoryTable(categories) {
    $("#categoryTable tbody").empty();
    let i = 1;
    if (Array.isArray(categories)) {
        categories.forEach(function(category) {
            let row = 
                "<tr row-id=\"" + category.categoryId + "\">" +
                "<td>" + i + "</td>" +
                "<td>" + category.categoryName + "</td>" +
                "<td>" + category.description + "</td>" +
                "<td>" +
                    "<button class='btn btn-info' onclick='showDetails(" + category.categoryId + ")'>Details</button>" +
                    "<button class='btn btn-warning' onclick='updateCategory(" + category.categoryId + ")'>Update</button>" +
                    "<button class='btn btn-danger' onclick='deleteCategory(" + category.categoryId + ")'>Delete</button>" + 
                "</td>" +
                "</tr>";
            ++i;
            $("#categoryTable tbody").append(row);
        });
    } else {
        console.error("Categories is not an array:", categories);
    }
}

// Function to refresh the category list
function refreshCategoryList() {
    Common.ajax({
        url: "/category-management/categories",
        type: "GET",
        headers: {          
            Accept: "application/json",         
        },
        success: function(response) {
            populateCategoryTable(response);
        }
    });
}

// Populate the category table when the page is loaded
refreshCategoryList();

    // Click event handler for the "Add Category" button
    $("#btnAddCategory").click(function() {
        $("#categoryModal").modal("show");
    });

    // Function to handle addition of a new category
    $("#btnAddCategoryModal").click(function() {
        let categoryName = $("#categoryName").val();
        let description = $("#description").val();

        let newCategory = {
            categoryName: categoryName,
            description: description
        };

        // AJAX request to add the new category
        Common.ajax({
            url: "/category-management/create",
            type: "POST",
            data: JSON.stringify(newCategory),
            success: function(response) {
                alert("Category added successfully!");
                $("#categoryModal").modal("hide");
                refreshCategoryList(); // Refresh the category list after addition
            },
        });
    });

    // Click event handler for the "Update" button
    $("#categoryTable").on("click", ".btn-warning", function() {
        var categoryId = $(this).closest("tr").attr("row-id");
        // Retrieve category details via AJAX request
        $.ajax({
            url: "/category-management/" + categoryId,
            type: "GET",
            headers: {          
                Accept: "application/json",         
            },
            success: function(category) {
                // Populate the modal with category details
                $("#updateCategoryId").val(category.categoryId);
                $("#updateCategoryName").val(category.categoryName);
                $("#updateDescription").val(category.description);
                
                // Show the update modal
                $("#updateCategoryModal").modal("show");
            },
            error: function(xhr, status, error) {
                console.error("Error retrieving category details: " + error);
            }
        });
    });

    // Click event handler for the "Update" button within the update modal
    $("#btnUpdateCategory").click(function() {
        var categoryId = $("#updateCategoryId").val();
        var categoryName = $("#updateCategoryName").val();
        var description = $("#updateDescription").val();
        
        var updatedCategory = {
            categoryId: categoryId,
            categoryName: categoryName,
            description: description,
        };
        
        // AJAX request to update the category's information
        $.ajax({
            url: "/category-management/" + categoryId,
            type: "PUT",
            headers: {
                Accept: "application/json"
            },
            contentType: "application/json",
            data: JSON.stringify(updatedCategory),
            success: function(response) {
                alert("Category information updated successfully!");
                $("#updateCategoryModal").modal("hide");
                refreshCategoryList(); // Refresh the category list after updating
            },
            error: function(xhr, status, error) {
                alert("Error updating category information: " + error);
            }
        });
    });

    // Function to delete a category
    function deleteCategory(categoryId) {
    // Confirm with the user before proceeding with the deletion
    if (confirm("Are you sure you want to delete this category?")) {
        // Send AJAX request to delete the category
        $.ajax({
            url: "/category-management/" + categoryId,
            type: "DELETE",
            headers: {
                Accept: "application/json"
            },
            success: function(response) {
                alert("Category deleted successfully!");
                refreshCategoryList(); // Refresh the category list after deletion
            },
            error: function(xhr, status, error) {
                alert("Error deleting category: " + error);
            }
        });
    }
}
});
