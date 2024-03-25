$(document).ready(function() {
	// Populate the category table when the page is loaded
	refreshCategoryList();

	// Click event handler for the "Add Category" button
	$("#btnAddCategory").click(function() {
		$("#categoryModal").modal("show");
	});

	// Function to handle addition of a new category
	$("#btnRegister").click(function() {
		var categoryName = $("#categoryName").val();
		var description = $("#description").val();

		var newCategory = {
			categoryName: categoryName,
			description: description
		};

		// AJAX request to add the new category
		Common.ajax({
			url: "/category-management/create",
			type: "POST",
			headers: {
				Accept: "application/json",
			},
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
});

function updateCategory(categoryId) {
	//Retrieve category details via AJAX request
	$.ajax({
		url:"/category-management/" + categoryId,
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
				
				// Click event handler for the "Update" button within the update modal
				$("#btnUpdateCategory").off("click").on("click", function() {
					var categoryId = $("#updateCategoryId").val();
					var categoryName = $("#updateCategoryName").val();
					var description = $("#updateDescription").val();

					var updatedCategory = {
						categoryId: categoryId,
						categoryName: categoryName,
						description: description
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
			},
			error: function(xhr, status, error) {
				console.error("Error retrieving category details: " + error);
			}
		});
	}


// Function to delete a category
function deleteCategory(categoryId) {
	// Show the delete confirmation modal
		$("#deleteCategoryModal").modal("show");
	// Set up the delete confirmation button click event
		$("#btnConfirmDelete").off("click").on("click", function() {
		// Send AJAX request to delete the category
		$.ajax({
			url: "/category-management/" + categoryId,
			type: "DELETE",
			headers: {
				Accept: "application/json"
			},
			success: function(response) {
				alert("Category deleted successfully!");
				$("#deleteCategoryModal").modal("hide");
				refreshCategoryList(); // Refresh the category list after deletion
			},
			error: function(xhr, status, error) {
				alert("Error deleting category: " + error);
			}
		});
	});
}

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
				//"<button class='btn btn-info' onclick='showDetails(" + category.categoryId + ")'>Details</button>" +
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
	$.ajax({
		url: "/category-management/categories",
		type: "GET",
		headers: {
			Accept: "application/json",
		},
		success: function(response) {
			populateCategoryTable(response);
		},
		error: function(xhr, status, error) {
			console.error("Error refreshing category list: " + error);
		}
	});
}

