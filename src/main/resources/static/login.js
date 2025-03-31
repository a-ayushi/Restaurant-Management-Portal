document
  .getElementById("login-form")
  .addEventListener("submit", function (event) {
    event.preventDefault();

    let email = document.getElementById("login-email").value;
    let password = document.getElementById("login-password").value;

    fetch("http://localhost:8080/auth/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email, password }),
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.userId) {
          localStorage.setItem("userId", data.userId);
          localStorage.setItem("role", data.role); // Store user role
          if (data.role === "CUSTOMER") {
            window.location.href = "index.html"; // Redirect CUSTOMER to login
          } else {
            showMessage("Login Successful ✅", "success");
            setTimeout(() => {
              window.location.href = "dashboard.html";
            }, 3000);
          }
        } else {
          showMessage("Login failed: " + data.message, "error");
        }
      })
      .catch((error) => {
        console.error("Fetch Error:", error);
        alert("Login failed due to a network error.");
      });
  });
