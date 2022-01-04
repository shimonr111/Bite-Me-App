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
import users.HrManager;
/**
 *  Class description: 
 * This is a class for 
 * controlling the UI of hrManager
 * form.
 * 
 * @author Mousa, Srour.
 * @author Alexander, Martinov.
 * @author Lior, Guzovsky.
 * @version 25/12/2021
 */
public class UserPortalOfHRManagerController extends AbstractBiteMeController  implements Initializable{
	
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
	private static UserPortalOfHRManagerController userPortalOfHRManagerController;
	
	@FXML
	/**
	 * Company Registration Button.
	 */
	private Button btnCompanyReg;
	
	@FXML
	/**
	 * Workers Confirmation Button.
	 */
	private Button btnBusinessCustomerConfirm;
	
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
	 * Customer Name Label.
	 */
	private Label customerNameLabel;
	
	@FXML
	/**
	 * Company Name Label.
	 */
	private Label companyNameLabel;
	
    @FXML
    /**
     * HR Manager Name Text.
     */
    private Text hrManagerName;
    
    @FXML
    /**
     * Company Name Text.
     */
    private Text companyName;
    
    @FXML
    /**
     * Company Status Text.
     */
    private Text companyStatus;
    
    @FXML
    /*
     * HR Manager status text.
     */
    private Text statusText;

    /**
     * This method Load the screen of company Registration.
     * 
     * @param event ActionEvent of javaFX.
     */
	@FXML
	public void getCompanyReg(ActionEvent event) {
		CompanyRegistartionScreenController companyRegistartionScreenController = new CompanyRegistartionScreenController();
		companyRegistartionScreenController.initCompanyRegistrationScreen();
		((Node) event.getSource()).getScene().getWindow().hide();
	}
	
	/**
     * This method will load the screen
     * of business customers confirmation.
     * 
     * @param event ActionEvent of javaFX.
     */
	@FXML
	public void getBusinessCustomerConfirm(ActionEvent event) {
		BusinessCustomerConfirmationScreenController businessCustomerConfirmationScreenController = new BusinessCustomerConfirmationScreenController();
		businessCustomerConfirmationScreenController.initBusinessCustomerConfirmationScreen();
		((Node) event.getSource()).getScene().getWindow().hide();
	}
	
	/**
     * This method will disconnect the specific client,
     * log out the connected user and exit.
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
     * This method log out the connected  user,
     * and load the login screen.
     * 
     * @param event ActionEvent of javaFX.
     */
	@FXML
	public void getLogoutBtn(ActionEvent event) {
		Message message = new Message(Task.LOGOUT,Answer.WAIT_RESPONSE,connectedUser);
		sendToClient(message);
		connectedUser = null;
		setToLoginScreen(event);
	}
	
	/**
	 * This is pop message for the help button.
	 * 
	 * @param event ActionEvent of javaFX.
	 */
	@FXML
	public void getHelpBtn(ActionEvent event) {
		PopUpMessages.helpMessage("This is you'r the User-Portal, from here you can access the system functionalities!");	

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
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("HR Manager Main Screen");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * This method will be called from the next screen when clicking on back button
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
	 * This method sets the correct values of the hrManager in portal.
	 * 
	 * @param arg0
	 * @param arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		hrManagerName.setText(connectedUser.getUserFirstName());
		if(((HrManager)connectedUser).getCompany().getStatusCompanyInSystem().equals(ConfirmationStatus.PENDING_REGISTRATION)) {
			companyName.setText(((HrManager)connectedUser).getCompany().getCompanyName());
			companyStatus.setText("Not Registered");
			btnBusinessCustomerConfirm.setDisable(true);
			statusText.setText(connectedUser.getStatusInSystem().toString());
		}
		else if(((HrManager)connectedUser).getCompany().getStatusCompanyInSystem().equals(ConfirmationStatus.PENDING_APPROVAL)) {
			companyName.setText(((HrManager)connectedUser).getCompany().getCompanyName());
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
				companyName.setText(((HrManager)connectedUser).getCompany().getCompanyName());
				btnBusinessCustomerConfirm.setDisable(true);
			}
			else if(((HrManager)connectedUser).getCompany().getStatusCompanyInSystem().equals(ConfirmationStatus.PENDING_APPROVAL)) {
				btnBusinessCustomerConfirm.setDisable(true);
				companyName.setText(((HrManager)connectedUser).getCompany().getCompanyName());
			}
			else
				companyName.setText(((HrManager)connectedUser).getCompany().getCompanyName());
		}
		
		
	}
}
