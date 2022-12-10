/*Nagashree A20514959
 * Vinutha A20504262
 *  */



package controller;


import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.LoginModel;

public class LeaveApplication extends Application {
	@Override
	public void start(Stage stage) {
		try {
			System.out.println("Test");
			 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
			 Scene scene = new Scene(fxmlLoader.load(), 600, 400);

			stage.setTitle("Leave management System");
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) { 	
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
