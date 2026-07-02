<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tap.model.Order" %>

<%
    Order order = (Order) session.getAttribute("latestOrder");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Order Placed Successfully - QuickBite</title>
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Outfit', sans-serif;
            background: linear-gradient(135deg, #fbfbfd 0%, #f0f4f8 100%);
            margin: 0;
            padding: 0;
            color: #1d1d1f;
            min-height: 100vh;
        }
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
        .success-container {
            max-width: 600px;
            margin: 150px auto 60px auto;
            padding: 0 24px;
            text-align: center;
        }
        .success-card {
            background: white;
            border-radius: 24px;
            padding: 48px 40px;
            box-shadow: 0 15px 40px rgba(0, 0, 0, 0.04);
            border: 1px solid rgba(0, 0, 0, 0.05);
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .success-icon {
            width: 90px;
            height: 90px;
            background: #e6f9ed;
            color: #34c759;
            font-size: 44px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 50%;
            margin-bottom: 24px;
            box-shadow: 0 8px 24px rgba(52, 199, 89, 0.15);
        }
        h1 {
            font-size: 32px;
            font-weight: 700;
            color: #1d1d1f;
            margin: 0 0 12px 0;
            letter-spacing: -0.5px;
        }
        .subtitle {
            font-size: 16px;
            color: #86868b;
            margin: 0 0 36px 0;
            line-height: 1.5;
        }
        .details-box {
            width: 100%;
            background: #f5f5f7;
            border-radius: 16px;
            padding: 24px;
            margin-bottom: 36px;
            box-sizing: border-box;
            border: 1px solid rgba(0, 0, 0, 0.04);
        }
        .details-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 16px;
            font-size: 15px;
        }
        .details-row:last-child {
            margin-bottom: 0;
            padding-top: 16px;
            border-top: 1px solid rgba(0, 0, 0, 0.05);
        }
        .label {
            color: #86868b;
            font-weight: 500;
        }
        .value {
            color: #1d1d1f;
            font-weight: 600;
        }
        .value.highlight {
            color: black;
        }
        .btn-home {
            display: inline-block;
           background:linear-gradient(135deg, black, #3949ab);
            color: white;
            padding: 16px 36px;
            text-align: center;
            border-radius: 12px;
            text-decoration: none;
            font-weight: 600;
            font-size: 16px;
            box-shadow: 0 8px 24px rgba(233, 30, 99, 0.25);
            transition: all 0.3s ease;
        }
        .btn-home:hover {
            transform: translateY(-2px);
            box-shadow: 0 12px 32px rgba(233, 30, 99, 0.35);
            background: black;
        }
        .btn-home:active {
            transform: translateY(0);
        }
    </style>
</head>
<body>

<div class="navbar">
    <div class="logo">🍴 QuickBite</div>
    <div class="nav-links">
        <a href="home">Home</a>
        <a href="login.html">Login</a>
        <a href="register.html">Signup</a>
       
        <a href="cart.jsp">🛒 Cart</a>
    </div>
</div>

<div class="success-container">
    <div class="success-card">
        <div class="success-icon">✓</div>
        <h1>Order Placed!</h1>
        <p class="subtitle">Thank you for your order. We are preparing your food and it will arrive shortly.</p>

        <%
            if (order != null) {
        %>
        <div class="details-box">
            <div class="details-row">
                <span class="label">Order ID</span>
                <span class="value">#<%= order.getOrderId() %></span>
            </div>
            <div class="details-row">
                <span class="label">Payment Method</span>
                <span class="value" style="text-transform: uppercase;"><%= order.getPaymentMode() %></span>
            </div>
            <div class="details-row">
                <span class="label">Status</span>
                <span class="value" style="color: #ff9500;"><%= order.getStatus() %></span>
            </div>
            <div class="details-row">
                <span class="label">Total Paid</span>
                <span class="value highlight">₹<%= String.format("%.2f", order.getTotalAmount()) %></span>
            </div>
        </div>
        <%
            }
        %>

        <a href="home" class="btn-home">Explore More Restaurants</a>
    </div>
</div>

</body>
</html>
