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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import users.BusinessCustomer;
import util.OrderForView;

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
 * 
 * @author Mousa, Srour
 * Class description: 
 * This class is a controller of the view reports screen.
 * 
 * @version 25/12/2021
 */
public class WatchOrderHistoryScreenController extends AbstractBiteMeController implements Initializable{
	
	
	/**
	 * class members description:
	 */
	
	public static FXMLLoader loader;
	private static WatchOrderHistoryScreenController watchOrderHistoryScreenController;
	public static ArrayList<OrderForView> ordersForCustomer = new ArrayList<>();
	
    @FXML // fx:id="backBtn"
    private Button backBtn; // Value injected by FXMLLoader

    @FXML // fx:id="dateCol"
    private TableColumn<OrderForView,String> dateCol; // Value injected by FXMLLoader

    @FXML // fx:id="exitBtn"
    private Button exitBtn; // Value injected by FXMLLoader

    @FXML // fx:id="helpBtn"
    private Button helpBtn; // Value injected by FXMLLoader

    @FXML // fx:id="nameTxt"
    private Text nameTxt; // Value injected by FXMLLoader

    @FXML // fx:id="orderDetailsCol"
    private TableColumn<OrderForView,String> orderDetailsCol; // Value injected by FXMLLoader

    @FXML // fx:id="ordersTable"
    private TableView<OrderForView> ordersTable; // Value injected by FXMLLoader
    
    @FXML // fx:id="refreshBtn"
    private Button refreshBtn; // Value injected by FXMLLoader

    @FXML // fx:id="resturantNameCol"
    private TableColumn<OrderForView,String> resturantNameCol; // Value injected by FXMLLoader

    @FXML // fx:id="statusCol"
    private TableColumn<OrderForView,String> statusCol; // Value injected by FXMLLoader

    @FXML // fx:id="timeCol"
    private TableColumn<OrderForView,String> timeCol; // Value injected by FXMLLoader


    @FXML
    void getBackBtn(ActionEvent event) {
    	if(connectedUser instanceof BusinessCustomer)
    		setBusinessCustomerPortal(event);
    	else
    		setPrivateCustomerPortal(event);
    }

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
    void getHelpBtn(ActionEvent event) {
    	PopUpMessages.helpMessage("On this table you can see your orders that have not been completed yet.");	
    }
    
    @FXML
    void getRefreshBtn(ActionEvent event) {
    	initialize(null, null);
    }
    
    /**
     * this method loads the current screen, it will be called from the previous screen.
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
					Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) { 
							event.consume();
							Stage.close();
						}
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
	 * this method loads the private customer portal.
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
	 * this method loads the business customer portal
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
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Message message = new Message (Task.GET_ORDERS_FOR_USER,Answer.WAIT_RESPONSE,connectedUser.getUserId());
		sendToClient(message);
		nameTxt.setText(connectedUser.getUserFirstName() + "'s orders in progres: ");
		ObservableList<OrderForView> ordersObservable = FXCollections.observableArrayList();
		if(!ordersForCustomer.isEmpty()) {
			ordersObservable.addAll(ordersForCustomer);
			resturantNameCol.setCellValueFactory(new PropertyValueFactory<OrderForView,String>("resturantName"));
			dateCol.setCellValueFactory(new PropertyValueFactory<OrderForView,String>("orderDate"));
			timeCol.setCellValueFactory(new PropertyValueFactory<OrderForView,String>("orderTime"));
			orderDetailsCol.setCellValueFactory(new PropertyValueFactory<OrderForView,String>("orderDetails"));
			statusCol.setCellValueFactory(new PropertyValueFactory<OrderForView,String>("orderStatus"));
			ordersTable.setItems(ordersObservable);
		}
		
	}

}
