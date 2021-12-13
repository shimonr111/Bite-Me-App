package controllers_gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

    @FXML
    private ComboBox<?> chooseResComboBox;

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

    @FXML
    void getResturantComboBox(ActionEvent event) {

    }

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
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
