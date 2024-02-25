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
			let actionsTd = Common.createTd();
			let showDetailsBtn = $('<button>', {
				class: 'btn btn-info',
				onclick: 'showDetails(' + subcategory.subcategoryId + ')',
				text: 'Detaylar'
			});
			actionsTd.append(showDetailsBtn);
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

populateCategoryCombo();

// Click event handler for the "Add Subcategory" button
$("#btnAddSubcategory").click(function() {
    $("#subcategoryModal").modal("show");
});

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

// Function to edit a subcategory
function editSubcategory(subcategoryId) {
    // Implement functionality to edit a subcategory
}

// Function to delete a subcategory
function deleteSubcategory(subcategoryId) {
    // Implement functionality to delete a subcategory
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

});

function showDetails(subcategoryId) {
    alert('çalıştı');
}
