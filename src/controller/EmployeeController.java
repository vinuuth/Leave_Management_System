/*Nagashree A20514959
 * vinutha A20504262
 * employee controller has the functions related to the employee view
 * */

package controller;
import java.awt.TextArea;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.event.Event;
import model.AdminModel;
import model.DBConnect;
import model.EmployeeModel;
import model.LeaveModel;
import model.ManagerModel;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;

import java.time.DayOfWeek;
import java.time.temporal.ChronoUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class EmployeeController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	ObservableList<String> listLeaveType = FXCollections.observableArrayList("Annual", "Casual", "Sick", "Maternity", "Paternity");

    @FXML
    private JFXTextArea reasonbox;
    
    @FXML
    private Button applyleavebutton;
    
    @FXML
    private Label empidlabel;
    
    @FXML
    private Label departmentlabel;

    @FXML
    private Label designationlabel;

    @FXML
    private Label doblabel;
    
    @FXML
    private Label reportstolabel;

    @FXML
    private Label emailidlabel;
    
    @FXML
    private Label namelabel;

    @FXML
    private Label phonelabel;

    @FXML
    private DatePicker leavefrom;
    
    @FXML
    private Tab leavehistory;
    
    @FXML
    private Tab userdetails;

    @FXML
    private DatePicker leaveto;
    
    @FXML
    private ComboBox<String> combobox;
    
    @FXML
    private TableColumn<LeaveModel , String> tabcoltype;

    @FXML
    private TableColumn<LeaveModel, String> tabcolfrom;

    @FXML
    private TableColumn<LeaveModel , String> tabcolto;

    @FXML
    private TableColumn<LeaveModel , String> tabcolnod;
    
    @FXML
    private TableColumn<HashMap.Entry<Integer, Integer>,Integer> udtabcollt;

    @FXML
    private TableColumn<HashMap.Entry<Integer, Integer>,Integer> udtabcolbal;
    
    @FXML
    private TableView<LeaveModel> lhtable;
    
    @FXML
    private TableView<ObservableMap.Entry<Integer, Integer>> leavebalance;
   // table.setView(true);
    
    @FXML
    private BarChart<String, Integer> leavechart;
    
    @FXML
    private Button revokeleave;
    

    @FXML
    private TableColumn<EmployeeModel, String> MEditTableLeaveComments;

    @FXML
    private TableColumn<EmployeeModel, String> MEditTableLeaveFrom;

    @FXML
    private TableColumn<EmployeeModel, String> MEditTableLeaveTo;

    @FXML
    private TableColumn<EmployeeModel, String> MEditTableLeaveType;

    @FXML
    private TableColumn<EmployeeModel, String> MEditTableNod;
    
    @FXML
    private TableColumn<EmployeeModel, String> tid;
    
    @FXML
    private TableView<EmployeeModel> leavemodifytable;
    
    @FXML
    private Button logoutbutton;
    
	DBConnect dbConnect = null;
	Statement Statement = null;
	public String sUsername;
	public String sPassword;
	int index = -1;
	PreparedStatement pst = null;	
    ObservableList<LeaveModel> leaveslist;
    LeaveModel leavemodel =new LeaveModel();
    EmployeeModel empmodel;
    ObservableList<EmployeeModel> leavelistemp;

    
    @FXML
	private void initialize()
	{
		combobox.setItems(listLeaveType);
		dbConnect = new DBConnect();
	
	}
    @FXML
    void event(Event ev) {
        if (leavehistory.isSelected()) {
         
            //Do stuff here
        
    	String query = "SELECT * from elm_leaverecords where emp_id='"+sUsername+"' and approve='yes';";
    	leaveslist = leavemodel.getleavehistory(query); 
  
    	tabcoltype.setCellValueFactory(new PropertyValueFactory<LeaveModel,String>("type"));
    	tabcolfrom.setCellValueFactory(new PropertyValueFactory<LeaveModel,String>("fromdate"));
    	tabcolto.setCellValueFactory(new PropertyValueFactory<LeaveModel,String>("todate"));
    	tabcolnod.setCellValueFactory(new PropertyValueFactory<LeaveModel,String>("nod"));
    	
        lhtable.setItems(leaveslist);

    }
        
    }
    @FXML
    void event1(Event ev) {   
    if (userdetails.isSelected()) {
		empidlabel.setText(sUsername);
			HashMap<String,Integer> map=new HashMap<String,Integer>();
    		
    		String sql = "SELECT * from elm_employees where emp_id='"+sUsername+"';";
    		empmodel=new EmployeeModel();
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
	       // XYChart.Series series1 = new XYChart.Series();

	       XYChart.Series<String, Integer> series1 = new XYChart.Series<>();
	       
	        final CategoryAxis xAxis = new CategoryAxis();
	        final NumberAxis yAxis = new NumberAxis();
	        
	       xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList("Annual", "Casual", "Sick", "Maternity", "Paternity")));
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
	            //String tmpString = entry.getKey();
	            Integer tmpValue = entry.getValue();
	            XYChart.Data<String, Integer> d = new XYChart.Data<>(tmpString, tmpValue);
	            
	            series1.getData().add(new XYChart.Data(tmpString, tmpValue));;
	        }
	        leavechart.setTitle("Leave");
	        leavechart.getData().clear();
	        leavechart.getData().add(series1);
	        
    	}
    }
    
    @FXML
    private void deleteleave() {
    	index = leavemodifytable.getSelectionModel().getSelectedIndex();
    	String idfordelete = tid.getCellData(index).toString();
    	Connection conn = dbConnect.getconnection();
    	//String leavetypeapprove = ATblLeaveType.getCellData(index).toString();
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
    private void onpendclick() {
    	String query = "SELECT * from elm_leaverecords where emp_id ='"+sUsername+"' and approve is NULL;";
    	
        empmodel=new EmployeeModel();

    	leavelistemp = empmodel.getemployeeleaves(query);
    	
    	
    	tid.setCellValueFactory(new PropertyValueFactory<EmployeeModel,String>("tid"));
    	MEditTableLeaveType.setCellValueFactory(new PropertyValueFactory<EmployeeModel,String>("type"));
    	MEditTableLeaveFrom.setCellValueFactory(new PropertyValueFactory<EmployeeModel,String>("fromdate"));
    	MEditTableLeaveTo.setCellValueFactory(new PropertyValueFactory<EmployeeModel,String>("todate"));
    	MEditTableNod.setCellValueFactory(new PropertyValueFactory<EmployeeModel,String>("nod"));
    	MEditTableLeaveComments.setCellValueFactory(new PropertyValueFactory<EmployeeModel,String>("comments"));
    	
    	leavemodifytable.setItems(leavelistemp);
    }
    public void onapplyleave(ActionEvent event) throws IOException
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
    		int aa=leavebal.get(String.valueOf(leavetypeint));
    		
    		if(nod<=aa) {
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
   public void onlogout(ActionEvent event) throws IOException
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
		root = fxmlLoader.load();
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root, 600, 400);
		stage.setScene(scene);
		stage.show();
    }
   
    
    private static int countLeaveDays(final LocalDate startDate,
            final LocalDate endDate
            )
    {
        
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Invalid method argument(s) to countBusinessDaysBetween (" + startDate+ "," + endDate + ")");
        }

       
        Predicate<LocalDate> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
                || date.getDayOfWeek() == DayOfWeek.SUNDAY;

        // Get all days between two dates
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);

        // Iterate over stream of all dates and check each day against any weekday or
        return Stream.iterate(startDate, date -> date.plusDays(1))
                .limit(daysBetween)
                .filter(isWeekend.negate())
                .collect(Collectors.toList()).size()+1;
        
    }


}
