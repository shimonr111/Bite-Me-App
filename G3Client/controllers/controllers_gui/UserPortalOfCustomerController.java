package controllers_gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.awt.Label;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import bitemeclient.PopUpMessages;
import communication.Answer;
import communication.Message;
import communication.Task;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import users.Branch;
import users.ConfirmationStatus;

/**
 * 
 * @author Lior, Guzovsky
 * @author Mousa, Srour
 * @author Alexander, Martinov
 * Class description: 
 * This is a class for 
 * controlling the UI of customer
 * form.
 * 
 * @version 09/12/2021
 */
public class UserPortalOfCustomerController extends AbstractBiteMeController implements Initializable{
	@FXML
	private Button btnStartOrder;
	@FXML
	private Button btnExit;
	@FXML
	private Button btnLogout;
	@FXML
	private Button btnHelp;
    @FXML
    private Text userName;
    @FXML
    private Text statusText;

	public static FXMLLoader loader;
	private static UserPortalOfCustomerController userPortalOfCustomerController;
	
	/**
	 * This is a function
	 * for going to the next screen of w4c
	 * identification.
	 * 
	 * @param event
	 */
	// Event Listener on Button[#btnStartOrder].onAction
	@FXML
	public void getStartOrderBtn(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		OrderW4cIdentificationScreenController w4cIdentificationController = new OrderW4cIdentificationScreenController();
		w4cIdentificationController.initW4cIdentificationScreen(); // call the init of the next screen
	}
	// Event Listener on Button[#btnExit].onAction
	@FXML
	public void getExitBtn(ActionEvent event) {
		Message message = new Message(Task.LOGOUT,Answer.WAIT_RESPONSE,connectedUser);
		sendToClient(message);
		connectedUser = null;
		Message disconnectMessage= new Message(Task.CLIENT_DICONNECT,Answer.WAIT_RESPONSE,null);
		sendToClient(disconnectMessage);
		System.exit(0);
	}
	// Event Listener on Button[#btnLogout].onAction
	@FXML
	public void getLogoutBtn(ActionEvent event) {
		//TBD: add logic for updating logged in in DB to logged out
		//System.out.println(connectedUser);
		Message logOutMessage = new Message(Task.LOGOUT,Answer.WAIT_RESPONSE,connectedUser);  
		sendToClient(logOutMessage);
		connectedUser = null;  // after we changed the status in DB we set the connectedUser as null so we can get new login from same client.
		setToLoginScreen(event);  //set the login screen
	}
	
	/**
	 * This is pop message for the help button.
	 * @param event
	 */
	// Event Listener on Button[#btnHelp].onAction
	@FXML
	public void getHelpBtn(ActionEvent event) {
		PopUpMessages.helpMessage("This is the Main screen of the Customer, Please press any button!");	
		}

	/**
	 * Returns to login screen
	 * @param event
	 */	
	private void setToLoginScreen(ActionEvent event) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				FXMLLoader loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/LoginScreen.fxml").openStream());
					LoginScreenController LSC = loader.getController();
					LSC.initLoginScreen();
					Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) { 
							event.consume();
							Stage.close();
						}
					});
				} catch (IOException e) {
					e.printStackTrace();
				}
				((Node) event.getSource()).getScene().getWindow().hide();
			}
		});
	}
	
	/**
	 * This is the initialization function for this 
	 * screen.
	 * 
	 * @param primaryStage
	 * @param fxmlPath
	 */
	public void initCustomerUserPortal(Stage primaryStage,String fxmlPath) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
					primaryStage.hide(); 
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource(fxmlPath).openStream());
					userPortalOfCustomerController = loader.getController();
					Scene scene = new Scene(root);
					Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) { 
							event.consume();
							Stage.close();
						}
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("Main menu");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * This method will initialize the values according to 
	 * connected user.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		userName.setText(connectedUser.getUserFirstName());
		statusText.setText(connectedUser.getStatusInSystem().toString());
		if(connectedUser.getStatusInSystem().equals(ConfirmationStatus.FROZEN)) {
			btnStartOrder.setDisable(true);
		}
	}
}
