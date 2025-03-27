document.addEventListener("DOMContentLoaded", function () {
  const addRestaurantBtn = document.getElementById("add-restaurant-btn");
  const restaurantFormContainer = document.getElementById("restaurant-form-container");
  const restaurantForm = document.getElementById("restaurant-form");
  const restaurantList = document.getElementById("restaurant-list");
  const userId = localStorage.getItem("userId");

  function restaurantExistsForUser(restaurants) {
    return restaurants.some(restaurant => restaurant.userId && restaurant.userId.toString() === userId);
  }

      // Show form when Add Restaurant button is clicked
  addRestaurantBtn.addEventListener("click", function () {
    restaurantFormContainer.style.display = "block";
  });


//handle restaurant form submission
  restaurantForm.addEventListener("submit", function (event) {
    event.preventDefault();

    const name = document.getElementById("restaurant-name").value;
    const address = document.getElementById("restaurant-address").value;
    const imageFile = document.getElementById("restaurant-image").files[0]; // Get image

        if (!imageFile) {
            alert("Please select an image!");
            return;
        }

        const formData = new FormData();
        formData.append("name", name);
        formData.append("address", address);
        formData.append("ownerId", userId);
        formData.append("image", imageFile);

    fetch("http://localhost:8080/restaurants", {
      method: "POST",
      body:formData
    })
      .then((response) => response.json())
      .then((data) => {
        alert("Restaurant created successfully!");
        restaurantFormContainer.style.display = "none";
        restaurantForm.reset();
        // After creating a restaurant, refresh the view by fetching the restaurant by user id
        fetchRestaurantByUserId();
      })
      .catch((error) => console.error("Error creating restaurant:", error));
  });


  function fetchRestaurantByUserId() {
    if (!userId) {
      console.error("No userId found in localStorage.");
      return;
    }

    fetch(`http://localhost:8080/restaurants/user/${userId}`)
      .then((response) => {
        if (response.ok) {
          // Restaurant found for the current user
          return response.json();
        } else if (response.status === 404) {
          // No restaurant exists for the user
          return null;
        } else {
          throw new Error("Unexpected error while fetching restaurant.");
        }
      })
      .then((restaurant) => {
        restaurantList.innerHTML = "";
        if (restaurant) {
          // Display the restaurant details for the current user
          const div = document.createElement("div");
          div.classList.add("restaurant-card");
          div.innerHTML = `
             <img src="${restaurant.imageUrl}" alt="${restaurant.name}" class="restaurant-image">  <!--  Display image -->
            <h3>${restaurant.name}</h3>
            <p>${restaurant.address}</p>
          `;

          div.addEventListener("click", function () {
            window.location.href = `menu.html?restaurantId=${restaurant.id}`;
          });
          restaurantList.appendChild(div);
          // Hide the add restaurant button because one exists already
          addRestaurantBtn.style.display = "none";
        } else {
          // No restaurant exists for the user, so show the add restaurant button
          addRestaurantBtn.style.display = "block";
        }
      })
      .catch((error) =>
        console.error("Error fetching restaurant by user id:", error)
      );
  }

  fetchRestaurantByUserId();
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
