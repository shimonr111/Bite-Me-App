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
import javafx.stage.WindowEvent;
import users.BusinessCustomer;
import users.HrManager;
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
 * 
 * @author Mousa, Srour
 * Class description: 
 * This is a class for 
 * controlling the UI of business customer confirmation from the HR manager portal
 * after clicking on that button.
 * 
 * @version 15/12/2021
 */
public class BusinessCustomerConfirmationScreenController extends AbstractBiteMeController implements Initializable{
	
	/**
	 * Class members description:
	 */
	private static FXMLLoader loader;
    private static BusinessCustomerConfirmationScreenController businessCustomerConfirmationScreenController;
    public static ArrayList<BusinessCustomer> businessCustomersWaitingForConfirmation = new ArrayList<>();
	
	@FXML
    private Button btnExit;

    @FXML
    private Button confirmBtn;

    @FXML
    private Button btnBack;

    @FXML
    private Button denyBtn;

    @FXML
    private TableView<BusinessCustomer> businessCustomerTable;

    @FXML
    private TableColumn<BusinessCustomer,String> employeeIdCol;

    @FXML
    private TableColumn<BusinessCustomer, String> employeeNameCol;


    @FXML
    private TableColumn<BusinessCustomer, String> employeeLastNameCol;

    @FXML
    private Button btnHelp;

    @FXML
    private Button saveBtn;

    @FXML
    private Text errorText;
    
    /**
     * after pressing on back button we will call the method that loads the previous screen.
     * @param event
     */
    @FXML
    void getBackBtn(ActionEvent event) {
    	setHrManagerPortal(event);
    }
    
    /**
     * Pressing on confirm will lead to confirm the employee and change his status ,
     * he will be deleted from the list after confirming him because the list will have
     * only the remaining request to confirm/deny.
     * @param event
     */
    @FXML
    void getConfirmBtn(ActionEvent event) {
    	User selectedCustomer = businessCustomerTable.getSelectionModel().getSelectedItem();
    	if(selectedCustomer != null) {
    		Optional<ButtonType> result = PopUpMessages.confirmationMessage("Click OK if you are sure you want to confirm this employee");
    		if(result.get() == ButtonType.OK) {
    			ArrayList<String> objectToMessage = new ArrayList<>();
    			objectToMessage.add(selectedCustomer.getUserId());
    			objectToMessage.add("CONFIRMED");
    			objectToMessage.add("businesscustomer"); // table name
        		Message message = new Message(Task.UPDATE_CUSTOMER_STATUS,Answer.WAIT_RESPONSE,objectToMessage);
        		sendToClient(message);
        		errorText.setText("Customer ID: "+selectedCustomer.getUserId()+" was confirmed.");
        		initialize(null, null);	
    		}
    	}
    }
    
    /**
     * Denying confirmation request for employee will lead to delete him from the DB
     * and deleting him from the table.
     * @param event
     */
    @FXML
    void getDenyBtn(ActionEvent event) {
      	User selectedCustomer = businessCustomerTable.getSelectionModel().getSelectedItem();  	
    	if(selectedCustomer != null) {
    		Optional<ButtonType> result = PopUpMessages.confirmationMessage("Denying the confirmation will lead to remove this customer");
    		if(result.get() == ButtonType.OK) {
    		ArrayList<String> objectToMessage = new ArrayList<>();
    		objectToMessage.add(selectedCustomer.getUserId());
    		objectToMessage.add("businesscustomer");
    		Message message = new Message(Task.REMOVE_USER_FROM_DB,Answer.WAIT_RESPONSE,objectToMessage);
    		sendToClient(message);
    		errorText.setText("Customer ID: "+selectedCustomer.getUserId()+" was removed.");
    		initialize(null, null);	
    		}
    	}
    }
    
    /**
     * clicking on exit button will log out the user then disconnect and exit.
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
     * display a pop up message.
     * @param event
     */
    @FXML
    void getHelpBtn(ActionEvent event) {
    	PopUpMessages.helpMessage("Confirm or Deny the confirmation request for business customers");
    }
    
    /**
     * this method loads the current screen, it will be called from the previous screen.
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
					Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) { 
							event.consume();
							Stage.close();
						}
					});
					//scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
					Stage.setTitle("Business Customer Registration");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * this method loads the previous screen.
	 * @param event
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
	 * Initializing the list after getting all the relevant data from DB.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<BusinessCustomer> businessCustomers = FXCollections.observableArrayList();
		Message message = new Message (Task.GET_BUSINESS_CUSTOMERS_FOR_CONFIRMATION,Answer.WAIT_RESPONSE,connectedUser);
		sendToClient(message);
		if(businessCustomersWaitingForConfirmation.isEmpty())
			errorText.setText("No Confirmation Requests");
		employeeIdCol.setCellValueFactory(new PropertyValueFactory<BusinessCustomer,String>("userId"));
		employeeNameCol.setCellValueFactory(new PropertyValueFactory<BusinessCustomer,String>("userFirstName"));
		employeeLastNameCol.setCellValueFactory(new PropertyValueFactory<BusinessCustomer,String>("userLastName"));
		businessCustomers.addAll(businessCustomersWaitingForConfirmation);
		businessCustomerTable.setItems(businessCustomers);
		
		
	}

}
