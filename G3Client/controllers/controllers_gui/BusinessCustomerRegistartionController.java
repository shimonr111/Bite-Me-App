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
import javafx.stage.WindowEvent;
import users.Branch;
import users.BudgetType;
import users.BusinessCustomer;
import users.ConfirmationStatus;
import users.CreditCard;
import users.Customer;
import users.HrManager;
import users.Login;
import users.PositionType;
import users.UserForRegistration;
import util.DataLists;

/**
 * 
 * @author Mousa, Srour
 * lass description: 
 * This is a class for 
 * controlling the UI of PrivateCustomerResitrationScreen that appears immediately after clicking
 * on Private Customer registration from the BusinessCustomerRegistrationScreen form.
 * @version 11/12/2021
 */
public class BusinessCustomerRegistartionController extends AbstractBiteMeController implements Initializable{
	
	/**
	 * Class members description:
	 */
	private static FXMLLoader loader;
    private static BusinessCustomerRegistartionController businessCustomerRegistrationController;
    public static ArrayList<String> companies;
    private ArrayList<TextField> textFields = new ArrayList<>();
    private ArrayList<TextField> integerFields = new ArrayList<>();

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
    private TextField userNameField;

    @FXML
    private TextField monthlyMaxBudgedTxtField;

    @FXML
    private Button btnExit;

    @FXML
    private Button saveBtn;

    @FXML
    private Button btnBack;

    @FXML
    private TextField confirmedEmailTxtField;

    @FXML
    private ComboBox<String> positionCombo;

    @FXML
    private ComboBox<String> setHomeBranchCombo;

    @FXML
    private ComboBox<String> companyNameCombo;

    @FXML
    private ComboBox<String> budgetTypeCombo;
    
    @FXML
    private ComboBox<String> phonePrefixCombo;

    @FXML
    private TextField cvvTxtField;

    @FXML
    private TextField creditNumTxtField;

    @FXML
    private TextField expirationTxtField;

    @FXML
    private Button btnHelp;

    @FXML
    private Text errorText;

    @FXML
    private TextField passwordField;

    @FXML
    private Text displayMessage;

    /**
     * This method loads the previous screen.
     * @param event
     */
    @FXML
    void getBackBtn(ActionEvent event) {
    	setUserRegistrationScreen(event);
    }
    
    /**
     * this method does log out and then exit.
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
     * displaying a popup message to the user.
     * @param event
     */
    @FXML
    void getHelpBtn(ActionEvent event) {
    	PopUpMessages.helpMessage("Please fill in all the fields to complete the registration!");
    }
    
    @FXML
    void getPositionComboBox(ActionEvent event) {
    	if(positionCombo.getValue().equals("Human Resources")) {
    		companyNameCombo.setValue("None");
    		companyNameCombo.setDisable(true);
    	}
    	else {
    		companyNameCombo.setValue("Select company:");
    		companyNameCombo.setDisable(false);
    	}
    }
    
    /**
     * this method will run after clicking on save button, it checks if all the fields were correctly filled.
     * and then create a relevant message to the server.
     * @param event
     */
    @FXML
    void getSaveBtn(ActionEvent event) {
    	if(checkAllFields()==true) {
    		ArrayList<Object> list = new ArrayList<>();
    		Branch homeBranch = getHomeBranch();
    		PositionType positionType = getPositionType();
    		BudgetType budgetType = getBudgetType();
    		CreditCard creditCard = new CreditCard(creditNumTxtField.getText(),expirationTxtField.getText(),cvvTxtField.getText());
    		Login login = new Login (userNameField.getText(),passwordField.getText());
    		list.add(login);
    		list.add(creditCard);
    		if(positionType.equals(PositionType.REGULAR)) {
    			BusinessCustomer businessCustomer = new BusinessCustomer(idNumTxtField.getText(),ConfirmationStatus.PENDING_APPROVAL,firstNameTxtField.getText(),
    				lastNameTxtField.getText(),homeBranch,false,emailTxtField.getText(),phoneTxtField.getText(),
    				creditNumTxtField.getText(),companyNameCombo.getValue(),budgetType,positionType,Integer.parseInt(monthlyMaxBudgedTxtField.getText()));
    			list.add(businessCustomer);
    			Message message = new Message(Task.REGISTER_BUSINESS_CUSTOMER,Answer.WAIT_RESPONSE,list);
        		sendToClient(message);
    		}
    		else {
    			HrManager hrManager = new HrManager(idNumTxtField.getText(),ConfirmationStatus.CONFIRMED,firstNameTxtField.getText(),
        				lastNameTxtField.getText(),homeBranch,false,emailTxtField.getText(),phoneTxtField.getText(),
        				creditNumTxtField.getText(),"Waiting_Registration",budgetType,positionType,Integer.parseInt(monthlyMaxBudgedTxtField.getText()));
    			list.add(hrManager);
    			Message message = new Message(Task.REGISTER_BUSINESS_CUSTOMER,Answer.WAIT_RESPONSE,list);
        		sendToClient(message);
    		}
    		
    		
    	}
    }
    
    /**
     * gets the budget type selected from the combo box and returns it as an enum.
     * @return
     */
    private BudgetType getBudgetType() {
    	if(budgetTypeCombo.getValue().equals("Daily budget"))
    		return BudgetType.DAILY;
    	else if(budgetTypeCombo.getValue().equals("Weekly budget"))
    		return BudgetType.WEEKLY;
    	return BudgetType.MONTHLY;
    }
    
    /**
     * gets the position type selected from the combo box and returns it as an enum.
     * @return
     */
    private PositionType getPositionType() {
    	if(positionCombo.getValue().equals("Human Resources"))
    			return PositionType.HR;
    	return PositionType.REGULAR;
    }
    
    /**
     * gets the HomeBranch from the combo box as a string and returns it as a Branch Enum.
     * @return
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
 	 * this method sets a message from the listeners to the displayMessage .
 	 * @param message
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
					Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) { 
							event.consume();
							Stage.close();
						}
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
     * this message gets all the confirmed companies from DB to display them into the comboBox.
     */
    public void getCompanies(){
    	Message message = new Message(Task.GET_COMPANIES_FROM_DB,Answer.WAIT_RESPONSE,null);
    	sendToClient(message);
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
 				txt.setStyle( "-fx-border-color: red");
 				returnVal=false;
 			}
 		if(returnVal==false) {
 			displayMessage.setText("Please, Fill in all the marked fields !!");
 			return false;
 		} 
 		if(!checkComboBoxInput(companyNameCombo,"Select company:")) {
 			displayMessage.setText("Please, pick your choice from the 'Select company' box!");
 			return false;
 			}
 		if(!checkComboBoxInput(positionCombo,"Select position:")) {
 			displayMessage.setText("Please, pick your choice from the 'Select position' box!");
 			return false;
 			}
 		if(!checkComboBoxInput(budgetTypeCombo, "Select Budget type:")) {
 			displayMessage.setText("Please, pick your choice from the 'Select Budget type' box!");
 			return false;
 		}
 		
 		if(phonePrefixCombo.getValue() == null) {
 			displayMessage.setText("Please, pick your choice from the 'Prefix' box!");
 			return false;
 		}
 		for(TextField intTxt : integerFields) {
 			if(!isInt(intTxt)) {
 				intTxt.setStyle("-fx-border-color: red");
 				displayMessage.setText("Marked field must contain only numbers !");
 				return false;
 			}
 		}
 		if(emailTxtField.getText().contains("@")==false) {
 			emailTxtField.setStyle("-fx-border-color: red");
 			displayMessage.setText("Please, Fill in a correct Email (E-mail must contain a '@') !!");
 			return false;
 		}
 		else if(emailTxtField.getText().equals(confirmedEmailTxtField.getText())== false) {
 			confirmedEmailTxtField.setStyle("-fx-border-color: red");
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
 	 		if(checkIfInt>0) {
 	 			return true;
 	 		}
 	 		return false;
 	 		}catch(NumberFormatException e) {
 	 			return false;
 	 		}
 	}
 	
 	/**
 	 * in this method we initialize all the fields and combo boxes.
 	 * in addition we initialize the listeners.
 	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		textFields.add(confirmedEmailTxtField); textFields.add(creditNumTxtField); textFields.add(cvvTxtField); textFields.add(emailTxtField); textFields.add(expirationTxtField);
		textFields.add(firstNameTxtField); textFields.add(idNumTxtField); textFields.add(lastNameTxtField); textFields.add(monthlyMaxBudgedTxtField); textFields.add(passwordField);
		textFields.add(phoneTxtField); textFields.add(userNameField);
		integerFields.add(creditNumTxtField); integerFields.add(cvvTxtField); integerFields.add(idNumTxtField); integerFields.add(monthlyMaxBudgedTxtField);
		integerFields.add(phoneTxtField); 
		phonePrefixCombo.getItems().addAll(DataLists.getPhonePrefix());
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
				positionCombo.setDisable(true);
				companyNameCombo.setDisable(true);
				budgetTypeCombo.setDisable(true);
				saveBtn.setDisable(true);
				displayMessage.setText("You can not register an employer, no confirmed companies");
			}
		}
		else {
			companyNameCombo.setValue("Select company:");
			companyNameCombo.getItems().addAll(companies);
			positionCombo.setValue("Regular worker");
			positionCombo.getItems().addAll("Regular worker","Human Resources");
			positionCombo.setDisable(true);
			budgetTypeCombo.setValue("Select Budget type:");
			budgetTypeCombo.getItems().addAll("Daily budget","Weekly budget","Monthly budget");
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
					if(positionCombo.getValue().equals("Regular worker"))
						setRelevantTextToDisplayMessageText("Registration Succeed, waiting for confirmation from "+companyNameCombo.getValue()+" HR manager.");
					else
						setRelevantTextToDisplayMessageText("Registration Succeed,this user can now log in and register his company.");			
				}
			});
		}
	}
	

}
