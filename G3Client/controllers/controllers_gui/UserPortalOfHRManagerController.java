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
import users.Branch;
import users.ConfirmationStatus;
import users.HrManager;
import javafx.scene.control.ComboBox;

/**
 * 
 * @author Mousa, Srour
 * @author Alexander, Martinov
 * @author Lior, Guzovsky
 * 
 * Class description: 
 * This is a class for 
 * controlling the UI of hrManager
 * form.
 * 
 * @version 25/12/2021
 */
public class UserPortalOfHRManagerController extends AbstractBiteMeController  implements Initializable{
	

	@FXML
	private Button btnCompanyReg;
	@FXML
	private Button btnBusinessCustomerConfirm;
	@FXML
	private Button btnExit;
	@FXML
	private Button btnLogout;
	@FXML
	private Button btnHelp;
	@FXML
	private Label customerNameLabel;
	@FXML
	private Label companyNameLabel;
    @FXML
    private Text hrManagerName;
    @FXML
    private Text companyName;
    @FXML
    private Text companyStatus;
    @FXML
    private Text statusText;

	public static FXMLLoader loader;
	private static UserPortalOfHRManagerController userPortalOfHRManagerController;
	
	// Event Listener on Button[#btnCompanyReg].onAction
	@FXML
	public void getCompanyReg(ActionEvent event) {
		CompanyRegistartionScreenController companyRegistartionScreenController = new CompanyRegistartionScreenController();
		companyRegistartionScreenController.initCompanyRegistrationScreen();
		((Node) event.getSource()).getScene().getWindow().hide();
	}
	// Event Listener on Button[#btnBusinessCustomerConfirm].onAction
	@FXML
	public void getBusinessCustomerConfirm(ActionEvent event) {
		BusinessCustomerConfirmationScreenController businessCustomerConfirmationScreenController = new BusinessCustomerConfirmationScreenController();
		businessCustomerConfirmationScreenController.initBusinessCustomerConfirmationScreen();
		((Node) event.getSource()).getScene().getWindow().hide();
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
		Message message = new Message(Task.LOGOUT,Answer.WAIT_RESPONSE,connectedUser);
		sendToClient(message);
		connectedUser = null;
		setToLoginScreen(event);
	}
	
	/**
	 * This is pop message for the help button.
	 * @param event
	 */
	// Event Listener on Button[#btnHelp].onAction
	@FXML
	public void getHelpBtn(ActionEvent event) {
		PopUpMessages.helpMessage("This is the Main screen of the HR manager, Please press any button!");	

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
	public void initHrManagerUserPortal(Stage primaryStage,String fxmlPath) {
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
					userPortalOfHRManagerController = loader.getController();
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
	 * this method will be called from the next screen when clicking on back button
	 */
	public void initPortalAgain() {
		loader = new FXMLLoader();
		Stage primaryStage = new Stage();
		Pane root = null;
		try {
			root = loader.load(getClass().getResource("/fxmls/UserPortalOfHRManager.fxml").openStream());
			//root = FXMLLoader.load(getClass().getResource("/fxmls/LoginScreen.fxml"));
			userPortalOfHRManagerController = loader.getController();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();/* show the new screen */
	}
	
	/**
	 * This method sets the correct values of the hrManager in portal.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		hrManagerName.setText(connectedUser.getUserFirstName());
		if(((HrManager)connectedUser).getcompanyOfBusinessCustomer().getStatusCompanyInSystem().equals(ConfirmationStatus.PENDING_REGISTRATION)) {
			companyName.setText(((HrManager)connectedUser).getcompanyOfBusinessCustomer().getCompanyName());
			companyStatus.setText("Not Registered");
			btnBusinessCustomerConfirm.setDisable(true);
			statusText.setText(connectedUser.getStatusInSystem().toString());
		}
		else if(((HrManager)connectedUser).getcompanyOfBusinessCustomer().getStatusCompanyInSystem().equals(ConfirmationStatus.PENDING_APPROVAL)) {
			companyName.setText(((HrManager)connectedUser).getcompanyOfBusinessCustomer().getCompanyName());
			companyStatus.setText("Pending Approval");
			btnCompanyReg.setDisable(true);
			btnBusinessCustomerConfirm.setDisable(true);
			statusText.setText(connectedUser.getStatusInSystem().toString());
		}
		else {
			btnCompanyReg.setDisable(true);
			statusText.setText(connectedUser.getStatusInSystem().toString());
			companyStatus.setText("Confirmed");
			if(connectedUser.getStatusInSystem().equals(ConfirmationStatus.FROZEN)) {
				companyName.setText(((HrManager)connectedUser).getcompanyOfBusinessCustomer().getCompanyName());
				btnBusinessCustomerConfirm.setDisable(true);
			}
			else if(((HrManager)connectedUser).getcompanyOfBusinessCustomer().getStatusCompanyInSystem().equals(ConfirmationStatus.PENDING_APPROVAL)) {
				btnBusinessCustomerConfirm.setDisable(true);
				companyName.setText(((HrManager)connectedUser).getcompanyOfBusinessCustomer().getCompanyName());
			}
			else
				companyName.setText(((HrManager)connectedUser).getcompanyOfBusinessCustomer().getCompanyName());
		}
		
		
	}
}
