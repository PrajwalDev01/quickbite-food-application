<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.tap.model.Menu"%>

<%
    List<Menu> allMenusByRestaurant = (List<Menu>) request.getAttribute("allMenusByRestaurant");
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Restaurant Menu</title>
<style>
body {
    margin: 0;
    padding: 0;
    font-family: Arial, sans-serif;
    background: #f9f9f9;
}

.navbar {
  background: linear-gradient(135deg, #ff6a00, #ff8c00);
  color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 30px; /* reduced padding */
  height: 55px; /* smaller height */
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1000;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

body {
  margin: 0;
  padding-top: 65px; /* adds space below navbar */
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

/* Menu container */
.menu-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    padding: 20px;
}

/* Menu card */
.menu-card {
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 6px rgba(0,0,0,0.1);
    margin: 15px;
    padding: 15px;
    width: 240px;
    transition: transform 0.4s ease, 
                box-shadow 0.4s ease, 
                background-color 0.4s ease, 
                border-color 0.4s ease, 
                color 0.4s ease;
}

.menu-card:hover {
    transform: translateY(-8px) scale(1.05); /* lift + zoom */
    box-shadow: 0 8px 20px rgba(0,0,0,0.25); /* deeper shadow */
    background-color: #e3f2fd; /* soft light blue tint */
    border-color: #2196f3; /* blue accent border */
    color: #0d47a1; /* text shifts to deep blue */
}


.menu-card img {
    width: 100%;
    height: 150px;
    border-radius: 8px;
    object-fit: cover;
}

.menu-card h3 {
    font-size: 18px;
    margin: 8px 0;
    color: #333;
}

.menu-card p {
    font-size: 14px;
    color: #555;
    margin: 4px 0;
}

.price {
    font-weight: bold;
    color: #333;
    display: block;
    margin-top: 6px;
}

.order-btn {
    display: inline-block;
    margin-top: 10px;
    padding: 8px 12px;
   background: linear-gradient(135deg, #ff6a00, #ff8c00);
    color: #fff;
    border: none;
    border-radius: 4px;
    font-weight: 500;
    cursor: pointer;
    transition: background 0.2s ease;
}

.order-btn:hover {
    background: black;
    
}

.no-data {
    text-align: center;
    color: #666;
    font-size: 18px;
    margin-top: 20px;
}
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


<!-- Navbar -->
	<div class="navbar">
		<div class="logo">🍴 QuickBite</div>
		<div class="nav-links">
			<a href="home"> Home</a> <a href="login.html"> Login</a> 
			<a href="cart.jsp"> 🛒 Cart  </a>
			<a	href="register.html">Signup</a> 
			    <button id="darkModeToggle" class="dark-toggle">🌙 Dark Mode</button>
			
		</div>
	</div>


<section class="menu-container">
<%
    if (allMenusByRestaurant != null && !allMenusByRestaurant.isEmpty()) {
        for (Menu menu : allMenusByRestaurant) {
%>
    <div class="menu-card">
        <img src="<%= menu.getImagePath() %>" >
        <h3><%= menu.getItemName() %></h3>
        <p><%= menu.getDescription() %></p>

        <span class="price">₹<%= String.format("%.2f", menu.getPrice()) %></span>
        
        <span><%= menu.getIsAvailable() ? "Available ✅" : "Unavailable ❌" %></span>
       
        <form action="callCartServlet" method="post">
        <input type="hidden" name="action" value="add">
        <input type="hidden" name="menuId" value="<%= menu.getMenuId() %>">
        <input type="hidden" name="restaurantId" value="<%= menu.getRestaurantId() %>">
        <input type="hidden" name="quantity" value="1">
        <button class="order-btn">Add to Cart</button>
    </form>
    </div>
<%
        }
    } else {
%>
    <h2 class="no-data">No Menu Items Available 😔</h2>
<%
    }
%>
</section>
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
