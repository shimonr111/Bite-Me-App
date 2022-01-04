package controllers_gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import orders.DeliverySupplyMethod;
import orders.DeliveryType;
import orders.Item;
import orders.ItemSize;
import orders.Order;
import orders.OrderTimeType;
import util.Constans;
/**
 *  Class description: 
 * This is a class for the summary of the 
 * order process.
 * 
 * @author Lior, Guzovsky
 * @author Shimon, Rubin
 * 
 * @version 17/12/2021
 */
public class OrderSummaryScreenController extends AbstractBiteMeController implements Initializable{
	
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
    private static OrderSummaryScreenController orderSummaryScreenController;
    
    /**
     * Static Order object for the current order flow.
     */
    private static Order order;
    
    /**
     * Static String of the path of last screen, so we can change it accordingly.
     */
    private String pathForLastScreen= null;
    
    /**
     * Static String of page title of last screen, so we can change it accordingly.
     */
    private String pageTitle;
    
    /**
     * Delivery type used if it is multiple participants delivery.
     */
    public static DeliveryType deliveryType= DeliveryType.REGULAR;
    
    /**
     * The Multiple Participant delivery order number that the user wants to join to.
     */
    public static int MultiOrderNumber;
    
    /**
     * The Amount of participants of multiple delivery order.
     */
    public static int joinMultiNumberOfParticipants;
    
    /**
     * The Cost of delivery fee for multiple participants delivery.
     */
    public static int joinMultiDeliveryCost;
    
    @FXML
    /**
     * The Choose Payment Button.
     */
    private Button choosePaymentMethodBtn;

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
     * The empty text that we use to display messages to the user.
     */
    private Text errorText;

    @FXML
    /**
     * TableView of Items for summary.
     */
    private TableView<Item> orderSummaryTable;

    @FXML
    /**
     * The Column of Item Name.
     */
    private TableColumn<Item, String> iteamNameColumn;

    @FXML
    /**
     * The Column of Item Size.
     */
    private TableColumn<Item, ItemSize> sizeColumn;

    @FXML
    /**
     * The Column of Item Price.
     */
    private TableColumn<Item, Double> priceColumn;

    @FXML
    /**
     * The Column of Item Comment.
     */
    private TableColumn<Item, String> commentColumn;

    @FXML
    /**
     * Text Field of total price of the order.
     */
    private TextField totalOrderPriceTextField;
    
    @FXML
    /**
     * The text that shows if there is discount for the order.
     */
    private Text discountText;

    /**
     * Back button for the 
     * 
     * @param event ActionEvent of javaFx.
     */
    @FXML
    public void getBackBtn(ActionEvent event) {

    	switch(order.getSupplyType()){
		  case TAKE_AWAY:
			  pathForLastScreen = "/fxmls/ORD5OrderAMealTAMethod.fxml";
			  pageTitle = "Take away";
			  break;
		  case DELIVERY:
			  if(OrderSummaryScreenController.deliveryType == DeliveryType.JOIN_MULTI) {
				  pathForLastScreen = "/fxmls/ORD8JoinMultiDeliveryScreen.fxml";
			  }
			  else {
				  pathForLastScreen = "/fxmls/ORD5OrderAMealDeliveryMethod.fxml";
				  pageTitle = "Delivery";
				  //set total price to the price as it was after choose supply method stage
		  			if(order.getSupplyMethodInformation() instanceof DeliverySupplyMethod) {
		  				order.setTotalPrice(order.getTotalPrice()-((DeliverySupplyMethod)order.getSupplyMethodInformation()).getDeliveryFee()); //update the total cost of the order
		  			}
			  }
			  break;
		  	default:
			 break;
	  }
		  switch(order.getTimeType()) {
	  		case PRE:
	  			order.setTotalPrice(order.getTotalPrice()/(1-Constans.PRE_ORDER_DISCOUNT)); //set discount according to the instructions
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
     * @param event ActionEvent of javaFx.
     */
    @FXML
    public void getChoosePaymenMethodtBtn(ActionEvent event) {
    	
    	if(OrderSummaryScreenController.deliveryType == DeliveryType.JOIN_MULTI) {
    		// add logic
    		ArrayList<Object> objectToServer = new ArrayList<>();
    		objectToServer.add(order);
    		objectToServer.add(Integer.toString(MultiOrderNumber));
    		sendToClient(new Message (Task.JOIN_MULTI_FINISH_ORDER,Answer.WAIT_RESPONSE,objectToServer));
    		PopUpMessages.updateMessage("Your order cost will be added to the Multiple-Participants owner, you have finished your order!");
    		OrderChooseItemsScreenController.order= null;
    		setBusinessCustomerPortal(event);
    	}
    	else {
    		//now we need to change this screen to the next one
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			OrderPaymentConfigurationScreenController orderPaymentConfigurationScreenController = new OrderPaymentConfigurationScreenController();
			orderPaymentConfigurationScreenController.initPaymentConfigurationScreen(order); // call the init of the next screen
    	}

    }

      /**
     * Exit from screen and update
     * DB.
     * 
     * @param event ActionEvent of javaFx.
     */
    @FXML
    public void getExitBtn(ActionEvent event) {
    	Message message = new Message(Task.LOGOUT,Answer.WAIT_RESPONSE,connectedUser);
		sendToClient(message);
		connectedUser = null;
		Message disconnectMessage = new Message(Task.CLIENT_DICONNECT,Answer.WAIT_RESPONSE,null);
		sendToClient(disconnectMessage);
		System.exit(0);
    }

     /**
     * This is a method for getting 
     * information for the user
     * 
     * @param event ActionEvent of javaFx.
     */
    @FXML
    public void getHelpBtn(ActionEvent event) {
    	PopUpMessages.helpMessage("On this screen you see the summary of your order and confirm the order to proceed to payment.");
    }


   /**
     * This is the init for the current 
     * screen, in which we load the fxml.
     * This function is called from the previous 
     * screen controller.
     * 
     * @param order The current Order.
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
	 * This method loads the business customer portal
	 * 
	 * @param event ActionEvent of javaFx.
	 */
	public void setBusinessCustomerPortal(ActionEvent event) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					FXMLLoader loader = new FXMLLoader();
					Pane root;
					try {
						Stage Stage = new Stage();
						Stage.setResizable(false);
						root = loader.load(getClass().getResource("/fxmls/UserPortalOfBusinessCustomer.fxml").openStream());
						UserPortalOfBusinessCustomerController UPOBCC = new UserPortalOfBusinessCustomerController();
						UPOBCC.initPortalAgain();
						Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
							@Override
							public void handle(WindowEvent event) { 
								event.consume();
								Stage.close();
							}
						});
					} catch (IOException e) {
						e.printStackTrace();
					}
					((Node) event.getSource()).getScene().getWindow().hide();
				}
			});
		
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
  	totalOrderPriceTextField.setDisable(true); //set total price disable so that the user can't edit the price of the order
  	joinMultiDeliveryCost = 0;
	if(OrderSummaryScreenController.deliveryType == DeliveryType.JOIN_MULTI) {
		// add logic
		ArrayList<Object> objectToServer = new ArrayList<>();
		objectToServer.add(Integer.toString(MultiOrderNumber));
		sendToClient(new Message (Task.JOIN_MULTI_GET_NUMBER_OF_PARTICIPANTS,Answer.WAIT_RESPONSE,objectToServer));
		switch (joinMultiNumberOfParticipants) {
		case 1:
			joinMultiDeliveryCost = 20;
			break;
		default:
			joinMultiDeliveryCost = 15;
			break;
		}
		
	}
	else if (order.getTimeType() == OrderTimeType.PRE){
		discountText.setText("You recived 10% discount due the Pre-Order!");
	}
  	totalOrderPriceTextField.setText(String.valueOf(order.getTotalPrice() + joinMultiDeliveryCost)); //set total price in screen
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
