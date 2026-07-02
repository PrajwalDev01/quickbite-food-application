package com.Cart.Servlet;

import java.io.IOException;

import com.tap.DAOImpl.MenuDAOImpl;
import com.tap.model.Cart;
import com.tap.model.CartItem;
import com.tap.model.Menu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/callCartServlet")
public class CartServlet extends HttpServlet{
	

@Override
protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	HttpSession session=req.getSession();
	
	 Cart cart=(Cart)session.getAttribute("cart");
	
	 
	Integer  restaurantId= (Integer)session.getAttribute("restaurantId");
	
	String restaurantIdParam = req.getParameter("restaurantId");
	int newrestaurantId = 0;
	if (restaurantIdParam != null && !restaurantIdParam.isEmpty()) {
		newrestaurantId = Integer.parseInt(restaurantIdParam);
	} else if (restaurantId != null) {
		newrestaurantId = restaurantId;
	}

	 if(cart==null|| restaurantId == null || restaurantId != newrestaurantId) 
	 {
		 cart=new Cart();
		session.setAttribute("cart", cart);
		session.setAttribute("restaurantId", newrestaurantId);
		
	}
	
	String action =	req.getParameter("action");

	if(action != null) {
		if(action.equals("add")) {
			addItemToCart(req,cart);
		}
		else if (action.equals("update")) {
			updateItemToCart(req,cart);
		}
		else if (action.equals("delect")) {
			removeItemToCart(req,cart);
		}
	}
		
	resp.sendRedirect("cart.jsp");
}
private void addItemToCart(HttpServletRequest req, Cart cart) {
int menuId=Integer.parseInt(req.getParameter("menuId"))	;
int quantity=Integer.parseInt(req.getParameter("quantity"))	;	

MenuDAOImpl menuDAOImpl=new MenuDAOImpl();
Menu menu =menuDAOImpl.getMenu(menuId);
           HttpSession session= req.getSession();
           session.setAttribute("restaurantId", menu.getRestaurantId());

if(menu !=null) {
  CartItem cartItem =	new CartItem(menuId,
		  menu.getRestaurantId(),
		  menu.getItemName(),
		  menu.getPrice(),
		  quantity);
  
  cart.addItem(cartItem);
}



}
private void updateItemToCart(HttpServletRequest req, Cart cart) {
int menuId=Integer.parseInt(req.getParameter("menuId"));
int quantity= Integer.parseInt(req.getParameter("quantity"));
	cart.updateItem(menuId,quantity);
}


private void removeItemToCart(HttpServletRequest req, Cart cart) {
	int menuId=Integer.parseInt(req.getParameter("menuId"));
	cart.removeItem(menuId);
	
}






}
