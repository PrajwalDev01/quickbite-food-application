package com.Ordersuccess.Servlet;

import java.io.IOException;
import java.sql.Timestamp;

import com.tap.DAOImpl.OrderDAOImpl;
import com.tap.DAOImpl.OrderTableDAOImpl;
import com.tap.model.Cart;
import com.tap.model.CartItem;
import com.tap.model.Order;
import com.tap.model.OrderItem;
import com.tap.model.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/OrderSuccess")
public class OrderSuccess extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		
		if (user == null) {
			RequestDispatcher rd = req.getRequestDispatcher("login.html");
			rd.forward(req, resp);
			return;
		}
		
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null || cart.getItems() == null || cart.getItems().isEmpty()) {
			resp.sendRedirect("home");
			return;
		}

		Integer restaurantIdObj = (Integer) session.getAttribute("restaurantId");
		int restaurantId = (restaurantIdObj != null) ? restaurantIdObj : 0;
		String paymentMode = req.getParameter("payment");

		double totalAmount = 0.0;
		for (CartItem item : cart.getItems().values()) {
			totalAmount += item.getTotalPrice();
		}

		Order order = new Order();
		order.setUserId(user.getUserid());
		order.setRestaurantId(restaurantId);
		order.setOrderDate(new Timestamp(System.currentTimeMillis()));
		order.setPaymentMode(paymentMode != null ? paymentMode : "cod");
		order.setStatus("pending");
		order.setTotalAmount(totalAmount);

		OrderTableDAOImpl orderTableDAO = new OrderTableDAOImpl();
		orderTableDAO.addOrder(order);

		if (order.getOrderId() > 0) {
			OrderDAOImpl orderItemDAO = new OrderDAOImpl();
			for (CartItem item : cart.getItems().values()) {
				OrderItem orderItem = new OrderItem();
				orderItem.setOrderId(order.getOrderId());
				orderItem.setMenuId(item.getMenuId());
				orderItem.setQuantity(item.getQuantity());
				orderItem.setItemTotal(item.getTotalPrice());
				orderItemDAO.addOrderItem(orderItem);
			}
		}

		session.setAttribute("latestOrder", order);
		session.removeAttribute("cart");
		session.removeAttribute("restaurantId");

		RequestDispatcher rd = req.getRequestDispatcher("ordersuccess.jsp");
		rd.forward(req, resp);
	}
}
