package controllers_gui;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * 
 * @author Alexander, Martinov
 * Class description: 
 * This is a class for 
 * controlling the UI of viewing system reports 
 * form.
 * 
 * @version 14/12/2021
 */
public class ViewSystemReportsScreenController extends AbstractBiteMeController implements Initializable {

	/**
	 * Class members description:
	 */
	@FXML
	private Button btnBack;

	@FXML
	private Button btnExit;

	@FXML
	private Button btnHelp;

	@FXML
	private Text errorText;

	@FXML
	private TableView<?> sysReportsTable;

	@FXML
	private Button viewReportBtn;

	private static FXMLLoader loader;
	private static ViewSystemReportsScreenController viewSystemReportsScreenController;

	/**
	 * this method calls the setBranchManagerPortal to get back the previous screen
	 * this method works immedietly after clicking on back button.
	 * @param event
	 */
	@FXML
	void getBackBtn(ActionEvent event) {
		setBranchManagerPortal(event);

	}

	@FXML
	void getExitBtn(ActionEvent event) {
		Message message = new Message(Task.LOGOUT,Answer.WAIT_RESPONSE,connectedUser);
		sendToClient(message);
		connectedUser = null;
		Message disconnectMessage= new Message(Task.CLIENT_DICONNECT,Answer.WAIT_RESPONSE,null);
		sendToClient(disconnectMessage);
		System.exit(0);
	}

	@FXML
	void getHelpBtn(ActionEvent event) {
		PopUpMessages.helpMessage("Select a report from the list and click view");
	}

	@FXML
	void getViewReport(ActionEvent event) {

	}
	  /**
     * loads the previous screen after clicking on back button.
     */
	public void setBranchManagerPortal(ActionEvent event) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				FXMLLoader loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/UserPortalOfBranchManager.fxml").openStream());
					UserPortalOfBranchManagerController UOBMC = new UserPortalOfBranchManagerController();
					UOBMC.initPortalAgain();
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}
	/**
	 * This is the initialization function for this 
	 * screen.
	 * 
	 */
	public void initViewSystemReportsScreen() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/BM1ViewSystemReportsScreen.fxml").openStream());
					viewSystemReportsScreenController = loader.getController();
					Scene scene = new Scene(root);
					Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) { 
							event.consume();
							Stage.close();
						}
					});
					Stage.setTitle("View Reports");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

}


