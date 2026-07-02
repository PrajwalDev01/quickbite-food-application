package com.Login.Servlet;

import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;

import com.tap.DAOImpl.UserDAOImpl;
import com.tap.model.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	String userName = req.getParameter("userName");
    String password = req.getParameter("password");
    
    HttpSession session=req.getSession();

    
    UserDAOImpl userDaoImpl = new UserDAOImpl();
   User user= userDaoImpl.getUserByUserName(userName);
 String dbPassword=user.getPassword();
   
   if(BCrypt.checkpw(password, dbPassword)) {
	   session.setAttribute("user", user);
	   resp.sendRedirect("home");
   }else {
	   RequestDispatcher rd=req.getRequestDispatcher("login");
	   rd.forward(req, resp);
	  
   }
   }
}
 