document.getElementById("login-form").addEventListener("submit", function (event) {
    event.preventDefault();

    let email = document.getElementById("login-email").value;
    let password = document.getElementById("login-password").value;

    fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password })
    })
    .then(response => response.json())
    .then(data => {
        if (data.userId) {
            localStorage.setItem("userId", data.userId);
            localStorage.setItem("role", data.role); // Store user role
// =            alert("Login Successful!");
            if (data.role === "CUSTOMER") {
                window.location.href = "index.html"; // Redirect CUSTOMER to login
            }
            else{
                window.location.href = "dashboard.html";
            }
        } else {
            alert("Login failed: " + data.message);
        }
    })
    .catch(error => {
        console.error("Fetch Error:", error);
        alert("Login failed due to a network error.");
    });
});




