package controllers_gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import users.Branch;
import users.ConfirmationStatus;
import users.CreditCard;
import users.Customer;
import users.Login;
import users.Supplier;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
 * Class description: 
 * This is a class for 
 * controlling the UI of Supplier registration Screen that appears immediately after clicking
 * on supplier registration from the branch portal.
 * form.
 * 
 * @version 12/12/2021
 */
public class SupplierRegistrationScreenController extends AbstractBiteMeController implements Initializable {
	/**
	 * Class members description:
	 */
	private static FXMLLoader loader;
    private static SupplierRegistrationScreenController supplierRegistrationScreenController;
    private ArrayList<TextField> textFields = new ArrayList<>();
    private ArrayList<TextField> integerFields = new ArrayList<>();
	   @FXML
	    private TextField resturantNameTxtField;

	    @FXML
	    private TextField managerFirstName;

	    @FXML
	    private TextField managerLastName;

	    @FXML
	    private TextField managerUserId;

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
	    		Branch homeBranch;
	    		if(homeBranchtxtField.getText().equals("South Branch")) 
	    			homeBranch = Branch.SOUTH;
	    		
	    		else if(homeBranchtxtField.getText().equals("North Branch"))
	    			homeBranch = Branch.NORTH;
	    		else
	    			homeBranch = Branch.CENTER;
	    		Supplier supplier = new Supplier(managerUserId.getText(),ConfirmationStatus.CONFIRMED,managerFirstName.getText(),managerLastName.getText(),
	    				homeBranch,false,emailTxtField.getText(),phoneTxtField.getText(),resturantNameTxtField.getText(),Double.parseDouble(revenueTxtField.getText()));
	    		Login login = new Login (userName.getText(),passwordTextField.getText());
	    		ArrayList<Object> list = new ArrayList<>();
	    		list.add(supplier);
	    		list.add(login);
	    		Message message = new Message(Task.REGISTER_SUPPLIER,Answer.WAIT_RESPONSE,list);
	    		sendToClient(message);

	    	}
	    }
	    
	    /**
	     * calls the method that loads the previous screen.
	     * @param event
	     */
	    @FXML
	    void getBackBtn(ActionEvent event) {
	    	setBranchManagerPortal(event);
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
	 		if (!checkRevenueFee(revenueTxtField))
	 			return false;
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
	 	 * 
	 	 * @param txtField
	 	 * @return
	 	 */
	 	public boolean checkRevenueFee(TextField txtField) {
	 		try {
		 		double checkRevenueFee = Double.parseDouble(txtField.getText());
		 		if(checkRevenueFee < 7.00 || checkRevenueFee > 12.00) {
	 				displayMessage.setText("Revenue Fee must be between 7-12.");
	 				return false;
		 			
		 		}
		 		return true;
		 		}catch(NumberFormatException e) {
	 				displayMessage.setText("Revenue Fee must be in the form xx.x (ex: 10.0)");
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
					//	primaryStage.hide(); 
						Stage Stage = new Stage();
						Stage.setResizable(false);
						root = loader.load(getClass().getResource("/fxmls/BM4SupplierRegistrationScreen.fxml").openStream());
						supplierRegistrationScreenController = loader.getController();
						Scene scene = new Scene(root);
						Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
							@Override
							public void handle(WindowEvent event) { 
								event.consume();
								Stage.close();
							}
						});
						//scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
						Stage.setTitle("Supplier registration");
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
	 	 * this method sets a message from the listeners to the displayMessage .
	 	 * @param message
	 	 */
	 	private void setRelevantTextToDisplayMessageText(String message) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					supplierRegistrationScreenController.displayMessage.setText(message);
				}
			});
			
		}
	 	
	 	/**
	 	 * this method initialize all the fields and boxes and  listeners accordingly.
	 	 */
	 	@Override
	 	public void initialize(URL arg0, ResourceBundle arg1) {
		textFields.add(resturantNameTxtField); textFields.add(confirmEmailTxtField); textFields.add(emailTxtField); textFields.add(managerFirstName); textFields.add(managerLastName); textFields.add(managerUserId);
		textFields.add(phoneTxtField); textFields.add(revenueTxtField); textFields.add(userName); textFields.add(passwordTextField);
		integerFields.add(phoneTxtField); integerFields.add(managerUserId);
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
		AnalyzeMessageFromServer.addClientListener(new AnalyzeClientListener(){
			@Override
			public void clientSupplierRegistrationSucceed() {
				setRelevantTextToDisplayMessageText("Supplier Registration Succeed!");
			}
			@Override
			public void clientSupplierIdExist() {
				setRelevantTextToDisplayMessageText("This ID already exist on system, try again");
			}
			@Override
			public void clientSupplierUserNameExist() {
				setRelevantTextToDisplayMessageText("This User Name already exist on system,try again");
			}
			@Override
			public void clientSupplierNameExist() {
				setRelevantTextToDisplayMessageText("This Resturant Name already exist on system,try again");
			}
			
		});
		
		
	}

}
