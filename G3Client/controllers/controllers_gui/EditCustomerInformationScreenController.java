package controllers_gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
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
import users.ConfirmationStatus;
import users.Customer;
import users.HrManager;
import users.User;
import javafx.fxml.Initializable;

/**
 * Class description: 
 * This is a class for 
 * controlling the UI of Edit Customer 
 * form.
 */
/**
 * 
 * @author Mousa, Srour.
 * 
 * 
 * @version 13/12/2021
 */
public class EditCustomerInformationScreenController extends AbstractBiteMeController implements Initializable{
	
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
    private static EditCustomerInformationScreenController editCustomerInformationScreenController;
    
    /**
     * The arrayList of customers that we get from DB to display into a table.
     */
    public static ArrayList<User> customersList = new ArrayList<>();

    @FXML
    /**
     * The Exit Button.
     */
    private Button btnExit;

    @FXML
    /**
     * The Save Button.
     */
    private Button saveBtn;

    @FXML
    /**
     * The Back Button.
     */
    private Button btnBack;
    
    @FXML
    /**
     * The Edit Button.
     */
    private Button editCustomerBtn;

    @FXML
    /**
     * The Remove Button.
     */
    private Button removeCustomerBtn;

    @FXML
    /**
     * The Help Button.
     */
    private Button btnHelp;

    @FXML
    /**
     * The TableView of customer.
     */
    private TableView<User> customerListTable;
    
    @FXML
    /**
     * The Column that contains the customer ID.
     */
    private TableColumn<User, String> customerIdCol;

    @FXML
    /**
     * The Column that contains the customer first name.
     */
    private TableColumn<User, String> firstNameCol;

    @FXML
    /**
     * The Column that contains the customer last name.
     */
    private TableColumn<User, String> lastNameCol;

    @FXML
    /**
     * The Column that contains the customer type.
     */
    private TableColumn<User, String> customerTypeCol;

    @FXML
    /**
     * The Column that contains the customer status.
     */
    private TableColumn<User, String> customerStatusCol;

    @FXML
    /**
     * The empty text that we use to display messages to the user.
     */
    private Text errorText;
    
    /**
     * This method calls the setBranchManagerPortal to get back the previous screen
     * This method works immedietly after clicking on back button.
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getBackBtn(ActionEvent event) {
    	setBranchManagerPortal(event);
    }
    
    /**
     * This method runs after clicking on edit customer , it checks if the user selected a row 
     * from the table and if not so it displays a message
     * we check if the user is instance of hr manager or business 
     * customer and send message to the server accordingly.
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getEditCustomer(ActionEvent event) {
    	if(customerListTable.getSelectionModel().getSelectedItem() != null) {
    		if(customerListTable.getSelectionModel().getSelectedItem() instanceof BusinessCustomer) {
    			EditBusinessCustomerInformationScreenController.businessCustomer = (BusinessCustomer)customerListTable.getSelectionModel().getSelectedItem();
    			EditBusinessCustomerInformationScreenController EBCIS = new EditBusinessCustomerInformationScreenController();
    			EBCIS.initEditBusinessCustomerInformationScreen();
    			((Node) event.getSource()).getScene().getWindow().hide();
    		}
    
    		else {
    			EditPrivateCustomerInformationScreenController.customer= (Customer) customerListTable.getSelectionModel().getSelectedItem();
    			EditPrivateCustomerInformationScreenController EPCISC = new EditPrivateCustomerInformationScreenController();
    			EPCISC.initEditPrivateCustomerInformationScreen();
    			((Node) event.getSource()).getScene().getWindow().hide();
    			}
    	}
    	else {
    		errorText.setText("Select customer to edit.");
    	}
    }
    
    /**
     * This method does log out and then exit .
     * 
     * @param event ActionEvent of javaFX.
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
     * Displays a pop up message to the user .
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getHelpBtn(ActionEvent event) {
    	PopUpMessages.helpMessage("On this screen you can select whice customer you want to edit.");
    }
    
    /**
     * This method runs after clicking on remove button, it creates and sends the
     * revelvant message to the server according to the user type.
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    void getRemoveBtn(ActionEvent event) {
    	User selectedUser = customerListTable.getSelectionModel().getSelectedItem();  	
    	if(selectedUser != null) {
    		Optional<ButtonType> result = PopUpMessages.confirmationMessage("Remove this customer?");
    		if(result.get() == ButtonType.OK) {
    		ArrayList<String> objectToMessage = new ArrayList<>();
    		objectToMessage.add(selectedUser.getUserId());
    		if(selectedUser instanceof HrManager) {
    			objectToMessage.add("hrmanager");
    		}
    		else if (selectedUser instanceof BusinessCustomer) {
    			objectToMessage.add("businesscustomer");
    		}
    		else {
    			objectToMessage.add("customer");
    		}
    		Message message = new Message(Task.REMOVE_USER_FROM_DB,Answer.WAIT_RESPONSE,objectToMessage);
    		sendToClient(message);
    		errorText.setText("Customer ID:"+selectedUser.getUserId()+" removed.");
    		initialize(null, null);	
    		}
    	}
    	else {
    		errorText.setText("Select customer to remove!");
    	}
    }
    
    /**
     * Loads the current screen , it will be called from another controller ( previous one).
     */
	public void initEditCustomerInformationScreen() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
				//	primaryStage.hide(); 
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/BM10EditCustomerInformationScreen.fxml").openStream());
					editCustomerInformationScreenController = loader.getController();
					Scene scene = new Scene(root);
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("Edit Customers");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	 /**
     * Loads the previous screen after clicking on back button.
     * 
     * @param event ActionEvent of javaFX.
     */
	public void setBranchManagerPortal(ActionEvent event) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					FXMLLoader loader = new FXMLLoader();
					Pane root;
					try {
						Stage Stage = new Stage();
						Stage.setResizable(false);
						root = loader.load(getClass().getResource("/fxmls/UserPortalOfBranchManager.fxml").openStream());
						UserPortalOfBranchManagerController UOBMC = new UserPortalOfBranchManagerController();
						UOBMC.initPortalAgain();
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
	 * This method will load this screen after it will be called from the next screen.
	 */
	public void initPortalAgain() {
		loader = new FXMLLoader();
		Stage primaryStage = new Stage();
		Pane root = null;
		try {
			root = loader.load(getClass().getResource("/fxmls/BM10EditCustomerInformationScreen.fxml").openStream());
			editCustomerInformationScreenController = loader.getController();
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
		primaryStage.setTitle("Edit Customers");
		primaryStage.setScene(scene);
		primaryStage.show();/* show the new screen */
	}
	
	/**
	 * Initialize the data into this screen :
	 * first of all we create an observableList with User type , then after getting all the customers from db
	 * we add them into the observable list , initialize every column of the table and then sets the list into the table.
	 * 
	 * @param  arg0
	 * @param arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<User> customers = FXCollections.observableArrayList();
		Message message = new Message (Task.GET_CUSTOMERS_FROM_DB,Answer.WAIT_RESPONSE,connectedUser);
		sendToClient(message);
		for(User user : customersList)
			if((!user.getStatusInSystem().equals(ConfirmationStatus.PENDING_APPROVAL)) && !(user instanceof HrManager))
				customers.add(user);
		customerIdCol.setCellValueFactory(new PropertyValueFactory<User,String>("userId"));
		firstNameCol.setCellValueFactory(new PropertyValueFactory<User,String>("userFirstName"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<User,String>("userLastName"));
		customerStatusCol.setCellValueFactory(new PropertyValueFactory<User,String>("statusInSystem"));
		customerListTable.setItems(customers);
	}

}
