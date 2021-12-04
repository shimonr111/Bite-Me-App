package gui;

import bitemeclient.PopUpMessages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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
	
	
	public void start(Stage primaryStage) throws Exception {

	}


	/**
	 * Using enter button hide 
	 * window and go login 
	 * screen 
	 */
	public void getEnterBtn(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxmls/EntryLoginScreenForm.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/css/EntryLoginScreenForm.css").toExternalForm());
		primaryStage.setTitle("Login");
		primaryStage.setScene(scene);
		primaryStage.show();/* show the new screen */
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
	


}

