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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import orders.AbatractSupplyMethod;
import orders.Order;
import orders.TakeAwaySupplyMethod;
import util.Constans;


/**
 * 
 * @author Lior, Guzovsky
 * @author Shimon, Rubin
 * Class description: 
 * This is a class for 
 * 
 * 
 * 
 * @version 17/12/2021
 */
public class OrderAMealTAMethodScreenController extends AbstractBiteMeController implements Initializable{
	/**
	 * Class members description:
	 */

	private static FXMLLoader loader;
    private static OrderAMealTAMethodScreenController orderAMealTAMethodScreenController;
    private static Order order;
    private static TakeAwaySupplyMethod takeAwayInformation;
    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField phoneTxtField;

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

    @FXML
    private TextField lastNameTextField;

     /**
     * Back button for the 
     * 
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
					root = loader.load(getClass().getResource("/fxmls/ORD4ChooseSupplyMethod.fxml").openStream());
					Scene scene = new Scene(root);
					Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) { 
							event.consume();
							Stage.close();
						}
					});
					//scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
					Stage.setTitle("Choose supply method");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
				((Node) event.getSource()).getScene().getWindow().hide();
			}
		});

    }

        /**
     * Exit from screen and update
     * DB.
     * 
     * @param event
     */
    @FXML
    void getExitBtn(ActionEvent event) {
    	Message message = new Message(Task.LOGOUT,Answer.WAIT_RESPONSE,connectedUser);
		sendToClient(message);
		connectedUser = null;
		Message disconnectMessage= new Message(Task.CLIENT_DICONNECT,Answer.WAIT_RESPONSE,null);
		sendToClient(disconnectMessage);
		System.exit(0);
    }

    /**
     * This is a method for getting 
     * information for the user
     * 
     * @param event
     */
    @FXML
    void getHelpBtn(ActionEvent event) {
    	PopUpMessages.helpMessage("Please insert information for the Take away process");
    }



   /**
     * This function is used for
     * switching to the next screen and 
     * 
     * 
     *
     * 
     * @param event
     */
    @FXML
    void getNextBtn(ActionEvent event) {
    	if(isEmptyFields()) {
    		errorText.setText("Please, fill all the fields!");
    		errorText.setFill(Color.RED);
    	}
    	else {
    		takeAwayInformation = new TakeAwaySupplyMethod(order.getSupplyId(),order.getOrderNumber(),firstNameTextField.getText(),lastNameTextField.getText(),
    				phoneTxtField.getText());
    		//move to next screen
    		 ((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
    		 OrderSummaryScreenController orderSummaryScreenController = new OrderSummaryScreenController();
    		 orderSummaryScreenController.initOrderSummaryScreen(order,takeAwayInformation); // call the init of the next screen
    	}
    }
    
    /**
     * This is a method
     * for checking all the validity of the 
     * fields received by the user
     * 
     * @return boolean if fields are empty
     */
    private boolean isEmptyFields() {
    	if(firstNameTextField.getText().equals("") || lastNameTextField.getText().equals("") ||
    			phoneTxtField.getText().equals("")) {
    		return true;
    	}
    	return false;
    }

   /**
     * This is the init for the current 
     * screen, in which we load the fxml.
     * This function is called from the previous 
     * screen controller.
     * 
     */
  public void initTAMethodScreen(Order order) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/ORD5OrderAMealTAMethod.fxml").openStream());
					orderAMealTAMethodScreenController = loader.getController();
					Scene scene = new Scene(root);
					Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) { 
							event.consume();
							Stage.close();
						}
					});
					//scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
					Stage.setTitle("Take Away");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		OrderAMealTAMethodScreenController.order=order; // save the order from the previous screen	
	}





  	/**
     * This is a function for 
     * initializing the screen.
     * 
     */
  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {

  }

}
