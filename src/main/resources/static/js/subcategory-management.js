$(document).ready(function() {
    // Function to populate the subcategory table
    function populateSubcategoryTable(subcategories) {
        $("#subcategoryTable tbody").empty();
        let i = 1;
        subcategories.forEach(function(subcategory) {
            let row = $('<tr>', {
                "row-id": subcategory.subcategoryId
            });
            row.append(Common.createTd(i));
            row.append(Common.createTd(subcategory.subcategoryName));
            row.append(Common.createTd(subcategory.category.categoryName));

            // Actions column containing update and delete buttons
            let actionsTd = Common.createTd();
            
            // Details button
            let showDetailsBtn = $('<button>', {
                class: 'btn btn-info',
                onclick: 'showDetails(' + subcategory.subcategoryId + ')',
                text: 'Details'
            });
            actionsTd.append(showDetailsBtn);

            // Update button
            let updateBtn = $("<button>", {
                class: "btn btn-warning",
                text: "Update",
                click: function() {
                    updateSubcategory(subcategory.subcategoryId); // Call update function with subcategoryId
                }
            });
            actionsTd.append(updateBtn);

            // Delete button
            let deleteBtn = $("<button>", {
                class: "btn btn-danger",
                text: "Delete",
                click: function() {
                    deleteSubcategory(subcategory.subcategoryId); // Call delete function with subcategoryId
                }
            });
            actionsTd.append(deleteBtn);

            // Add actions column to the row
            row.append(actionsTd);
            ++i;
            $("#subcategoryTable tbody").append(row);
        });
    }

    // Function to refresh the subcategory list
    function refreshSubcategoryList() {
        Common.ajax({
            url: "/subcategory-management/subcategories",
            type: "GET",
            success: function(subcategories) {
                populateSubcategoryTable(subcategories);
            },
        });
    }

    // Populate the subcategory table when the page is loaded
    refreshSubcategoryList();

    // Function to handle addition of a new subcategory
    $("#btnAddSubcategoryModal").click(function() {
        let subcategoryName = $("#subcategoryName").val();
        let categoryId = $("#categoryId").val();

        let newSubcategory = {
            subcategoryName: subcategoryName,
            category: {
                categoryId: categoryId
            }
        };

        // AJAX request to add the new subcategory
        Common.ajax({
            url: "/subcategory-management/create",
            type: "POST",
            data: JSON.stringify(newSubcategory),
            success: function(response) {
                alert("Subcategory added successfully!");
                $("#subcategoryModal").modal("hide");
                refreshSubcategoryList(); // Refresh the subcategory list after addition
            },
        });
    });

    // Function to delete a subcategory
    function deleteSubcategory(subcategoryId) {
        // Confirm with the user before proceeding with the deletion
        if (confirm("Are you sure you want to delete this subcategory?")) {
            // Send AJAX request to delete the subcategory
            $.ajax({
                url: "/subcategory-management/delete/" + subcategoryId,
                type: "DELETE",
                headers: {
                    Accept: "application/json"
                },
                success: function(response) {
                    alert("Subcategory deleted successfully!");
                    refreshSubcategoryList(); // Refresh the subcategory list after deletion
                },
                error: function(xhr, status, error) {
                    alert("Error deleting subcategory: " + error);
                }
            });
        }
    }

    // Function to update a subcategory
    function updateSubcategory(subcategoryId) {
        // Retrieve subcategory details via AJAX request
        $.ajax({
            url: "/subcategory-management/" + subcategoryId,
            type: "GET",
            headers: {
                Accept: "application/json",
            },
            success: function(subcategory) {
                // Populate the modal with subcategory details
                $("#updatedSubcategoryName").val(subcategory.subcategoryName);
                $("#updatedCategoryId").val(subcategory.category.categoryId);

                // Show the update modal
                $("#updateSubcategoryModal").modal("show");
            },
            error: function(xhr, status, error) {
                console.error("Error retrieving subcategory details: " + error);
            }
        });
    }

    function populateCategoryCombo() {
        Common.ajax({
            url: "/category-management/categories",
            type: "GET",
            success: function(categories) {
                if (categories && categories.length > 0) {
                    categories.forEach(function(category) {
                        let option = $('<option>', {
                            value: category.categoryId,
                            text: category.categoryName
                        });
                        $('select#categoryId').append(option);
                    });
                }
            }
        });
    }

    populateCategoryCombo();
});
