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
import users.Customer;
import users.HrManager;
import users.User;
import javafx.fxml.Initializable;

/**
 * 
 * @author Mousa, Srour
 * Class description: 
 * This is a class for 
 * controlling the UI of Edit Customer 
 * form.
 * 
 * @version 13/12/2021
 */
public class EditCustomerInformationScreenController extends AbstractBiteMeController implements Initializable {
	private static FXMLLoader loader;
    private static EditCustomerInformationScreenController editCustomerInformationScreenController;
    public static ArrayList<User> customersList = new ArrayList<>();

    @FXML
    private Button btnExit;

    @FXML
    private Button saveBtn;

    @FXML
    private Button btnclose;

    @FXML
    private Button btnBack;

    @FXML
    private TableView<User> customerListTable;
    

    @FXML
    private TableColumn<User, String> customerIdCol;

    @FXML
    private TableColumn<User, String> firstNameCol;

    @FXML
    private TableColumn<User, String> lastNameCol;

    @FXML
    private TableColumn<User, String> customerTypeCol;

    @FXML
    private TableColumn<User, String> customerStatusCol;

    @FXML
    private Button editCustomerBtn;

    @FXML
    private Button removeCustomerBtn;

    @FXML
    private Button btnHelp;

    @FXML
    private Text errorText;

    @FXML
    void getBackBtn(ActionEvent event) {
    	setBranchManagerPortal(event);
    }

    @FXML
    void getCloseBtn(ActionEvent event) {

    }

    @FXML
    void getEditCustomer(ActionEvent event) {
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
    		errorText.setText("Please, select one customer to edit.");
    	}
    }

    @FXML
    void getExitBtn(ActionEvent event) {
		Message message = new Message(Task.LOGOUT,Answer.WAIT_RESPONSE,connectedUser);
		sendToClient(message);
		connectedUser = null;
		System.exit(0);
    }

    @FXML
    void getHelpBtn(ActionEvent event) {
    	PopUpMessages.helpMessage("Please choose which customer you want to Edit or Remove");
    }

    @FXML
    void getRemoveBtn(ActionEvent event) {
    	User selectedUser = customerListTable.getSelectionModel().getSelectedItem();
    	if(selectedUser != null) {
    		
    		if(selectedUser instanceof HrManager) {
    			
    		}
    		else if (selectedUser instanceof BusinessCustomer) {
    			
    		}
    		else {
    			
    		}
    	}
    	else {
    		errorText.setText("Please, select one customer to remove.");
    	}
    }
    
    /**
     * 
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
					Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) { 
							event.consume();
							Stage.close();
						}
					});
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
	 * 
	 * @param event
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
	 * 
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
		primaryStage.setTitle("Edit Customers");
		primaryStage.setScene(scene);
		primaryStage.show();/* show the new screen */
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<User> customers = FXCollections.observableArrayList();
		Message message = new Message (Task.GET_CUSTOMERS_FROM_DB,Answer.WAIT_RESPONSE,connectedUser);
		sendToClient(message);
		for(User user : customersList)
			customers.add(user);
		customerIdCol.setCellValueFactory(new PropertyValueFactory<User,String>("userId"));
		firstNameCol.setCellValueFactory(new PropertyValueFactory<User,String>("userFirstName"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<User,String>("userLastName"));
		customerStatusCol.setCellValueFactory(new PropertyValueFactory<User,String>("statusInSystem"));
		customerListTable.setItems(customers);
		
		
		

		
	}

}
