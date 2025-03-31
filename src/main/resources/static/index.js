document.addEventListener("DOMContentLoaded", function () {
    // Check if the role is 'customer' or if a userId exists
    const role = localStorage.getItem("role");
    const userId = localStorage.getItem("userId");

    const loginButton = document.getElementById("login-btn");
          const registerButton = document.getElementById("register-btn");
          const logoutButton = document.getElementById("logout-btn");
    
     if (userId || role === "customer") {
            // Hide login and register buttons
            if (loginButton) loginButton.style.display = "none";
            if (registerButton) registerButton.style.display = "none";
            // Show logout button
            if (logoutButton) logoutButton.style.display = "block";
        } else {
            // Show login and register buttons, hide logout button
            if (loginButton) loginButton.style.display = "block";
            if (registerButton) registerButton.style.display = "block";
            if (logoutButton) logoutButton.style.display = "none";
        }


     function fetchRestaurants() {
        fetch("http://localhost:8080/restaurants")
            .then(response => response.json())
            .then(restaurants => {
                const restaurantList = document.getElementById("restaurant-list");
                restaurantList.innerHTML = ""; // Clear old list

                restaurants.forEach(restaurant => {
                    const div = document.createElement("div");
                    div.classList.add("restaurant-card");
                    div.innerHTML = `
                        <h3>${restaurant.name}</h3>
                        <p>${restaurant.address}</p>
                    `;
                    restaurantList.appendChild(div);
                });
            })
            .catch(error => console.error("Error fetching restaurants:", error));
    }

    fetchRestaurants(); // Load restaurants on homepage
});


//logout implementation
document.getElementById("logout-btn").addEventListener("click", function () {
  fetch("http://localhost:8080/auth/logout", {
      method: "POST"
  })
  .then(response => {
      console.log("Logout response:", response);
      if (!response.ok) {
          throw new Error("Logout failed with status " + response.status);
      }
      return response;
  })
  .then(() => {
      localStorage.removeItem("userId");
      localStorage.removeItem("role");
      alert("Logout successful!");
      window.location.href = "login.html";
  })
  .catch(error => {
      console.error("Logout error:", error);
      alert("Logout failed. Please try again.");
  });
});