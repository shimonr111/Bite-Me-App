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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
 * This is a class for the summary of the 
 * order process.
 * 
 * @version 17/12/2021
 */
public class OrderSummaryScreenController extends AbstractBiteMeController implements Initializable{
	/**
	 * Class members description:
	 */

	private static FXMLLoader loader;
    private static OrderSummaryScreenController orderSummaryScreenController;
    private static Order order;
    private static AbatractSupplyMethod supplyMethodInformation;
    private String  pathForLastScreen= null;
    @FXML
    private Button choosePaymentMethodBtn;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnHelp;

    @FXML
    private Text errorText;

    @FXML
    private TableView<?> orderSummaryTable;

    @FXML
    private TableColumn<?, ?> iteamNameColumn;

    @FXML
    private TableColumn<?, ?> sizeColumn;

    @FXML
    private TableColumn<?, ?> priceColumn;

    @FXML
    private TableColumn<?, ?> commentColumn;

    @FXML
    private TextField totalOrderPriceTextField;

    @FXML
    private TextField itemSumTextField;

    @FXML
    private TextField discountPreOrderTextField;

    @FXML
    private TextField supplyFeeTextField;

    /**
     * Back button for the 
     * 
     * 
     * @param event
     */
    @FXML
    void getBackBtn(ActionEvent event) {
    	switch(order.getSupplyType()){
		  case TAKE_AWAY:
			  pathForLastScreen = "/fxmls/ORD5OrderAMealTAMethod.fxml";
			  break;
		  case DELIVERY:
			  pathForLastScreen = "/fxmls/ORD5OrderAMealDeliveryMethod.fxml";
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
					root = loader.load(getClass().getResource(pathForLastScreen).openStream());
					Scene scene = new Scene(root);
					Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) { 
							event.consume();
							Stage.close();
						}
					});
					//scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
					Stage.setTitle("Order Summary");
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
     * This function is used for
     * switching to the next screen and 
     * 
     * 
     *
     * 
     * @param event
     */
    @FXML
    void getChoosePaymenMethodtBtn(ActionEvent event) {

//  //now we need to change this screen to the next one
//		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
//		OrderPaymentConfigurationScreenController orderPaymentConfigurationScreenController = new OrderPaymentConfigurationScreenController();
//		orderPaymentConfigurationScreenController.initPaymentConfigurationScreen(); // call the init of the next screen

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
    	PopUpMessages.helpMessage("Please confirm the summary of your order and choose payment method (cash / credit card)");
    }



   /**
     * This is the init for the current 
     * screen, in which we load the fxml.
     * This function is called from the previous 
     * screen controller.
     * 
     */
  public void initOrderSummaryScreen(Order order , AbatractSupplyMethod supplyMethodInformation) {
	  OrderSummaryScreenController.order = order;
	  OrderSummaryScreenController.supplyMethodInformation = supplyMethodInformation;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/ORD6OrderSummary.fxml").openStream());
					orderSummaryScreenController = loader.getController();
					Scene scene = new Scene(root);
					Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) { 
							event.consume();
							Stage.close();
						}
					});
					//scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
					Stage.setTitle("Choose Items");
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
