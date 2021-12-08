package controllers_gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.io.IOException;
import communication.Answer;
import communication.Message;
import communication.Task;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class UserPortalOfBranchManagerController extends AbstractBiteMeController {
	@FXML
	private Button btnViewSysReports;
	@FXML
	private Button btnCompanyRegManagement;
	@FXML
	private Button btnEditCustomerInfo;
	@FXML
	private Button btnSupplierReg;
	@FXML
	private Button btnCustomerReg;
	@FXML
	private Button btnExit;
	@FXML
	private Button btnLogout;
	@FXML
	private Button btnAddQuarterlyReports;
	@FXML
	private Label customerNameLabel;
	@FXML
	private Button btnHelp;

	// Event Listener on Button[#btnViewSysReports].onAction
	@FXML
	public void getViewReports(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#btnCompanyRegManagement].onAction
	@FXML
	public void getCompRegManagement(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#btnEditCustomerInfo].onAction
	@FXML
	public void getEditCustomerInfo(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#btnSupplierReg].onAction
	@FXML
	public void getSupplierReg(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#btnCustomerReg].onAction
	@FXML
	public void getCustomerReg(ActionEvent event) {
		// TODO Autogenerated
	}
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
	// Event Listener on Button[#btnAddQuarterlyReports].onAction
	@FXML
	public void getAddQuarterlyReports(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#btnHelp].onAction
	@FXML
	public void getHelpBtn(ActionEvent event) {
		// TODO Autogenerated
	}
	
	/**
	 * 
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
