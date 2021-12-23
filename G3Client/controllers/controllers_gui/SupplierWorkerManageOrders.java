package controllers_gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import orders.Item;
import orders.ItemCategory;
import orders.Order;
import orders.OrderStatus;
import orders.SupplyType;
import users.SupplierWorker;

/**
 * 
 * @author Lior, Guzovsky
 * @author Shimon, Rubin
 * 
 * Class description: 
 * This is a class for managing all the orders
 * for the supplier. In this screen the supplier worker changes status of 
 * orders.
 * 
 * @version 21/12/2021
 */
public class SupplierWorkerManageOrders extends AbstractBiteMeController implements Initializable{
	/**
	 * Class members description:
	 */

	private static FXMLLoader loader;
    private static SupplierWorkerManageOrders supplierWorkerManageOrders;
	public static ArrayList<Order> orderListFromDB = new ArrayList<>();
	public static ArrayList<Order> updateOrders = new ArrayList<>();

	@FXML
    private Button btnExit;

    @FXML
    private Button saveBtn;

    @FXML
    private Button btnBack;

    @FXML
    private TableView<Order> manageOrdersTable;

    @FXML
    private TableColumn<Order, Integer> orderNumColum;

    @FXML
    private TableColumn<Order, SupplyType> orderTypeColumn;

    @FXML
    private TableColumn<Order, Date> orderDateColumn;

    @FXML
    private TableColumn<Order, Date> estSupplyTimeColumn;

    @FXML
    private TableColumn<Order, String> customerPhoneColumn;

    @FXML
    private TableColumn<Order, OrderStatus> statusColumn; 

    @FXML
    private Button btnHelp;

    @FXML
    private Text errorText;
  
    
    /**
     * This is a function for going back
     * to the previous screen.
     * 
     * @param event
     */
    @FXML
    void getBackBtn(ActionEvent event) {
    	/*TBD: delete observable list*/
    	updateOrders.clear();// clear this array for the next time we come back for this screen
    	
    	Platform.runLater(new Runnable() {
			@Override
			public void run() {
				FXMLLoader loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/UserPortalOfSupplier.fxml").openStream());
					Scene scene = new Scene(root);
					Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) { 
							event.consume();
							Stage.close();
						}
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("Main Screen");
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
     * This button is for updating  
     * the status of orders in the manage order table into the 
     * Order DB.
     * 
     * 
     * @param event
     */
    @FXML
    void getBtnSave(ActionEvent event) {
    	 // if the table is empty 
        if(updateOrders.isEmpty()) {
     	   errorText.setVisible(true);
     	   errorText.setText("There is no orders!");
     	   errorText.setFill(Color.RED);
        }
        else {
     	   Message message = new Message(Task.MANAGE_ORDER_FINISHED,Answer.WAIT_RESPONSE,updateOrders);
           sendToClient(message);//send message to the server telling the manage order is finished and then push into DB
        	   
           /*Give notice for the user that the changes have been saved*/
     	   PopUpMessages.updateMessage("Order changes saved successfully!");
        }

    }

    /**
     * Exit and logout button
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
     * This is a function for 
     * popping help message if pressed by the user.
     * 
     * @param event
     */
    @FXML
    void getHelpBtn(ActionEvent event) {
    	PopUpMessages.helpMessage("This is a screen for managing the status (Pending / Approved ) of the orders!");
    }

	
	/**
	 * This is the initialization function for this 
	 * screen.
	 * 
	 * @param primaryStage
	 * @param fxmlPath
	 */
	public void initSupplierWorkerManageOrdersScreen() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/SUP2ManageOrdersScreen.fxml").openStream());
					supplierWorkerManageOrders = loader.getController();
					Scene scene = new Scene(root);
					Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) { 
							event.consume();
							Stage.close();
						}
					});
					//scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("Choose Items");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		String supplierId=((SupplierWorker) connectedUser).getSupplier().getSupplierId();
		ObservableList<Order> ordersForManageOrderTable = FXCollections.observableArrayList();
		/*send message to server to get all orders for this supplier worker that works in specific restaurant*/
		Message message = new Message (Task.SUPPLIER_WORKER_GET_ALL_RELEVANT_ORDERS,Answer.WAIT_RESPONSE,supplierId); 
		sendToClient(message);
		
		/*Check if the DB doesn't have orders for this restaurant*/
		if(orderListFromDB == null) {
			errorText.setVisible(true);
			errorText.setText("There are no orders for this restaurant!");
			errorText.setFill(Color.RED);
		}
		else {
			//add all the wrapper orders to the table view
			ordersForManageOrderTable.addAll(orderListFromDB);
		}
		
		 updateOrders = new ArrayList<Order>(orderListFromDB); //copy the orders we got from DB to our updateOrders array
		
		/*Set data in the table */
		orderNumColum.setCellValueFactory(new PropertyValueFactory<Order,Integer>("orderNumber"));
		orderTypeColumn.setCellValueFactory(new PropertyValueFactory<Order,SupplyType>("supplyType"));
		orderDateColumn.setCellValueFactory(new PropertyValueFactory<Order,Date>("issueDateTime"));
	    estSupplyTimeColumn.setCellValueFactory(new PropertyValueFactory<Order,Date>("estimatedSupplyDateTime"));
	    customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("receiverPhoneNumber"));
	    statusColumn.setCellValueFactory(new PropertyValueFactory<Order,OrderStatus>("status"));
	    
	    statusColumn.setCellFactory((param) -> new ComboBoxTableCell<>(new StringConverter<OrderStatus>() {

			@Override
			public String toString(OrderStatus object) {
				return object.toString();
			}

			@Override
			public OrderStatus fromString(String string) {
				return OrderStatus.valueOf(string);
			}
			
		}, OrderStatus.values()));
	    
	    manageOrdersTable.setItems(ordersForManageOrderTable);
	    manageOrdersTable.setEditable(true); // set the table editable in order to edit items
	    
	    
	    /*Save changes in the table view in the local memory*/
	    statusColumn.setOnEditCommit(
	            event ->
	    {
			Order order = event.getRowValue();  // create new object of item get the specific row where we made the change in the status column
			updateOrders.remove(order); // remove this row from updateOrders array
			order.setStatus(event.getNewValue()); // set the new status in the order array
			updateOrders.add(order); // update the updateOrders array with the row that contains the new status
	    	
	    });
		
	} 


}
