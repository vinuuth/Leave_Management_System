/*Nagashree A20514959
 * Vinutha A20504262
 *  */



package model;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import controller.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;


/** Login Model to authenticate user and get roles */
public class LoginModel extends DBConnect {

	private String username;
	private String password;
	public int role;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public boolean authenticate(String user,String pass) {
		
		String query = "SELECT * FROM elm_employees WHERE emp_id = ? and password = ?";
		try(PreparedStatement stmt = conn.prepareStatement(query)) 
		{
			
			stmt.setString(1, user);
			stmt.setString(2, pass);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
			{ 
				setUsername(rs.getString("email"));
				setPassword(rs.getString("password"));
				setRole(rs.getInt("role"));
				System.out.println("records fetced");
			
				return true;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();   
		}
		return false;
		
		
	}
	
	

	
}
