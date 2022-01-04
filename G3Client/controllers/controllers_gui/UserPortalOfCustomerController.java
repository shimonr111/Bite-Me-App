package controllers_gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import users.ConfirmationStatus;

/**
 * Class description: 
 * This is a class for 
 * controlling the UI of customer
 * form.
 */

/**
 * 
 * @author Lior, Guzovsky.
 * @author Mousa, Srour.
 * @author Alexander, Martinov.
 * 
 * @version 09/12/2021
 */
public class UserPortalOfCustomerController extends AbstractBiteMeController implements Initializable{
	
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
	private static UserPortalOfCustomerController userPortalOfCustomerController;
	
	@FXML
	/**
	 * Start Order Button
	 */
	private Button btnStartOrder;
	
    @FXML
    /**
     * View Orders Button.
     */
    private Button viewOrdersBtn;
    
	@FXML
	/**
	 * Exit Button.
	 */
	private Button btnExit;
	
	@FXML
	/**
	 * Log Out Button.
	 */
	private Button btnLogout;
	
	@FXML
	/**
	 * Help Button.
	 */
	private Button btnHelp;
	
    @FXML
    /**
     * The text to display the customer Name.
     */
    private Text userName;
    
    @FXML
    /**
     * The text to display the customer status.
     */
    private Text statusText;
   
	/**
	 * This is a function
	 * for going to the next screen of w4c
	 * identification.
	 * 
	 * @param event ActionEvent of javaFX.
	 */
	@FXML
	public void getStartOrderBtn(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		OrderW4cIdentificationScreenController w4cIdentificationController = new OrderW4cIdentificationScreenController();
		w4cIdentificationController.initW4cIdentificationScreen(); // call the init of the next screen
	}
	
	/**
     * This method disconnect the specific
     * client, log out the connected user
     * and exit.
     * 
     * @param event ActionEvent of javaFX.
     */
	@FXML
	public void getExitBtn(ActionEvent event) {
		Message message = new Message(Task.LOGOUT,Answer.WAIT_RESPONSE,connectedUser);
		sendToClient(message);
		connectedUser = null;
		Message disconnectMessage= new Message(Task.CLIENT_DICONNECT,Answer.WAIT_RESPONSE,null);
		sendToClient(disconnectMessage);
		System.exit(0);
	}
	
	/**
     * This method log out the connected user
     * and load the login screen.
     * 
     * @param event ActionEvent of javaFX.
     */
	@FXML
	public void getLogoutBtn(ActionEvent event) {
		//System.out.println(connectedUser);
		Message logOutMessage = new Message(Task.LOGOUT,Answer.WAIT_RESPONSE,connectedUser);  
		sendToClient(logOutMessage);
		connectedUser = null;  // after we changed the status in DB we set the connectedUser as null so we can get new login from same client.
		setToLoginScreen(event);  //set the login screen
	}
	
	/**
	 * This is pop message for the help button.
	 * 
	 * @param event
	 */
	@FXML
	public void getHelpBtn(ActionEvent event) {
		PopUpMessages.helpMessage("This is you'r the User-Portal, from here you can access the system functionalities!");	
		}
	
	/**
     * This method load the screen of
     * View Orders.
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getViewOrdersBtn(ActionEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
    	WatchOrderHistoryScreenController watchOrderHistoryScreenController = new WatchOrderHistoryScreenController();
    	watchOrderHistoryScreenController.initOrderHistoryScreen(); // call the init of the next screen
    }

	/**
	 * Returns to login screen
	 * 
	 * @param event ActionEvent of javaFX.
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
	 * @param primaryStage The stage of the screen.
	 * @param fxmlPath The fxml path of the current screen.
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
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("Private Customer Main Screen");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Init customer portal again.
	 */
	public void initPortalAgain() {
		loader = new FXMLLoader();
		Stage primaryStage = new Stage();
		Pane root = null;
		try {
			root = loader.load(getClass().getResource("/fxmls/UserPortalOfCustomer.fxml").openStream());
			userPortalOfCustomerController = loader.getController();
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
		primaryStage.setScene(scene);
		primaryStage.show();/* show the new screen */
	}
	
	
	/**
	 * This method will initialize the values according to 
	 * connected user.
	 * 
	 * @param arg0
	 * @param arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		userName.setText(connectedUser.getUserFirstName());
		statusText.setText(connectedUser.getStatusInSystem().toString());
		if(connectedUser.getStatusInSystem().equals(ConfirmationStatus.FROZEN)) {
			btnStartOrder.setDisable(true);
			viewOrdersBtn.setDisable(true);
		}
	}
}
