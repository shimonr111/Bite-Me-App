package controllers_gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import bitemeclient.PopUpMessages;
import communication.Answer;
import communication.Message;
import communication.Task;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import orders.AbatractSupplyMethod;
import orders.DeliverySupplyMethod;
import orders.Item;
import orders.ItemCategory;
import orders.ItemSize;
import orders.Order;
import util.Constans;

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
    private String pathForLastScreen= null;
    private String pageTitle;
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
    private TableView<Item> orderSummaryTable;

    @FXML
    private TableColumn<Item, String> iteamNameColumn;

    @FXML
    private TableColumn<Item, ItemSize> sizeColumn;

    @FXML
    private TableColumn<Item, Double> priceColumn;

    @FXML
    private TableColumn<Item, String> commentColumn;

    @FXML
    private TextField totalOrderPriceTextField;

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
			  pageTitle = "Take away";
			  break;
		  case DELIVERY:
			  pathForLastScreen = "/fxmls/ORD5OrderAMealDeliveryMethod.fxml";
			  pageTitle = "Delivery";
			  //set total price to the price as it was after choose supply method stage
			  switch(order.getTimeType()) {
		  		case PRE:
		  			order.setTotalPrice(order.getTotalPrice()/(1-Constans.PRE_ORDER_DISCOUNT)); //set discount according to the instructions
		  			if(order.getSupplyMethodInformation() instanceof DeliverySupplyMethod) {
		  				order.setTotalPrice(order.getTotalPrice()-((DeliverySupplyMethod)order.getSupplyMethodInformation()).getDeliveryFee()); //update the total cost of the order
		  			}
		  			break;
		  		case REGULAR:
		  			order.setTotalPrice(order.getTotalPrice()-((DeliverySupplyMethod)order.getSupplyMethodInformation()).getDeliveryFee()); //update the total cost of the order
		  			break;
		  		default:
		  			break;
			  }
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
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle(pageTitle); 
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
     * @param event
     */
    @FXML
    void getChoosePaymenMethodtBtn(ActionEvent event) {

  //now we need to change this screen to the next one
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		OrderPaymentConfigurationScreenController orderPaymentConfigurationScreenController = new OrderPaymentConfigurationScreenController();
		orderPaymentConfigurationScreenController.initPaymentConfigurationScreen(order); // call the init of the next screen

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
    	PopUpMessages.helpMessage("On this screen you see the summary of your order and confirm the order to proceed to payment.");
    }


   /**
     * This is the init for the current 
     * screen, in which we load the fxml.
     * This function is called from the previous 
     * screen controller.
     * 
     */
  public void initOrderSummaryScreen(Order order) {
	  OrderSummaryScreenController.order = order;
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
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("Order Summary");
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
  	totalOrderPriceTextField.setDisable(true); //set total price disable so that the user can't edit the price of the order
  	totalOrderPriceTextField.setText(String.valueOf(order.getTotalPrice())); //set total price in screen
  	/*Set data in the table for the summary*/
	ObservableList<Item> items = FXCollections.observableArrayList();	
	items.addAll(order.itemList);
	iteamNameColumn.setCellValueFactory(new PropertyValueFactory<Item,String>("itemName"));
	sizeColumn.setCellValueFactory(new PropertyValueFactory<Item,ItemSize>("size"));
	priceColumn.setCellValueFactory(new PropertyValueFactory<Item,Double>("price"));
	commentColumn.setCellValueFactory(new PropertyValueFactory<Item,String>("comment"));
	orderSummaryTable.setItems(items);
	}

}
