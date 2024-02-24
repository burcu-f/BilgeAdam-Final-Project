$(document).ready(function() {
    // Function to populate the category table
    function populateCategoryTable(categories) {
        $("#categoryTableBody").empty();
        categories.forEach(function(category) {
            var row = "<tr><td>" + category.id + "</td><td>" + category.name + "</td>" +
                      "<td>" + category.description + "</td>" +
                      "<td><button class='btn btn-warning' onclick='editCategory(" + category.id + ")'>Edit</button>" +
                      "<button class='btn btn-danger' onclick='deleteCategory(" + category.id + ")'>Delete</button></td></tr>";
            $("#categoryTableBody").append(row);
        });
    }

    // Function to refresh the category list
    function refreshCategoryList() {
        $.ajax({
            url: "/category-management",
            type: "GET",
            success: function(categories) {
                populateCategoryTable(categories);
            },
            error: function(xhr, status, error) {
                console.error("Error refreshing category list: " + error);
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
        var name = $("#name").val();
        var description = $("#description").val();

        var newCategory = {
            name: name,
            description: description
        };

        // AJAX request to add the new category
        $.ajax({
            url: "/category-management",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(newCategory),
            success: function(response) {
                alert("Category added successfully!");
                $("#categoryModal").modal("hide");
                refreshCategoryList(); // Refresh the category list after addition
            },
            error: function(xhr, status, error) {
                alert("Error adding category: " + error);
            }
        });
    });

    // Function to edit a category
    function editCategory(categoryId) {
        // Implement functionality to edit a category
    }

    // Function to delete a category
    function deleteCategory(categoryId) {
        // Implement functionality to delete a category
    }
});
