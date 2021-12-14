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
* @author Alexander, Martinov
* Class description: 
* This is a class for 
* controlling the UI of viewing system reports 
* form.
* 
* @version 14/12/2021
*/
public class ViewQuarterlyReportsScreenController extends AbstractBiteMeController implements Initializable {
	@FXML
	private Button btnBack;

	@FXML
	private Button btnExit;

	@FXML
	private Button btnHelp;

	@FXML
	private Text errorText;

	@FXML
	private TableView<?> quarterlyReportsTable;

	@FXML
	private Button viewReportBtn;
	

	private static FXMLLoader loader;
	private static ViewQuarterlyReportsScreenController viewQuarterlyReportsScreenController;

	@FXML
	void getBackBtn(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		UserPortalOfCEOController UPCC = new UserPortalOfCEOController();
		UPCC.initCEOUserPortalAgain();
	}

	@FXML
	void getExitBtn(ActionEvent event) {
		Message message = new Message(Task.LOGOUT,Answer.WAIT_RESPONSE,connectedUser);
		sendToClient(message);
		connectedUser = null;
		System.exit(0);
	}

	@FXML
	void getHelpBtn(ActionEvent event) {
		PopUpMessages.helpMessage("Select a report from the list and click view");
	}

	@FXML
	void getReport(ActionEvent event) {

	}
	
	public void initViewQuarterlyReportsScreen() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/CEO1ViewQuarterlyReportsScreen.fxml").openStream());
					viewQuarterlyReportsScreenController = loader.getController();
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}

