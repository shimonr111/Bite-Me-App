package controllers_gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;

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
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
/**
 * 
 * @author Lior, Guzovsky
 * @author Shimon, Rubin
 * Class description: 
 * This is a class for 
 * controlling the UI of the restaurant 
 * choosing for the private customer.
 * 
 * @version 13/12/2021
 */
public class OrderChooseResturantInOrderScreenController extends AbstractBiteMeController implements Initializable{
	/**
	 * Class members description:
	 */
	
	private static FXMLLoader loader;
    private static OrderChooseResturantInOrderScreenController orderChooseResturantInOrderScreenController;
    /*This array List will help us to get the data we want 
     * about the restaurants in the specific
     *  Branch that the user picked*/
    public static Map<String,String> suppliersList = new HashMap<>(); 

    @FXML
    private ComboBox<String> chooseResComboBox;

    @FXML
    private Button nextBtn;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnHelp;

    @FXML
    private Text errorText;

    /**
     * This is a function for returning
     * to the previous screen of the customer 
     * w4c identification window.
     * 
     * @param event
     */
    @FXML
    void getBackBtn(ActionEvent event) {
    	Platform.runLater(new Runnable() {
			@Override
			public void run() {
				FXMLLoader loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/ORD1W4C_Identification_Screen.fxml").openStream());
					Scene scene = new Scene(root);
					Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) { 
							event.consume();
							Stage.close();
						}
					});
					//scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
					Stage.setTitle("W4C Identification");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
				((Node) event.getSource()).getScene().getWindow().hide();
			}
		});
    }

    @FXML
    void getBtnNext(ActionEvent event) {

    }

    /**
     * This is the function for exiting 
     * the system of Bite me
     * 
     * @param event
     */
    @FXML
    void getExitBtn(ActionEvent event) {
    	Message message = new Message(Task.LOGOUT,Answer.WAIT_RESPONSE,connectedUser);
		sendToClient(message);
		connectedUser = null;
		System.exit(0);
    }

    /**
     * This is a button for 
     * getting information about the current screen
     * 
     * @param event
     */
    @FXML
    void getHelpBtn(ActionEvent event) {
    	PopUpMessages.helpMessage("Please choose the resturant from the list below!");
    }

    /**
     * This is the init for the current 
     * screen, in which we load the fxml.
     * This function is called from the previous 
     * screen controller.
     * 
     */
    public void initChooseRestaurantScreen() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/ORD2ChooseResturantInOrderScreen.fxml").openStream());
					orderChooseResturantInOrderScreenController = loader.getController();
					Scene scene = new Scene(root);
					Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) { 
							event.consume();
							Stage.close();
						}
					});
					//scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
					Stage.setTitle("Choose restaurant");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
    /**
     * This is a function for 
     * initializing the screen.
     * 
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//prepare message for the server to get the relevant restaurants
		Message message = new Message (Task.GET_RESTAURANTS_FOR_ORDER,Answer.WAIT_RESPONSE,connectedUser);
		sendToClient(message);
		//There are no restaurants for this Branch, set message to user
		if(suppliersList == null) {
			errorText.setText("Switch Branch, this one has no restaurants!, go back");
    		errorText.setFill(Color.RED);
		}
		else {
			//add the relevant suppliers to the combo box
			List<String> restaurantsNames = new ArrayList<>();
			for(Entry<String, String> entry: suppliersList.entrySet()) {
				//chooseResComboBox.setValue(entry.getValue());
				restaurantsNames.add(entry.getValue());
			}
			//String[] restaurantsListForComboBox = (String[]) restaurantsNames.toArray();
		}
			
	}

}
