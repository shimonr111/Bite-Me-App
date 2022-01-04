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
import javafx.stage.StageStyle;
import orders.DeliverySupplyMethod;
import orders.DeliveryType;
import orders.Order;
import util.Constans;
import util.DataLists;

/**
 *  
 * Class description: 
 * This is a class for controlling 
 * the UI of the delivery method
 * that was picked by the user.
 */
/**
 * 
 * @author Lior, Guzovsky.
 * @author Shimon, Rubin.
 * @author Mousa, Srour.
 * 
 * @version 17/12/2021
 */
public class OrderAMealDeliveryMethodScreenController extends AbstractBiteMeController implements Initializable{
	
	 /**
	 * Class members description:
	 */
	
	/**
	 * The FXMLLoader of the current screen.
	 */
	private static FXMLLoader loader;
	
	/**
	 * A static object of the current class.
	 */
    private static OrderAMealDeliveryMethodScreenController orderAMealDeliveryMethodScreenController;
    
    /**
     * Static Order object for the current order flow.
     */
    private static Order order;
    
    /**
     * Static Delivery Type, information that we have to know .
     */
    private static  DeliveryType deliveryType;
    
    /**
     * Static Supply Method, information that we have to know .
     */
    private static DeliverySupplyMethod deliveryInformation;
    
    @FXML
    /**
     * Address Text Field.
     */
    private TextField addressTextField;

    @FXML
    /**
     * First Name Text Field.
     */
    private TextField firstNameTextField;

    @FXML
    /**
     * Phone Number Text Field.
     */
    private TextField phoneTxtField;

    @FXML
    /**
     * The Exit Button.
     */
    private Button btnExit;

    @FXML
    /**
     * The Back Button.
     */
    private Button btnBack;

    @FXML
    /**
     * The Help Button.
     */
    private Button btnHelp;

    @FXML
    /**
     * The Choose Payment Method Button.
     */
    private Button choosePayMethBtn;

    @FXML
    /**
     * The empty text that we use to display messages to the user.
     */
    private Text errorText;

    @FXML
    /**
     * Delivery Fee Text Field.
     */
    private TextField deliveryFeeTextField;

    @FXML
    /**
     * Last Name Text Field.
     */
    private TextField lastNameTextField;
    
    @FXML
    /**
     * The Next Button.
     */
    private Button btnNext;
    
    @FXML
    /**
     * ComboBox of the cities in the specific branch.
     */
    private ComboBox<String> cityCombo;
    
    @FXML
    /**
     * ComboBox of the streets of the specific city.
     */
    private ComboBox<String> streetCombo;
    
    @FXML
    /**
     * ComboBox of Phone Number Prefix.
     */
    private ComboBox<String> phonePrefixCombo;

	/**
     * Back button for the 
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getBackBtn(ActionEvent event) {
    	//fix the back button so that the price will be without the fee and the discount
    	switch(order.getTimeType()) {
  		case PRE:
  			order.setTotalPrice(order.getTotalPrice()-((DeliverySupplyMethod)order.supplyMethodInformation).getDeliveryFee()); //update the total cost of the order
  			order.setTotalPrice(order.getTotalPrice()/(1-Constans.PRE_ORDER_DISCOUNT)); //set discount according to the instructions
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
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
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
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getExitBtn(ActionEvent event) {
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
    public void getHelpBtn(ActionEvent event) {
    	PopUpMessages.helpMessage("On this screen you insert you'r contact information for the Delivery process.!");
    }


    /**
     * This function is used for
     * switching to the next screen and 
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getNextBtn(ActionEvent event) {
    	if(isEmptyFields()) {
    		errorText.setText("Please fill all the required fields (*)!");
    		errorText.setFill(Color.RED);
    	}
    	
    	else {
    		
    		if (!(isInt(phoneTxtField))) {
        		errorText.setText("Insert only numbers in the phone field!");
        		errorText.setFill(Color.RED);
        	}
    		
    		else {
    		deliveryInformation.setReceiverFirstName(firstNameTextField.getText());
    		deliveryInformation.setReceiverLastName(lastNameTextField.getText());
    		deliveryInformation.setReceiverPhoneNumber(phonePrefixCombo.getValue() + phoneTxtField.getText());
    		deliveryInformation.setReciverAddress(cityCombo.getValue() + " " + streetCombo.getValue() + " " + addressTextField.getText());
    		
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
    	boolean res = false;

    	if(firstNameTextField.getText().equals("")) {
    		firstNameTextField.setStyle("-fx-border-color: red");
    		res = true;
    	}else {
    		firstNameTextField.setStyle("-fx-border-color: black");
    	}
    	if(lastNameTextField.getText().equals("")) {
    		lastNameTextField.setStyle("-fx-border-color: red");
    		res = true;
    	}else {
    		lastNameTextField.setStyle("-fx-border-color: black");
    	}
    	if(phoneTxtField.getText().equals("")) {
    		phoneTxtField.setStyle("-fx-border-color: red");
    		res = true;
    	}else {
    		phoneTxtField.setStyle("-fx-border-color: black");
    	}
    	if(addressTextField.getText().equals("")) {
    		addressTextField.setStyle("-fx-border-color: red");
    		res = true;
    	}else {
    		addressTextField.setStyle("-fx-border-color: black");
    	}
    	if(cityCombo.getValue() == null) {
    		cityCombo.setStyle("-fx-border-color: red");
    		res = true;
    	}else {
    		cityCombo.setStyle("-fx-border-color: black");
    	}
    	if(streetCombo.getValue() == null) {
    		streetCombo.setStyle("-fx-border-color: red");
    		res = true;
    	}else {
    		streetCombo.setStyle("-fx-border-color: black");
    	}
    	if(phonePrefixCombo.getValue() == null) {
    		phonePrefixCombo.setStyle("-fx-border-color: red");
    		res = true;
    	}else {
    		phonePrefixCombo.setStyle("-fx-border-color: black");
    	}
    	return res;
    }

    /**
   	* This is the init for the current 
  	* screen, in which we load the fxml.
  	* This function is called from the previous 
  	* screen controller.
  	* 
  	* @param order The current order.
  	* @param deliveryType the delivery type chosen for the order.
  	*/
    public void initDeliveryMethodScreen(Order order, DeliveryType deliveryType) {
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
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
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
		OrderAMealDeliveryMethodScreenController.deliveryType=deliveryType;
	}

  	 /**
     * This is a function for 
     * initializing the screen.
     * 
     * @param arg0
     * @param arg1
     */
  	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
  		deliveryInformation = new DeliverySupplyMethod(order.getSupplyId(),order.getOrderNumber(),null,null,null,deliveryType,null);
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
  	 */
  	private void calcTotalBill() {
  	
  		switch(order.getTimeType()) {
  		case PRE:
  			order.setTotalPrice(order.getTotalPrice()*(1-Constans.PRE_ORDER_DISCOUNT)); //set discount according to the instructions
  			break;
  		default:
  			break;
  		
  		}
  		order.setTotalPrice(order.getTotalPrice()+deliveryInformation.getDeliveryFee()); //update the total cost of the order
  	}
  	
  	/**
 	 * This method checks if the textField contains numbers only.
 	 * 
 	 * @param txtField the textField that we want to check.
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
