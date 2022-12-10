/*Nagashree A20514959
 * Vinutha A20504262
 *  */


package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	protected Connection conn;
	public Connection getconnection() 
	{ 
		return conn; 
	}
public static String url = "jdbc:mysql://papademas.net:3307/510fp?autoReconnect=true&useSSL=false";
public static String user = "fp510";
public static String pass = "510";

	public DBConnect() {

		try {
			conn = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			System.out.println("Error creating connection to database: " + e);
			System.exit(-1);
		}
	}
}
