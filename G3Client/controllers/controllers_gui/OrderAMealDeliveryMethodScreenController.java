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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import orders.DeliverySupplyMethod;
import orders.DeliveryType;
import orders.Order;
import users.Branch;
import util.Constans;
import util.DataLists;


/**
 * 
 * @author Lior, Guzovsky
 * @author Shimon, Rubin
 * 
 * Class description: 
 * This is a class for controlling 
 * the UI of the delivery method
 * that was picked by the user.
 * 
 * 
 * 
 * @version 17/12/2021
 */
public class OrderAMealDeliveryMethodScreenController extends AbstractBiteMeController implements Initializable{
	 /**
	 * Class members description:
	 */

	private static FXMLLoader loader;
    private static OrderAMealDeliveryMethodScreenController orderAMealDeliveryMethodScreenController;
    private static Order order;
    private static DeliverySupplyMethod deliveryInformation;
    @FXML
    private TextField addressTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField phoneTxtField;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnHelp;

    @FXML
    private Button choosePayMethBtn;

    @FXML
    private Text errorText;

    @FXML
    private TextField deliveryFeeTextField;

    @FXML
    private TextField lastNameTextField;
    
    @FXML
    private Button btnNext;
    
    @FXML
    private ComboBox<String> cityCombo;
    
    @FXML
    private ComboBox<String> streetCombo;
    
    @FXML
    private ComboBox<String> phonePrefixCombo;

	/**
     * Back button for the 
     * 
     * 
     * @param event
     */
    @FXML
    void getBackBtn(ActionEvent event) {
    	//fix the back button so that the price will be without the fee and the discount
    	switch(order.getTimeType()) {
  		case PRE:
  			order.setTotalPrice(order.getTotalPrice()/(1-Constans.PRE_ORDER_DISCOUNT)); //set discount according to the instructions
  			order.setTotalPrice(order.getTotalPrice()-((DeliverySupplyMethod)order.supplyMethodInformation).getDeliveryFee()); //update the total cost of the order
  			break;
  		case REGULAR:
  			order.setTotalPrice(order.getTotalPrice()-((DeliverySupplyMethod)order.supplyMethodInformation).getDeliveryFee()); //update the total cost of the order
  			break;
  		default:
  			break;
	  }
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
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("Fill delivery details");
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
    	PopUpMessages.helpMessage("Please enter your information as written for the delivery!");
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
    		
    		if (!(isInt(phoneTxtField))) {
        		errorText.setText("Please, insert only numbers in the phone field!");
        		errorText.setFill(Color.RED);
        	}
    		
    		else {
    		deliveryInformation.setReceiverFirstName(firstNameTextField.getText());
    		deliveryInformation.setReceiverLastName(lastNameTextField.getText());
    		deliveryInformation.setReceiverPhoneNumber(phonePrefixCombo.getValue() + phoneTxtField.getText());
    		deliveryInformation.setReciverAddress(cityCombo.getValue() + streetCombo.getValue() + addressTextField.getText());
    		
    		//move to next screen
    		 ((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
    		 OrderSummaryScreenController orderSummaryScreenController = new OrderSummaryScreenController();
    		 orderSummaryScreenController.initOrderSummaryScreen(order); // call the init of the next screen
    	}
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
    			phoneTxtField.getText().equals("") || addressTextField.getText().equals("") || 
    			(cityCombo.getValue() == null) || (streetCombo.getValue() == null) || (phonePrefixCombo.getValue() == null)){
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
    public void initDeliveryMethodScreen(Order order) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/ORD5OrderAMealDeliveryMethod.fxml").openStream());
					orderAMealDeliveryMethodScreenController = loader.getController();
					Scene scene = new Scene(root);
					Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) { 
							event.consume();
							Stage.close();
						}
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("Delivery");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		OrderAMealDeliveryMethodScreenController.order=order; // save the order from the previous screen	
	}


  	 /**
     * This is a function for 
     * initializing the screen.
     * 
     */
  	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
  		deliveryInformation = new DeliverySupplyMethod(order.getSupplyId(),order.getOrderNumber(),null,null,null,DeliveryType.REGULAR,null);
  		order.setSupplyMethodInformation(deliveryInformation);
  		calcTotalBill();
  		deliveryFeeTextField.setText(String.valueOf(Constans.REGULAR_DELIVERY_FEE_IN_NIS));	
  		deliveryFeeTextField.setDisable(true);
  		switch(order.getBranch()) {
  		case NORTH:
  	  		cityCombo.getItems().addAll(DataLists.getNorthCitys());
  			break;
  		case CENTER:
  	  		cityCombo.getItems().addAll(DataLists.getCenterCitys());
  			break;
  		case SOUTH:
  	  		cityCombo.getItems().addAll(DataLists.getSouthCitys());
  			break;
  		default:
  		break;
  			
  		}
  		streetCombo.getItems().addAll(DataLists.getStreets());
  		phonePrefixCombo.getItems().addAll(DataLists.getPhonePrefix());
		}
		

  	/**
  	 * Calculate the total bill depending on
  	 * the fee and the time type order (Pre order and Regular)
  	 * 
  	 */
  	private void calcTotalBill() {
  		order.setTotalPrice(order.getTotalPrice()+deliveryInformation.getDeliveryFee()); //update the total cost of the order
  		switch(order.getTimeType()) {
  		case PRE:
  			order.setTotalPrice(order.getTotalPrice()*(1-Constans.PRE_ORDER_DISCOUNT)); //set discount according to the instructions
  			break;
  		default:
  			break;
  		
  		}
  	}
  	
  	/**
 	 * this method checks if the textField contains numbers only.
 	 * @param txtField
 	 * @return true or false according to the input.
 	 */
 	public boolean isInt(TextField txtField) {
 		try {
 	 		int checkIfInt = Integer.parseInt(txtField.getText());
 	 		if(checkIfInt>0) {
 	 			return true;
 	 		}
 	 		return false;
 	 		}catch(NumberFormatException e) {
 	 			return false;
 	 		}
 	}
}
