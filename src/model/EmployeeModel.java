/*Nagashree A20514959
 * Vinutha A20504262
 *  */




package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EmployeeModel extends DBConnect {
	
	 
	    private String emp_id;
		private String fname,lname,email,phone,department,designation,dob,reports_to,role;
		private String fromdate,todate,type,comments,nod,tid;
		public String getFromdate() {
			return fromdate;
		}
		public void setFromdate(String fromdate) {
			this.fromdate = fromdate;
		}
		public String getTodate() {
			return todate;
		}
		public void setTodate(String todate) {
			this.todate = todate;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getComments() {
			return comments;
		}
		public void setComments(String comments) {
			this.comments = comments;
		}
		public String getNod() {
			return nod;
		}
		public void setNod(String nod) {
			this.nod = nod;
		}
		public String getTid() {
			return tid;
		}
		public void setTid(String tid) {
			this.tid = tid;
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
		public String getDepartment() {
			return department;
		}
		public void setDepartment(String department) {
			this.department = department;
		}
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
		public void setReports_to(String reports_to) {
			this.reports_to = reports_to;
		}
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}

	  public EmployeeModel getDetails(String sql) {
	  try(PreparedStatement ps = conn.prepareStatement(sql))
		{
			
          ResultSet rs = ps.executeQuery();
      	EmployeeModel empmodel=new EmployeeModel();
          
          while (rs.next())
          {
          	empmodel.setEmp_id(rs.getString("emp_id"));
          	empmodel.setFname(rs.getString("fname"));
          	empmodel.setLname(rs.getString("lname"));
          	empmodel.setEmail(rs.getString("email"));
          	empmodel.setDepartment(rs.getString("department"));
          	empmodel.setDob(rs.getString("dob"));
          	empmodel.setReports_to(rs.getString("reports_to"));
          	empmodel.setDesignation(rs.getString("designation"));
          	empmodel.setPhone(rs.getString("phone"));
        

          }
      	return empmodel;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	  }
	  public ObservableList<EmployeeModel> getemployeeleaves(String query){
			ObservableList<EmployeeModel> leavelist = FXCollections.observableArrayList();
			
			try(PreparedStatement ps = conn.prepareStatement(query))
			{
	            ResultSet rs = ps.executeQuery();
	            
	            while (rs.next())
	            {
	            	EmployeeModel emp=new EmployeeModel();
	            	emp.setEmp_id(rs.getString("emp_id"));
	            	emp.setNod(rs.getString("nod"));
	            	emp.setFromdate(rs.getString("fromdate"));
	            	emp.setTodate(rs.getString("todate"));
	            	if(rs.getString("type").equalsIgnoreCase("1"))
	            	{
	            		emp.setType("Annual");
	            	}
	            	else if(rs.getString("type").equalsIgnoreCase("2"))
	            	{
	            		emp.setType("Casual");
	            	}
	            	else if(rs.getString("type").equalsIgnoreCase("3"))
	            	{
	            		emp.setType("Sick");
	            	}
	            	else {
	            		emp.setType("Other");
	            	}
	            	emp.setComments(rs.getString("comments"));
	            	emp.setTid(rs.getString("tid"));
	            	leavelist.add(emp);
	            }
			}
	        catch(Exception e) {
	        	e.printStackTrace();
	        	System.out.println("Error Displaying user details ");
	        }
			return leavelist;
		}
}
