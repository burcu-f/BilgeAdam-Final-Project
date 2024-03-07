$(document).ready(function() {
    // Function to populate the user table
    function populateUserTable(users) {
        $("#userTable tbody").empty();
        let i = 1;
        users.forEach(function(user) {
            let row = 
    		  "<tr row-id=\"" + user.accountId + "\">" +
        		  "<td>" + i + "</td>" +
        		  "<td>" + user.name + "</td>" +
        		  "<td>" + user.surname + "</td>" +
        		  "<td>" + user.email + "</td>" +
        		  "<td>" + user.accountType + "</td>" +
                  "<td>" +
	                  "<button class='btn btn-info' onclick='showDetails(" + user.accountId + ")'>Details</button>" +
	                  "<button class='btn btn-warning' onclick='updateUser(" + user.accountId + ")'>Update</button>" +
	                  "<button class='btn btn-danger' onclick='deleteUser(" + user.accountId + ")'>Delete</button>" + 
                  "</td>" +
              "</tr>";
            ++i;
            $("#userTable tbody").append(row);
        });
    }

    // Function to refresh the user list
    function refreshUserList() {
        $.ajax({
            url: "/user-management/accounts",
            headers: {          
				Accept: "application/json",         
			},
            type: "GET",
            success: function(users) {
                populateUserTable(users);
            },
            error: function(xhr, status, error) {
                console.error("Error refreshing user list: " + error);
            }
        });
    }

    // Populate the user table when the page is loaded
    refreshUserList();

    // Click event handler for the "Add User" button
    $("#btnAddUser").click(function() {
        $("#userModal").modal("show");
    });

    // Function to handle registration of a new user
    $("#btnRegister").click(function() {
        var name = $("#name").val();
        var surname = $("#surname").val();
        var email = $("#email").val();
        var password = $("#password").val();
        var accountType = $("#accountType").val();

        var newUser = {
            name: name,
            surname: surname,
            email: email,
            password: password,
            accountType: accountType
        };
        // AJAX request to register the new user
        $.ajax({
            url: "/user-management/create",
            type: "POST",
            headers: {          
				Accept: "application/json",         
			},
            contentType: "application/json",
            data: JSON.stringify(newUser),
            success: function(response) {
                alert("User registration successful!");
                $("#userModal").modal("hide");
                refreshUserList(); // Refresh the user list after registration
            },
            error: function(xhr, status, error) {
                alert("Error registering user: " + error);
            }
        });
    });

// Click event handler for the "Details" button
$("#userTable").on("click", ".btn-info", function() {
    // Get the account ID from the row
    var accountId = $(this).closest("tr").attr("row-id");
    
    // Show user details
    showDetails(accountId);
});


function showDetails(accountId) {
    console.log("Showing details for accountId:", accountId);
    
    // AJAX request to fetch user details
    $.ajax({
        url: "/user-management/" + accountId,
        type: "GET",
        headers: {          
            Accept: "application/json",         
        },
        success: function(user) {
            console.log("Received user details:", user);
            
            // Populate modal with user details
            $("#userDetailsName").text(user.name);
            $("#userDetailsSurname").text(user.surname);
            $("#userDetailsEmail").text(user.email);
            $("#userDetailsAccountType").text(user.accountType);
            $("#userDetailsAddress").text(user.address ? user.address.fullAddress : "N/A");
            
            // Show the details modal
            $("#userDetailsModal").modal("show");
        },
        error: function(xhr, status, error) {
            console.error("Error retrieving user details:", error);
        }
    });
}


  // Click event handler for the "Update" button
$("#userTable").on("click", ".btn-warning", function() {
    // Get the account ID from the row
    var accountId = $(this).closest("tr").attr("row-id");
    
    // Retrieve user details via AJAX request
    $.ajax({
        url: "/user-management/" + accountId,
        type: "GET",
        headers: {          
				Accept: "application/json",         
			},
        success: function(user) {
            // Populate the modal with user details
            $("#updateUserId").val(user.id);
            $("#updateName").val(user.name);
            $("#updateSurname").val(user.surname);
            $("#updateEmail").val(user.email);
            $("#updateAccountType").val(user.accountType);
            
            // Show the update modal
            $("#updateUserModal").modal("show");

            // Click event handler for the "Update" button within the update modal
            $("#btnUpdateUser").off("click").on("click", function() {
                var userId = $("#updateUserId").val();
                var name = $("#updateName").val();
                var surname = $("#updateSurname").val();
                var email = $("#updateEmail").val();
                var accountType = $("#updateAccountType").val();

                var updatedUser = {
                    id: userId,
                    name: name,
                    surname: surname,
                    email: email,
                    accountType: accountType
                };

                // AJAX request to update the user's information
                $.ajax({
                    url: "/user-management/" + accountId, // Use accountId here
                    type: "PUT",
                    headers: {
                        Accept: "application/json"
                    },
                    contentType: "application/json",
                    data: JSON.stringify(updatedUser), // Use updatedUser here
                    success: function(response) {
                        alert("User information updated successfully!");
                        $("#updateUserModal").modal("hide");
                        refreshUserList(); // Refresh the user list after updating
                    },
                    error: function(xhr, status, error) {
                        alert("Error updating user information: " + error);
                    }
                });
            });
        },
        error: function(xhr, status, error) {
            console.error("Error retrieving user details: " + error);
        }
    });
});


    
    // Event delegation for the "Delete" button
    $("#userTable").on("click", ".btn-danger", function() {
        // Get the user's ID from the row
        var accountId = $(this).closest("tr").attr("row-id");
        
        // Show the delete confirmation modal
        $("#deleteUserModal").modal("show");

        // Set up the delete confirmation button click event
        $("#btnConfirmDelete").off("click").on("click", function() {
            // Send an AJAX request to delete the user
            $.ajax({
                url: "/user-management/" + accountId,
                type: "DELETE",
                headers: {          
				Accept: "application/json",         
			},
                success: function(response) {
                    alert("User deleted successfully!");
                    $("#deleteUserModal").modal("hide");
                    refreshUserList(); // Refresh the user list after deletion
                },
                error: function(xhr, status, error) {
                    alert("Error deleting user: " + error);
                }
            });
        });
    });
});
