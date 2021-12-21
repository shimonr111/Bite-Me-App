package controllers_gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import users.Branch;
import users.ConfirmationStatus;
import users.CreditCard;
import users.Customer;
import users.Login;
import users.PositionType;
import users.Supplier;
import users.SupplierWorker;
import users.UserForRegistration;
import users.WorkerPosition;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import bitemeclient.PopUpMessages;
import clientanalyze.AnalyzeClientListener;
import clientanalyze.AnalyzeMessageFromServer;
import communication.Answer;
import communication.Message;
import communication.Task;
import javafx.fxml.Initializable;

/**
 * 
 * @author Mousa, Srour
 * @author Alexander, Martinov
 * Class description: 
 * This is a class for 
 * controlling the UI of SupplierWorker registration Screen that appears immediately after clicking
 * on supplier registration from the branch portal.
 * form.
 * 
 * @version 15/12/2021
 */
public class SupplierWorkerRegistrationScreenController extends AbstractBiteMeController implements Initializable {
	/**
	 * Class members description:
	 */
	private static FXMLLoader loader;
    private static SupplierWorkerRegistrationScreenController supplierWorkerRegistrationScreenController;
    private ArrayList<TextField> textFields = new ArrayList<>();
    private ArrayList<TextField> integerFields = new ArrayList<>();
    public static Map<String,String> suppliersList = new HashMap<>(); 
    public static Supplier supplier = new Supplier(null, null, null, null, null, 0,null);
	   @FXML
	    private TextField resturantNameTxtField;
	   
	    @FXML
	    private ComboBox<String> chooseResComboBox;
	    
	    @FXML
	    private ComboBox<String> workerCombo;

	    @FXML
	    private TextField FirstName;

	    @FXML
	    private TextField LastName;

	    @FXML
	    private TextField UserId;

	    @FXML
	    private TextField emailTxtField;

	    @FXML
	    private TextField confirmEmailTxtField;

	    @FXML
	    private TextField phoneTxtField;

	    @FXML
	    private TextField revenueTxtField;
	    
	    @FXML
	    private TextField passwordTextField;

	    @FXML
	    private TextField userName;
	    
	    @FXML
	    private TextField homeBranchtxtField;

	    @FXML
	    private Button saveBtn;

	    @FXML
	    private Button btnExit;

	    @FXML
	    private Button btnBack;

	    @FXML
	    private Button btnHelp;
	    
	    @FXML
	    private Text displayMessage;
	    
	    /**
	     * log out and then exit.
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
	     * displays a message to the user.
	     * @param event
	     */
	    @FXML
	    void getHelpBtn(ActionEvent event) {
	    	PopUpMessages.helpMessage("Please fill in all the fields to complete the registration, then click on Save.");
	    }
	    
	    /**
	     * this method runs after clicking on save button, it checks if all the fields were filled correctly 
	     * creates a relevant message to the server to insert the data into DB.
	     * @param event
	     */
	    @FXML
	    void getSaveBtn(ActionEvent event) {
	    	if(checkAllFields()) {
	    		String supplierName=chooseResComboBox.getValue();
	        	for(Entry<String, String> entry: suppliersList.entrySet()) {
	      	      // if give value is equal to value from entry
	      	      // get the corresponding key
	      	      if(entry.getValue().equals(supplierName)) {
	      	    	  supplierName = entry.getKey();
	      	        break;
	      	      }
	        	}
	    		Message message = new Message (Task.GET_SUPPLIER_FOR_SUPPLIER_REGISTRATION,Answer.WAIT_RESPONSE,supplierName);
	    		sendToClient(message);
	    		Branch homeBranch;
	    		if(homeBranchtxtField.getText().equals("South Branch")) 
	    			homeBranch = Branch.SOUTH;
	    		
	    		else if(homeBranchtxtField.getText().equals("North Branch"))
	    			homeBranch = Branch.NORTH;
	    		else
	    			homeBranch = Branch.CENTER;
	    		SupplierWorker supplierWorker = new SupplierWorker(UserId.getText(),ConfirmationStatus.PENDING_APPROVAL,FirstName.getText(),LastName.getText(),
	    				homeBranch,false,emailTxtField.getText(),phoneTxtField.getText(),
	    				new Supplier(supplier.getSupplierId(),supplier.getSupplierName(), supplier.getHomeBranch(),supplier.getEmail(),supplier.getPhoneNumber(), supplier.getRevenueFee(),supplier.getStatusInSystem()),getWorkerPosition());
	    		Login login = new Login (userName.getText(),passwordTextField.getText());
	    		ArrayList<Object> list = new ArrayList<>();
	    		list.add(supplierWorker);
	    		list.add(login);
	    		message = new Message(Task.REGISTER_SUPPLIER,Answer.WAIT_RESPONSE,list);
	    		sendToClient(message);

	    	}
	    }
	    
	    private WorkerPosition getWorkerPosition() {
	    	if(workerCombo.getValue().equals("Certified worker"))
	    			return WorkerPosition.CERTIFIED;
	    	return WorkerPosition.REGULAR;
	    }

		/**
	     * calls the method that loads the previous screen.
	     * @param event
	     */
	    @FXML
	    void getBackBtn(ActionEvent event) {
	    	setUserRegistrationScreen(event);
	    }
	
	    /**
	     * 
	     * this method checks if all the fields are filled
	     * in addition here we check if the input were correct.
	     * @return
	     */
	 	public boolean checkAllFields() {
	 		boolean returnVal=true;
	 		for (TextField txt : textFields)

	 			txt.setStyle(null);
	 			
	 		for(TextField txt: textFields) 
	 			if(txt.getText().equals("")) {
	 				txt.setStyle("-fx-border-color: red");
	 				returnVal=false;
	 			}
	 		if(returnVal==false) {
	 			displayMessage.setText("Please, Fill in all the marked fields !!");
	 			return false;
	 		}
	 		for(TextField intTxt : integerFields) {
	 			if(!isInt(intTxt)) {
	 				intTxt.setStyle("-fx-border-color: red");
	 				displayMessage.setText("Marked field must contain only numbers !");
	 				return false;
	 			}
	 		}
	 		if(!checkComboBoxInput(chooseResComboBox,"Select supplier:")) {
	 			displayMessage.setText("Please, pick your choice from the 'Select supplier' box!");
	 			return false;
	 			}
	 		if(!checkComboBoxInput(workerCombo,"Select worker type:")) {
	 			displayMessage.setText("Please, pick your choice from the 'Select worker type' box!");
	 			return false;
	 			}
	 		if(emailTxtField.getText().contains("@")==false) {
	 			emailTxtField.setStyle("-fx-border-color: red");
	 			displayMessage.setText("Please, Fill in a correct Email (E-mail must contain a '@') !!");
	 			return false;
	 		}
	 		else if(emailTxtField.getText().equals(confirmEmailTxtField.getText())== false) {
	 			confirmEmailTxtField.setStyle("-fx-border-color: red");
	 			displayMessage.setText("Please, fill the same Email address on both email fields!!");
	 			return false;
	 		}
	 		return true;
	 	}
	    public boolean checkComboBoxInput(ComboBox comboBox, String message) {
	    	if(comboBox.getValue().equals(message))
	    		return false;
	    	return true;
	    }
	 	
	 	/**
	 	 * this method checks if the textField contains numbers only.
	 	 * @param txtField
	 	 * @return true or false according to the input.
	 	 */
	 	public boolean isInt(TextField txtField) {
	 		try {
	 		int checkIfInt = Integer.parseInt(txtField.getText());
	 		return true;
	 		}catch(NumberFormatException e) {
	 			return false;
	 		}
	 	}
	 	
	    
	    /**
	     * this method loads the current screen, it will be called from the previous screen.
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
						root = loader.load(getClass().getResource("/fxmls/BM4SupplierWorkerRegistrationScreen.fxml").openStream());
						supplierWorkerRegistrationScreenController = loader.getController();
						Scene scene = new Scene(root);
						Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
							@Override
							public void handle(WindowEvent event) { 
								event.consume();
								Stage.close();
							}
						});
						scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
						Stage.setTitle("SupplierWorker registration");
						Stage.setScene(scene);
						Stage.show();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
		
	    /**
	     * This method sets the previous screen .
	     * @param event
	     */
	    public void setUserRegistrationScreen(ActionEvent event) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					FXMLLoader loader = new FXMLLoader();
					Pane root;
					try {
						Stage Stage = new Stage();
						Stage.setResizable(false);
						root = loader.load(getClass().getResource("/fxmls/BM13UsersRegistrationScreen.fxml").openStream());
						UsersRegistrationScreenController URSC = new UsersRegistrationScreenController();
						URSC.initPortalAgain();
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
	 	 * this method sets a message from the listeners to the displayMessage .
	 	 * @param message
	 	 */
	 	private void setRelevantTextToDisplayMessageText(String message) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					supplierWorkerRegistrationScreenController.displayMessage.setText(message);
				}
			});
			
		}
	 	
	 	/**
	 	 * this method initialize all the fields and boxes and  listeners accordingly.
	 	 */
	 	@Override
	 	public void initialize(URL arg0, ResourceBundle arg1) {
	 	Message message = new Message (Task.GET_RESTAURANTS_FOR_SUPPLIER_REGISTRATION,Answer.WAIT_RESPONSE,connectedUser);
		sendToClient(message);
		//There are no restaurants for this Branch, set message to user
		chooseResComboBox.setValue("Select supplier:");
		if(suppliersList == null) {
			setRelevantTextToDisplayMessageText("No suppliers in selected branch");
		}
		else {
			//add the relevant suppliers to the combo box
			List<String> restaurantsNames = new ArrayList<>();
			for(Entry<String, String> entry: suppliersList.entrySet()) {
				restaurantsNames.add(entry.getValue());
			}
			chooseResComboBox.getItems().addAll(restaurantsNames);
		}
		workerCombo.setValue("Select worker type:");
		workerCombo.getItems().addAll("Certified worker","Regular worker");
		textFields.add(confirmEmailTxtField); textFields.add(emailTxtField); textFields.add(FirstName); textFields.add(LastName); textFields.add(UserId);
		textFields.add(phoneTxtField); textFields.add(userName); textFields.add(passwordTextField);
		integerFields.add(phoneTxtField); integerFields.add(UserId);
		Branch homeBranch = connectedUser.getHomeBranch();
		if(homeBranch.equals(Branch.NORTH))
			homeBranchtxtField.setText("North Branch");
		else if(homeBranch.equals(Branch.CENTER))
			homeBranchtxtField.setText("Center Branch");
		else
			homeBranchtxtField.setText("South Branch");
		homeBranchtxtField.setEditable(false);
		homeBranchtxtField.setDisable(true);
		displayMessage.setText("");
		UserForRegistration userForRegistration = UsersRegistrationScreenController.userForRegistration;
		confirmEmailTxtField.setText(userForRegistration.getEmail()); emailTxtField.setText(userForRegistration.getEmail()); FirstName.setText(userForRegistration.getFirstName());
		UserId.setText(userForRegistration.getUserID()); LastName.setText(userForRegistration.getLastName()); passwordTextField.setText(userForRegistration.getPassword());
		phoneTxtField.setText(userForRegistration.getPhoneNumber()); userName.setText(userForRegistration.getUsername());
		AnalyzeMessageFromServer.addClientListener(new AnalyzeClientListener(){
			@Override
			public void clientSupplierRegistrationSucceed() {
				setRelevantTextToDisplayMessageText("SupplierWorker Registration Succeed!");
			}
			@Override
			public void clientSupplierIdExist() {
				setRelevantTextToDisplayMessageText("This ID already exist on system, try again");
			}
			@Override
			public void clientSupplierUserNameExist() {
				setRelevantTextToDisplayMessageText("This User Name already exist on system,try again");
			}
			
		});
		
		
	}

}
