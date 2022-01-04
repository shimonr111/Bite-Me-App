package controllers_gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import users.BusinessCustomer;
import users.User;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import bitemeclient.PopUpMessages;
import communication.Answer;
import communication.Message;
import communication.Task;
import javafx.fxml.Initializable;

/**
 * Class description: 
 * This is a class for 
 * controlling the UI of business customer confirmation
 * from the HR manager portal
 * after clicking on that button.
 */

/**
 * 
 * @author Mousa, Srour.
 * 
 * @version 15/12/2021
 */
public class BusinessCustomerConfirmationScreenController extends AbstractBiteMeController implements Initializable{
	
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
    private static BusinessCustomerConfirmationScreenController businessCustomerConfirmationScreenController;
    
    /**
     * A static arrayList of business customers that we get from DB .
     */
    public static ArrayList<BusinessCustomer> businessCustomersWaitingForConfirmation = new ArrayList<>();
    
    /**
     * The chosen customer to confirm.
     */
	public static BusinessCustomer businessCustomerForConfirmation;
	
	@FXML
	/**
	 * The Exit Button.
	 */
    private Button btnExit;

    @FXML
    /**
     * The Confirm Button.
     */
    private Button confirmBtn;

    @FXML
    /**
     * The Back Button.
     */
    private Button btnBack;

    @FXML
    /**
     * The Deny Button.
     */
    private Button denyBtn;

    @FXML
    /**
     * The Table that display the customers.
     */
    private TableView<BusinessCustomer> businessCustomerTable;

    @FXML
    /**
     * The column that contains the customer ID.
     */
    private TableColumn<BusinessCustomer,String> employeeIdCol;

    @FXML
    /**
     * The column that contains the customer's first Name.
     */
    private TableColumn<BusinessCustomer, String> employeeNameCol;

    @FXML
    /**
     * The column that contains the customer's last Name.
     */
    private TableColumn<BusinessCustomer, String> employeeLastNameCol;

    @FXML
    /**
     * The Help Button.
     */
    private Button btnHelp;

    @FXML
    /**
     * The Save Button.
     */
    private Button saveBtn;

    @FXML
    /**
     * The Empty text that we display messages to the customer threw it.
     */
    private Text errorText;
    
    @FXML
    /**
     * The text fields that we use as a search bar.
     */
    private TextField searchField;
    
    /**
     * This is a function that 
     * after pressing on back button we will call 
     * the method that loads the previous screen.
     * 
     * @param event An ActionEvent of javaFX.
     */
    @FXML
    public void getBackBtn(ActionEvent event) {
    	setHrManagerPortal(event);
    }
    
    /**
     * Pressing on confirm will lead to confirm the employee and change his status ,
     * he will be deleted from the list after confirming him because the list will have
     * only the remaining request to confirm/deny.
     * 
     * @param event An ActionEvent of javaFX.
     */
    @FXML
    public void getConfirmBtn(ActionEvent event) {
    	User selectedCustomer = businessCustomerTable.getSelectionModel().getSelectedItem();
    	if(selectedCustomer != null) {
    			ArrayList<String> objectToMessage = new ArrayList<>();
    			businessCustomerForConfirmation = (BusinessCustomer)selectedCustomer;
    			ConfirmBusinessAskForDetailsController confirmBusinessAskForDetailsController = new ConfirmBusinessAskForDetailsController();
    			confirmBusinessAskForDetailsController.initConfirmBusinessAskForDeatilsScreen();
    			((Node) event.getSource()).getScene().getWindow().hide();
    	}
    	else {
    		errorText.setText("Please choose one employee to confirm");
    	}
    }
    
    /**
     * Denying confirmation request for employee will lead to delete him from the DB
     * and deleting him from the table.
     * 
     * @param event An ActionEvent of javaFX.
     */
    @FXML
    public void getDenyBtn(ActionEvent event) {
      	User selectedCustomer = businessCustomerTable.getSelectionModel().getSelectedItem();  	
    	if(selectedCustomer != null) {
    		Optional<ButtonType> result = PopUpMessages.confirmationMessage("Denying the confirmation will lead to remove this customer!");
    		if(result.get() == ButtonType.OK) {
    		ArrayList<String> objectToMessage = new ArrayList<>();
    		objectToMessage.add(selectedCustomer.getUserId());
    		objectToMessage.add("businesscustomer");
    		Message message = new Message(Task.REMOVE_USER_FROM_DB,Answer.WAIT_RESPONSE,objectToMessage);
    		sendToClient(message);
    		errorText.setText("Customer ID:"+selectedCustomer.getUserId()+" removed.");
    		initialize(null, null);	
    		}
    	}
    }
    
    /**
     * Clicking on exit button will log out the user then disconnect and exit.
     * 
     * @param event An ActionEvent of javaFX.
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
     * Display a pop up message.
     * 
     * @param event An ActionEvent of javaFX.
     */
    @FXML
    public void getHelpBtn(ActionEvent event) {
    	PopUpMessages.helpMessage("On this screen you can confirm or deny business customers registration requests.");
    }
    
    /**
     * This method loads the current screen, it will be 
     * called from the previous screen.
     */
	public void initBusinessCustomerConfirmationScreen() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/HR2BusinessCustomerConfirmationScreen.fxml").openStream());
					businessCustomerConfirmationScreenController = loader.getController();
					Scene scene = new Scene(root);
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("Business Customer Confirmation");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * This method loads the previous screen.
	 * 
	 * @param event An ActionEvent of javaFX.
	 */
	public void setHrManagerPortal(ActionEvent event) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					FXMLLoader loader = new FXMLLoader();
					Pane root;
					try {
						Stage Stage = new Stage();
						Stage.setResizable(false);
						root = loader.load(getClass().getResource("/fxmls/UserPortalOfHRManager.fxml").openStream());
						UserPortalOfHRManagerController UPOHRMC = new UserPortalOfHRManagerController();
						UPOHRMC.initPortalAgain();
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
	 * This method will be called from the next screen
	 * when clicking on back button
	 */
	public void initPortalAgain() {
		loader = new FXMLLoader();
		Stage primaryStage = new Stage();
		Pane root = null;
		try {
			root = loader.load(getClass().getResource("/fxmls/HR2BusinessCustomerConfirmationScreen.fxml").openStream());
			//root = FXMLLoader.load(getClass().getResource("/fxmls/LoginScreen.fxml"));
			businessCustomerConfirmationScreenController = loader.getController();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		scene.setOnMousePressed(pressEvent -> {
		    scene.setOnMouseDragged(dragEvent -> {
		    	primaryStage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
		    	primaryStage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
		    });
		});
		scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();/* show the new screen */
	}

	/**
	 * Initializing the list after getting all the relevant data from DB.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<BusinessCustomer> businessCustomers = FXCollections.observableArrayList();
		Message message = new Message (Task.GET_BUSINESS_CUSTOMERS_FOR_CONFIRMATION,Answer.WAIT_RESPONSE,connectedUser);
		sendToClient(message);
		if(businessCustomersWaitingForConfirmation.isEmpty())
			errorText.setText("No confirmation requests!");
		employeeIdCol.setCellValueFactory(new PropertyValueFactory<BusinessCustomer,String>("userId"));
		employeeNameCol.setCellValueFactory(new PropertyValueFactory<BusinessCustomer,String>("userFirstName"));
		employeeLastNameCol.setCellValueFactory(new PropertyValueFactory<BusinessCustomer,String>("userLastName"));
		businessCustomers.addAll(businessCustomersWaitingForConfirmation);
		businessCustomerTable.setItems(businessCustomers);
		
		FilteredList<BusinessCustomer> filteredData = new FilteredList<BusinessCustomer>(businessCustomers , b -> true);
		searchField.textProperty().addListener((observable, oldValue, newValue)->{
			filteredData.setPredicate(BusinessCustomer ->{//newValue.isBlank() ||
				if(newValue.isEmpty() ||  newValue == null) {
					return true;
				}
				
				String searchKeyWord = newValue.toLowerCase();
				
				if(BusinessCustomer.getUserId().toLowerCase().indexOf(searchKeyWord)>-1) {
					return true;
				}
				else if(BusinessCustomer.getUserFirstName().toLowerCase().indexOf(searchKeyWord)>-1) {
					return true;
				}
				else if(BusinessCustomer.getUserLastName().toLowerCase().indexOf(searchKeyWord)>-1) {
					return true;
				}
				else
					return false; // nothing to display
				
				
			});
		});
		
		SortedList<BusinessCustomer>  sortedData = new SortedList<>(filteredData);
		
		sortedData.comparatorProperty().bind(businessCustomerTable.comparatorProperty());
		
		businessCustomerTable.setItems(sortedData);
		
		
	}

}
