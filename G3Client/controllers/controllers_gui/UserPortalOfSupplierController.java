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
import users.SupplierWorker;
import users.WorkerPosition;
/**
 *  Class description: 
 * This is a class for 
 * controlling the UI of supplier
 * form.
 * 
 * @author Mousa, Srour
 * @author Alexander, Martinov.
 * 
 * @version 15/12/2021
 */
public class UserPortalOfSupplierController extends AbstractBiteMeController  implements Initializable{
	
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
	private static UserPortalOfSupplierController userPortalOfSupplierController;
	
	@FXML
	/**
	 * The Exit Button.
	 */
	private Button btnExit;
	
	@FXML
	/**
	 * Log Out Button.
	 */
	private Button btnLogout;
	
	@FXML
	/**
	 * Manage Orders Button.
	 */
	private Button btnManageOrders;
	
	@FXML
	/**
	 * Manage Menu Button.
	 */
	private Button btnManageMenu;
	
	@FXML
	/**
	 * Supplier Name Text.
	 */
	private Text supplierName;
	
	@FXML
	/**
	 * Help Button.
	 */
	private Button btnHelp;
	
    @FXML
    /**
     * Restaurant name text.
     */
    private Text resturantName;
    
    @FXML
    /**
     * Company Status text.
     */
    private Text statusText;
    
    @FXML
    /**
     * View Receipts Button.
     */
    private Button viewReceiptsBtn;
	
    /**
     * This method Disconnect the specific client,
     * Log out the connected user and exit.
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
     * This method Log out the connected user and returns to the login form.
     * 
     * @param event event ActionEvent of javaFX.
     */
	@FXML
	public void getLogoutBtn(ActionEvent event) {
		Message message = new Message(Task.LOGOUT,Answer.WAIT_RESPONSE,connectedUser);
		sendToClient(message);
		connectedUser = null;  // after we changed the status in DB we set the connectedUser as null so we can get new login from same client.
		setToLoginScreen(event);  //set the login screen
	}
	
	/**
	 * This is a button for going 
	 * to the next screen of manage orders.
	 * 
	 * @param event event ActionEvent of javaFX.
	 */
	@FXML
	public void getManageOrdersBtn(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		SupplierWorkerManageOrdersController supplierWorkerManageOrders = new SupplierWorkerManageOrdersController();
		supplierWorkerManageOrders.initSupplierWorkerManageOrdersScreen(); // call the init of the next screen	
	}
	
	/**
     * This method loads the manage menu screen.
     * 
     * @param event event ActionEvent of javaFX.
     */
	@FXML
	public void getManageMenuBtn(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		SupplierWorkerManageMenuController supplierWorkerManageMenuController = new SupplierWorkerManageMenuController();
		supplierWorkerManageMenuController.initSupplierWorkerManageMenuScreen(); // call the init of the next screen	
	}
	
	/**
	 * This is pop message for the help button.
	 * 
	 * @param event event ActionEvent of javaFX.
	 */
	@FXML
	public void getHelpBtn(ActionEvent event) {
		PopUpMessages.helpMessage("This is you'r the User-Portal, from here you can access the system functionalities!");
	}

    @FXML
    void getViewReceipts(ActionEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
    	ViewReceiptsSummaryController viewReceiptsSummaryController = new ViewReceiptsSummaryController();
    	viewReceiptsSummaryController.initViewReceiptsSummaryScreen(); // call the init of the next screen	
    }

	/**
	 * Returns to login screen
	 * 
	 * @param event event ActionEvent of javaFX.
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
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("Supplier Main Screen");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Set the supplier details on the supplierPortal according
	 * to the connected supplier.
	 * 
	 * @param arg0
	 * @param arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		viewReceiptsBtn.setDisable(true);
		if(((SupplierWorker)connectedUser).getSupplier().getStatusInSystem().equals(ConfirmationStatus.PENDING_REGISTRATION)) {
			resturantName.setText(((SupplierWorker)connectedUser).getSupplier().getSupplierName()+" - Pending for Approval"); 
			btnManageMenu.setDisable(true);
			btnManageOrders.setDisable(true);
		}
		else {
			resturantName.setText(((SupplierWorker)connectedUser).getSupplier().getSupplierName()); 
			statusText.setText(connectedUser.getStatusInSystem().toString());
			supplierName.setText(connectedUser.getUserFirstName());
			if(connectedUser.getStatusInSystem().equals(ConfirmationStatus.FROZEN)) {
				btnManageMenu.setDisable(true);
				btnManageOrders.setDisable(true);
			
			}
			if(((SupplierWorker)connectedUser).getWorkerPosition().equals(WorkerPosition.MANAGER)) {
				viewReceiptsBtn.setDisable(false);
			}
			if(((SupplierWorker)connectedUser).getWorkerPosition().equals(WorkerPosition.REGULAR)) {
				btnManageMenu.setDisable(true);
			}
		}
	}
}
