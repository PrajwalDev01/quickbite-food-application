<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="com.tap.model.Cart" %>
<%@ page import="com.tap.model.CartItem" %>

<%
    Cart cart = (Cart) session.getAttribute("cart");
    Map<Integer, CartItem> cartItems = (cart != null) ? cart.getItems() : null;
    double subtotal = 0.0;
    if (cartItems != null) {
        for (CartItem item : cartItems.values()) {
            subtotal += item.getTotalPrice();
        }
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Checkout - QuickBite</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f9f9f9; margin: 0; padding: 0; }
        .navbar { background : linear-gradient(135deg, #ff6a00, #ff8c00); color: white; padding: 14px 40px; }
        .checkout-container { max-width: 900px; margin: 40px auto; background: #fff; padding: 30px; border-radius: 10px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); }
        h1 { margin-bottom: 20px; color: #333; }
        .section { margin-bottom: 30px; }
        .section h2 { font-size: 20px; margin-bottom: 15px; color: black; }
        input, select, textarea { width: 100%; padding: 10px; margin-bottom: 12px; border: 1px solid #ddd; border-radius: 6px; }
        .btn-place-order { background: linear-gradient(135deg, #ff6a00, #ff8c00); color: white; border: none; padding: 14px; border-radius: 6px; cursor: pointer; font-size: 16px; width: 100%; }
        .btn-place-order:hover { background: black; }
        .summary { margin-top: 20px; font-size: 16px; }
        .summary strong { color: black; }
    </style>
</head>
<body>

<div class="navbar">
    🍴 QuickBite - Checkout
</div>

<div class="checkout-container">
    <h1>Checkout</h1>

    <form action="OrderSuccess" method="post">
        <div class="section">
            <h2>Delivery Address</h2>
            <input type="text" name="fullname" placeholder="Full Name" required>
            <input type="text" name="address" placeholder="Street Address" required>
            <input type="text" name="city" placeholder="City" required>
            <input type="text" name="pincode" placeholder="Pincode" required>
            <input type="text" name="phone" placeholder="Phone Number" required>
        </div>

        <div class="section">
            <h2>Payment Method</h2>
            <select name="payment" required>
                <option value="">Select Payment Method</option>
                <option value="cod">Cash on Delivery</option>
                <option value="card">Credit/Debit Card</option>
                <option value="upi">UPI</option>
            </select>
        </div>

        <div class="section">
            <h2>Order Summary</h2>
            <div class="summary">
                <% if (cartItems != null) { 
                       for (CartItem item : cartItems.values()) { %>
                    <p><%= item.getName() %> x <%= item.getQuantity() %> — ₹<%= String.format("%.2f", item.getTotalPrice()) %></p>
                <% } } %>
                <p><strong>Total: ₹<%= String.format("%.2f", subtotal) %></strong></p>
            </div>
        </div>

        <button type="submit" class="btn-place-order">Place Order</button>
    </form>
</div>

</body>
</html>
