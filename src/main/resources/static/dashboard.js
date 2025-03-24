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

        fetch("http://localhost:8080/restaurants", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ name, address }) // Send userId & role
        })
 .then(response => response.json())
        .then(data => {
            alert("Restaurant created successfully!");
  // ✅ Hide the form after successful creation
            restaurantFormContainer.style.display = "none";

            // ✅ Clear the form fields
            restaurantForm.reset();            fetchRestaurants();  // Refresh list

        })
        .catch(error => console.error("Error creating restaurant:", error));
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

                       div.addEventListener("click", function () {
                                            window.location.href = `menu.html?restaurantId=${restaurant.id}`;
                                        });

                    restaurantList.appendChild(div);
                });
            })
            .catch(error => console.error("Error fetching restaurants:", error));
    }

    fetchRestaurants();
});
