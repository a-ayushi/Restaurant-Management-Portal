

document.getElementById("register-form").addEventListener("submit", function (event) {
    event.preventDefault();

    let email = document.getElementById("register-email").value;
    let password = document.getElementById("register-password").value;
    let role = document.getElementById("register-role").value;

    if (role === "") {
        alert("Please select a role!");
        return;
    }

    fetch("http://localhost:8080/auth/register", { // Replace with your backend API
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password, role })
    })
    .then(response => {
        if (response.ok) {
            alert("Registration successful! Redirecting to login...");
            window.location.href = "login.html"; // Redirect to login page
        } else {
            return response.json().then(err => {
                throw new Error(err.message || "Registration failed");
            });
        }
    })
    .catch(error => {
        console.error("Registration failed:", error);
        alert(error.message);
    });
});
