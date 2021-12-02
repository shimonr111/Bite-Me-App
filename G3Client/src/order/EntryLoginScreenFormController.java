
package order;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import bitemeclient.BiteMeClientCommunication;
import bitemeclient.BiteMeClientUI;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import orders.Order;

/** This class present the Login Screen and responsible for the functionality of this screen 
 * @author Shimon Rubin
 * @version 02/12/2021
 */
public class EntryLoginScreenFormController{
	
	/**User name of the user*/
	@FXML
	private TextField userNameField;
	
	/**Password field of the user */
	@FXML
	private TextField passwordField;
	
	/**This button login and move forward to the user portal after we insert user and pass */
	@FXML
	private Button btnLogin = null;
	
	/**This button exit from the app immediately */
	@FXML
	private Button btnExit = null;
	
	/**This button pop up message which show to us info about the current page */
	@FXML
	private Button btnHelp = null;
	
	/**This button move back to the previous screen */
	@FXML
	private Button btnClose = null;
	
	
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/order/EntryLoginScreenForm.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/order/EntryLoginScreenForm.css").toExternalForm());
		primaryStage.setTitle("Welcome to BiteMe App!");
		primaryStage.setScene(scene);
		primaryStage.show();
	}


	/**Using login button hide window and go user portal screen */
	public void getLoginBtn(ActionEvent event) throws Exception {
		
	}

	
	/**Using help button in order to show instructions for the current screen */
	public void getHelpBtn(ActionEvent event) throws Exception {
		
	}
	
	/**Using exit button in order to exit from the app */
	public void getExitBtn(ActionEvent event) throws Exception {
		System.exit(0);
	}
	
	
	/**Using close button hide window and go backwards */
	public void getCloseBtn(ActionEvent event) throws Exception {
		EntryHomeScreenFormController prevWindowController = new EntryHomeScreenFormController();
		Stage primaryStage = new Stage();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		prevWindowController.start(primaryStage);
	}


}

