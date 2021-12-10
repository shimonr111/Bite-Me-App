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
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import users.Supplier;

/**
 * 
 * @author Mousa, Srour
 * @author Alexander, Martinov
 * Class description: 
 * This is a class for 
 * controlling the UI of supplier
 * form.
 * 
 * @version 09/12/2021
 */
public class UserPortalOfSupplierController extends AbstractBiteMeController  implements Initializable {
	@FXML
	private Button btnExit;
	@FXML
	private Button btnLogout;
	@FXML
	private Button btnManageOrders;
	@FXML
	private Button btnManageMenu;
	@FXML
	private Label nameLabel;
	@FXML
	private Button btnHelp;
    @FXML
    private Text resturantName;

	
	public static FXMLLoader loader;
	private static UserPortalOfSupplierController userPortalOfSupplierController;
	
	// Event Listener on Button[#btnExit].onAction
	@FXML
	public void getExitBtn(ActionEvent event) {
		// TODO Autogenerated
		Message message = new Message(Task.LOGOUT,Answer.WAIT_RESPONSE,connectedUser);
		sendToClient(message);
		connectedUser = null;
		System.exit(0);
	}
	// Event Listener on Button[#btnLogout].onAction
	@FXML
	public void getLogoutBtn(ActionEvent event) {
		// TODO Autogenerated
		Message message = new Message(Task.LOGOUT,Answer.WAIT_RESPONSE,connectedUser);
		sendToClient(message);
		connectedUser = null;  // after we changed the status in DB we set the connectedUser as null so we can get new login from same client.
		setToLoginScreen(event);  //set the login screen
	}
	// Event Listener on Button[#btnManageOrders].onAction
	@FXML
	public void getManageOrdersBtn(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#btnManageMenu].onAction
	@FXML
	public void getManageMenuBtn(ActionEvent event) {
		// TODO Autogenerated
	}
	
	/**
	 * This is pop message for the help button.
	 * @param event
	 */
	// Event Listener on Button[#btnHelp].onAction
	@FXML
	public void getHelpBtn(ActionEvent event) {
		PopUpMessages.helpMessage("This is the Main screen of supplier, Please press any button!");
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
	public void initSupplierUserPortal(Stage primaryStage,String fxmlPath) {
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
					userPortalOfSupplierController = loader.getController();
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
	 * set the supplier details on the supplierPortal according
	 * to the connected supplier.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		resturantName.setText(((Supplier)connectedUser).getSupplierBusinessName());
		
	}
}