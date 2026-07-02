package com.Restaurant.Servlet;

import java.io.IOException;
import java.util.List;
import com.tap.DAOImpl.RestaurantDAOImpl;
import com.tap.model.Restaurant;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/home")

public class RestaurantServlet  extends  HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RestaurantDAOImpl rdao= new RestaurantDAOImpl();
		
		
	    List<Restaurant> allRestaurant =rdao.getAllRestaurants();
	    
//	    for(Restaurant restaurant: allRestaurant) {
//	    	System.out.println(restaurant);
//	    	
//	    }
//	   
	    req.setAttribute("allRestaurants", allRestaurant);
	RequestDispatcher rd= req.getRequestDispatcher("restaurant.jsp");
	rd.forward(req, resp);
	    

	}

}
