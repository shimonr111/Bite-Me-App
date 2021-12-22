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
import javafx.util.converter.DefaultStringConverter;
import orders.AbatractSupplyMethod;
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
	private static ObservableList<Order> ordersForManageOrderTable = FXCollections.observableArrayList();

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
    private TableColumn<AbatractSupplyMethod, String> customerPhoneColumn;

    @FXML
    private TableColumn<Order, OrderStatus> statusColumn; // it was OrderStatus instead of String

    @FXML
    private Button btnHelp;

    @FXML
    private Text errorText;

    @FXML
    private Button refreshBtn;
    
    private ObservableList<String> statusData;
    
    
    /**
     * This is a function for going back
     * to the previous screen.
     * 
     * @param event
     */
    @FXML
    void getBackBtn(ActionEvent event) {
    	/*TBD: delete observable list*/
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
     * This is a button for getting new 
     * orders that might have been added 
     * to the order table inside the DB during the 
     * time the screen is already up.
     * 
     * @param event
     */
    @FXML
    void getBtnRefresh(ActionEvent event) {

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
		
		/* this is for the combo box of status column */
		statusData = FXCollections.observableArrayList();
		statusData.add(OrderStatus.APPROVED.toString());
		statusData.add(OrderStatus.UN_APPROVED.toString());
		/* this is for the combo box of status column */
		
		String supplierId=((SupplierWorker) connectedUser).getSupplier().getSupplierId();
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
			System.out.println(ordersForManageOrderTable);
		}
		
//		/*Set data in the table */
//		orderNumColum.setCellValueFactory(new PropertyValueFactory<Order,Integer>("orderNumber"));
//		orderTypeColumn.setCellValueFactory(new PropertyValueFactory<Order,SupplyType>("supplyType"));
//		orderDateColumn.setCellValueFactory(new PropertyValueFactory<Order,Date>("issueDateTime"));
//	    estSupplyTimeColumn.setCellValueFactory(new PropertyValueFactory<Order,Date>("estimatedSupplyDateTime"));
//		customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<AbatractSupplyMethod,String>("receiverPhoneNumber"));
//		
//		/* I have changed the column of status from <Order, OrderStatus> to <Order, String> just for check, to see if we get statusData values inside the combo box */
//		statusColumn.setCellValueFactory(new PropertyValueFactory<Order,OrderStatus>("status"));
//		
//		/* from here is the code to set combo box on this column after we double click on some cell in this column*/
//		statusColumn.setCellValueFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), statusData));
//		statusColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Order, OrderStatus>>(){
//
//			@Override
//			public void handle(TableColumn.CellEditEvent<Order, OrderStatus> event) {
//				System.out.println("Value: " + event.getNewValue());
//			}
//			
//		});
//		
//		manageOrdersTable.setItems(ordersForManageOrderTable);
		
		manageOrdersTable.setEditable(true); // so we can double click on one of the cells of status column, and then change status from the combo box
		
		
	} 


}
