package controllers_gui;

import java.io.IOException;

import bitemeclient.PopUpMessages;
import communication.Answer;
import communication.Message;
import communication.Task;
import controllers_analyze.LoginAnalyze;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import users.Login;

/** 
 * @author Shimon, Rubin.
 * @author Mousa, Srour. 
 *
 * Class description: 
 * This class present the Login Screen 
 * and responsible for the functionality
 * of this screen 
 * @version 04/12/2021
 */
public class EntryLoginScreenFormController{
	/**
	 * Class members description:
	 */
	
	/**
	 * name 
	 * of the user
	 */
	@FXML
	private TextField userNameField;
	
	/**
	 * Password field
	 * of the user 
	 */
	@FXML
	private TextField passwordField;
	
	/**
	 * This button login and move 
	 * forward to the user portal 
	 * after we insert user and pass
	 */
	@FXML
	private Button btnLogin = null;
	
	/**
	 * This button exit 
	 * from the app immediately 
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
	
	/**
	 * This button move back
	 * to the previous screen 
	 */
	@FXML
	private Button btnClose = null;
	
	
	/**
	 * This function sets the Login
	 * Form.
	 * 
	 * @param primaryStage
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/fxmls/EntryLoginScreenForm.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/css/EntryLoginScreenForm.css").toExternalForm());
		primaryStage.setTitle("Welcome to BiteMe App!");
		primaryStage.setScene(scene);
		primaryStage.show();
	}


	/**
	 * Using login button hide 
	 * window and go user portal 
	 * screen.
	 * @param event: the action event sent from the UI.
	 */
	public void getLoginBtn(ActionEvent event) throws Exception {
		Login login = new Login(userNameField.getText(),passwordField.getText());
		LoginAnalyze.checkInputFromUser(login);
	}

	
	/**
	 * Using help button
	 * in order to show instructions
	 * for the current screen 
	 * @param event: the action event sent from the UI.
	 */
	public void getHelpBtn(ActionEvent event) throws Exception {
		PopUpMessages.helpMessage("Insert your user name and password and then press Login.");
	}
	
	/**
	 * Using exit button
	 * in order to exit from
	 * the app. 
	 * @param event: the action event sent from the UI.
	 */
	public void getExitBtn(ActionEvent event) throws Exception {
		System.exit(0);
	}
	
	
	/**
	 * Using close button (back button)
	 * hide window and go 
	 * backwards 
	 * @param event: the action event sent from the UI.
	 */
	public void getCloseBtn(ActionEvent event) throws Exception {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				FXMLLoader loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/EntryHomeScreenForm.fxml").openStream());
					Scene scene = new Scene(root);
					Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) { 
							event.consume();
							Stage.close();
						}
					});
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
				((Node) event.getSource()).getScene().getWindow().hide();
			}
		});
	}
	



}

