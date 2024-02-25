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
	                  "<button class='btn btn-info' onclick='showDetails(" + user.accountId + ")'>Detaylar</button>" +
	                  "<button class='btn btn-warning' onclick='editUser(" + user.accountId + ")'>Güncelle</button>" +
	                  "<button class='btn btn-danger' onclick='deleteUser(" + user.accountId + ")'>Sil</button>" + 
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

    // Function to show user details
    function showDetails(userId) {
        // Implement functionality to show user details
    }

    // Function to edit a user
    function editUser(userId) {
        // Implement functionality to edit a user
    }

    // Function to delete a user
    function deleteUser(userId) {
        // Implement functionality to delete a user
    }
});
