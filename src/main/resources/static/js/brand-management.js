$(document).ready(function() {
    // Function to populate the brand table
    function populateBrandTable(brands) {
        $("#brandTable tbody").empty();
        let i = 1;
        brands.forEach(function(brand) {
            let row = $('<tr>', {
                "row-id": brand.brandId
            });
            row.append(Common.createTd(i));
            row.append(Common.createTd(brand.brandName));
            // Add more cells for other brand attributes if needed

            // Actions column containing update and delete buttons
            let actionsTd = Common.createTd();
            
            // Update button
            let updateBtn = $("<button>", {
                class: "btn btn-warning",
                text: "Update",
                click: function() {
        			var brandId = $(this).closest("tr").attr("row-id");
                    updateBrand(brandId); // Call update function with brandId
                }
            });
            actionsTd.append(updateBtn);

            // Delete button
            let deleteBtn = $("<button>", {
                class: "btn btn-danger",
                text: "Delete",
                click: function() {
                    deleteBrand(brand.brandId); // Call delete function with brandId
                }
            });
            actionsTd.append(deleteBtn);

            // Add actions column to the row
            row.append(actionsTd);
            ++i;
            $("#brandTable tbody").append(row);
        });
    }

    // Function to refresh the brand list
    function refreshBrandList() {
        Common.ajax({
            url: "/brand-management/brands",
            type: "GET",
            success: function(brands) {
                populateBrandTable(brands);
            },
            error: function(xhr, status, error) {
                console.error("Error refreshing brand list: " + error);
            }
        });
    }

    // Populate the brand table when the page is loaded
    refreshBrandList();

    // Function to handle addition of a new brand
    $("#btnAddBrandModal").click(function() {
        let brandName = $("#brandName").val();
        // Add more variables for other brand attributes here

        let newBrand = {
            brandName: brandName,
            // Add other brand attributes here
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

    // Function to update a brand
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
                $("#updatedBrandId").val(brand.brandId);
                $("#updatedBrandName").val(brand.brandName);
                // Add code to populate other input fields for updated brand attributes if needed

                // Show the update modal
                $("#updateBrandModal").modal("show");
            },
            error: function(xhr, status, error) {
                console.error("Error retrieving brand details: " + error);
            }
        });
    }

    // Add event handler for update button click
    $("#btnUpdateBrandModal").click(function() {
        var brandId = $("#updatedBrandId").val();
        var brandName = $("#updatedBrandName").val();
        // Add more variables for other updated brand attributes here

        var updatedBrand = {            
            brandId: brandId,
            brandName: brandName,
            // Add other updated brand attributes here
        };

        // Make AJAX request to update brand
        $.ajax({
            url: "/brand-management/" + brandId,
            type: "PUT", 
            headers: {
                Accept: "application/json",
            },
            data: JSON.stringify(updatedBrand),
            success: function(response) {
                alert("Brand information updated successfully!");
                $("#updateBrandModal").modal("hide");
                refreshBrandList();
            },
            error: function(xhr, status, error) {
                alert("Error updating brand information: " + error);
            },
        });
    });
});
