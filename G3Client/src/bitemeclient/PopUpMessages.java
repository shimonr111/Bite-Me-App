package bitemeclient;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

/**
 * 
 * @author Shimon, Rubin.
 * Class description: 
 * 
 * This is a class for pop
 * up messages to the client.
 *
 * @version 02/12/2021
 */
public class PopUpMessages {
	/**
	 * Class members description:
	 */
	
	/**
	 * This function shows pop
	 * up messages to the client UI.
	 * 
	 * @param message
	 */
	public static void helpMessage(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Message");
		alert.setHeaderText(null);
		alert.setContentText(message);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		DialogPane dialogPane = alert.getDialogPane();
		alert.showAndWait();
	}

}
