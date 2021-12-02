package order;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

public class PopUpMessages {
	
	public static void helpMessage(String msg) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Message");
		alert.setHeaderText(null);
		alert.setContentText(msg);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		DialogPane dialogPane = alert.getDialogPane();
		alert.showAndWait();
	}

}
