<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.tap.model.Order" %>
<%
    List<Order> orderList = (List<Order>) request.getAttribute("orderList");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>QuickBite - Order History</title>
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Outfit', sans-serif;
            background: linear-gradient(135deg, #fbfbfd 0%, #f0f4f8 100%);
            margin: 0; padding: 0; color: #1d1d1f; min-height: 100vh;
        }
        .navbar {
            background : linear-gradient(135deg, #ff6a00, #ff8c00);
            color: white; display: flex; justify-content: space-between;
            align-items: center; padding: 14px 40px; position: fixed;
            top: 0; left: 0; right: 0; z-index: 1000;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        .logo { font-size: 22px; font-weight: bold; }
        .nav-links a {
            color: white; text-decoration: none; margin-left: 25px;
            font-weight: 500; font-size: 16px; transition: 0.3s ease;
        }
        .nav-links a:hover { color: #ffe0b3; }
        .container {
            max-width: 1000px; margin: 120px auto 60px auto; padding: 0 24px;
        }
        h1 { font-size: 32px; font-weight: 700; margin-bottom: 28px; }
        
        .history-table {
            width: 100%; border-collapse: collapse; background: white;
            border-radius: 16px; overflow: hidden;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.04);
            border: 1px solid rgba(0, 0, 0, 0.05);
        }
        .history-table th, .history-table td {
            padding: 18px 24px; text-align: left;
        }
        .history-table th {
            background: #f8fafd; font-weight: 600; font-size: 13px;
            text-transform: uppercase; color: #86868b;
            border-bottom: 1px solid rgba(0, 0, 0, 0.05);
        }
        .history-table td {
            border-bottom: 1px solid rgba(0, 0, 0, 0.04);
            font-size: 15px; color: #1d1d1f;
        }
        .status-badge {
            padding: 6px 12px; border-radius: 20px; font-weight: 600; font-size: 12px;
            text-transform: capitalize; display: inline-block;
        }
        .status-pending { background: #fff3cd; color: #856404; }
        .status-completed { background: #d4edda; color: #155724; }
        .empty-history {
            text-align: center; padding: 60px; background: white;
            border-radius: 16px; box-shadow: 0 10px 30px rgba(0, 0, 0, 0.04);
        }
    </style>
</head>
<body>

    <div class="navbar">
        <div class="logo">🍴 QuickBite</div>
        <div class="nav-links">
            <a href="home">Home</a>
            <a href="orderHistory">📜 Order History</a>
            <a href="cart.jsp">🛒 Cart</a>
        </div>
    </div>

    <div class="container">
        <h1>Your Order History</h1>
        
        <% if (orderList == null || orderList.isEmpty()) { %>
            <div class="empty-history">
                <h2>No Orders Found 😔</h2>
                <p>You haven't placed any orders yet. Go to <a href="home">Home</a> to order delicious food!</p>
            </div>
        <% } else { %>
            <table class="history-table">
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Date</th>
                        <th>Restaurant ID</th>
                        <th>Total Amount</th>
                        <th>Status</th>
                        <th>Payment Mode</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Order order : orderList) { %>
                        <tr>
                            <td>#<%= order.getOrderId() %></td>
                            <td><%= order.getOrderDate() %></td>
                            <td><%= order.getRestaurantId() %></td>
                            <td>₹<%= String.format("%.2f", order.getTotalAmount()) %></td>
                            <td>
                                <span class="status-badge <%= "pending".equalsIgnoreCase(order.getStatus()) ? "status-pending" : "status-completed" %>">
                                    <%= order.getStatus() %>
                                </span>
                            </td>
                            <td><%= order.getPaymentMode().toUpperCase() %></td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        <% } %>
    </div>
</body>
</html>
