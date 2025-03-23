document.addEventListener("DOMContentLoaded", function () {
    // Get elements
    const addRestaurantBtn = document.getElementById("add-restaurant-btn");
    const restaurantFormContainer = document.getElementById("restaurant-form-container");
    const restaurantForm = document.getElementById("restaurant-form");
    const restaurantList = document.getElementById("restaurant-list");

    // Show form when "Add Restaurant" button is clicked
    addRestaurantBtn.addEventListener("click", function () {
        restaurantFormContainer.style.display = "block";
    });

    // Handle form submission
    restaurantForm.addEventListener("submit", function (event) {
        event.preventDefault();

        // Get input values
        const name = document.getElementById("restaurant-name").value;
        const address = document.getElementById("restaurant-address").value;

        // Send data to API
        //this API will create a new restaurants
        fetch("http://localhost:8080/restaurants", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ name, address })
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert("Restaurant added successfully!");
                restaurantFormContainer.style.display = "none"; // Hide form
                restaurantForm.reset(); // Clear input fields
                fetchRestaurants(); // Refresh the list
            } else {
                alert("Failed to add restaurant: " + data.message);
            }
        })
        .catch(error => console.error("Error adding restaurant:", error));
    });

    // Function to fetch and display restaurants
    function fetchRestaurants() {
        fetch("http://localhost:8080/restaurants")
            .then(response => response.json())
            .then(restaurants => {
                restaurantList.innerHTML = ""; // Clear old list
                restaurants.forEach(restaurant => {
                    const div = document.createElement("div");
                    div.innerHTML = `<h3>${restaurant.name}</h3>
                    <p>${restaurant.address}</p>`;
                    restaurantList.appendChild(div);
                });
            })
            .catch(error => console.error("Error fetching restaurants:", error));
    }

    fetchRestaurants(); // Load restaurants on page load
});
