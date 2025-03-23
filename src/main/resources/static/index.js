document.addEventListener("DOMContentLoaded", function () {
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
