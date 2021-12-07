package controllers_gui;

import java.io.IOException;

import bitemeclient.BiteMeClientUI;
import clientanalyze.AnalyzeClientListener;
import clientanalyze.AnalyzeMessageFromServer;
import communication.Answer;
import communication.Message;
import communication.Task;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @author Lior, Guzovsky.
 * @author Ori, Malka. 
 * @author Mousa, Srour.
 * @author Alexander, Martinov
 * Class description: 
 * 
 * This is a class for 
 * controlling the UI IP
 * form.
 * 
 * @version 06/12/2021
 */
public class EntryIPConfirmationFormController extends AbstractBiteMeController{
	/**
	 * Class members description:
	 */
	
	/**
	 * This is an atribute for 
	 * working with this controller
	 * from outside of the class.
	 */
	
	@FXML
	private Button btnExit = null;;

	@FXML
	private Button btnConfirmIP = null;;

	@FXML
	private TextField enterIPTxt;

	/**
	 * Load the IP form
	 * 
	 * @param primaryStage
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/fxmls/EntryIPConfirmationForm.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Connect to server");
		primaryStage.setScene(scene);
		primaryStage.show();
		//add listener and override relevant methods with implementation
		AnalyzeMessageFromServer.addClientListener(new AnalyzeClientListener(){ 
		@Override
		public void clientIpConfirmed() {
			try {
				setHomeScreen(primaryStage); //primary stage passed to hide previous window
				} catch (IOException e) {
			}
		}

		@Override
		public void clientIpNotConfirmed() {
			//TBD: add notification to client
			}
		});
	}
	
	/**
	 * button to exit from
	 * the frame. 
	 */
	public void exitBtn(ActionEvent event) throws Exception {
		System.out.println("exit IP server configuration");
		//TBD: add sending the client ip to the server (Disconnected)
		System.exit(0);
	}

	/**
	 * Button for confirmation 
	 * and replacing to a new screen 
	 * that asks for the order
	 * number.
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void Confirm(ActionEvent event) throws Exception {
		Task task = Task.CONFIRM_IP;
		Answer answer = Answer.WAIT_RESPONSE;
		Message message = new Message(task, answer, null);
		BiteMeClientUI.startController(enterIPTxt.getText()); /* get the entered IP by the client */
		sendToClient(message);
	}

	/**
	 * This is a function
	 * for setting the login Form.
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void setHomeScreen(Stage primaryStage) throws IOException { //should be changed to start method of entry home screen form
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				FXMLLoader loader = new FXMLLoader();
				Pane root;
				try {
					primaryStage.hide(); 
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/EntryHomeScreenForm.fxml").openStream());
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
	 * This is a function for setting Error 
	 * to the Client UI 
	 * 
	 * TBD: add textfield to IP confirmation screen - ip inccorect
	 * 
	 * @param event
	 */
	public void setErrorToClientUI(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		return;
	}

}
