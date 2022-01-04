package controllers_gui;

import java.io.IOException;
import bitemeclient.PopUpMessages;
import clientanalyze.AnalyzeClientListener;
import clientanalyze.AnalyzeMessageFromServer;
import communication.Answer;
import communication.Message;
import communication.Task;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import users.Login;

/**
 *  Class description: 
 * This class present the Login Screen 
 * and responsible for the functionality
 * of this screen 
 */

/** 
 * 
 * @author Shimon, Rubin.
 * @author Mousa, Srour.
 * @author Lior, Guzovsky.
 * 
 * @version 07/12/2021
 */
public class LoginScreenController extends AbstractBiteMeController{
	/**
	 * Class members description:
	 */
	
	/**
	 * The FXMLLoader of the current screen.
	 */
	public static FXMLLoader loader;
	
	/**
	 * A static object of the current class.
	 */
	private static LoginScreenController loginScreenController;
	
    /**
     * The listener for the current form , so when we finish working in this page we stop it.
     */
	private static AnalyzeClientListener listener;
	
	@FXML
	/**
	 * User Name Text Field.
	 */
	private TextField userNameField;
	
	@FXML
	/**
	 * Password Text Field.
	 */
	private PasswordField passwordField;
	
	@FXML
	/**
	 * The Exit Button.
	 */
	private Button btnExit;
	
	@FXML
	/**
	 * The Login Button.
	 */
	private Button btnLogin;
	
	@FXML
	/**
	 * The Back Button.
	 */
	private Button btnBack;
	
	@FXML
	/**
	 * The Help Button.
	 */
	private Button btnHelp;
	
	@FXML
	/**
	 * The empty text that we use to display messages for the customer.
	 */
	private Label errorLabel;

	/**
	 * Using exit button
	 * in order to exit from
	 * the app. 
	 * 
	 * @param event: the action event sent from the UI.
	 */
	// Event Listener on Button[#btnExit].onAction
	@FXML
	public void getExitBtn(ActionEvent event) {
		Message message= new Message(Task.CLIENT_DICONNECT,Answer.WAIT_RESPONSE,null);
		sendToClient(message);
		System.exit(0);
	}
	
	/**
	 * Using login button hide 
	 * window and go user portal 
	 * screen.
	 * 
	 * @param event: the action event sent from the UI.
	 */
	// Event Listener on Button[#btnLogin].onAction
	@FXML
	public void getLoginBtn(ActionEvent event) {
		passwordField.setStyle(null); userNameField.setStyle(null);
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
	 * 
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
					AnalyzeMessageFromServer.removeClientListener(listener);
					root = loader.load(getClass().getResource("/fxmls/EntryHomeScreenForm.fxml").openStream());
					Scene scene = new Scene(root);
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("Home");
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
	 * 
	 * @param event: the action event sent from the UI.
	 */
	// Event Listener on Button[#btnHelp].onAction
	@FXML
	public void getHelpBtn(ActionEvent event) {
		PopUpMessages.helpMessage("On this screen you enter you'r credintials to log into Bite-Me system."
				+ "\nOnly confirmed users can log into the system!");
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
	 * @return boolean
	 */
	 private boolean isLoginDataValidFromUser(Login login) {
		if(login.getUserName().equals(""))
			userNameField.setStyle("-fx-border-color: red");
		if(login.getPassword().equals("")) { // checking if the user didn't enter both username and password.
			passwordField.setStyle("-fx-border-color: red");
		}
		if(login.getUserName().equals("") || login.getPassword().equals("")) {
			setRelevantTextToErrorLable("Please fill all the required fields (*)!",true);
			return false;
		}
		else
			return true;
	}
	 
	 /**
	  * This is a function which sets all
	  * the messages to the Error lable 
	  * at the Login screen.
	  * 
	  * @param message
	  * @param isVisible
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
			loginScreenController = loader.getController();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		scene.setOnMousePressed(pressEvent -> {
		    scene.setOnMouseDragged(dragEvent -> {
		    	primaryStage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
		    	primaryStage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
		    });
		});
		scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
		primaryStage.setTitle("Login");
		primaryStage.setScene(scene);
		primaryStage.show();/* show the new screen */
		AnalyzeMessageFromServer.addClientListener(listener=new AnalyzeClientListener(){ 
			@Override
			public void clientGotCustomerLogin() {
				AnalyzeMessageFromServer.removeClientListener(this);
				UserPortalOfCustomerController userPortalOfCustomerController = new UserPortalOfCustomerController();
				userPortalOfCustomerController.initCustomerUserPortal(primaryStage, "/fxmls/UserPortalOfCustomer.fxml");
			}
			
			@Override
			public void clientGotBusinessCustomerLogin() {
				AnalyzeMessageFromServer.removeClientListener(this);
				UserPortalOfBusinessCustomerController userPortalOfBusinessCustomerController = new UserPortalOfBusinessCustomerController();
				userPortalOfBusinessCustomerController.initBusinessCustomerUserPortal(primaryStage, "/fxmls/UserPortalOfBusinessCustomer.fxml");
			}
			
			@Override
			public void clientGotSupplierLogin() {
				AnalyzeMessageFromServer.removeClientListener(this);
				UserPortalOfSupplierController userPortalOfSupplierController = new UserPortalOfSupplierController();
				userPortalOfSupplierController.initSupplierUserPortal(primaryStage, "/fxmls/UserPortalOfSupplier.fxml");
			}
			
			@Override
			public void clientGotHRManagerLogin() {
				AnalyzeMessageFromServer.removeClientListener(this);
				UserPortalOfHRManagerController userPortalOfHRManagerController = new UserPortalOfHRManagerController();
				userPortalOfHRManagerController.initHrManagerUserPortal(primaryStage, "/fxmls/UserPortalOfHRManager.fxml");
			}
			
			@Override
			public void clientGotBranchManagerLogin() {
				AnalyzeMessageFromServer.removeClientListener(this);
				UserPortalOfBranchManagerController userPortalOfBranchManagerController = new UserPortalOfBranchManagerController();
				userPortalOfBranchManagerController.initBranchManagerUserPortal(primaryStage, "/fxmls/UserPortalOfBranchManager.fxml");
			}
			
			@Override
			public void clientGotCEOLogin() {
				AnalyzeMessageFromServer.removeClientListener(this);
				UserPortalOfCEOController userPortalOfCEOController = new UserPortalOfCEOController();
				userPortalOfCEOController.initCEOUserPortal(primaryStage, "/fxmls/UserPortalOfCEO.fxml");
			}
			
			@Override
			public void clientIsLogedIn() {
				setRelevantTextToErrorLable("User already logged in!",true);
			}
			
			@Override
			public void clientNotFoundInSystem() {
				setRelevantTextToErrorLable("Wrong username or password!",true);
			}
			
			@Override
			public void clientIsNotConfirmed() {
				setRelevantTextToErrorLable("User is not confirmed!",true);
			}	
	});
	}
}
