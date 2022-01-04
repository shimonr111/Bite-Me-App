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
import users.Supplier;
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
 *  Class description: 
 * This is a class for 
 * controlling the UI of Supplier Registration Screen that appears immediately after clicking
 * on Supplier Registration  from the branch manager portal form.
 * 
 * 
 * @author Mousa, Srour.
 * @version 22/12/2021
 */
public class SupplierRegistrationScreenController extends AbstractBiteMeController implements Initializable{
	
	/**
	 * class members description:
	 */
	
	/**
	 * The FXMLLoader of the current screen.
	 */
	private static FXMLLoader loader;
	
	/**
	 * A static object of the current class.
	 */
    private static SupplierRegistrationScreenController supplierRegistrationScreenController;
    
    /**
     * ArrayList of suppliers waiting for registration that we get from DB.
     */
    public static ArrayList<Supplier> suppliersWaitingForConfirmation = new ArrayList<>();
    
    @FXML
    /**
     * The Back Button.
     */
    private Button BackBtn;

    @FXML
    /**
     * The Complete Registration Button.
     */
    private Button completeRegistrationBtn;

    @FXML
    /**
     * The Empty text that we display messages to the customer threw it.
     */
    private Text displayMessage;

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
     * The text field that we use as a search bar.
     */
    private TextField searchField;

    @FXML
    /**
     * The column of supplier ID.
     */
    private TableColumn<Supplier, String> supllierIdCol;

    @FXML
    /**
     * The column of supplier Email.
     */
    private TableColumn<Supplier, String>  supplierEmailCol;

    @FXML
    /**
     * The column of supplier Name.
     */
    private TableColumn<Supplier, String>  supplierNameCol;

    @FXML
    /**
     * The column of supplier Phone Number.
     */
    private TableColumn<Supplier, String> supplierPhoneCol;

    @FXML
    /**
     * The column of supplier Revenue Fee.
     */
    private TableColumn<Supplier, Double> supplierRevenueFeeCol;

    @FXML
    /**
     * The TableView of suppliers.
     */
    private TableView<Supplier> suppliersTable;

    /**
     * This method will call the method that loads
     * the previous screen.
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getBackBtn(ActionEvent event) {
    	setBranchManagerPortal(event);
    }

    /**
     * This method will run after clicking save button
     * it will show a confirmation box to confirm if the branch manager
     * wants to confirm m this supplier, and if he clicks on OK button
     * so we create a message to the server to change the supplier
     * status.
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getCompleteRegistrationBtn(ActionEvent event) {
    	Supplier selectedSupplier = suppliersTable.getSelectionModel().getSelectedItem();
    	if(selectedSupplier != null) {
    		Optional<ButtonType> result = PopUpMessages.confirmationMessage("Are you sure you want to confirm:" + selectedSupplier.getSupplierName() + "?");
    		if(result.get() == ButtonType.OK) {
        		Message message = new Message(Task.CONFIRM_SUPPLIER,Answer.WAIT_RESPONSE,selectedSupplier);
        		sendToClient(message);
        		displayMessage.setText("Supplier:"+ selectedSupplier.getSupplierName() +" confirmed.");
        		initialize(null, null);	
    		}
    	}
    	else {
    		displayMessage.setText("Select company to confirm.");
    	}
    }

    /**
     * This method will disconnect the specific client,
     * log out the connected user and exit.
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
     * This method display a pop up message 
     * to the user.
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    void getHelpBtn(ActionEvent event) {
    	PopUpMessages.helpMessage("Confirm or Deny the supplier registration requests by clicking on the buttons while selecting suppliers at the table!");
    }
    
    /**
     * This method loads the current screen, it will be called from the previous screen.
     */
	public void initSupplierRegistrationScreen() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/BM14SupplierRegistrationScreen.fxml").openStream());
					supplierRegistrationScreenController = loader.getController();
					Scene scene = new Scene(root);
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("Supplier Registration");
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
     * This method will initialize the list and
     * gets all the relevant data from the DB.
     * 
     * @param arg0
     * @param arg1
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<Supplier> suppliersObservable = FXCollections.observableArrayList();
		Message message = new Message (Task.GET_SUPPLIERS_FOR_REGISTRATION,Answer.WAIT_RESPONSE,connectedUser.getHomeBranch());
		sendToClient(message);
		if(suppliersWaitingForConfirmation.isEmpty())
			displayMessage.setText("No suppliers waiting for registration");
		suppliersObservable.addAll(suppliersWaitingForConfirmation);
		supllierIdCol.setCellValueFactory(new PropertyValueFactory<Supplier,String>("supplierId"));
		supplierEmailCol.setCellValueFactory(new PropertyValueFactory<Supplier,String>("email"));
		supplierNameCol.setCellValueFactory(new PropertyValueFactory<Supplier,String>("supplierName"));
		supplierRevenueFeeCol.setCellValueFactory(new PropertyValueFactory<Supplier,Double>("revenueFee"));
		supplierPhoneCol.setCellValueFactory(new PropertyValueFactory<Supplier,String>("phoneNumber"));
		suppliersTable.setItems(suppliersObservable);
		
		FilteredList<Supplier> filteredData = new FilteredList<Supplier>(suppliersObservable , b -> true);
		searchField.textProperty().addListener((observable, oldValue, newValue)->{
			filteredData.setPredicate(Supplier ->{//newValue.isBlank() ||
				if(newValue.isEmpty() ||  newValue == null) {
					return true;
				}
				String searchKeyWord = newValue.toLowerCase();
				
				if(Supplier.getSupplierId().toLowerCase().indexOf(searchKeyWord)>-1) {
					return true;
				}
				else if(Supplier.getSupplierName().toLowerCase().indexOf(searchKeyWord)>-1) {
					return true;
				}
				else if(Supplier.getEmail().toLowerCase().indexOf(searchKeyWord)>-1) {
					return true;
				}
				else if(Supplier.getPhoneNumber().toLowerCase().indexOf(searchKeyWord)>-1) {
					return true;
				}
				else if(Double.toString(Supplier.getRevenueFee()).toLowerCase().indexOf(searchKeyWord)>-1) {
					return true;
				}
				else
					return false; // nothing to display
				
				
			});
		});
		
		SortedList<Supplier>  sortedData = new SortedList<>(filteredData);
		
		sortedData.comparatorProperty().bind(suppliersTable.comparatorProperty());
		
		suppliersTable.setItems(sortedData);

		
		
	}

}
