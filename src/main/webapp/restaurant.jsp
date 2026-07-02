<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.tap.model.Restaurant"%>

<%
    List<Restaurant> allRestaurants = (List<Restaurant>) request.getAttribute("allRestaurants");
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>QuickBite - Discover Best Restaurants</title>
<style>
body {
	margin: 0;
	padding: 0;
	font-family: Arial, sans-serif;
	background: #f9f9f9;
}

/* Navbar */
.navbar {
	background : linear-gradient(135deg, #ff6a00, #ff8c00);
	color: white;
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 14px 40px;
	position: fixed;
	top: 0;
	left: 0;
	right: 0;
	z-index: 1000;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.logo {
	font-size: 22px;
	font-weight: bold;
	display: flex;
	align-items: center;
	gap: 8px;
}


.nav-links a {
	color: white;
	text-decoration: none;
	margin-left: 25px;
	font-weight: 500;
	font-size: 16px;
	transition: 0.3s ease;
}

.nav-links a:hover {
	color: #ffe0b3;
	text-shadow: 0 0 8px rgba(255, 255, 255, 0.6);
}

/* Hero section */
.hero {
	margin-top: 80px; /* adjust based on navbar height */
	background: linear-gradient(135deg, #0f172a, #1e293b); /* Deep dark slate/charcoal gradient */
	color: #ffffff; /* Premium white text */
	padding: 60px 8%;
	display: flex;
	align-items: center;
	justify-content: space-between;
	gap: 40px;
	min-height: 480px;
	box-sizing: border-box;
	font-family: 'Poppins', sans-serif;
}

.hero-content {
	flex: 1.2;
	max-width: 600px;
	text-align: left;
}

.hero h1 {
	font-size: 3.2rem;
	margin-bottom: 20px;
	font-weight: 800;
	line-height: 1.2;
}

.hero p {
	font-size: 1.25rem;
	margin-bottom: 30px;
	color: #d1d5db; /* Light slate grey paragraph */
	line-height: 1.6;
}

.hero-image-container {
	flex: 1;
	display: flex;
	justify-content: center;
	align-items: center;
}

.hero-image {
	max-width: 100%;
	max-height: 380px;
	object-fit: contain;
	border-radius: 16px;
	box-shadow: 0 20px 40px rgba(0, 0, 0, 0.5);
	transition: transform 0.3s ease;
}

.hero-image:hover {
	transform: translateY(-5px);
}

@media(max-width: 768px){
	.hero {
		flex-direction: column;
		text-align: center;
		padding: 40px 20px;
		gap: 30px;
	}
	.hero-content {
		text-align: center;
		flex: 1;
	}
	.hero-image {
		max-height: 280px;
	}
}

/* Grid container */
.container {
	display: flex;
	flex-wrap: wrap;
	justify-content: center;
	padding: 20px;
}

/* Restaurant card */
.card {
	background: #fff;
	border-radius: 8px;
	box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
	margin: 15px;
	padding: 15px;
	width: 260px;
	transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.card:hover {
	transform: translateY(-5px);
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.card-img-container {
	position: relative;
}

.card-img {
	width: 100%;
	height: 160px;
	border-radius: 8px;
	object-fit: cover;
}

.card-img-placeholder {
	width: 100%;
	height: 160px;
	border-radius: 8px;
	background: #eee;
	display: flex;
	align-items: center;
	justify-content: center;
	font-size: 40px;
}

.card-content {
	margin-top: 12px;
}

.restaurant-name {
	font-size: 18px;
	margin-bottom: 6px;
	color: #333;
}

.badges {
	margin: 6px 0;
}

.rating {
	color: #4CAF50;
	font-weight: bold;
	margin-right: 10px;
}

.delivery {
	color: #555;
	font-size: 14px;
}

.info {
	font-size: 14px;
	color: #555;
	margin: 4px 0;
}

.order-btn {
	width: 40%; /* narrower width */
	margin: 12px auto 0; /* centers button neatly */
	padding: 12px 0; /* keep height large */
	border: none;
	border-radius: 6px;
	background: red; /* warm orange */
	color: #fff;
	cursor: pointer;
	font-weight: 600;
	font-size: 15px;
	text-decoration: none;
	display: block;
	text-align: center;
	transition: background 0.3s ease, transform 0.2s ease;
}

.order-btn:hover {
	background: #e65c2d; /* darker orange on hover */
	transform: translateY(-3px);
}

.no-data {
	text-align: center;
	color: #666;
	font-size: 18px;
	margin-top: 20px;
}

.card {
	background: #fff;
	border-radius: 12px; /* refined corners */
	box-shadow: 0 2px 8px rgba(0, 0, 0, 0.12);
	margin: 15px;
	padding: 15px;
	width: 260px;
	transition: transform 0.4s ease, box-shadow 0.4s ease, background 0.4s
		ease;
}

.card:hover {
	transform: translateY(-8px) scale(1.03);
	box-shadow: 0 10px 24px rgba(0, 0, 0, 0.25);
	/* deeper, elegant shadow */
	background: linear-gradient(135deg, #fdfcfb, /* soft cream */
    #e2d1c3, /* warm beige */
    #c9d6ff, /* subtle blue */
    #e2e2e2 /* light silver */
  ); /* luxury gradient */
}

.search-container {
	width: 100%;
	display: flex;
	justify-content: center;
	margin: 30px 0;
}

.search-bar {
	width: 50%;
	padding: 12px 20px;
	border: none;
	border-radius: 40px;
	font-size: 16px;
	outline: none;
	color: #333;
	background: linear-gradient(135deg, #fff8f5, #fff) padding-box,
		linear-gradient(135deg, #ff7043, #e91e63) border-box;
	border: 2px solid transparent;
	box-shadow: 0 4px 12px rgba(255, 112, 67, 0.25);
	transition: all 0.4s ease;
	background-image:
		url('https://cdn-icons-png.flaticon.com/512/1046/1046784.png');
	background-repeat: no-repeat;
	background-size: 24px;
	background-position: 15px center;
	padding-left: 50px; /* space for icon */
}

.search-bar::placeholder {
	color: #999;
	font-style: italic;
	text-shadow: 0 0 1px #ff7043;
}

.search-bar:focus {
	transform: scale(1.03);
	box-shadow: 0 0 15px rgba(233, 30, 99, 0.4);
	background: linear-gradient(135deg, #fffaf5, #fff) padding-box,
		linear-gradient(135deg, #e91e63, #ff7043) border-box;
}

input:focus {
	box-shadow: 0 0 10px rgba(255, 106, 0, 0.5);
	outline: none;
}
/* Base dark mode */
/* Dark mode base */
body.dark-mode {
    background-color: #121212;
    color: #f5f5f5;
}

/* Navbar */
.navbar.dark-mode {
    background-color: #333 !important;
    color: #fff !important;
}

/* Restaurant cards */
body.dark-mode .card {
    background-color: #1e1e1e !important;
    color: #f5f5f5 !important;
    box-shadow: 0 2px 6px rgba(255, 255, 255, 0.1);
}

/* Fix card text */
body.dark-mode .card h2,
body.dark-mode .card p,
body.dark-mode .card .info,
body.dark-mode .card .delivery {
    color: #f5f5f5 !important;
}

/* Menu page sections */
body.dark-mode .menu-container,
body.dark-mode .menu-card {
    background-color: #1e1e1e !important;
    color: #f5f5f5 !important;
}

/* Buttons */
body.dark-mode .order-btn {
    background-color: #e91e63 !important; /* keep pink accent */
    color: #fff !important;
}

/* Dark Mode Toggle Button */
#darkModeToggle {
    background-color: #ff9800 !important; /* QuickBite orange */
    color: #fff !important;
    border: none !important;
    border-radius: 6px !important;
    padding: 6px 12px !important;
    font-weight: 600 !important;
    cursor: pointer !important;
    box-shadow: 0 2px 6px rgba(0,0,0,0.2) !important;
    transition: background-color 0.3s ease, color 0.3s ease !important;
}

#darkModeToggle:hover {
    background-color: #e68900 !important;
}

body.dark-mode #darkModeToggle {
    background-color: #f5f5f5 !important;
    color: #333 !important;
    border: none !important;
    box-shadow: 0 2px 6px rgba(255,255,255,0.2) !important;
}

.navbar #darkModeToggle {
    margin-left: 10px !important;
    height: 32px !important;
}

/* Smooth transition */
body, .card, .navbar, .menu-container, .menu-card, .order-btn {
    transition: background-color 0.3s ease, color 0.3s ease;
}


</style>
</head>
<body>
	<!-- Search Bar -->





		<!-- Navbar -->
	<div class="navbar">
		<div class="logo">🍴 QuickBite</div>
		<div class="nav-links">
			<a href="home"> Home</a> 
			<a href="orderHistory">📜 Order History</a> <!-- Added Link -->
			<a href="login.html"> Login</a> 
			<a href="cart.jsp"> 🛒 Cart  </a>
			<a	href="register.html">Signup</a> 
			<button id="darkModeToggle" class="dark-toggle">🌙 Dark Mode</button>
		</div>
	</div>
	


	<!-- Hero Section -->
	<div class="hero">
		<div class="hero-content">
			<h1>Find Your Flavor</h1>
			<p>Discover trending restaurants, explore cuisines, and enjoy fast
				delivery — all in one place.</p>
		</div>
		<div class="hero-image-container">
			<img src="Images/hero.png" alt="QuickBite App" class="hero-image">
		</div>
	</div>
	<!-- Search Bar -->
	<div class="search-container">
		<input type="text" id="searchBar" class="search-bar"
			placeholder="     Search restaurants...">
	</div>


	<!-- Grid Container for Cards -->
	<div class="container">
		<%
        if (allRestaurants != null && !allRestaurants.isEmpty()) {
            for (Restaurant restaurant : allRestaurants) {
    %>

		<a href="menu?restaurantId=<%= restaurant.getRestaurantId()%>">

			<div class="card">
				<div class="card-img-container">
					<% if (restaurant.getImagePath() != null && !restaurant.getImagePath().isEmpty()) { %>
					<img class="card-img" src="<%= restaurant.getImagePath() %>"
						alt="<%= restaurant.getName() %>">
					<% } else { %>
					<div class="card-img-placeholder">🍔</div>
					<% } %>
				</div>
				<div class="card-content">
					<h2 class="restaurant-name"><%= restaurant.getName() %></h2>
					<div class="badges">
						<span class="rating">★ <%= restaurant.getRating() %></span> <span
							class="delivery">🕒 <%= restaurant.getDeliveryTime() %>
							mins
						</span>
					</div>
					<p class="info">
						<strong>Cuisine:</strong>
						<%= restaurant.getCuisineType() %></p>
					<p class="info">
						<strong>Address:</strong>
						<%= restaurant.getAddress() %></p>





				</div>
			</div> <%
            }
        } else {
    %>
			<h2 class="no-data">No Restaurants Available 😔</h2> <%
        }
    %>
		
	</div>
	<script>
  const searchBar = document.getElementById("searchBar");
  const restaurantCards = document.querySelectorAll(".card");

  // Create a "No results" message element
  const noResultsMsg = document.createElement("h2");
  noResultsMsg.textContent = "No Restaurants Found 😔";
  noResultsMsg.classList.add("no-data");
  noResultsMsg.style.display = "none";
  document.querySelector(".container").appendChild(noResultsMsg);

  searchBar.addEventListener("keyup", function() {
    const query = searchBar.value.toLowerCase();
    let anyVisible = false;

    restaurantCards.forEach(card => {
      const nameElement = card.querySelector(".restaurant-name");
      const name = nameElement.textContent.toLowerCase();

      if (name.includes(query)) {
        card.style.display = "";
        anyVisible = true;
      } else {
        card.style.display = "none";
      }
    });

    // Show or hide "No results" message
    noResultsMsg.style.display = anyVisible ? "none" : "block";
  });
</script>
	
	<script>
    const toggleBtn = document.getElementById("darkModeToggle");
    toggleBtn.addEventListener("click", () => {
        document.body.classList.toggle("dark-mode");
        document.querySelector(".navbar").classList.toggle("dark-mode");

        // Change button text/icon
        if (document.body.classList.contains("dark-mode")) {
            toggleBtn.textContent = "☀️ Light Mode";
        } else {
            toggleBtn.textContent = "🌙 Dark Mode";
        }
    });
</script>
	
</body>
</html>
