package com.login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistertionServlet
 */
@WebServlet("/RegistertionServlet")
public class RegistertionServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User u = new User();
		
		u.setname(request.getParameter("name"));
		u.setemail(request.getParameter("email"));
		u.setpassword(request.getParameter("password"));
		
		UserDAO.register(u);
		response.sendRedirect("login.jsp");
		
	}

}
