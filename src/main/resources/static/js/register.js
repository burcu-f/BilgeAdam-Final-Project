function register() {
    //form verilerini al
    const formData = {
        email: document.getElementById('email').value,
        firstName: document.getElementById('firstName').value,
        surname: document.getElementById('surname').value,
        password: document.getElementById('password').value,
    };
	
	Common.ajax({
        url: '/register',
        type: 'POST',
        data: JSON.stringify(formData),
        contentType: 'application/json',
        success: function(response) {
			window.location = '/login?message=Registeration successful.';
        },
        error: function(xhr, status, error) {
            console.error('Error adding product to cart:', error);
            alert('Error adding product to cart. Please try again later.');
        }
    });
}

function goToHomePage() {
    window.location.href = "/";
}