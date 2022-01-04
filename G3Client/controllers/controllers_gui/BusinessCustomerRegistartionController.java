package controllers_gui;

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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import users.CreditCard;
import users.Login;
import users.UserForRegistration;


/**
 *  Class description: 
 * This is a class for 
 * controlling the UI of PrivateCustomerResitrationScreen that appears immediately after clicking
 * on Business Customer registration from the BusinessCustomerRegistrationScreen form.
 * 
 */

/**
 * 
 * @author Mousa, Srour.
 * 
 * @version 11/12/2021
 */
public class BusinessCustomerRegistartionController extends AbstractBiteMeController implements Initializable{
	
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
    private static BusinessCustomerRegistartionController businessCustomerRegistrationController;
    
    /**
     * Static ArrayList of companies that we get From the DB.
     */
    public static ArrayList<String> companies;
    
    /**
     * ArrayList of textfield's so we can check them all into a loop.
     */
    private ArrayList<TextField> textFields = new ArrayList<>();
    
    /**
     * ArrayList of textfield's that should contain numbers only, so we can check them all into a loop.
     */
    private ArrayList<TextField> integerFields = new ArrayList<>();

    @FXML
    /**
     * First Name Text Field.
     */
    private TextField firstNameTxtField;
    
    /**
     * Last Name Text Field.
     */
    @FXML
    private TextField lastNameTxtField;
    
    /**
     * User ID Text Field.
     */
    @FXML
    private TextField idNumTxtField;
    
    /**
     * Phone Number Text Field.
     */
    @FXML
    private TextField phoneTxtField;
    
    /**
     * Email Text Field.
     */
    @FXML
    private TextField emailTxtField;
    
    /**
     * User Name for login Text Field.
     */
    @FXML
    private TextField userNameField;
    
    /**
     * The Exit Button.
     */
    @FXML
    private Button btnExit;
    
    /**
     * The Save Button.
     */
    @FXML
    private Button saveBtn;
    
    /**
     * The Back Button.
     */
    @FXML
    private Button btnBack;
    
    /**
     * Confirm Email Text Field.
     */
    @FXML
    private TextField confirmedEmailTxtField;
    
    /**
     * ComboBox of Branches.
     */
    @FXML
    private ComboBox<String> setHomeBranchCombo;
    
    /**
     * ComboBox of Companies.
     */
    @FXML
    private ComboBox<String> companyNameCombo;
    
    /**
     * The CVV Numbers of credit card Text Field.
     */
    @FXML
    private TextField cvvTxtField;
    
    /**
     * The Credit Card Number Text Field.
     */
    @FXML
    private TextField creditNumTxtField;
    
    /**
     * The Expiration Date of credit card Text Field.
     */
    @FXML
    private TextField expirationTxtField;
    

    @FXML
    /**
     * The Help Button.
     */
    private Button btnHelp;

    @FXML
    /**
     * The empty text that we display a messages to the customer threw it.
     */
    private Text errorText;

    @FXML
    /**
     * The User Password for Login Text Field.
     */
    private TextField passwordField;

    @FXML
    /**
     * The empty text that we display a messages to the customer threw it.
     */
    private Text displayMessage;

    /**
     * This method loads the previous screen.
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getBackBtn(ActionEvent event) {
    	setUserRegistrationScreen(event);
    }
    
    /**
     * This method does log out and then exit.
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
     * Displaying a popup message to the user.
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getHelpBtn(ActionEvent event) {
    	PopUpMessages.helpMessage("On this screen you register business customers.");
    }
    
    /**
     * This method will run after clicking on save button,
     * it checks if all the fields were correctly filled.
     * and then create a relevant message to the server.
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getSaveBtn(ActionEvent event) {
    	if(checkAllFields()==true) {
    		ArrayList<Object> list = new ArrayList<>();
    		Branch homeBranch = getHomeBranch();
    		CreditCard creditCard = new CreditCard(creditNumTxtField.getText(),expirationTxtField.getText(),cvvTxtField.getText());
    		Login login = new Login (userNameField.getText(),passwordField.getText());
    		list.add(login);
    		list.add(creditCard);
    		BusinessCustomer businessCustomer = new BusinessCustomer(idNumTxtField.getText(),ConfirmationStatus.PENDING_APPROVAL,firstNameTxtField.getText(),
    		lastNameTxtField.getText(),homeBranch,false,emailTxtField.getText(),phoneTxtField.getText(),
			creditNumTxtField.getText(),companyNameCombo.getValue(),BudgetType.DAILY,0);
   			list.add(businessCustomer);
   			Message message = new Message(Task.REGISTER_BUSINESS_CUSTOMER,Answer.WAIT_RESPONSE,list);
       		sendToClient(message);		
    	}
    }
    
    /**
     * Gets the HomeBranch from the combo box as a string and returns it as a Branch Enum.
     * 
     * @return Branch: SOUTH or NORTH or CENTER
     */
    private Branch getHomeBranch() {
    	if(setHomeBranchCombo.getValue().equals("South Branch")) 
			return Branch.SOUTH;
		
		else if(setHomeBranchCombo.getValue().equals("North Branch"))
			return Branch.NORTH;
		else
			return Branch.CENTER;
    }
    
 	/**
 	 * This method sets a message from the listeners to the displayMessage.
 	 * 
 	 * @param message The message that we want to display for the user.
 	 */
 	private void setRelevantTextToDisplayMessageText(String message) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				businessCustomerRegistrationController.displayMessage.setText(message);
			}
		});
		
	}
    
    /**
     * This method initialize the business customer registration form.
     */
    public void initBusinessCustomerRegistrationScreen() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/BM8BusinessCustomerRegistartionScreen.fxml").openStream());
					businessCustomerRegistrationController = loader.getController();
					Scene scene = new Scene(root);
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("Business customer registration");
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
     * 
     * @param event ActionEvent of javaFX.
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
     * This message gets all the confirmed companies from DB to display them into the comboBox.
     */
    public void getCompanies(){
    	Message message = new Message(Task.GET_COMPANIES_FROM_DB,Answer.WAIT_RESPONSE,null);
    	sendToClient(message);
    }
    
    /**
     * This method checks if all the fields are filled
     * in addition here we check if the input were correct.
     * 
     * @return boolean: true if field all fields
     */
    public boolean checkAllFields() {
    	boolean returnVal=true;
 		for (TextField txt : textFields)
 			txt.setStyle(null);
 			
 		for(TextField txt: textFields) 
 			if(txt.getText().equals("")) {
 				txt.setStyle( "-fx-border-color: red");
 				returnVal=false;
 			}
 		if(returnVal==false) {
 			displayMessage.setText("Please fill all the required fields (*)!");
 			return false;
 		} 
 		if(!checkComboBoxInput(companyNameCombo,"Select company:")) {
 			displayMessage.setText("Please fill all the required fields (*)!");
 			return false;
 			}
 		
 		for(TextField intTxt : integerFields) {
 			if(!isInt(intTxt)) {
 				intTxt.setStyle("-fx-border-color: red");
 				displayMessage.setText("Marked field must contain only numbers!");
 				return false;
 			}
 		}
 		if(emailTxtField.getText().contains("@")==false) {
 			emailTxtField.setStyle("-fx-border-color: red");
 			displayMessage.setText("Incorrect email format, email must contain '@'!");
 			return false;
 		}
 		else if(emailTxtField.getText().equals(confirmedEmailTxtField.getText())== false) {
 			confirmedEmailTxtField.setStyle("-fx-border-color: red");
 			displayMessage.setText("Emails doesn't match!");
 			return false;
 		}
 		return true;
    }
    
    /**
     * This method checks if the selected value of the
     * comboBox equals to a message.
     * 
     * @return boolean: true if equals.
     */
    public boolean checkComboBoxInput(ComboBox comboBox, String message) {
    	if(comboBox.getValue().equals(message))
    		return false;
    	return true;
    }
    
 	/**
 	 * This method checks if the textField contains numbers only.
 	 * 
 	 * @param txtField the text field that we want to check if it contains numbers only.
 	 * @return true or false according to the input.
 	 */
 	public boolean isInt(TextField txtField) {
 		try {
 	 		int checkIfInt = Integer.parseInt(txtField.getText());
 	 		if(checkIfInt>0) {
 	 			return true;
 	 		}
 	 		return false;
 	 		}catch(NumberFormatException e) {
 	 			return false;
 	 		}
 	}
 	
 	/**
 	 * In this method we initialize all the fields and combo boxes.
 	 * in addition we initialize the listeners.
 	 * 
 	 * @param arg0
 	 * @param arg1
 	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		textFields.add(confirmedEmailTxtField); textFields.add(creditNumTxtField); textFields.add(cvvTxtField); textFields.add(emailTxtField); textFields.add(expirationTxtField);
		textFields.add(firstNameTxtField); textFields.add(idNumTxtField); textFields.add(lastNameTxtField); textFields.add(passwordField);
		textFields.add(phoneTxtField); textFields.add(userNameField);
		integerFields.add(creditNumTxtField); integerFields.add(cvvTxtField); integerFields.add(idNumTxtField);
		integerFields.add(phoneTxtField); 
		getCompanies();
		Branch homeBranch = connectedUser.getHomeBranch();
		if(homeBranch.equals(Branch.NORTH))
			setHomeBranchCombo.setValue("North Branch");
		else if(homeBranch.equals(Branch.CENTER))
			setHomeBranchCombo.setValue("Center Branch");
		else
			setHomeBranchCombo.setValue("South Branch");
		if(companies.isEmpty()) {
			for(TextField txt : textFields) {
				txt.setDisable(true);
				txt.setEditable(false);
				companyNameCombo.setDisable(true);
				saveBtn.setDisable(true);
				displayMessage.setText("No confirmed companies. Cannot register employees!");
			}
		}
		else {
			companyNameCombo.setValue("Select company:");
			companyNameCombo.getItems().addAll(companies);
			displayMessage.setText("");
		
			UserForRegistration userForRegistration = UsersRegistrationScreenController.userForRegistration;
			confirmedEmailTxtField.setText(userForRegistration.getEmail()); creditNumTxtField.setText(userForRegistration.getCreditCardNumber()); cvvTxtField.setText(userForRegistration.getCreditCardCvvCode());
			emailTxtField.setText(userForRegistration.getEmail()); expirationTxtField.setText(userForRegistration.getCreditCardDateOfExpiration()); firstNameTxtField.setText(userForRegistration.getFirstName());
			idNumTxtField.setText(userForRegistration.getUserID()); lastNameTxtField.setText(userForRegistration.getLastName()); passwordField.setText(userForRegistration.getPassword());
			phoneTxtField.setText(userForRegistration.getPhoneNumber()); userNameField.setText(userForRegistration.getUsername());
			idNumTxtField.setDisable(true); idNumTxtField.setEditable(false); 
			AnalyzeMessageFromServer.addClientListener(new AnalyzeClientListener(){
				@Override
				public void clientBusinessUserIdExist() {
					setRelevantTextToDisplayMessageText("This ID already exist on system");
				}
				public void clientBusinessUserNameExist() {
					setRelevantTextToDisplayMessageText("This User Name already exist on system");
				}
				public void clientBusinessCustomerRegistrationSucceed() {
					setRelevantTextToDisplayMessageText("Registration Succeed, waiting for confirmation from "+companyNameCombo.getValue()+" HR manager.");		
				}
			});
		}
	}
	

}
