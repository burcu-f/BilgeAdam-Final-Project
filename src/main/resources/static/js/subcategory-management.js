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

			/* Details button
			let showDetailsBtn = $('<button>', {
				class: 'btn btn-info',
				onclick: 'showDetails(' + subcategory.subcategoryId + ')',
				text: 'Details'
			});
			actionsTd.append(showDetailsBtn);*/

			// Update button
			let updateBtn = $("<button>", {
				class: "btn btn-warning",
				text: "Edit",
				click: function() {
					var subcategoryId = $(this).closest("tr").attr("row-id");
					updateSubcategory(subcategoryId); // Call update function with subcategoryId
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
			url: "/subcategory/list",
			type: "GET",
			success: function(subcategories) {
				populateSubcategoryTable(subcategories);
			},
			error: function(xhr, status, error) {
				console.error("Error refreshing subcategory list: " + error);
			}
		});
	}

	// Populate the subcategory table when the page is loaded
	refreshSubcategoryList();

	$("#btnAddSubcategory").click(function() {
		$("#subcategoryModal").modal('show');
	});
	// Function to handle addition of a new subcategory
	$("#btnRegister").click(function() {
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
			url: "/admin/subcategory-management/create",
			type: "POST",
			data: JSON.stringify(newSubcategory),
			success: function(response) {
				alert("Subcategory added successfully!");
				$("#subcategoryModal").modal("hide");
				refreshSubcategoryList(); // Refresh the subcategory list after addition
			},
			error: function(xhr, status, error) {
				alert("Error adding subcategory: " + error);
			}
		});
	});

	// Function to delete a subcategory
	function deleteSubcategory(subcategoryId) {
		// Show the delete confirmation modal
		$("#deleteSubcategoryModal").modal("show");
		// Set up the delete confirmation button click event
		$("#btnConfirmDelete").off("click").on("click", function() {
		// Send AJAX request to delete the subcategory
			$.ajax({
				url: "/admin/subcategory-management/" + subcategoryId,
				type: "DELETE",
				headers: {
					Accept: "application/json"
				},
				success: function(response) {
					alert("Subcategory deleted successfully!");
					$("#deleteSubcategoryModal").modal("hide");
					refreshSubcategoryList(); // Refresh the subcategory list after deletion
				},
				error: function(xhr, status, error) {
					alert("Error deleting subcategory: " + error);
				}
			});
		});
	}

function updateSubcategory(subcategoryId) {
        // Retrieve subcategory details via AJAX request
        $.ajax({
            url: "/subcategory/" + subcategoryId,
            type: "GET",
            headers: {
                Accept: "application/json",
            },
            success: function(subcategory) {
                // Populate the modal with subcategory details
                $("#updatedSubcategoryId").val(subcategory.subcategoryId);
                $("#updatedSubcategoryName").val(subcategory.subcategoryName);
                $("#updatedCategoryId").val(subcategory.category.categoryId);

                // Show the update modal
                $("#updateSubcategoryModal").modal("show");
            },
            error: function(xhr, status, error) {
                console.error("Error retrieving subcategory details: " + error);
            }
        });

        // Add event handler for update button click
        $("#btnUpdateSubcategory").off("click").on("click", function() {
            var subcategoryId = $("#updatedSubcategoryId").val();
            var subcategoryName = $("#updatedSubcategoryName").val();
            var categoryId = $("#updatedCategoryId").val();

            var updatedSubcategory = {
                subcategoryId: subcategoryId,
                subcategoryName: subcategoryName,
                category: {
                    categoryId: categoryId
                }
            };

            // Make AJAX request to update subcategory
            $.ajax({
                url: "/admin/subcategory-management/" + subcategoryId,
                type: "PUT",
                headers: {
                    Accept: "application/json",
                },
                contentType: "application/json",
                data: JSON.stringify(updatedSubcategory),
                success: function(response) {
                    alert("Subcategory information updated successfully!");
                    $("#updateSubcategoryModal").modal("hide");
                    refreshSubcategoryList();
                },
                error: function(xhr, status, error) {
                    alert("Error updating subcategory information: " + error);
                }
            });
        });
    }
    
    
	function populateCategoryCombo() {
		Common.ajax({
			url: "/category/list",
			type: "GET",
			success: function(categories) {
				if (categories && categories.length > 0 && Array.isArray(categories)) {
					categories.forEach(function(category) {
						let option = $('<option>', {
							value: category.categoryId,
							text: category.categoryName
						});
						$('select#updatedCategoryId, select#categoryId').append(option);
					});
				} else {
					console.error("Invalid response format: ", categories);
				}
			},
			error: function(xhr, status, error) {
				console.error("Error populating category combo box: " + error);
			}
		});
	}
	
	// Populate the category combo box
	populateCategoryCombo();
});
