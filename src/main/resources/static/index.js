document.addEventListener("DOMContentLoaded", function () {
    // Check if the role is 'customer' or if a userId exists
    const role = localStorage.getItem("role");
    const userId = localStorage.getItem("userId");
    
    if (role === "customer" || userId) {
      // Assuming your login and register buttons have these IDs
      const loginButton = document.getElementById("login-btn");
      const registerButton = document.getElementById("register-btn");
      
      if (loginButton) {
        loginButton.style.display = "none";
      }
      if (registerButton) {
        registerButton.style.display = "none";
      }
      const logoutButton = document.getElementById("logout-btn");
      if (logoutButton) {
          logoutButton.style.display = "block";
      } else {
          console.error("logout-btn not found!");
      }
    }    function fetchRestaurants() {
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
