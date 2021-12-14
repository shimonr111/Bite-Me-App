package bitemeclient;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Mousa, Srour. 
 * @author Shimon, Rubin.
 *
 * Class description: 
 * 
 * This is a class for pop
 * up messages to the client.
 *
 * @version 14/12/2021
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
		alert.setTitle("Help?");
		alert.setHeaderText(null);
		alert.setContentText(message);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		DialogPane dialogPane = alert.getDialogPane();
		alert.showAndWait();
	}
	
	/**
	 * This method displays a confirmation box with the string (message)
	 * this box appears with 2 buttons OK and Cancel
	 * the method returns Optional<ButtonType> , we have to check if its equals to OK to continue , other wise we do nothing.
	 * example after calling this method : if(confirmationMessage(message) == ButtonType.OK)
	 * @param message
	 * @return
	 */
	public static Optional<ButtonType> confirmationMessage(String message ) {
		Alert.AlertType type = Alert.AlertType.CONFIRMATION;
		Alert alert = new Alert (type,"");
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.getDialogPane().setHeaderText(message);
		return alert.showAndWait();
		
	}
	
}
