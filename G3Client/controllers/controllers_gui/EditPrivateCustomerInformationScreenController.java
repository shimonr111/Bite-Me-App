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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import users.Branch;
import users.ConfirmationStatus;
import users.Customer;
import javafx.fxml.Initializable;

/**
 * Class description: 
 * This is a class for 
 * controlling the UI of Edit Customer that appears immediately after clicking
 * on Edit while selecting Customer.
 * form.
 * 
 * @author Mousa, Srour.
 * 
 * @version 13/12/2021
 */
public class EditPrivateCustomerInformationScreenController extends AbstractBiteMeController implements Initializable{
	 
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
    private static EditPrivateCustomerInformationScreenController editPrivateCustomerInformationScreenController;
    
    /**
     * The chosen customer for editing.
     */
    public static Customer customer;

    @FXML
    /**
     * The Exit Button.
     */
    private Button btnExit;

    @FXML
    /**
     * The Close Button.
     */
    private Button btnclose;

    @FXML
    private Button btnclose1;

    @FXML
    /**
     * The Help Button.
     */
    private Button btnHelp;
    
    @FXML
    /**
     * The Deny Button.
     */
    private Button btnSave;

    @FXML
    /**
     * First Name Text Field.
     */
    private TextField firstNameTxtField;

    @FXML
    /**
     * Last Name Text Field.
     */
    private TextField lastNameTxtField;

    @FXML
    /**
     * ID Text Field.
     */
    private TextField idNumTxtField;

    @FXML
    /**
     *  Phone Number Text Field.
     */
    private TextField phoneTxtField;

    @FXML
    /**
     * E-Mail Text Field.
     */
    private TextField emailTxtField;

    @FXML
    /**
     * ComboBox of Branches.
     */
    private ComboBox<Branch> setHomeBranchCombo;

    @FXML
    /**
     * ComboBox of statuses.
     */
    private ComboBox<ConfirmationStatus> setStatusComboBox;
    
    @FXML
    /***
     * The empty text to display messages to the user.
     */
    private Text displayMessage;

    /**
     * This method runs after clicking on SAVE button , 
     * it checks if there were any changes to the customer status
     * and if yes it sends a relevant message to the server.
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getSaveBtn(ActionEvent event) {
    	ConfirmationStatus oldStatus = customer.getStatusInSystem();
    	String newStatus = setStatusComboBox.getValue().toString();
    	if(oldStatus.toString().equals(newStatus)) {
    		displayMessage.setText("There were no changes.");
    	}
    	else {
      		Optional<ButtonType> result = PopUpMessages.confirmationMessage("Save changes?");
    		if(result.get() == ButtonType.OK) {
    		ArrayList<String> objectToMessage = new ArrayList<>();
    		objectToMessage.add(customer.getUserId());
    		objectToMessage.add(newStatus);
    		objectToMessage.add("customer"); // table name
    		Message message = new Message(Task.UPDATE_CUSTOMER_STATUS,Answer.WAIT_RESPONSE,objectToMessage);
    		sendToClient(message);
    		switch(newStatus) {
    		case "CONFIRMED":
    			customer.setStatusInSystem(ConfirmationStatus.CONFIRMED);
    			break;
    		case "FROZEN" :
    			customer.setStatusInSystem(ConfirmationStatus.FROZEN);
    			break;
    		case "PENDING_APPROVAL":
    			customer.setStatusInSystem(ConfirmationStatus.PENDING_APPROVAL);
    			break;
    		default:
    			break;		
    		}
    		displayMessage.setText("Customer status changed from:" + oldStatus.toString() + " to:" + newStatus + ".");
    		}
    	}
    }
    
    /**
     * Calls the method that loads the previous screen.
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getBackBtn(ActionEvent event) {
    	setEditCustomerInformationScreen(event);
    }
    
    /**
     * Logout then exit.
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
     * Display a message to the customer.
     * @param event ActionEvent of javaFX.
     */
    @FXML
    void getHelpBtn(ActionEvent event) {
    	PopUpMessages.helpMessage("On this screen you can edit private customers information.");

    }
    
    /**
     * Sets the screen , it will be called from previous screen.
     */
	public void initEditPrivateCustomerInformationScreen() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
				//	primaryStage.hide(); 
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/BM11EditPrivateCustomerInformationScreen.fxml").openStream());
					editPrivateCustomerInformationScreenController = loader.getController();
					Scene scene = new Scene(root);
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("Edit Private Customer");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 *  This method for the back button , it sets the previous screen.
	 *  
	 * @param event ActionEvent of javaFX.
	 */
	public void setEditCustomerInformationScreen(ActionEvent event) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					FXMLLoader loader = new FXMLLoader();
					Pane root;
					try {
						Stage Stage = new Stage();
						Stage.setResizable(false);
						root = loader.load(getClass().getResource("/fxmls/BM10EditCustomerInformationScreen.fxml").openStream());
						EditCustomerInformationScreenController ECISC = new EditCustomerInformationScreenController();
						ECISC.initPortalAgain();
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
	 * Initialize the fields and boxes accordingly.
	 * 
	 * @param arg0
	 * @param arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		firstNameTxtField.setText(customer.getUserFirstName()); lastNameTxtField.setText(customer.getUserLastName()); idNumTxtField.setText(customer.getUserId()); 
		phoneTxtField.setText(customer.getPhoneNumber());  emailTxtField.setText(customer.getUserEmail()); 
		setHomeBranchCombo.setValue(customer.getHomeBranch());
		setStatusComboBox.setValue(customer.getStatusInSystem());
		setStatusComboBox.getItems().addAll(ConfirmationStatus.CONFIRMED,ConfirmationStatus.FROZEN);
		
	}

}
