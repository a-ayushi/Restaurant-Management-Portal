
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
// Handle Registration Submission
document.getElementById("register-form").addEventListener("submit", function (event) {
    event.preventDefault();
    let email = document.getElementById("register-email").value;
    let password = document.getElementById("register-password").value;
    let role = document.getElementById("register-role").value;

// if user do not select it then give an alert
    if (role === "") {
        alert("Please select a role!");
        return;
    }
console.log("Registering:", { email, password, role });

    fetch("http://localhost:8080/auth/register", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password, role })
        })

        .then(response => {
            console.log("Full Response:", response);
            return response.json();
        })
       .then(data => {
           console.log("Response Data:", data);
           if (data.message === "User registered successfully!") {
               alert("Registration Successful! Redirecting to login...");
               window.location.href = "login.html";
           } else {
               alert("Registration failed: " + (data.error || "Unknown error"));
           }
       })
       .catch(error => {
           console.error("Fetch Error:", error);
           alert("Registration failed due to a network error.");
});
});