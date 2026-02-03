package project;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/signup")
@MultipartConfig
public class SignupServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        String fileName = "";

        Part filePart = request.getPart("photo");
        if (filePart != null && filePart.getSize() > 0) {

            fileName = filePart.getSubmittedFileName();
            String uploadPath = getServletContext().getRealPath("") + "uploads";

            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            filePart.write(uploadPath + File.separator + fileName);
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/student_managment",
                    "root",
                    "karthikeyan");

            PreparedStatement pst = con.prepareStatement(
                "INSERT INTO users(username,password,role,photo) VALUES(?,?,?,?)");

            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, role);
            pst.setString(4, fileName);

            pst.executeUpdate();

            Cookie c = new Cookie("lastRegistered", username);
            response.addCookie(c);

            con.close();

            response.sendRedirect("signupsuccess.html");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Signup Failed");
        }
    }
}
