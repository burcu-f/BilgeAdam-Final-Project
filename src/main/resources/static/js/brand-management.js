// Define the updateBrand function globally
function updateBrand(brandId) {
    // Retrieve brand details via AJAX request
    $.ajax({
        url: "/brand-management/" + brandId,
        type: "GET",
        headers: {
            Accept: "application/json",
        },
        success: function(brand) {
            // Populate the modal with brand details
            $("#updateBrandId").val(brand.brandId);
            $("#updateBrandName").val(brand.brandName);

            // Show the update modal
            $("#updateBrandModal").modal("show");
        },
        error: function(xhr, status, error) {
            console.error("Error retrieving brand details: " + error);
        }
    });
}

$(document).ready(function() {
    // Populate the brand table when the page is loaded
    refreshBrandList();

    // Click event handler for the "Add Brand" button
    $("#btnAddBrand").click(function() {
        $("#brandModal").modal("show");
    });

    // Function to handle addition of a new brand
    $("#btnAddBrandModal").click(function() {
        let brandName = $("#brandName").val();

        let newBrand = {
            brandName: brandName,
        };

        // AJAX request to add the new brand
        Common.ajax({
            url: "/brand-management/create",
            type: "POST",
            data: JSON.stringify(newBrand),
            success: function(response) {
                alert("Brand added successfully!");
                $("#brandModal").modal("hide");
                refreshBrandList(); // Refresh the brand list after addition
            },
            error: function(xhr, status, error) {
                alert("Error adding brand: " + error);
            }
        });
    });

    // Click event handler for the "Update" button within the update modal
    $("#btnUpdateBrand").click(function() {
        var brandId = $("#updateBrandId").val();
        var brandName = $("#updateBrandName").val();

        var updatedBrand = {
            brandId: brandId,
            brandName: brandName,
        };

        // AJAX request to update the brand's information
        $.ajax({
            url: "/brand-management/" + brandId,
            type: "PUT",
            headers: {
                Accept: "application/json"
            },
            contentType: "application/json",
            data: JSON.stringify(updatedBrand),
            success: function(response) {
                alert("Brand information updated successfully!");
                $("#updateBrandModal").modal("hide");
                refreshBrandList(); // Refresh the brand list after updating
            },
            error: function(xhr, status, error) {
                alert("Error updating brand information: " + error);
            }
        });
    });
});

// Function to delete a brand
function deleteBrand(brandId) {
    // Confirm with the user before proceeding with the deletion
    if (confirm("Are you sure you want to delete this brand?")) {
        // Send AJAX request to delete the brand
        $.ajax({
            url: "/brand-management/" + brandId,
            type: "DELETE",
            headers: {
                Accept: "application/json"
            },
            success: function(response) {
                alert("Brand deleted successfully!");
                refreshBrandList(); // Refresh the brand list after deletion
            },
            error: function(xhr, status, error) {
                alert("Error deleting brand: " + error);
            }
        });
    }
}

// Function to refresh the brand list
function refreshBrandList() {
    Common.ajax({
        url: "/brand-management/brands",
        type: "GET",
        headers: {
            Accept: "application/json",
        },
        success: function(response) {
            populateBrandTable(response);
        }
    });
}

// Function to populate the brand table
function populateBrandTable(brands) {
    $("#brandTable tbody").empty();
    let i = 1;
    if (Array.isArray(brands)) {
        brands.forEach(function(brand) {
            let row =
                "<tr row-id=\"" + brand.brandId + "\">" +
                "<td>" + i + "</td>" +
                "<td>" + brand.brandName + "</td>" +
                "<td>" +
                "<button class='btn btn-warning' onclick='updateBrand(" + brand.brandId + ")'>Update</button>" +
                "<button class='btn btn-danger' onclick='deleteBrand(" + brand.brandId + ")'>Delete</button>" +
                "</td>" +
                "</tr>";
            ++i;
            $("#brandTable tbody").append(row);
        });
    } else {
        console.error("Brands is not an array:", brands);
    }
}
