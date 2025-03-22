document.addEventListener("DOMContentLoaded", function () {
    showLogin();
});

//////Show Login Form, Hide Register Form
function showLogin() {
    document.getElementById("login-container").style.display = "block";
    document.getElementById("register-container").style.display = "none";
}


// Handle Login Submission

document.getElementById("login-form").addEventListener("submit", function (event) {
    event.preventDefault();
    let email = document.getElementById("login-email").value;
    let password = document.getElementById("login-password").value;


//After login the restaurant owner will be redirected to dashboard page
fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password })
    })
    .then(response => response.json())
    .then(data => {
        if (data.role === "OWNER") {
            window.location.href = "dashboard.html"; // Redirect to the dashboard
        } else {

            window.location.href = "index.html"; // Redirect customers to the homepage
        }
    })
    .catch(error => console.error("Login failed:", error));
});
