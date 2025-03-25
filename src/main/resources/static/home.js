document.addEventListener("DOMContentLoaded", function () {
    const role = localStorage.getItem("role");

    fetch("http://localhost:8080/restaurants")
        .then(response => response.json())
        .then(restaurants => {
            const restaurantList = document.getElementById("restaurant-list");
            restaurantList.innerHTML = "";

            restaurants.forEach(restaurant => {
                const div = document.createElement("div");
                 div.classList.add("restaurant-card");
                div.innerHTML = `<img src="${restaurant.imageUrl}" alt="${restaurant.name}" class="restaurant-image"> <!-- ✅ Display image -->
                                 <h3>${restaurant.name}</h3>
                                 <p>${restaurant.address}</p>`;

                // ✅ Allow only CUSTOMERS to click and view menu
                if (role === "CUSTOMER") {
                    div.addEventListener("click", function () {
                        window.location.href = `menuToCustomer.html?restaurantId=${restaurant.id}`;
                    });
                }

                restaurantList.appendChild(div);
            });
        })
        .catch(error => console.error("Error fetching restaurants:", error));
});
