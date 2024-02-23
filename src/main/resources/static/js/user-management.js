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
        // Reset the form fields when the modal is opened
        $("#userModal").on("shown.bs.modal", function() {
            $("#userForm")[0].reset();
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
