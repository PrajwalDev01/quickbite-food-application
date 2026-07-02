package com.OrderHistory.Servlet;

import java.io.IOException;
import java.util.List;

import com.tap.DAOImpl.OrderTableDAOImpl;
import com.tap.model.Order;
import com.tap.model.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/orderHistory")
public class OrderHistoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        
        // 1. Check if the user is logged in
        if (user == null) {
            resp.sendRedirect("login.html");
            return;
        }
        
        // 2. Fetch order history using OrderTableDAOImpl
        OrderTableDAOImpl orderDAO = new OrderTableDAOImpl();
        List<Order> orderList = orderDAO.getOrdersByUser(user.getUserid());
        
        // --- SIMULATED TRANSITION LOGIC ---
        long currentTime = System.currentTimeMillis();
        long twoMinutes = 2 * 60 * 1000; // 2 minutes in milliseconds
        
        for (Order order : orderList) {
            // Check if the order is still "pending"
            if ("pending".equalsIgnoreCase(order.getStatus())) {
                long orderTime = order.getOrderDate().getTime();
                
                // If more than 2 minutes have passed since the order was placed
                if (currentTime - orderTime > twoMinutes) {
                    order.setStatus("Delivered");
                    orderDAO.updateOrder(order); // Save the updated status to the database
                }
            }
        }
        // -----------------------------------
        
        // 3. Set the orders list as request attribute and forward to JSP
        req.setAttribute("orderList", orderList);
        RequestDispatcher rd = req.getRequestDispatcher("orderHistory.jsp");
        rd.forward(req, resp);
    }
}
