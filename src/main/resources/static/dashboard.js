document.addEventListener("DOMContentLoaded", function () {
    const addRestaurantBtn = document.getElementById("add-restaurant-btn");
    const restaurantFormContainer = document.getElementById("restaurant-form-container");
    const restaurantForm = document.getElementById("restaurant-form");
    const restaurantList = document.getElementById("restaurant-list");

    addRestaurantBtn.addEventListener("click", function () {
        restaurantFormContainer.style.display = "block";
    });

    restaurantForm.addEventListener("submit", function (event) {
        event.preventDefault();

        const name = document.getElementById("restaurant-name").value;
        const address = document.getElementById("restaurant-address").value;

        // Fetch userId and role from localStorage
        const userId = localStorage.getItem("userId");
        const role = localStorage.getItem("role");

        alert(userId);

//
//        if (!userId || !role) {
//            alert("Unauthorized: Please log in.");
//            return;
//        }

        fetch("http://localhost:8080/restaurants", {
            method: "POST",
            credentials: "include", // Ensure cookies/session work
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ name, address }) // Send userId & role
        })
        .then(response => {
            if (!response.ok) {
                return response.text().then(msg => { throw new Error(msg); });
            }
            return response.json();
        })
        .then(data => {
            alert("Restaurant added successfully!");
            restaurantFormContainer.style.display = "none";
            restaurantForm.reset();
            fetchRestaurants();
        })
        .catch(error => {
            alert("Error adding restaurant: " + error.message);
        });
    });

    function fetchRestaurants() {
        fetch("http://localhost:8080/restaurants")
            .then(response => response.json())
            .then(restaurants => {
                restaurantList.innerHTML = "";
                restaurants.forEach(restaurant => {
                    const div = document.createElement("div");
                    div.innerHTML = `<h3>${restaurant.name}</h3>
                    <p>${restaurant.address}</p>`;
                    restaurantList.appendChild(div);
                });
            })
            .catch(error => console.error("Error fetching restaurants:", error));
    }

    fetchRestaurants();
});
