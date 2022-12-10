/*Nagashree A20514959
 * Vinutha A20504262
 * manager has ability  to check and approve leave applied by employee hence
 * manager given different view which is exactly shown in this model
 * 
 *  */


package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ManagerModel extends DBConnect {
	
	DBConnect dbConnect = null;
	String emp_id,reports_to,fromdate,todate,type,comments,nod,fname,tid;
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getNod() {
		return nod;
	}
	public void setNod(String nod) {
		this.nod = nod;
	}
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	public String getReports_to() {
		return reports_to;
	}
	public void setReports_to(String reports_to) {
		this.reports_to = reports_to;
	}
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
	
	
	
	public ObservableList<ManagerModel> getemployeeleaves(String query){
		ObservableList<ManagerModel> leavelist = FXCollections.observableArrayList();
		try(PreparedStatement ps = conn.prepareStatement(query))
		{
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
            	ManagerModel man=new ManagerModel();
            	man.setEmp_id(rs.getString("emp_id"));
            	man.setNod(rs.getString("nod"));
            	man.setFromdate(rs.getString("fromdate"));
            	man.setTodate(rs.getString("todate"));
            	man.setFname(rs.getString("fname"));
            	if(rs.getString("type").equalsIgnoreCase("1"))
            	{
            		man.setType("Annual");
            	}
            	else if(rs.getString("type").equalsIgnoreCase("2"))
            	{
            		man.setType("Casual");
            	}
            	else if(rs.getString("type").equalsIgnoreCase("3"))
            	{
            		man.setType("Sick");
            	}
            	else {
            		man.setType("Other");
            	}
            	man.setComments(rs.getString("comments"));
            	man.setTid(rs.getString("tid"));
            	leavelist.add(man);
            }
		}
        catch(Exception e) {
        	e.printStackTrace();
        	System.out.println("Error Displaying user details ");
        }
		return leavelist;
	}
	
}
