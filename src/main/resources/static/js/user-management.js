$(document).ready(function() {
    // Function to populate the user table
    function populateUserTable(users) {
        $("#userTableBody").empty();
        users.forEach(function(user) {
            var row = "<tr><td>" + user.name + "</td><td>" + user.email + "</td>" +
                      "<td><button class='btn btn-info' onclick='showDetails(" + user.id + ")'>Detaylar</button>" +
                      "<button class='btn btn-warning' onclick='editUser(" + user.id + ")'>Güncelle</button>" +
                      "<button class='btn btn-danger' onclick='deleteUser(" + user.id + ")'>Sil</button></td></tr>";
            $("#userTableBody").append(row);
        });
    }

    // Function to refresh the user list
    function refreshUserList() {
        $.ajax({
            url: "/user-management/accounts",
            type: "GET",
            success: function(users) {
                populateUserTable(users);
            },
            error: function(xhr, status, error) {
                console.error("Error refreshing user list: " + error);
            }
        });
    }

    // Sample user data
    var users = [
        { id: 1, name: "User 1", email: "user1@example.com" },
        { id: 2, name: "User 2", email: "user2@example.com" }
        // More users can be added here
    ];

    // Populate the user table when the page is loaded
    populateUserTable(users);

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
            url: "/user-management",
            type: "POST",
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
