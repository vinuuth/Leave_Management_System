/*NagashreeA20514959
 * Vinutha A20504262
 *  */



package controller;


import java.io.IOException;
import java.security.MessageDigest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.LoginModel;


/** Controls the login screen */
public class LoginController {
	
  @FXML private TextField username;
  @FXML private TextField password;
  @FXML private Button login;
  
 private Stage stage;
private Scene scene;
private Parent root;
private int role;

private LoginModel loginmanager;

 
 public LoginController() 
	{ 
		  loginmanager = new LoginModel(); 
	}
 
 public void onclicklogin(ActionEvent event)
	{
	 System.out.println("Login clicked");
		String username = this.username.getText();
		String password = this.password.getText();
		
		String checkpassword = doHashing(password);

		// Validations
		if (username.equalsIgnoreCase("") && password.equalsIgnoreCase("")) {
  			Alert alert = new Alert(Alert.AlertType.ERROR);
  			alert.setTitle("Error");
  			alert.setHeaderText("please enter username and password");
  			alert.showAndWait();
  		} else if (username.equalsIgnoreCase("")) {
  			Alert alert = new Alert(Alert.AlertType.ERROR);
  			alert.setTitle("Error");
  			alert.setHeaderText("please enter username");
  			alert.showAndWait();
  		} else if (password.equalsIgnoreCase("")) {
  			Alert alert = new Alert(Alert.AlertType.ERROR);
  			alert.setTitle("Error");
  			alert.setHeaderText("please enter password");
  			alert.showAndWait();
  			}else
		checkCredentials(username, password, event);
  			}

		// authentication check
 
 private static String doHashing(String apass) {
		try {
			MessageDigest mdg = MessageDigest.getInstance("MD5");
			
			mdg.update(apass.getBytes());
			byte[] rba = mdg.digest();
			StringBuilder sb = new StringBuilder();
			for(byte b: rba) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		}
		catch(Exception e){
 		e.printStackTrace();
 	}
		return "";
	}
		
		
 public void checkCredentials(String username, String password, ActionEvent event) {
			 
		
		Boolean isValid = loginmanager.authenticate(username, password);
		System.out.println(isValid);
		if (!isValid) 
		{
			Alert alert = new Alert(Alert.AlertType.ERROR);
  			alert.setTitle("Error");
  			alert.setHeaderText("Incorrect username or password");
  			alert.showAndWait();
			return;
		}
		else {
			 role=loginmanager.getRole();
		}
		try 
		{
			String newscene="";
			int width = 0;
			int height = 0;

			switch(role){
			
			case 0: 
			{
				newscene="/view/EmployeeView.fxml";
				width = 857;
				height = 565;
			
			}
			break;

			case 1:
			{
				newscene="/view/ManagerView.fxml";
				width = 857;
				height = 565;
			}
			break;
			
			case 2:
			{
				newscene="/view/adminview.fxml";
				width = 857;
				height = 565;
			}
			break;
		}

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(newscene));
			Parent root = fxmlLoader.load();

			if(role==0)
			{
				EmployeeController empCtrl = ((EmployeeController)fxmlLoader.getController());
				empCtrl.sUsername = username;
			
			}
			else if(role==1)
			{
				ManagerController manCtrl = ((ManagerController)fxmlLoader.getController());
				manCtrl.sUsername = username;
				
			}
			else if(role == 2)
			{
				AdminController admCtrl = ((AdminController)fxmlLoader.getController());
				admCtrl.sUsername = username;
			}
			 
			Node source = (Node) event.getSource();
			Stage stage = (Stage)source.getScene().getWindow();
			Scene scene = new Scene(root, width, height);
			stage.setScene(scene);
			stage.show();

		} catch (Exception e) {
			System.out.println("Error occured while checking credentials: " + e);
			e.printStackTrace();
		}

	}  
}
