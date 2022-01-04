package bitemeclient;

import java.util.Optional;

import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Class description: 
 * This is a class for pop
 * up messages to the client.
 *
 * @author Mousa, Srour. 
 * @author Shimon, Rubin.
 * @author Lior, Guzovsky.
 * @version 20/12/2021
 */
public class PopUpMessages {

	/**
	 * This function shows pop
	 * up messages to the client UI.
	 * 
	 * @param message the message that we will display .
	 */
	public static void helpMessage(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Help?");
		alert.setHeaderText(null);
		alert.setContentText(message);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add("/css/G3_BiteMe_Main_Style_Sheet.css");
		dialogPane.setPrefHeight(200);
		dialogPane.setPrefWidth(450);
		dialogPane.setStyle("fx-text-alignment: left;");
		centerButtons(dialogPane);
		alert.showAndWait();
	}
	
	/**
	 * This method displays a confirmation box with the string (message)
	 * this box appears with 2 buttons OK and Cancel
	 * the method returns Optional<ButtonType> , we have to check if its equals to OK to continue , other wise we do nothing.
	 * example after calling this method : if(confirmationMessage(message) == ButtonType.OK)
	 * @param message message the message that we will display .
	 * @return the option that the user choose .
	 */
	public static Optional<ButtonType> confirmationMessage(String message ) {
		Alert.AlertType type = Alert.AlertType.CONFIRMATION;
		Alert alert = new Alert (type,"");
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.getDialogPane().setHeaderText(message);
		alert.getDialogPane().getStylesheets().add("/css/G3_BiteMe_Main_Style_Sheet.css");
		centerButtons(alert.getDialogPane());
		return alert.showAndWait();
	}
	
	
	/**
	 * This is a message for the user to that
	 * informs him about something (such as finishing the order
	 * process).
	 * 
	 * @param message message the message that we will display .
	 * @return the option that the user choose .
	 */
	public static Optional<ButtonType> updateMessage(String message ){
		Alert.AlertType type = Alert.AlertType.INFORMATION;
		Alert alert = new Alert (type,"");
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.getDialogPane().setHeaderText(message);
		alert.getDialogPane().getStylesheets().add("/css/G3_BiteMe_Main_Style_Sheet.css");
		centerButtons(alert.getDialogPane());
		return alert.showAndWait();
	}
	

	/**
	 * 
	 * Simulation Model for sending Email \ SMS 
	 * from the Supplier to the client about his 
	 * Order status.
	 * @param title the title of the message we want to display.
	 * @param Headermessage the header of the message.
	 * @param bodyMessage the message we want to display.
	 */
	public static void simulationMessage(String title ,String Headermessage, String bodyMessage) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(Headermessage);
		alert.setContentText(bodyMessage);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add("/css/G3_BiteMe_Main_Style_Sheet.css");
		centerButtons(dialogPane);
		alert.showAndWait();
	}
	
	/**
	 * this method is to put the buttons of Alert class in the center.
	 * @param dialogPane a dialog pane.
	 */
	public static void centerButtons(DialogPane dialogPane) {
        Region spacer = new Region();
        ButtonBar.setButtonData(spacer, ButtonBar.ButtonData.BIG_GAP);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        dialogPane.applyCss();
        HBox hboxDialogPane = (HBox) dialogPane.lookup(".container");
        hboxDialogPane.getChildren().add(spacer);
     }
	
}
