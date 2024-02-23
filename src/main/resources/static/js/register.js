function register() {
    //form verilerini al
    const formData = {
        email: document.getElementById('email').value,
        firstName: document.getElementById('firstName').value,
        surname: document.getElementById('surname').value,
        password: document.getElementById('password').value,
    };

    console.log('form datası çekildiğinde: ', formData)
    //TODO: API'ye Post atmam lazım

    fetch('http://localhost:8080/api/auth/signup', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
    .then(response => response.json())
    .then(data => {
        console.log("response for register : ", data)
        window.location.href = "login.html"
    })
    .catch(error => {
        console.error("Error", error)
    })
}