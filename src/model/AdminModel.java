/*Nagashree A20514959
 * Vinutha A20504262
 *  */



package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdminModel extends DBConnect {
	
	DBConnect dbConnect = null;
	String emp_id;
	String fname,lname,email,phone,department,designation,dob,reports_to,role;
	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getReports_to() {
		return reports_to;
	}

	public void setReports_to(String reportsto) {
		this.reports_to = reportsto;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	
	public String getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public ObservableList<AdminModel> getdataofusers(String query){
		ObservableList<AdminModel> userlist = FXCollections.observableArrayList();
		try(PreparedStatement ps = conn.prepareStatement(query))
		{
            ResultSet rs = ps.executeQuery();
            
            while (rs.next())
            {
            	AdminModel adm=new AdminModel();
            	adm.setEmp_id(rs.getString("emp_id"));
            	adm.setFname(rs.getString("fname"));
            	adm.setLname(rs.getString("lname"));
            	adm.setEmail(rs.getString("email"));
            	adm.setPhone(rs.getString("phone"));
            	adm.setDepartment(rs.getString("department"));
            	adm.setDesignation(rs.getString("designation"));
            	adm.setDob(rs.getString("dob"));
            	if(rs.getString("role").equalsIgnoreCase("1"))
            	{
            		adm.setRole("Manager");
            	}
            	else if(rs.getString("role").equalsIgnoreCase("0")) {
            		adm.setRole("Employee");
            	}
            	else {
            		adm.setRole("Admin");
            	}
            	//adm.setRole(rs.getString("role"));
            	adm.setReports_to(rs.getString("reports_to"));;
            	
            	userlist.add(adm);
            }
		}
		catch(SQLException e) {
			System.out.println("Error Displaying user details ");
		}
		return userlist; 
	}
}
