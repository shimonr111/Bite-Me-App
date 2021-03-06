package controllers_gui;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import orders.DeliveryType;
import orders.Order;
import orders.SupplyType;
import users.BusinessCustomer;
import users.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import bitemeclient.PopUpMessages;
import communication.Answer;
import communication.Message;
import communication.Task;
import javafx.fxml.Initializable;

/**
 *  Class description: 
 * This is a class for 
 * controlling the UI of Joun Multi Delivery Screen that appears after
 * choosing Join Multi option for delivery type.
 * form.
 * 
 * @author Mousa, Srour.
 * 
 * @version 2/1/2022
 */
public class JoinMultiDeliveryScreenController extends AbstractBiteMeController implements Initializable {
	
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
	    private static JoinMultiDeliveryScreenController joinMultiDeliveryScreenController;
	    
	    /**
	     * Static Order object for the current order flow.
	     */
	    private static Order order;
	    
	    /**
	     * Static Delivery Type, information that we have to know .
	     */
	    private static  DeliveryType deliveryType;
	    
	    /**
	     * ArrayList of available Multiple delivery order that are available , we get them from DB.
	     */
	    public static ArrayList<Order> availableMultiOrders = new ArrayList<>();
	    
		@FXML
		/**
		 * The Column of the delivery address.
		 */
	    private TableColumn<Order,String> addressCol;

	    @FXML
	    /**
	     * The Back Button.
	     */
	    private Button backBtn;

	    @FXML
	    /**
	     * The empty text that we use to display messages to the user.
	     */
	    private Text displayText;

	    @FXML
	    /**
	     * The Exit Button.
	     */
	    private Button exitBtn;

	    @FXML
	    /**
	     * The Help Button.
	     */
	    private Button helpBtn;

	    @FXML
	    /**
	     * The Orders table.
	     */
	    private TableView<Order> multiTable;

	    @FXML
	    /**
	     * The Column that contains the name of the order maker.
	     */
	    private TableColumn<Order,String> nameCol;

	    @FXML
	    /**
	     * The Next button.
	     */
	    private Button nextBtn;
	    
	    /**
	     * This method loads the previous screen.
	     * @param event ActionEvent of javaFX.
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
	     * This method diconnect the specific client,
	     * log out the conneted user and exit.
	     * @param event ActionEvent of javaFX.
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
	     * This method display a pop up message to the user.
	     * @param event
	     */
	    @FXML
	    void getHelpBtn(ActionEvent event) {
	    	PopUpMessages.helpMessage("Please select which multi-participants delivery you would like to join.");
	    }
	    
	    /**
	     * This method will check if the user choosed a row from the table,
	     * and if yes , so we move to the relevant screen while passing
	     * the nessecary information.
	     * @param event ActionEvent of javaFX.
	     */
	    @FXML
	    void getNextBtn(ActionEvent event) {
	    	if(multiTable.getSelectionModel().getSelectedItem() == null) {
	    		displayText.setText("Please select to which delivery would you like to join");
	    	}
	    	else {
	    		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window)
	    		OrderSummaryScreenController orderSummaryScreenController = new OrderSummaryScreenController ();
	    		orderSummaryScreenController.initOrderSummaryScreen(order);
	    		OrderSummaryScreenController.deliveryType = deliveryType;
	    		OrderSummaryScreenController.MultiOrderNumber= multiTable.getSelectionModel().getSelectedItem().getOrderNumber();
	    	}
	    }
	    
	    /**
	     * This method loads the current screen.
	     * @param order The current order.
	     * @param deliveryType the delivery type choosed for the order.
	     */
	    public void initMultiDeviveryScreenController(Order order,DeliveryType deliveryType) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					loader = new FXMLLoader();
					Pane root;
					try {
						Stage Stage = new Stage();
						Stage.setResizable(false);
						root = loader.load(getClass().getResource("/fxmls/ORD8JoinMultiDeliveryScreen.fxml").openStream());
						joinMultiDeliveryScreenController = loader.getController();
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
		JoinMultiDeliveryScreenController.order = order;
		JoinMultiDeliveryScreenController.deliveryType=deliveryType;
	    }
	    
	    /**
	     * This method will initialize the table with the relevant
	     * data after getting the data from the DB.
	     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		JoinMultiDeliveryScreenController.order.setSupplyType(SupplyType.DELIVERY);
		displayText.setText("");
		ObservableList<Order> availableOrdersObservable = FXCollections.observableArrayList();
		ArrayList<String> listToServer = new ArrayList<>();
		listToServer.add(JoinMultiDeliveryScreenController.order.getSupplierUserId());
		listToServer.add(connectedUser.getUserId());
		sendToClient(new Message (Task.GET_BUSINESS_CUSTOMERS_WITH_MULTI_ORDER,Answer.WAIT_RESPONSE,listToServer));
		if(availableMultiOrders.isEmpty()) {
			displayText.setText("There are no multi-participants delivery for this restaurant!");
		}
		else {
			availableOrdersObservable.addAll(availableMultiOrders);
			nameCol.setCellValueFactory(new PropertyValueFactory<Order,String>("customerName"));
			addressCol.setCellValueFactory(new PropertyValueFactory<Order,String>("deliveryAddress"));
			multiTable.setItems(availableOrdersObservable);
		}
		
	}

}
