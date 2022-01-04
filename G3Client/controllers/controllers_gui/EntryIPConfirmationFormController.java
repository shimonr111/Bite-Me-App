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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *  Class description: 
 * This is a class for 
 * controlling the UI IP
 * form.
 * @author Lior, Guzovsky.
 * @author Ori, Malka. 
 * @author Mousa, Srour.
 * @author Alexander, Martinov
 * 
 * @version 06/12/2021
 */
public class EntryIPConfirmationFormController extends AbstractBiteMeController{
	/**
	 * Class members description:
	 */
	
	@FXML
	/**
	 * The Exit Button.
	 */
	private Button btnExit = null;;

	@FXML
	/**
	 * The Confirm Button.
	 */
	private Button btnConfirmIP = null;;

	@FXML
	/**
	 * The Text Field of enter Ip .
	 */
	private TextField enterIPTxt;

	/**
	 * Load the IP form
	 * 
	 * @param primaryStage  the Stage of the screen.
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/fxmls/EntryIPConfirmationForm.fxml"));
		Scene scene = new Scene(root);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		scene.setOnMousePressed(pressEvent -> {
		    scene.setOnMouseDragged(dragEvent -> {
		        primaryStage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
		        primaryStage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
		    });
		});

		primaryStage.setTitle("Connect to server");
		primaryStage.setScene(scene);
		primaryStage.show();
		//add listener and override relevant methods with implementation
		AnalyzeMessageFromServer.addClientListener(new AnalyzeClientListener(){ 
		@Override
		public void clientIpConfirmed() {
			try {
				AnalyzeMessageFromServer.removeClientListener(this);
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
	 * Button to exit from
	 * the frame. 
	 * 
	 * @param event ActionEvent of javaFX.
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
	 * @param event ActionEvent of javaFX.
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
	 * @param event ActionEvent of javaFX.
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
					root = loader.load(getClass().getResource("/fxmls/EntryHomeScreenForm.fxml").openStream());
					Scene scene = new Scene(root);
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("Home");
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
	 * @param event ActionEvent of javaFX.
	 */
	public void setErrorToClientUI(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		return;
	}

}
