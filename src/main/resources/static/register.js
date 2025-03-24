
// Show Register Form, Hide Login Form
document.addEventListener("DOMContentLoaded", function () {
    showRegister();
});

function showRegister() {
    let registerContainer = document.getElementById("register-container");
    if (registerContainer) {
        registerContainer.style.display = "block";
    } else {
        console.error("register-container not found!");
    }
}
//// Handle Registration Submission
document.getElementById("register-form").addEventListener("submit", function (event) {
    event.preventDefault();

    let email = document.getElementById("register-email").value;
    let password = document.getElementById("register-password").value;
    let role = document.getElementById("register-role").value; // Dropdown: CUSTOMER / OWNER

    fetch("http://localhost:8080/auth/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password, role })
    })
    .then(response => response.text())
    .then(message => {
        alert(message);
        if (role === "CUSTOMER"|| role=== "OWNER") {
            window.location.href = "login.html"; // Redirect CUSTOMER to login
        } else {
            alert("Registration successful! Owners can now log in.");
        }
    })
    .catch(error => console.error("Error registering:", error));
});