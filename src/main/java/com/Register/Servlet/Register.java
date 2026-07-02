package com.Register.Servlet;

import java.io.IOException;
import org.mindrot.jbcrypt.BCrypt;
import com.tap.DAOImpl.UserDAOImpl;
import com.tap.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/registerServlet") // Removed semicolon
public class Register extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("userName");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String address = req.getParameter("address");
        String role = req.getParameter("role");

        
        String hashpw = BCrypt.hashpw(password, BCrypt.gensalt(12));

        User user = new User(name, hashpw, email, address, role);

        UserDAOImpl userDaoImpl = new UserDAOImpl();
int res=userDaoImpl.addUser(user);

if(res==1) {
    System.out.println("✅ User registered successfully!");
    resp.sendRedirect("login.html");
} else {
    System.out.println("❌ User registration failed!");
    resp.sendRedirect("register.html");
}


    }
}
