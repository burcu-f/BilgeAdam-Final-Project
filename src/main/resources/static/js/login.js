function submitForm() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    console.log("username: " + username);
    console.log("password: " + password);
  
    fetch("http://localhost:8080/api/auth/signin", {
      method: "POST",
      body: JSON.stringify({
        email: username,
        password,
      }),
      headers: {
        "Content-type": "application/json; charset=UTF-8",
        "Accept": "application/json"
      },
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error(
            "Login isteği başarısız. Durum kodu:" + response.status
          );
        }
        return response.json();
      })
      .then((data) => {
        console.log("login istek başarılı: ", data);
        localStorage.setItem("jwtToken", data.token);
        const role = parseJwt(data.token);
        document.getElementById("token").value = data.token; // Tokeni hidden input içerisine yerleştir
        document.getElementById("loginForm").submit(); // Formu submit et
      })
      .catch((error) => {
        console.error("Login hatası:", error);
        // Hata mesajını kullanıcıya gösterme işlemi eklenebilir
      });
  }
  
  function parseJwt(token) {
    const base64Url = token.split(".")[1];
    const base64 = base64Url.replace("-", "+").replace("_", "/");
    const decodedData = JSON.parse(atob(base64));
  
    const userRole = decodedData.authorities[0].authority;
    console.log(userRole);
    return userRole;
  }
  
  function goToHomePage() {
    window.location.href = "/"; // Redirect to the home page
}

