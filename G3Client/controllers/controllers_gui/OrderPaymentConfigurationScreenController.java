package controllers_gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import orders.AbatractSupplyMethod;
import orders.Order;
import orders.PaymentWay;
import users.BusinessCustomer;
import users.Customer;

/**
 * 
 * @author Lior, Guzovsky
 * @author Shimon, Rubin
 * Class description: 
 * This is a class for the user 
 * to add all his payment to the system (cash / credit card, etc.)
 * 
 * @version 17/12/2021
 */
public class OrderPaymentConfigurationScreenController  extends AbstractBiteMeController implements Initializable{
		/**
		 * Class members description:
		 */

		private static FXMLLoader loader;
	    private static OrderPaymentConfigurationScreenController orderPaymentConfigurationScreenController;
	    private static Order order;
	    private static AbatractSupplyMethod supplyMethodInformation;
	    

	    @FXML
	    private Button finishOrderBtn;

	    @FXML
	    private Button btnExit;

	    @FXML
	    private Button btnBack;

	    @FXML
	    private Button btnHelp;

	    @FXML
	    private TextField availableAccountBalanceTextField;

	    @FXML
	    private Label availableBudgetBalanceLabel;

	    @FXML
	    private TextField availableBudgetBalanceTextField;

	    @FXML
	    private TextField enterAmountTextField;

	    @FXML
	    private TextField totalToPayTextField;

	    @FXML
	    private Text errorText;

	    @FXML
	    private ComboBox<PaymentWay> paymentMethodCombo;

	    @FXML
	    private Button addAmountBtn;

	    @FXML
	    private Button removeAmountBtn;

	    @FXML
	    private Label employeeBudgetLabel;

	    @FXML
	    private TextField alreadyCashTextField;

	    @FXML
	    private TextField alreadyCreditCardTextField;

	    @FXML
	    private TextField alreadyAccountBalanceTextField;

	    @FXML
	    private TextField alreadyEmployeeBudgetTextField;

	   @FXML
	    void getAddAmountBtn(ActionEvent event) {

	    }

	   @FXML
	    void getRemoveAmountBtn(ActionEvent event) {

	    }
	   
	   
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
						root = loader.load(getClass().getResource("/fxmls/ORD6OrderSummary.fxml").openStream());
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

	    @FXML
	    void getFinishOrderBtn(ActionEvent event) {

	    	//now we need to change this screen to the next one
	    	//((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
	    	//UserPortalOfCustomerController userPortalOfCustomerController = new UserPortalOfCustomerController();
	    	//userPortalOfCustomerController.initCustomerUserPortal(); // call the init of the next screen

	    }

	       /**
	     * This is a method for getting 
	     * information for the user
	     * 
	     * @param event
	     */
	    @FXML
	    void getHelpBtn(ActionEvent event) {
	    	PopUpMessages.helpMessage("Please choose the way will pay (cash / credit card)");
	    }

	    /**
	     * This is the init for the current 
	     * screen, in which we load the fxml.
	     * This function is called from the previous 
	     * screen controller.
	     * 
	     */
	  public void initPaymentConfigurationScreen(Order order, AbatractSupplyMethod supplyMethodInformation) {
		  OrderPaymentConfigurationScreenController.order = order;
		  OrderPaymentConfigurationScreenController.supplyMethodInformation = supplyMethodInformation;
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					loader = new FXMLLoader();
					Pane root;
					try {
						Stage Stage = new Stage();
						Stage.setResizable(false);
						root = loader.load(getClass().getResource("/fxmls/ORD7PaymentConfiguration.fxml").openStream());
						orderPaymentConfigurationScreenController = loader.getController();
						Scene scene = new Scene(root);
						Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
							@Override
							public void handle(WindowEvent event) { 
								event.consume();
								Stage.close();
							}
						});
						//scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
						Stage.setTitle("Payment");
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
			List<PaymentWay> paymentMethods = new ArrayList<>();
			/*For business customer and hr manager*/
			if(connectedUser instanceof BusinessCustomer) {
				for(PaymentWay pay : PaymentWay.values()) {
					paymentMethods.add(pay);
				}
			}
			else { // if connectedUser is instance of Customer
				for(PaymentWay pay : PaymentWay.values()) {
					/*Add only enums that related to regular customer!*/
					if((pay != PaymentWay.EMPLOYEE_BUDGET)) {
						paymentMethods.add(pay);
					}	
				}
				employeeBudgetLabel.setVisible(false); //set the employee budget label invisible for regular customer, TBD: add fx:id for the text field next to him!!!!!!
				alreadyEmployeeBudgetTextField.setVisible(false);//set the employee text field invisible for regular customer
				availableBudgetBalanceLabel.setVisible(false);//set the employee text field invisible for regular customer
				availableBudgetBalanceTextField.setVisible(false);//set the employee text field invisible for regular customer
			}
			paymentMethodCombo.getItems().addAll(paymentMethods); //set all the relevant enums into the combo box
			totalToPayTextField.setText(String.valueOf(order.getTotalPrice()));//set the total price
			availableAccountBalanceTextField.setText((String.valueOf(((Customer) connectedUser).getBalance())));//set the amount of the balance of any customer according to how much the bite me system owe him 
			/*set all text fields to be not editable*/
			alreadyEmployeeBudgetTextField.setDisable(true);
			availableBudgetBalanceTextField.setDisable(true);
			totalToPayTextField.setDisable(true);
			alreadyCashTextField.setDisable(true);
			alreadyCreditCardTextField.setDisable(true);
			alreadyAccountBalanceTextField.setDisable(true);
			availableAccountBalanceTextField.setDisable(true);
		}

	}

