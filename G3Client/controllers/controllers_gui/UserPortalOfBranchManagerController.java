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
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import users.ConfirmationStatus;

/**
 *  Class description: 
 * This is a class for 
 * controlling the UI of Branch Manager
 * form.
 * 
 * @author Mousa, Srour.
 * @author Alexander, Martinov.
 * 
 * @version 15/12/2021
 */
public class UserPortalOfBranchManagerController extends AbstractBiteMeController implements Initializable{
	
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
	private static UserPortalOfBranchManagerController userPortalOfBranchManagerController;
	
	@FXML
	/**
	 * View System Reports Button.
	 */
	private Button btnViewSysReports;
	
	@FXML
	/**
	 * Company Registration Management Button.
	 */
	private Button btnCompanyRegManagement;
	
	@FXML
	/**
	 * Edit Customer Button.
	 */
	private Button btnEditCustomerInfo;
	
	@FXML
	/**
	 * Supplier Registration Button.
	 */
	private Button btnSupplierReg;
	
	@FXML
	/**
	 * Customer Registration Button.
	 */
	private Button btnCustomerReg;
	
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
	 * Add Quarterly Reports Button.
	 */
	private Button btnAddQuarterlyReports;
	
	@FXML
	/**
	 * Customer Name Table.
	 */
	private Label customerNameLabel;
	
	@FXML
	/**
	 * Help Button.
	 */
	private Button btnHelp;
	
    @FXML
    /**
     * The text to display the branch manager name.
     */
    private Text branchManagerName;
    
    @FXML
    /**
     * The text to display the branch manager's status in system.
     */
    private Text statusText;
    
    @FXML
    /**
     * The text to display the Branch.
     */
    private Text branchName;
    

    /**
     * This method Load the screen the shows the
     * reports.
     * 
     * @param event ActionEvent of javaFx.
     */
	@FXML
	public void getViewReports(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		ViewSystemReportsScreenController viewSystemReportsScreenController = new ViewSystemReportsScreenController();
		viewSystemReportsScreenController.initViewSystemReportsScreen();
	}
	
	/**
     * This method will load the screen
     * of company menagement.
     * 
     * @param event ActionEvent of javaFx.
     */
	@FXML
	public void getCompRegManagement(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		CompanyRegistartionManagementScreenController companyRegistartionManagementScreenController = new CompanyRegistartionManagementScreenController();
		companyRegistartionManagementScreenController.initCompanyRegistrationManagementScreen();
	}
	
	/**
     * This method will load the screen of 
     * Edit Customer.
     * 
     * @param event ActionEvent of javaFx.
     */
	@FXML
	public void getEditCustomerInfo(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		EditCustomerInformationScreenController editCustomerInformationScreenController = new EditCustomerInformationScreenController();
		editCustomerInformationScreenController.initEditCustomerInformationScreen(); // call the init of the next screen
	}
	
	/**
     * This method will load the screen
     * of supplier registration.
     * 
     * @param event ActionEvent of javaFx.
     */
	@FXML
	public void getSupplierReg(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		SupplierRegistrationScreenController supplierRegistrationScreenController = new SupplierRegistrationScreenController();
		supplierRegistrationScreenController.initSupplierRegistrationScreen();
	}
	
	/**
     * This method will load the screen of
     * Custoemr Registration.
     * 
     * @param event ActionEvent of javaFx.
     */
	@FXML
	public void getCustomerReg(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		UsersRegistrationScreenController usersRegistrationScreenController = new UsersRegistrationScreenController();
		usersRegistrationScreenController.initUserRegistrationScreen();; // call the init of the next screen
	}
	
	/**
     * This method will disconnect the specific client,
     * log out the connected user then exit.
     * 
     * @param event ActionEvent of javaFx.
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
     * This method will log out the  connected user,
     * then loads the previous screen.
     * 
     * @param event ActionEvent of javaFx.
     */
	@FXML
	public void getLogoutBtn(ActionEvent event) {
		// TODO Autogenerated
		Message message = new Message(Task.LOGOUT,Answer.WAIT_RESPONSE,connectedUser);
		sendToClient(message);
		connectedUser = null;  
		setToLoginScreen(event);  
	}
	
	/**
     * This method will load the screen of
     * Adding quarterly reports.
     * 
     * @param event ActionEvent of javaFx.
     */
	@FXML
	public void getAddQuarterlyReports(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		AddQuarterlyReportsScreenController addQuarterlyReportsScreenController = new AddQuarterlyReportsScreenController();
		addQuarterlyReportsScreenController.initAddQuarterlyReportsScreen();
	}
	
	/**
	 * This is pop message for the help button.
	 * 
	 * @param event ActionEvent of javaFx.
	 */
	@FXML
	public void getHelpBtn(ActionEvent event) {
		PopUpMessages.helpMessage("This is you'r the User-Portal, from here you can access the system functionalities!");
	}
	
	/**
	 * Returns to login screen
	 * 
	 * @param event ActionEvent of javaFx.
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
	 * @param primaryStage The Stage of the current Screen.
	 * @param fxmlPath The Patch of the current screen.
	 */
	public void initBranchManagerUserPortal(Stage primaryStage,String fxmlPath) {
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
					userPortalOfBranchManagerController = loader.getController();
					Scene scene = new Scene(root);
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("Branch Manager Main Screen");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
     * This method will be called from the next screen, 
     * it will load this screen again.
     */
	public void initPortalAgain() {
		loader = new FXMLLoader();
		Stage primaryStage = new Stage();
		Pane root = null;
		try {
			root = loader.load(getClass().getResource("/fxmls/UserPortalOfBranchManager.fxml").openStream());
			userPortalOfBranchManagerController = loader.getController();
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
	 * This method sets the correct values of the branchManager in portal.
	 * 
	 * @param arg0
	 * @param arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		branchManagerName.setText(connectedUser.getUserFirstName());
		statusText.setText(connectedUser.getStatusInSystem().toString());
		branchName.setText(connectedUser.getHomeBranch().toString());
		if(connectedUser.getStatusInSystem().equals(ConfirmationStatus.FROZEN)) {
			btnAddQuarterlyReports.setDisable(true);
			btnCustomerReg.setDisable(true);
			btnCompanyRegManagement.setDisable(true);
			btnSupplierReg.setDisable(true);
			btnEditCustomerInfo.setDisable(true);
			btnViewSysReports.setDisable(true);
		}
		
		
	}
}
