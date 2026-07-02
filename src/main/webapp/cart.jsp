<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="com.tap.model.Cart" %>
<%@ page import="com.tap.model.CartItem" %>

<%
    Cart cart = (Cart) session.getAttribute("cart");
    Map<Integer, CartItem> cartItems = null;
    double subtotal = 0.0;
    int totalItems = 0;

    if (cart != null && cart.getItems() != null) {
        cartItems = cart.getItems();
        for (CartItem item : cartItems.values()) {
            subtotal += item.getTotalPrice();
            totalItems += item.getQuantity();
        }
    }

    Object restaurantId = session.getAttribute("restaurantId");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Your Cart - QuickBite</title>
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
        
        .cart-container {
            max-width: 1100px;
            margin: 120px auto 60px auto;
            padding: 0 24px;
        }
        h1 {
            font-size: 32px;
            font-weight: 700;
            margin-bottom: 28px;
            color: #1d1d1f;
            letter-spacing: -0.5px;
        }
        .cart-layout {
            display: grid;
            grid-template-columns: 2.2fr 1fr;
            gap: 32px;
            align-items: start;
        }
        .cart-table {
            background: white;
            border-radius: 16px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.04);
            border: 1px solid rgba(0, 0, 0, 0.05);
            overflow: hidden;
        }
        .cart-header-row {
            display: grid;
            grid-template-columns: 2.2fr 1fr 1.2fr 1fr;
            background: #f8fafd;
            padding: 18px 24px;
            font-weight: 600;
            font-size: 13px;
            text-transform: uppercase;
            letter-spacing: 1px;
            color: #86868b;
            border-bottom: 1px solid rgba(0, 0, 0, 0.05);
        }
        .cart-item-row {
            display: grid;
            grid-template-columns: 2.2fr 1fr 1.2fr 1fr;
            padding: 24px;
            align-items: center;
            border-bottom: 1px solid rgba(0, 0, 0, 0.04);
            transition: all 0.3s ease;
        }
        .cart-item-row:last-child {
            border-bottom: none;
        }
        .cart-item-row:hover {
            background: #fafbfc;
        }
        .item-info {
            display: flex;
            flex-direction: column;
            gap: 6px;
        }
        .item-name {
            font-weight: 600;
            font-size: 18px;
            color: #1d1d1f;
        }
        .item-details {
            font-size: 13px;
            color: #86868b;
        }
        .item-price {
            font-size: 16px;
            color: #1d1d1f;
            font-weight: 500;
        }
        .item-subtotal {
            font-size: 18px;
            color: black;
            font-weight: 600;
        }
        .qty-wrapper {
            display: flex;
            flex-direction: column;
            gap: 12px;
            align-items: flex-start;
        }
        .qty-control {
            display: flex;
            align-items: center;
            background: #f5f5f7;
            padding: 4px;
            border-radius: 8px;
            border: 1px solid rgba(0,0,0,0.05);
        }
        .btn-qty {
            background: white;
            border: none;
            color: #1d1d1f;
            width: 28px;
            height: 28px;
            font-size: 16px;
            cursor: pointer;
            border-radius: 6px;
            font-weight: 600;
            display: flex;
            align-items: center;
            justify-content: center;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            transition: all 0.2s ease;
        }
        .btn-qty:hover {
            background: #e91e63;
            color: white;
            box-shadow: 0 4px 10px rgba(233, 30, 99, 0.2);
            transform: translateY(-1px);
        }
        .btn-qty:active {
            transform: translateY(0);
        }
        .qty-val {
            margin: 0 14px;
            font-size: 15px;
            font-weight: 600;
            min-width: 16px;
            text-align: center;
            color: #1d1d1f;
        }
        .btn-remove {
            background: transparent;
            color: #86868b;
            border: none;
            padding: 4px 8px;
            cursor: pointer;
            font-weight: 500;
            font-size: 13px;
            transition: all 0.2s ease;
            text-decoration: underline;
        }
        
.btn-remove {
  background: linear-gradient(135deg, #ff6a00, #ff8c00);/* theme color */
    color: #fff;                 /* white text */
    border: none;
    padding: 6px 12px;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
    text-decoration: none;       /* removes underline */
    display: inline-block;       /* ensures proper spacing */
}

.btn-remove:hover {
    background: black;   /* darker shade on hover */
}


        .summary-card {
            background: white;
            border-radius: 16px;
            padding: 28px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.04);
            border: 1px solid rgba(0, 0, 0, 0.05);
        }
        .summary-title {
            font-size: 20px;
            font-weight: 700;
            margin-bottom: 20px;
            color: #1d1d1f;
            border-bottom: 1px solid rgba(0,0,0,0.05);
            padding-bottom: 12px;
        }
        .summary-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 14px;
            font-size: 15px;
            color: #86868b;
        }
        .summary-total {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
            padding-top: 16px;
            border-top: 1px solid rgba(0,0,0,0.05);
            font-size: 22px;
            font-weight: 700;
            color: #1d1d1f;
        }
        .total-price {
            color: black;
        }
        .btn-checkout {
            display: block;
background: linear-gradient(135deg, #ff6a00, #ff8c00);
            color: white;
            padding: 16px;
            text-align: center;
            border-radius: 10px;
            text-decoration: none;
            margin-top: 24px;
            font-weight: 600;
            font-size: 16px;
            box-shadow: 0 8px 20px rgba(233, 30, 99, 0.25);
            transition: all 0.3s ease;
        }
        .btn-checkout:hover {
            transform: translateY(-2px);
            box-shadow: 0 12px 28px rgba(233, 30, 99, 0.35);
            background: black;
        }
        .btn-checkout:active {
            transform: translateY(0);
        }
        .btn-continue {
            display: block;
            text-align: center;
            margin-top: 16px;
            color: #86868b;
            text-decoration: none;
            font-weight: 500;
            font-size: 14px;
            transition: all 0.2s ease;
        }
        .btn-continue:hover {
            color: red;
        }
        .empty-cart {
            text-align: center;
            margin: 180px auto 60px auto;
            background: white;
            padding: 56px 40px;
            border-radius: 20px;
            max-width: 480px;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.04);
            border: 1px solid rgba(0, 0, 0, 0.05);
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 20px;
        }
        .empty-icon {
            font-size: 64px;
            margin-bottom: 8px;
            background: #fff0f5;
            width: 120px;
            height: 120px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 50%;
            color: #e91e63;
        }
        .empty-cart h2 {
            font-size: 26px;
            font-weight: 700;
            color: #1d1d1f;
            margin: 0;
        }
        .empty-cart p {
            color: #86868b;
            font-size: 15px;
            margin: 0 0 12px 0;
            line-height: 1.5;
        }
        .btn-explore {
            display: inline-block;
            background: linear-gradient(135deg, #ff4081 0%, #e91e63 100%);
            color: white;
            padding: 14px 28px;
            text-align: center;
            border-radius: 10px;
            text-decoration: none;
            font-weight: 600;
            font-size: 16px;
            box-shadow: 0 6px 16px rgba(233, 30, 99, 0.2);
            transition: all 0.3s ease;
        }
        .btn-explore:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 24px rgba(233, 30, 99, 0.3);
        }
        .btn-explore:active {
            transform: translateY(0);
        }
    </style>
</head>
<body>



<%
    if (cartItems == null || cartItems.isEmpty()) {
%>
    <div class="empty-cart">
        <div class="empty-icon">🛒</div>
        <h2>Your Cart is Empty</h2>
        <p>Looks like you haven't added anything to your cart yet. Go back and explore our delicious restaurants!</p>
        <a href="home" class="btn-explore">Explore Restaurants</a>
    </div>
<%
    } else {
%>
    <div class="cart-container">
        <h1>Shopping Cart (<%= totalItems %> <%= totalItems == 1 ? "item" : "items" %>)</h1>
        <div class="cart-layout">
            <div class="cart-table">
                <div class="cart-header-row">
                    <div>Product</div>
                    <div>Price</div>
                    <div>Quantity</div>
                    <div>Total</div>
                </div>
<%
        for (CartItem item : cartItems.values()) {
%>
                <div class="cart-item-row">
                    <div class="item-info">
                        <div class="item-name"><%= item.getName() %></div>
                        <div class="item-details">Restaurant ID: <%= item.getRestaurantId() %></div>
                    </div>
                    <div class="item-price">₹<%= String.format("%.2f", item.getPrice()) %></div>
                    <div class="qty-wrapper">
                        <div class="qty-control">
                            <form action="callCartServlet" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="restaurantId" value="<%= item.getRestaurantId() %>">
                                <input type="hidden" name="menuId" value="<%= item.getMenuId() %>">
                                <input type="hidden" name="quantity" value="<%= item.getQuantity() - 1 %>">
                                <button type="submit" class="btn-qty">-</button>
                            </form>
                            <span class="qty-val"><%= item.getQuantity() %></span>
                            <form action="callCartServlet" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="restaurantId" value="<%= item.getRestaurantId() %>">
                                <input type="hidden" name="menuId" value="<%= item.getMenuId() %>">
                                <input type="hidden" name="quantity" value="<%= item.getQuantity() + 1 %>">
                                <button type="submit" class="btn-qty">+</button>
                            </form>
                        </div>
                        <form action="callCartServlet" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="delect">
                            <input type="hidden" name="restaurantId" value="<%= item.getRestaurantId() %>">
                            <input type="hidden" name="menuId" value="<%= item.getMenuId() %>">
                            <button type="submit" class="btn-remove">Remove</button>
                        </form>
                    </div>
                    <div class="item-subtotal">₹<%= String.format("%.2f", item.getTotalPrice()) %></div>
                </div>
<%
        }
%>
            </div>
            
            <div class="summary-card">
                <div class="summary-title">Order Summary</div>
                <div class="summary-row">
                    <span>Subtotal</span>
                    <span>₹<%= String.format("%.2f", subtotal) %></span>
                </div>
                <div class="summary-row">
                    <span>Delivery Fee</span>
                    <span style="color: #34c759; font-weight: 600;">FREE</span>
                </div>
                <div class="summary-total">
                    <span>Total</span>
                    <span class="total-price">₹<%= String.format("%.2f", subtotal) %></span>
                </div>
                <a href="checkout.jsp" class="btn-checkout">Proceed to Checkout</a>
               <a href="menu?restaurantId=<%= restaurantId %>" class="btn-continue">← Continue Shopping</a>

            </div>
        </div>
    </div>
<%
    }
%>

</body>
</html>
