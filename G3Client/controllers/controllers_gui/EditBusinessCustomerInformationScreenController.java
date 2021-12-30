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
import javafx.fxml.Initializable;
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
import users.BudgetType;
import users.BusinessCustomer;
import users.ConfirmationStatus;


/**
 * 
 * @author Mousa, Srour.
 * 
 * Class description: 
 * This is a class for 
 * controlling the UI of Edit Business Customer that appears immediately after clicking
 * on Edit while selecting Business Customer.
 * form.
 * 
 * @version 13/12/2021
 */

public class EditBusinessCustomerInformationScreenController extends AbstractBiteMeController implements Initializable{
	
	/**
	 * Class members description:
	 */
	private static FXMLLoader loader;
    private static EditBusinessCustomerInformationScreenController editBusinessCustomerInformationScreenController;
    public static BusinessCustomer businessCustomer;
    
	@FXML
    private Button btnExit;

    @FXML
    private Button btnclose;

    @FXML
    private Button btnclose1;

    @FXML
    private Button btnHelp;
    
    @FXML
    private Button btnSave;

    @FXML
    private TextField firstNameTxtField;

    @FXML
    private TextField lastNameTxtField;

    @FXML
    private TextField idNumTxtField;

    @FXML
    private TextField phoneTxtField;

    @FXML
    private TextField emailTxtField;

    @FXML
    private TextField monthlyMaxBudgedTxtField;


    @FXML
    private ComboBox<Branch> setHomeBranchCombo;

    @FXML
    private ComboBox<String> companyNameCombo;

    @FXML
    private ComboBox<BudgetType> budgetTypeCombo;

    @FXML
    private ComboBox<ConfirmationStatus> statusComboBox;
    
    @FXML
    private Text displayMessage;

    /**
     * This method...
     * 
     * @param event
     */
    @FXML
    public void getSaveBtn(ActionEvent event) {
    	ConfirmationStatus oldStatus = businessCustomer.getStatusInSystem();
    	String newStatus = statusComboBox.getValue().toString();
    	if(oldStatus.toString().equals(newStatus)) {
    		displayMessage.setText("There were no changes.");
    	}
    	else {
    		Optional<ButtonType> result = PopUpMessages.confirmationMessage("Save changes?");
    		if(result.get() == ButtonType.OK) {
    		ArrayList<String> objectToMessage = new ArrayList<>();
    		objectToMessage.add(businessCustomer.getUserId());
    		objectToMessage.add(newStatus);
    		objectToMessage.add("businesscustomer"); // table name
    		Message message = new Message(Task.UPDATE_CUSTOMER_STATUS,Answer.WAIT_RESPONSE,objectToMessage);
    		sendToClient(message);
    		switch(newStatus) {
    		case "CONFIRMED":
    			businessCustomer.setStatusInSystem(ConfirmationStatus.CONFIRMED);
    			break;
    		case "FROZEN" :
    			businessCustomer.setStatusInSystem(ConfirmationStatus.FROZEN);
    			break;
    		case "PENDING_APPROVAL":
    			businessCustomer.setStatusInSystem(ConfirmationStatus.PENDING_APPROVAL);
    			break;
    		default:
    			break;		
    		}
    		displayMessage.setText("Customer status changed from:" + oldStatus.toString() +" to:" + newStatus +".");
    		}
    	}
    }

    /**
     * This method...
     * 
     * @param event
     */
    @FXML
    public void getBackBtn(ActionEvent event) {
    	setEditCustomerInformationScreen(event);
    }

    /**
     * This method...
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
     * This method...
     * 
     * @param event
     */
    @FXML
    public void getHelpBtn(ActionEvent event) {
    	PopUpMessages.helpMessage("On this screen you can edit business customers information.");

    }

    
    /**
     * Sets the screen , it will be called from previous screen.
     */
	public void initEditBusinessCustomerInformationScreen() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
				//	primaryStage.hide(); 
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/BM12EditBusinessCustomerInformationScreen.fxml").openStream());
					editBusinessCustomerInformationScreenController = loader.getController();
					Scene scene = new Scene(root);
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("Edit Business Customer");
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
	 * @param event
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
     * This method initialize the fields and combo boxes accordingly.
     * 
     * @param arg0
     * @param arg1
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		displayMessage.setText("");
		firstNameTxtField.setText(businessCustomer.getUserFirstName()); lastNameTxtField.setText(businessCustomer.getUserLastName()); idNumTxtField.setText(businessCustomer.getUserId()); 
		phoneTxtField.setText(businessCustomer.getPhoneNumber());  emailTxtField.setText(businessCustomer.getUserEmail());
		monthlyMaxBudgedTxtField.setText(Integer.toString(businessCustomer.getBudgetMaxAmount()));
		setHomeBranchCombo.setValue(businessCustomer.getHomeBranch());
		companyNameCombo.setValue(businessCustomer.getcompanyOfBusinessCustomer().getCompanyName());
		statusComboBox.setValue(businessCustomer.getStatusInSystem());
		budgetTypeCombo.setValue(businessCustomer.getBudgetOfBusinessCustomer());
		statusComboBox.getItems().addAll(ConfirmationStatus.CONFIRMED,ConfirmationStatus.FROZEN);		
	}

}
