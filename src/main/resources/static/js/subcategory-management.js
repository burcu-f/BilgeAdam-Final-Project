$(document).ready(function() {
    // Function to populate the subcategory table
    function populateSubcategoryTable(subcategories) {
        $("#subcategoryTableBody").empty();
        subcategories.forEach(function(subcategory) {
            var row = "<tr><td>" + subcategory.id + "</td><td>" + subcategory.name + "</td>" +
                      "<td>" + subcategory.category + "</td>" +
                      "<td><button class='btn btn-warning' onclick='editSubcategory(" + subcategory.id + ")'>Edit</button>" +
                      "<button class='btn btn-danger' onclick='deleteSubcategory(" + subcategory.id + ")'>Delete</button></td></tr>";
            $("#subcategoryTableBody").append(row);
        });
    }

// Function to refresh the subcategory list
function refreshSubcategoryList() {
    $.ajax({
        url: "/subcategory-management",
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

// Click event handler for the "Add Subcategory" button
$("#btnAddSubcategory").click(function() {
    $("#subcategoryModal").modal("show");
});

// Function to handle addition of a new subcategory
$("#btnAddSubcategoryModal").click(function() {
    var name = $("#name").val();
    var category = $("#category").val();

    var newSubcategory = {
        name: name,
        category: category
    };

    // AJAX request to add the new subcategory
    $.ajax({
        url: "/subcategory-management",
        type: "POST",
        contentType: "application/json",
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

// Function to edit a subcategory
function editSubcategory(subcategoryId) {
    // Implement functionality to edit a subcategory
}

// Function to delete a subcategory
function deleteSubcategory(subcategoryId) {
    // Implement functionality to delete a subcategory
}
});
