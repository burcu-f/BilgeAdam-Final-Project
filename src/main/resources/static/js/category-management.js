$(document).ready(function() {
    // Function to populate the category table
    function populateCategoryTable(categories) {
        $("#categoryTable tbody").empty();
        let i = 1;
        categories.forEach(function(category) {
            let row = 
    		  "<tr row-id=\"" + category.categoryId + "\">" +
        		  "<td>" + i + "</td>" +
        		  "<td>" + category.categoryName + "</td>" +
        		  "<td>" + category.description + "</td>" +
                  "<td>" +
	                  "<button class='btn btn-info' onclick='showDetails(" + category.categoryId + ")'>Detaylar</button>" +
	                  "<button class='btn btn-warning' onclick='editCategory(" + category.categoryId + ")'>Güncelle</button>" +
	                  "<button class='btn btn-danger' onclick='deleteCategory(" + category.categoryId + ")'>Sil</button>" + 
                  "</td>" +
              "</tr>";
            ++i;
            $("#categoryTable tbody").append(row);
        });
    }

    // Function to refresh the category list
    function refreshCategoryList() {
        Common.ajax({
            url: "/category-management/categories",
            type: "GET",
            success: function(categories) {
                populateCategoryTable(categories);
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

    // Function to edit a category
    function editCategory(categoryId) {
        // Implement functionality to edit a category
    }

    // Function to delete a category
    function deleteCategory(categoryId) {
        // Implement functionality to delete a category
    }
});
