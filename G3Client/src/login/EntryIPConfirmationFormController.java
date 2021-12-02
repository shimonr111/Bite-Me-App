
package login;

import bitemeclient.BiteMeClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EntryIPConfirmationFormController {

	@FXML
	private Button btnExit = null;;

	@FXML
	private Button btnConfirmIP = null;;

	@FXML
	private TextField enterIPTxt;

	/* button for exit from the frame */
	public void exitBtn(ActionEvent event) throws Exception {
		System.out.println("exit IP server configuration");
		System.exit(0);
	}

	/*
	 * button for confirmation and replacing to a new screen that asks for the order
	 * number
	 */
	public void Confirm(ActionEvent event) throws Exception {
		BiteMeClientUI.startController(enterIPTxt.getText()); /* get the entered IP by the client */
		BiteMeClientUI.biteMeClientController.accept("confirm"); // send message of confirm to the server after button
																	// pressed
		// replace main screen after connecting to server using IP
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/order/EntryHomeScreenForm.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Welcome to BiteMe App!");
		primaryStage.setScene(scene);
		primaryStage.show();/* show the new screen */
	}

	/* load the IP frame */
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/login/EntryIPConfirmationForm.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Connect to server");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
