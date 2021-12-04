package gui;

import java.io.IOException;
import java.security.Principal;
import analyze.IPConfirmationAnalyze;
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
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @author Lior, Guzovsky & Ori, Malka. 
 * Class description: 
 * 
 * This is a class for 
 * controlling the UI IP
 * form.
 * 
 * @version 03/12/2021
 */
public class EntryIPConfirmationFormController {
	/**
	 * Class members description:
	 */
	
	/**
	 * This is an atribute for 
	 * working with this controller
	 * from outside of the class.
	 */
	public static EntryIPConfirmationFormController ipConfirmationFormController;

	@FXML
	private Button btnExit = null;;

	@FXML
	private Button btnConfirmIP = null;;

	@FXML
	private TextField enterIPTxt;

	/**
	 * button to exit from
	 * the frame. 
	 */
	public void exitBtn(ActionEvent event) throws Exception {
		System.out.println("exit IP server configuration");
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
		IPConfirmationAnalyze.userIPConfirmationRequest(event, enterIPTxt.getText());
	}

	/**
	 * This is a function
	 * for setting the login Form.
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void setLoginScreen(ActionEvent event) throws IOException {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				FXMLLoader loader = new FXMLLoader();
				Pane root;
				try {
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				((Node) event.getSource()).getScene().getWindow().hide();
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

	/**
	 * Load the IP form
	 * 
	 * @param primaryStage
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception {
		ipConfirmationFormController = this;
		Parent root = FXMLLoader.load(getClass().getResource("/fxmls/EntryIPConfirmationForm.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Connect to server");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
