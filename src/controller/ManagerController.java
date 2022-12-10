/*Nagashree A20514959
 * Vinutha A20504262
 * Manager has the power to check and approve leave requests made by employees, and action items are necessary 
 * * to update buttons of manager view is elaborated in the code.
 *  */



package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;

import model.AdminModel;
import model.DBConnect;
import model.EmployeeModel;
import model.LeaveModel;
import model.ManagerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ManagerController {

	 ManagerModel managermodel;
	 public ManagerController() 
		{ 
			  managermodel = new ManagerModel(); 
		}
	//Initializing variables for fetching the username and password
	public String sUsername;
	public String sPassword;
	
	//Initializing the variables in private to fetch data from the database
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	ObservableList<String> lstUserType = FXCollections.observableArrayList("Annual", "Casual", "Sick", "Maternity", "Paternity");
	ObservableList<String> lstactions = FXCollections.observableArrayList("Approve","Deny");
	

	//Using annotations to connect the controller to the FXML to get the front end view of the page
    @FXML
    private JFXTextArea reasonbox;
    
    @FXML
    private Button applyleavebutton;

    @FXML
    private DatePicker leavefrom;
    
    @FXML
    private ComboBox<String> combobox;
    
    @FXML
    private JFXComboBox<String> ATblComboBoxAction;
    
    @FXML
    private TableView<ManagerModel> approvetable;
    
    @FXML
    private TableColumn<ManagerModel, String> ATblDateFrom;

    @FXML
    private TableColumn<ManagerModel, String> ATblDateTo;

    @FXML
    private TableColumn<ManagerModel, String> ATblEmployeeID;

    @FXML
    private TableColumn<ManagerModel, String> ATblLeaveType;

    @FXML
    private TableColumn<ManagerModel, String> ATblNoOfDays;

    @FXML
    private TableColumn<ManagerModel, String> ATblReason;
    
    @FXML
    private TableColumn<ManagerModel, String> ATblfname;
    
    @FXML
    private JFXTextArea employeeidfetch;
    
    @FXML
    private Label departmentlabel;

    @FXML
    private Label designationlabel;

    @FXML
    private Label doblabel;

    @FXML
    private Label emailidlabel;

    @FXML
    private Label empidlabel;

    @FXML
    private BarChart<String, Integer> leavechart;

    @FXML
    private Tab leavehistory;

    @FXML
    private DatePicker leaveto;

    @FXML
    private TableView<LeaveModel> lhtable;

    @FXML
    private Label namelabel;

    @FXML
    private Label phonelabel;


    @FXML
    private Label reportstolabel;

    @FXML
    private TableColumn<LeaveModel , String> tabcoltype;

    @FXML
    private TableColumn<LeaveModel, String> tabcolfrom;

    @FXML
    private TableColumn<LeaveModel , String> tabcolto;

    @FXML
    private TableColumn<LeaveModel , String> tabcolnod;

    @FXML
    private Tab userdetails;

    @FXML
    private Button logoutbutton;
    
    @FXML
    private TableColumn<ManagerModel, String> MEditTableLeaveComments;

    @FXML
    private TableColumn<ManagerModel, String> MEditTableLeaveFrom;

    @FXML
    private TableColumn<ManagerModel, String> MEditTableLeaveTo;

    @FXML
    private TableColumn<ManagerModel, String> MEditTableLeaveType;

    @FXML
    private TableColumn<ManagerModel, String> MEditTableNod;
    
    @FXML
    private TableColumn<ManagerModel, String> tid;
    @FXML
    private TableColumn<ManagerModel, String> approvetid;
    
    @FXML
    private TableView<ManagerModel> leavemodifytable;
    
    //connecting to the database and fetching the details from the backend and updating the changes in the database
	DBConnect dbConnect = null;
	Statement Statement = null;
	ObservableList<ManagerModel> leavelist;
	int index = -1;
	String emp_id,sql;
	PreparedStatement pst = null;	
	ObservableList<LeaveModel> leaveslistlea;
    LeaveModel leavemodel =new LeaveModel();
    
    
    //Initializing the fxml view -- login view to enter username and password
    @FXML
	private void initialize()
	{
		combobox.setItems(lstUserType);
		dbConnect = new DBConnect();
		ATblComboBoxAction.setItems(lstactions);
		
	}
    
    
    //Loading the table from backend to the fxml view
    @FXML
    private void loadtable() {
    	dbConnect = new DBConnect();
    	String query = "SELECT emptbl.emp_id, emptbl.fname, lrtbl.fromdate, lrtbl.todate, lrtbl.type, lrtbl.approve, lrtbl.comments,lrtbl.nod,lrtbl.tid FROM elm_employees emptbl, elm_leaverecords lrtbl WHERE (emptbl.emp_ID = lrtbl.emp_ID AND emptbl.reports_to ='"+sUsername+"') AND lrtbl.approve is null;";
				
		leavelist = managermodel.getemployeeleaves(query);

		
		ATblEmployeeID.setCellValueFactory(new PropertyValueFactory<ManagerModel,String>("emp_id"));
		ATblfname.setCellValueFactory(new PropertyValueFactory<ManagerModel,String>("fname"));
		ATblLeaveType.setCellValueFactory(new PropertyValueFactory<ManagerModel,String>("type"));
		ATblDateFrom.setCellValueFactory(new PropertyValueFactory<ManagerModel,String>("fromdate"));
		ATblDateTo.setCellValueFactory(new PropertyValueFactory<ManagerModel,String>("todate"));
		ATblNoOfDays.setCellValueFactory(new PropertyValueFactory<ManagerModel,String>("nod"));
		ATblReason.setCellValueFactory(new PropertyValueFactory<ManagerModel,String>("comments"));
		approvetid.setCellValueFactory(new PropertyValueFactory<ManagerModel,String>("tid"));
		
		approvetable.setItems(leavelist);
    }
    
    //selecting the required record
    @FXML
    private void getselected() {
    	index = approvetable.getSelectionModel().getSelectedIndex();
    	if(index<= -1) {
    		return;
    	}
    	employeeidfetch.setText(ATblEmployeeID.getCellData(index).toString());
    }
    
    //getting the leave history of the manager (logged in user)
    @FXML
    void event3() {

    	if (leavehistory.isSelected()) {

    	String query = "SELECT * from elm_leaverecords where emp_id='"+sUsername+"' and approve='yes';";

    	leaveslistlea = leavemodel.getleavehistory(query); 

    	tabcoltype.setCellValueFactory(new PropertyValueFactory<LeaveModel,String>("type"));
    	tabcolfrom.setCellValueFactory(new PropertyValueFactory<LeaveModel,String>("fromdate"));
    	tabcolto.setCellValueFactory(new PropertyValueFactory<LeaveModel,String>("todate"));
    	tabcolnod.setCellValueFactory(new PropertyValueFactory<LeaveModel,String>("nod"));
    	
        lhtable.setItems(leaveslistlea);

    	
    }
    }

    @FXML
    void event1() {
        if (userdetails.isSelected()) {
    		empidlabel.setText(sUsername);
    			HashMap<String,Integer> map=new HashMap<String,Integer>();
        		
        		String sql = "SELECT * from elm_employees where emp_id='"+sUsername+"';";
        		EmployeeModel empmodel=new EmployeeModel();
        		EmployeeModel em;
        		em=empmodel.getDetails(sql);
        		namelabel.setText(em.getFname()+" "+em.getLname());
        		emailidlabel.setText(em.getEmail());
        		departmentlabel.setText(em.getDepartment());
        		doblabel.setText(em.getDob());
        		reportstolabel.setText(em.getReports_to());
        		designationlabel.setText(em.getDesignation());
    	        phonelabel.setText(em.getPhone());
    	        
    	        String sql1= "Select * from elm_leaverecords where emp_id='"+sUsername+"'and approve='yes';";
    	        LeaveModel lmodel=new LeaveModel();
    	        map=lmodel.getLeaveBalances(sql1);
    	        XYChart.Series series1 = new XYChart.Series();

    	       // XYChart.Series<String, Integer> series1 = new XYChart.Series<>();
    	       
    	        final CategoryAxis xAxis = new CategoryAxis();
    	        final NumberAxis yAxis = new NumberAxis();
    	      //  xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList("Annual", "Casual", "Sick", "Maternity", "Paternity")));
    	        xAxis.setLabel("Leave types");       
    	        yAxis.setLabel("No. of days");
    	        series1.setName("Leave balance");
    	        for (Map.Entry<String, Integer> entry : map.entrySet()) {
    	        	String temp= entry.getKey();
    	        	String tmpString="";
    	        	switch(temp) {
    	        	case "1": tmpString = "Annual";
    	        	break;
    	        	case "2": tmpString = "Casual";
    	        	break;
    	        	case "3": tmpString = "Sick";
    	        	break;
    	        	case "4": tmpString = "Maternity";
    	        	break;
    	        	case "5": tmpString = "Paternity";
    	        	break;
    	        	}
    	            Integer tmpValue = entry.getValue();    	          
    	            
    	            series1.getData().add(new XYChart.Data(tmpString, tmpValue));;
    	        }
    	        leavechart.setTitle("Leave");
    	        leavechart.getData().clear();
    	        leavechart.getData().add(series1);
    	        
        }
    }
    @FXML
    public void onApprove() {
    	index = approvetable.getSelectionModel().getSelectedIndex();
    	String idforapprove = ATblEmployeeID.getCellData(index).toString();
    	String leavetypeapprove = ATblLeaveType.getCellData(index).toString();
    	String leavetopush;
    	if(leavetypeapprove.equalsIgnoreCase("Annual")) {
    		leavetopush = "1";
    	}
    	else if(leavetypeapprove.equalsIgnoreCase("Casual")) {
    		leavetopush = "2";
    	}
    	else if(leavetypeapprove.equalsIgnoreCase("Sick")) {
    		leavetopush = "3";
    	}
    	else if(leavetypeapprove.equalsIgnoreCase("Maternity")) {
    		leavetopush = "4";
    	}
    	else {
    		leavetopush = "5";
    	}
    	Connection conn = dbConnect.getconnection();
    	try {
    		if(ATblComboBoxAction.getValue() == "Approve") {
        		sql = "UPDATE elm_leaverecords set approve='YES' where emp_id=? AND type=?;";
        	}
        	else {
        		sql = "UPDATE elm_leaverecords set approve='NO' where emp_id =? AND type=?;";
        	}
    	
        	pst = conn.prepareStatement(sql);
        	pst.setString(1, idforapprove);
        	pst.setString(2, leavetopush);
    		pst.execute();
    		JOptionPane.showMessageDialog(null,"Update done");
    		loadtable();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    }
    
    @FXML
    private void onpendclick() {
    	String query = "SELECT * from elm_leaverecords where emp_id ='"+sUsername+"' and approve is NULL;";
    	
    	
    	leavelist = managermodel.getemployeeleaves(query);
    	tid.setCellValueFactory(new PropertyValueFactory<ManagerModel,String>("tid"));
    	MEditTableLeaveType.setCellValueFactory(new PropertyValueFactory<ManagerModel,String>("type"));
    	MEditTableLeaveFrom.setCellValueFactory(new PropertyValueFactory<ManagerModel,String>("fromdate"));
    	MEditTableLeaveTo.setCellValueFactory(new PropertyValueFactory<ManagerModel,String>("todate"));
    	MEditTableNod.setCellValueFactory(new PropertyValueFactory<ManagerModel,String>("nod"));
    	MEditTableLeaveComments.setCellValueFactory(new PropertyValueFactory<ManagerModel,String>("comments"));
    	
    	leavemodifytable.setItems(leavelist);
    }
    
    @FXML
    private void deleteleave() {
    	index = leavemodifytable.getSelectionModel().getSelectedIndex();
    	String idfordelete = tid.getCellData(index).toString();
    	Connection conn = dbConnect.getconnection();
    	try {
    		String query = "DELETE from elm_leaverecords where tid=?;";
    		
        	pst = conn.prepareStatement(query);
        	pst.setString(1, idfordelete);
        	pst.execute();
    		JOptionPane.showMessageDialog(null,"Delete done");
    		onpendclick();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    }
    
    
    @FXML
  private void onApplyLeave(ActionEvent event) throws IOException
    {
    	try {
    		LocalDate dleavefrom = leavefrom.getValue();
    		LocalDate dleaveto = leaveto.getValue();
    		String leavetype = combobox.getValue();
    		String scomments = reasonbox.getText();
    		int nod=countLeaveDays(dleavefrom,dleaveto);
    		int leavetypeint=-1;
    		String leaveconflict;
    		LeaveModel lm=new LeaveModel();
    		Map<String,Integer> leavebal=new HashMap<String,Integer>();
	        String sql1= "Select * from elm_leaverecords where emp_id='"+sUsername+"';";
	        String sql2= "Select * from elm_leaverecords where( fromdate='"+dleavefrom+"'or todate='"+dleaveto+"' ) and (approve='YES' or approve is null) and emp_id='"+sUsername+"';";
    		
	        leaveconflict=lm.checkLeaveDateConflict(sql2);
	        
	        if(leaveconflict.equalsIgnoreCase("no")) {
	        leavebal=lm.getLeaveBalances(sql1);
    		
    		
    		switch(leavetype) {
    		case "Annual": leavetypeint=1;
    			break;
    		case "Casual":leavetypeint=2;
    			break;
    		case "Sick": leavetypeint=3;
    			break;
    		case "Maternity": leavetypeint=4;
    			break;
    		case "Paternity": leavetypeint=5;
    			break;
    		}
    		int count=leavebal.get(String.valueOf(leavetypeint));
    	
    		 
    		
    		
    	
    		if(nod<=count) {
    		Statement = dbConnect.getconnection().createStatement();
    		
    		String sql = "INSERT into elm_leaverecords (emp_id,fromdate,todate,nod,type,comments) VALUES"
    				+ " ('"+sUsername+"','"+dleavefrom+"','"+dleaveto+"','"+nod+"','"+leavetypeint+"','"+scomments+"')";
    		
    		int con = Statement.executeUpdate(sql);
			if (con > 0) 
			{
				JOptionPane.showMessageDialog(null,"Leave applied for "+nod+" days");
			}
    		}
    		else {
    			JOptionPane.showMessageDialog(null,"Insufficient leave balance");
    		}
	        }
	        else {
	        	JOptionPane.showMessageDialog(null,leaveconflict);
	        }
    	}
    	catch(SQLException e) {
    		e.printStackTrace();
    	}
    	
        }
  
  private static int countLeaveDays(final LocalDate startDate,final LocalDate endDate)
  {
      // Validate method arguments
      if (startDate == null || endDate == null) {
          throw new IllegalArgumentException("Invalid method argument(s) to countBusinessDaysBetween (" + startDate+ "," + endDate + ")");
      }

      // Predicate 1: Is a given date is a holiday
     // Predicate<LocalDate> isHoliday = date -> holidays.isPresent() 
           //   && holidays.get().contains(date);

      // Predicate 2: Is a given date is a weekday
      Predicate<LocalDate> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
              || date.getDayOfWeek() == DayOfWeek.SUNDAY;

      // Get all days between two dates
      long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);

      // Iterate over stream of all dates and check each day against any weekday or
      // holiday
      return Stream.iterate(startDate, date -> date.plusDays(1))
              .limit(daysBetween)
              .filter(isWeekend.negate())
              .collect(Collectors.toList()).size()+1;
      
  }
   public void onlogout(ActionEvent event) throws IOException
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
		root = fxmlLoader.load();
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root, 600, 400);
		stage.setScene(scene);
		stage.show();
    }

}
