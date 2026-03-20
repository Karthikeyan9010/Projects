package com.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
	
	public static Connection getConnection() throws Exception{
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/login_db",
				"root",
				"karthikeyan");
	}
	
	public static int register(User u) {
		int status = 0;
		try {
			
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("insert into users(name,email,password) values(?,?,?)");
			
			ps.setString(1, u.getname());
			ps.setString(2, u.getemail());
			ps.setString(3, u.getpassword());
			
			status = ps.executeUpdate();
			
			con.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return status;
		
	}
		
		public static boolean login(String email,String password) {
			boolean status = false;
			
			try {
				
				Connection con = getConnection();
				
				PreparedStatement ps = con.prepareStatement("select * from users where email=? and password=?");
				
				ps.setString(1, email);
				ps.setString(2, password);
				
				ResultSet rs = ps.executeQuery();
				
				status = rs.next();
				con.close();
			
			
			}catch(Exception e) {
				e.printStackTrace();
			}
			return status;
		}
		
		
		
	}


