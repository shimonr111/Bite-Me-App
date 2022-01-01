package controllers_gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import users.BusinessCustomer;
import util.OrderForView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import bitemeclient.PopUpMessages;
import communication.Answer;
import communication.Message;
import communication.Task;
import javafx.fxml.Initializable;

/**
 * 
 * @author Mousa, Srour.
 * 
 * Class description: 
 * This class is a controller of the view reports screen.
 * 
 * @version 25/12/2021
 */
public class WatchOrderHistoryScreenController extends AbstractBiteMeController implements Initializable{
	
	/**
	 * Class members description:
	 */
	public static FXMLLoader loader;
	private static WatchOrderHistoryScreenController watchOrderHistoryScreenController;
	public static ArrayList<OrderForView> ordersForCustomer = new ArrayList<>();
	
    @FXML 
    private Button backBtn; 

    @FXML 
    private TableColumn<OrderForView,String> dateCol; 

    @FXML
    private Button exitBtn; 

    @FXML
    private Button helpBtn; 
    
    @FXML 
    private Button confirmBtn; 
    
    @FXML 
    private Text nameTxt; 

    @FXML 
    private TableColumn<OrderForView,String> orderDetailsCol; 

    @FXML 
    private TableView<OrderForView> ordersTable;
    
    @FXML 
    private Button refreshBtn; 

    @FXML 
    private TableColumn<OrderForView,String> resturantNameCol;

    @FXML 
    private TableColumn<OrderForView,String> statusCol; 

    @FXML
    private TableColumn<OrderForView,String> timeCol; 
    

    @FXML
    private Text displayText;

	/**
     * This method calls the method that loads
     * the previous screen.
     * 
     * @param event
     */
    @FXML
    public void getBackBtn(ActionEvent event) {
    	if(connectedUser instanceof BusinessCustomer)
    		setBusinessCustomerPortal(event);
    	else
    		setPrivateCustomerPortal(event);
    }
    
    /**
     * This function is used for updating the balance 
     * of a user if the supplier was late according to the
     * delivery type.
     * We will save in that stage the ActualDate according to the course guidelines.
     * 
     * @param event
     */
    @FXML
    public void getConfirmBtn(ActionEvent event) {
    	displayText.setText("");
     	OrderForView orderForView = ordersTable.getSelectionModel().getSelectedItem();
    	if(orderForView != null) {
    		if(orderForView.getOrderStatus().equals("Pending Approval")) {
    			displayText.setText("You can't confirm orders that are still not approved from the restaurant");
    		}
    		else {
    			Optional<ButtonType> result = PopUpMessages.confirmationMessage("Pressing OK button means that you have recieved your order.");
    			if(result.get() == ButtonType.OK) {
    				ArrayList<Object> objectToMessage = new ArrayList<>();
    				Date actualDate = new Date();
    				int orderNumber = orderForView.getOrderNum();
    				
    				objectToMessage.add(actualDate);
    				objectToMessage.add(Integer.toString(orderNumber));
    				Message message = new Message(Task.SET_ACTUAL_DATE_AND_BALANCE,Answer.WAIT_RESPONSE,objectToMessage);
    				sendToClient(message);
    				initialize(null, null);	
    			}
    		}
    	}
    	else {
    		displayText.setText("Select an order to confirm!");
    	}
    }

	/**
     * This method disconnect the specific client,
     * log out the connected user and exit.
     * 
     * @param event
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
     * This method displays a pop up message to the user.
     * 
     * @param event
     */
    @FXML
    public void getHelpBtn(ActionEvent event) {
    	PopUpMessages.helpMessage("On this table you can see your orders that have not been completed yet.");	
    }
    
	/**
     * This method refreshes the table .
     * 
     * @param event
     */
    @FXML
    public void getRefreshBtn(ActionEvent event) {
    	ordersForCustomer.clear(); 
    	initialize(null, null);
    }
    
    /**
     * This method loads the current screen, it will be called from the previous screen.
     */
	public void initOrderHistoryScreen() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/WatchOrderHistoryScreen.fxml").openStream());
					watchOrderHistoryScreenController = loader.getController();
					Scene scene = new Scene(root);
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("Order's History");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	/**
	 * This method loads the private customer portal.
	 * 
	 * @param event
	 */
	public void setPrivateCustomerPortal(ActionEvent event) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					FXMLLoader loader = new FXMLLoader();
					Pane root;
					try {
						Stage Stage = new Stage();
						Stage.setResizable(false);
						root = loader.load(getClass().getResource("/fxmls/UserPortalOfCustomer.fxml").openStream());
						UserPortalOfCustomerController UPOCC = new UserPortalOfCustomerController();
						UPOCC.initPortalAgain();
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
	 * This method loads the business customer portal
	 * 
	 * @param event
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
     * This method initialize the table according to the existing orders
     * for the customer.
     * 
     * @param arg0
     * @param arg1
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		displayText.setText("");
		Message message = new Message (Task.GET_ORDERS_FOR_USER,Answer.WAIT_RESPONSE,connectedUser.getUserId());
		sendToClient(message);
		nameTxt.setText(connectedUser.getUserFirstName() + "'s orders in progres: ");
		orderDetailsCol.setStyle("-fx-text-alignment: left;");
		ObservableList<OrderForView> ordersObservable = FXCollections.observableArrayList();
		ordersObservable.addAll(ordersForCustomer);
		resturantNameCol.setCellValueFactory(new PropertyValueFactory<OrderForView,String>("resturantName"));
		dateCol.setCellValueFactory(new PropertyValueFactory<OrderForView,String>("orderDate"));
		timeCol.setCellValueFactory(new PropertyValueFactory<OrderForView,String>("orderTime"));
		orderDetailsCol.setCellValueFactory(new PropertyValueFactory<OrderForView,String>("orderDetails"));
		statusCol.setCellValueFactory(new PropertyValueFactory<OrderForView,String>("orderStatus"));
		ordersTable.setItems(ordersObservable);
	}

}
