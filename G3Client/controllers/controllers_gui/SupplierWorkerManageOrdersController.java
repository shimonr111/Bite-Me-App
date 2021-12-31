package controllers_gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import bitemeclient.PopUpMessages;
import clientanalyze.AnalyzeClientListener;
import clientanalyze.AnalyzeMessageFromServer;
import communication.Answer;
import communication.Message;
import communication.Task;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import orders.Order;
import orders.OrderStatus;
import orders.SupplyType;
import users.SupplierWorker;

/**
 * 
 * @author Lior, Guzovsky.
 * @author Shimon, Rubin.
 * 
 * Class description: 
 * This is a class for managing all the orders
 * for the supplier. In this screen the supplier worker changes status of 
 * orders.
 * 
 * @version 31/12/2021
 */
public class SupplierWorkerManageOrdersController extends AbstractBiteMeController implements Initializable{
	/**
	 * Class members description:
	 */
	
	private static FXMLLoader loader;
	private static SupplierWorkerManageOrdersController supplierWorkerManageOrders; /*used for switching the screen to the next one*/
	public static ArrayList<Order> orderListFromDB = new ArrayList<>();
	public static ArrayList<Order> updateOrders = new ArrayList<>();
	private static AnalyzeClientListener listener;
	public static ObservableList<Order> ordersForManageOrderTable;
	public static String approvedCustomerEmail;
	
	@FXML
    private Button btnExit;

    @FXML
    private Button btnBack;

    @FXML
    private TableView<Order> manageOrdersTable;

    @FXML
    private TableColumn<Order, Integer> orderNumColum;

    @FXML
    private TableColumn<Order, SupplyType> orderTypeColumn;

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
    
    @FXML
    private TextField searchTextField;
    
    /**
     * This is a function for going back
     * to the previous screen.
     * 
     * @param event
     */
    @FXML
    public void getBackBtn(ActionEvent event) {
    	/*TBD: delete observable list*/
    	updateOrders.clear();// clear this array for the next time we come back for this screen
    	AnalyzeMessageFromServer.removeClientListener(listener); //delete listener from listener list
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
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
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
     * This is a function for exiting 
     * the screen.
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
     * This is a function for 
     * popping help message if pressed by the user.
     * 
     * @param event
     */
    @FXML
    public void getHelpBtn(ActionEvent event) {
    	PopUpMessages.helpMessage("On this screen you can manage the resturant orders (update the orders status).");
    }

	/**
	 * This is the start function for this 
	 * screen.
	 * In this function we add the controller
	 * as a listener that listens for orders 
	 * added by any customer so that he can
	 * display them without the use of refresh 
	 * button.
	 */
	public void initSupplierWorkerManageOrdersScreen() {
		/*Add listener for */
		AnalyzeMessageFromServer.addClientListener(listener = new AnalyzeClientListener(){ 
			
			@Override
			public void addOrderToSupplierTable(Order order) {
				/*check if the supplier worker that is connected in the system is working in the same restaurant that is in the order received*/
				if(((SupplierWorker)connectedUser).getSupplier().getSupplierId().equals(order.getSupplierUserId())) {
				orderListFromDB.add(order); //add the new order received from the user to the array list 
				ordersForManageOrderTable.add(order); //add to the observable list as well
				}
			}
		});
		
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
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("Manage Orders");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * This method is used for initialization 
	 * of the screen.
	 * We add in this functions all the orders received
	 * from the DB related to that specific restaurant that their
	 * status is  OrderStatus.PENDING_APPROVAL, than we show it 
	 * to the supplier worker and enable him to approve or disapprove
	 * the order sent by the customer. In addition, we send an email if the
	 * order is approved by the supplier worker to the customers personal email. 
	 * When an order that is shown in the table view changes status (from OrderStatus.PENDING_APPROVAL
	 * to OrderStatus.UN_APPROVED or OrderStatus.APPROVED) we delete it from the table view 
	 * and update the DB.
	 * 
	 * @param arg0 - not in use
	 * @param arg1 - not in use
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		String supplierId=((SupplierWorker) connectedUser).getSupplier().getSupplierId();
		ordersForManageOrderTable = FXCollections.observableArrayList();
		/*send message to server to get all orders for this supplier worker that works in specific restaurant*/
		Message message = new Message (Task.SUPPLIER_WORKER_GET_ALL_RELEVANT_ORDERS,Answer.WAIT_RESPONSE,supplierId); 
		sendToClient(message);
	
		
		/*Check if the DB doesn't have orders for this restaurant*/
		if(orderListFromDB.isEmpty()) {
			errorText.setVisible(true);
			errorText.setText("There are no orders for this restaurant!");
			errorText.setFill(Color.RED);
		}
			//add all the wrapper orders to the table view
			ordersForManageOrderTable.addAll(orderListFromDB);

		
		 updateOrders = new ArrayList<Order>(); //copy the orders we got from DB to our updateOrders array ---orderListFromDB
		
		/*Set data in the table */
		orderNumColum.setCellValueFactory(new PropertyValueFactory<Order,Integer>("orderNumber"));
		orderTypeColumn.setCellValueFactory(new PropertyValueFactory<Order,SupplyType>("supplyType"));
	    estSupplyTimeColumn.setCellValueFactory(new PropertyValueFactory<Order,Date>("estimatedSupplyDateTime"));
	    customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("receiverPhoneNumber"));
	    statusColumn.setCellValueFactory(new PropertyValueFactory<Order,OrderStatus>("status"));
	    /*Show only the options below for the combo box in the table view*/
	    OrderStatus[] orderStatusArray = {OrderStatus.PENDING_APPROVAL , OrderStatus.UN_APPROVED , OrderStatus.APPROVED};
	    statusColumn.setCellFactory((param) -> new ComboBoxTableCell<>(new StringConverter<OrderStatus>() {

			@Override
			public String toString(OrderStatus object) {
				return object.toString();
			}

			@Override
			public OrderStatus fromString(String string) {
				return OrderStatus.valueOf(string);
			}
			
		},orderStatusArray));
	    
	    manageOrdersTable.setItems(ordersForManageOrderTable);
	    manageOrdersTable.setEditable(true); // set the table editable in order to edit items
	    
	    
	    /*Save changes in the table view when the user changed the status of the reservation*/
	    statusColumn.setOnEditCommit(
	            event ->
	    {
			Order order = event.getRowValue();  // create new object of item get the specific row where we made the change in the status column
			order.setStatus(event.getNewValue()); // set the new status in the order array
	        /*Simulation for sending an email to the user with his details*/
	        if(event.getNewValue() == OrderStatus.APPROVED) {
	        	// get the user email
	        	ArrayList<String> list = new ArrayList<>();
	        	list.add(order.getCustomerUserId());
	        	list.add(order.getCustomerUserType());
	        	sendToClient(new Message(Task.GET_USER_EMAIL,Answer.WAIT_RESPONSE,list));
				if(approvedCustomerEmail!=null) {
					try {
						sendMail(approvedCustomerEmail);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				PopUpMessages.simulationMessage("Simulation","Simulation Email to the user!","E-mail sent to : " +approvedCustomerEmail);
				/*remove from the table view after being approved*/
				ordersForManageOrderTable.remove(order);
				/*Set issued Date time to the point where the order is approved in the restaurant*/
				order.setIssueDateTime(new Date()); //test
			}else if(event.getNewValue() == OrderStatus.UN_APPROVED) {
				ArrayList<String> updateBudgetAndBalance = new ArrayList<>();
	        	updateBudgetAndBalance.add(order.getCustomerUserId());
	        	updateBudgetAndBalance.add(String.valueOf(order.getOrderNumber()));
	        	updateBudgetAndBalance.add(order.getSupplierUserId());
	        	/*Send to the server message for restoring the payment amount*/
	        	sendToClient(new Message(Task.SUPPLIER_RESTORE_BALANCE_AND_BUDGET_BALANCE,Answer.WAIT_RESPONSE,updateBudgetAndBalance));
	        	ordersForManageOrderTable.remove(order);
			}
			updateOrders.add(order); // update the updateOrders array with the row that contains the new status
	        sendToClient(new Message(Task.MANAGE_ORDER_FINISHED,Answer.WAIT_RESPONSE,updateOrders));//send message to the server telling the manage order is finished and then push into DB
	        updateOrders.remove(order); //remove from the updated arrayList
	    });
	    
	    /*Used for searching bar in the table view*/
	    FilteredList<Order> filteredData = new FilteredList<Order>(ordersForManageOrderTable , b -> true);
		searchTextField.textProperty().addListener((observable, oldValue, newValue)->{
			filteredData.setPredicate(Order ->{
				if(newValue.isEmpty() ||  newValue == null) {
					return true;
				}
				
				String searchKeyWord = newValue.toLowerCase();
				
				if(Integer.toString(Order.getOrderNumber()).toLowerCase().indexOf(searchKeyWord)>-1) {
					return true;
				}
				
				else
					return false; // nothing to display
				
				
			});
		});
		
		SortedList<Order>  sortedData = new SortedList<>(filteredData);
		
		sortedData.comparatorProperty().bind(manageOrdersTable.comparatorProperty());
		
		manageOrdersTable.setItems(sortedData);
		
	} 
	
	/**
	 * This method sets the properties for sending an email
	 * to the customer.
	 * 
	 * @param reciever - the receivers order email
	 * @throws Exception
	 */
	public static void sendMail(String reciever) throws Exception {
		System.out.println("Sending email");
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		String myEmail = "g3.biteme@gmail.com";
		String password = "G.bite.3.me";
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myEmail, password);
			}
		});
		
		javax.mail.Message message = prepareMessage(session, myEmail,reciever);
		
		Transport.send(message);
		System.out.println("Message sent");
		
		
	}
	
	/**
	 * This method prepares the message that is being sent via email
	 * to the user.
	 * 
	 * @param session
	 * @param myEmail - the companies (G3-Bite-Me) email.
	 * @param reciever - the receivers email.
	 * 
	 * @throws MessagingException in case the setFrom() method doesn't work.
	 * @return javax.mail.Message - the message we want to send to the customer
	 */
	private static javax.mail.Message prepareMessage(Session session, String myEmail,String reciever) {
		try {
			javax.mail.Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myEmail));
			message.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(reciever));
			message.setSubject("Bite Me - G3 - Email Simulation");
			message.setText("Your order is approved by the restaurant, soon you will meet again!");
			return message;
		}catch(Exception ex) {
			System.out.println("sending email failed");
		}
		return null;
	}


}
