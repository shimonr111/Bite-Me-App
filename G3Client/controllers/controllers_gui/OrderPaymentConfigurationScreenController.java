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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import orders.AbatractSupplyMethod;
import orders.Order;

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
	    private TextField totalToPayInCashTextField;

	    @FXML
	    private Text errorText;

	    @FXML
	    private ComboBox<?> paymentMethodCombo;

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
			
		}

	}

