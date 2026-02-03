package project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/student_managment",
					"root",
					"karthikeyan"
					);
			
			String sql = "SELECT * FROM users WHERE username=? AND password=? ";
			PreparedStatement pst = con.prepareStatement(sql);
			
			pst.setString(1, username);
			pst.setString(2, password);
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				//Create sessioin
				HttpSession session = request.getSession(true);
				session.setAttribute("username", username);
				
				//Redirect to dashboard
				response.sendRedirect("dashboard");
			}else {
				RequestDispatcher rd = request.getRequestDispatcher("login.html");
				rd.include(request, response);
				out.println("<h3> style='color:red'>Invalid Credentials</h3>");
			}
			con.close();

			
		}catch(Exception e) {
			e.printStackTrace();
			out.println("Login error");
		}
	}

}
