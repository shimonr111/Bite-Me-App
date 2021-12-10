package controllers_gui;

import java.io.IOException;

import bitemeclient.PopUpMessages;
import clientanalyze.AnalyzeClientListener;
import clientanalyze.AnalyzeMessageFromServer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @author Shimon Rubin
 * Class description: 
 * 
 * This class present the Home Screen and 
 * responsible for the functionality of this screen 
 *
 * @version 02/12/2021
 */
public class EntryHomeScreenFormController{
	/**
	 * Class members description:
	 */
	
	/**
	 * This button enter to
	 * the Login screen
	 */
	@FXML
	private Button btnEnter = null;
	
	/**
	 * This button import data
	 * from the simulation
	 */
	@FXML
	private Button importBtn = null;
	
	/**
	 * This button exits
	 * the application
	 */
	@FXML
	private Button btnExit = null;
	
	/**
	 * This button pop 
	 * up message which show 
	 * to us info about the 
	 * current page 
	 */
	@FXML
	private Button btnHelp = null;

	
	private LoginScreenController entryLoginScreenFormController = new LoginScreenController();
	public void start(Stage primaryStage) throws Exception {

	}


	/**
	 * Using enter button hide 
	 * window and go login 
	 * screen 
	 */
	public void getEnterBtn(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		entryLoginScreenFormController.initLoginScreen();
	}

	
	/**
	 * Using help button in order 
	 * to show instructions for the 
	 * current screen 
	 */
	public void getHelpBtn(ActionEvent event) throws Exception {
		PopUpMessages.helpMessage("Welcome to BiteMe App! press Enter to login your user.");
	}
	
	/**
	 * Using exit button 
	 * in order to exit 
	 * from the App 
	 */
	public void getExitBtn(ActionEvent event) throws Exception {
		System.exit(0);
	}
	
	/**
	 * Using import button 
	 * in order to import data 
	 * from the db 
	 */
	public void getImportBtn(ActionEvent event) throws Exception {
		
	}

}

