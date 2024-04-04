$(document).ready(function() {

	// Populate the user table when the page is loaded
	refreshUserList();

	// Click event handler for the "Add User" button
	$("#btnAddUser").click(function() {
		$("#userModal").modal("show");
	});

	// Function to handle registration of a new user
	$("#btnRegister").click(function() {
		let name = $("#name").val();
		let surname = $("#surname").val();
		let email = $("#email").val();
		let password = $("#password").val();
		let accountType = $("#accountType").val();

		let newUser = {
			name: name,
			surname: surname,
			email: email,
			password: password,
			accountType: accountType
		};
		// AJAX request to register the new user
		$.ajax({
			url: "/admin/user-management/create",
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

});

function updateUser(accountId) {
	// Retrieve user details via AJAX request
	Common.ajax({
		url: "/admin/user-management/" + accountId,
		type: "GET",
		headers: {
			Accept: "application/json",
		},
		success: function(user) {
			// Populate the modal with user details
			$("#updateUserId").val(user.accountId);
			$("#updateName").val(user.name);
			$("#updateSurname").val(user.surname);
			$("#updateEmail").val(user.email);
			$("#updateAccountType").val(user.accountType);

			// Show the update modal
			$("#updateUserModal").modal("show");

			// Click event handler for the "Update" button within the update modal
			$("#btnUpdateUser").off("click").on("click", function() {
				let accountId = $("#updateUserId").val();
				let name = $("#updateName").val();
				let surname = $("#updateSurname").val();
				let email = $("#updateEmail").val();
				let accountType = $("#updateAccountType").val();

				let updatedUser = {
					accountId: accountId,
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
}



function deleteUser(accountId) {
		// Show the delete confirmation modal
		$("#deleteUserModal").modal("show");
	
		// Set up the delete confirmation button click event
		$("#btnConfirmDelete").off("click").on("click", function() {
			// Send an AJAX request to delete the user
			$.ajax({
				url: "/admin/user-management/" + accountId,
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
	}
	
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
			//"<button class='btn btn-info' onclick='showDetails(" + user.accountId + ")'>Details</button>" +
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
		url: "/admin/user-management/list",
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
