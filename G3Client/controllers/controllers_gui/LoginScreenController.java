package controllers_gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import bitemeclient.PopUpMessages;
import clientanalyze.AnalyzeClientListener;
import clientanalyze.AnalyzeMessageFromServer;
import communication.Answer;
import communication.Message;
import communication.Task;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import users.Login;

/** 
 * @author Shimon, Rubin.
 * @author Mousa, Srour.
 * @author Lior, Guzovsky.
 *
 * Class description: 
 * This class present the Login Screen 
 * and responsible for the functionality
 * of this screen 
 * @version 07/12/2021
 */
public class LoginScreenController extends AbstractBiteMeController{
	/**
	 * Class members description:
	 */
	
	/**
	 * Used for switching label in
	 * Screen.
	 */
	public static FXMLLoader loader;
	public static LoginScreenController loginScreenController;
	
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
	 * This button exit 
	 * from the app immediately 
	 */
	@FXML
	private Button btnExit;
	
	/**
	 * This button login and move 
	 * forward to the user portal 
	 * after we insert user and pass
	 */
	@FXML
	private Button btnLogin;
	
	/**
	 * This button move back
	 * to the previous screen 
	 */
	@FXML
	private Button btnBack;
	
	/**
	 * This button pop
	 * up message which show
	 * to us info about the
	 * current page 
	 */
	@FXML
	private Button btnHelp;
	
	/**
	 * This is an error
	 * label for giving the user
	 * indication about errors
	 * that have occurred during the
	 * login phase.
	 */
	@FXML
	private Label errorLabel;

	/**
	 * From here those are the functions
	 * that handle the GUI events that
	 * happened.
	 */
	
	/**
	 * This function starts when the 
	 * screen is up.
	 * 
	 * @param primaryStage
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception {
		
	}
	
	/**
	 * Using exit button
	 * in order to exit from
	 * the app. 
	 * @param event: the action event sent from the UI.
	 */
	// Event Listener on Button[#btnExit].onAction
	@FXML
	public void getExitBtn(ActionEvent event) {
		System.exit(0);
	}
	
	/**
	 * Using login button hide 
	 * window and go user portal 
	 * screen.
	 * @param event: the action event sent from the UI.
	 */
	// Event Listener on Button[#btnLogin].onAction
	@FXML
	public void getLoginBtn(ActionEvent event) {
		Login login = new Login(userNameField.getText(),passwordField.getText());
		//check validity of Login content
		if(isLoginDataValidFromUser(login)) {
		Message message = new Message (Task.LOGIN,Answer.WAIT_RESPONSE,login);
		sendToClient(message);
		}
	}
	
	/**
	 * Using back button
	 * in order to exit from
	 * the current app
	 * @param event: the action event sent from the UI.
	 */
	// Event Listener on Button[#btnBack].onAction
	@FXML
	public void getBackBtn(ActionEvent event) {
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
	
	/**
	 * Using help button
	 * in order to show instructions
	 * for the current screen 
	 * @param event: the action event sent from the UI.
	 */
	// Event Listener on Button[#btnHelp].onAction
	@FXML
	public void getHelpBtn(ActionEvent event) {
		PopUpMessages.helpMessage("Insert your user name and password and then press Login.");
	}
	
	/**
	 * Those are Util functions for
	 * this screen.
	 * 
	 */
	
	/**
	 * This method checks the validity of the data 
	 * which means that we check for user name and password 
	 * existence.
	 * If they are not ok, pop up message accordingly.
	 * 
	 * @param login
	 * @return
	 */
	 private boolean isLoginDataValidFromUser(Login login) {
		if(login.getUserName().equals("") || login.getPassword().equals("")) { // checking if the user didn't enter both username and password.
			setRelevantTextToErrorLable("Please fill all the required fields (*)",true);
			return false;
		}
		return true;
	}
	 
	 /**
	  * This is a functions used to set the 
	  * relevant user portal in the next screen
	  * according to data that came from the server.
	  * 
	  * @param primaryStage
	  * @param fxmlPath
	  */
	 private void setUserPortalFormByType(Stage primaryStage,String fxmlPath) {
		 Platform.runLater(new Runnable() {
				@Override
				public void run() {
					FXMLLoader loader = new FXMLLoader();
					Pane root;
					try {
						primaryStage.hide(); 
						Stage Stage = new Stage();
						Stage.setResizable(false);
						root = loader.load(getClass().getResource(fxmlPath).openStream());
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
				}
			});
	 }
	
	 /**
	  * This is a function which sets all
	  * the messages to the Error lable 
	  * at the Login screen.
	  * 
	  * @param message
	  */
	private void setRelevantTextToErrorLable(String message, boolean isVisible) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loginScreenController.errorLabel.setText(message);
				loginScreenController.errorLabel.setVisible(isVisible); //set the label to be visible
			}
		});
		
	}
	 
	/**
	 * This function sets init to the Login screen
	 * from the previous screen (Home screen).
	 */
	public void initLoginScreen() {
		loader = new FXMLLoader();
		Stage primaryStage = new Stage();
		Pane root = null;
		errorLabel = new Label();
		setRelevantTextToErrorLable("",false);
		try {
			root = loader.load(getClass().getResource("/fxmls/LoginScreen.fxml").openStream());
			//root = FXMLLoader.load(getClass().getResource("/fxmls/LoginScreen.fxml"));
			loginScreenController = loader.getController();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/css/EntryLoginScreenForm.css").toExternalForm());
		primaryStage.setTitle("Login");
		primaryStage.setScene(scene);
		primaryStage.show();/* show the new screen */
		AnalyzeMessageFromServer.addClientListener(new AnalyzeClientListener(){ 
			@Override
			public void clientGotCustomerLogin() {
				setUserPortalFormByType(primaryStage,"/fxmls/UserPortalOfCustomer.fxml"); //primary stage passed to hide previous window
			}
			
			@Override
			public void clientGotBusinessCustomerLogin() {
				setUserPortalFormByType(primaryStage,"/fxmls/UserPortalOfBusinessCustomer.fxml"); //primary stage passed to hide previous window
			}
			
			@Override
			public void clientGotSupplierLogin() {
				setUserPortalFormByType(primaryStage,"/fxmls/UserPortalOfSupplier.fxml"); //primary stage passed to hide previous window
			}
			
			@Override
			public void clientGotHRManagerLogin() {
				setUserPortalFormByType(primaryStage,"/fxmls/UserPortalOfHRManager.fxml"); //primary stage passed to hide previous window
			}
			
			@Override
			public void clientGotBranchManagerLogin() {
				setUserPortalFormByType(primaryStage,"/fxmls/UserPortalOfBranchManager.fxml"); //primary stage passed to hide previous window
			}
			
			@Override
			public void clientGotCEOLogin() {
				setUserPortalFormByType(primaryStage,"/fxmls/UserPortalOfCEO.fxml"); //primary stage passed to hide previous window
			}
			
			@Override
			public void clientIsLogedIn() {
				setRelevantTextToErrorLable("User already logged in!",true);
			}
			
			@Override
			public void clientNotFoundInSystem() {
				setRelevantTextToErrorLable("User is not found in the system!",true);
			}
			
			@Override
			public void clientIsNotConfirmed() {
				setRelevantTextToErrorLable("User is not confirmed in the system yet!",true);
			}	
	});
	}
}
