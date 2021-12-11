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
	private static FXMLLoader loader;
    private static BusinessCustomerRegistartionController businessCustomerRegistrationController;
    public static ArrayList<String> companies;

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

    @FXML
    void getBackBtn(ActionEvent event) {
    	setCustomerRegistrationScreen(event);
    }

    @FXML
    void getBudgetType(ActionEvent event) {

    }

    @FXML
    void getCVV(ActionEvent event) {

    }

    @FXML
    void getCompanyName(ActionEvent event) {

    }

    @FXML
    void getConfirmedEmail(ActionEvent event) {

    }

    @FXML
    void getCreditNum(ActionEvent event) {

    }

    @FXML
    void getEmail(ActionEvent event) {

    }

    @FXML
    void getEmployeeId(ActionEvent event) {

    }

    @FXML
    void getExitBtn(ActionEvent event) {
      	Message message = new Message(Task.LOGOUT,Answer.WAIT_RESPONSE,connectedUser);
      	sendToClient(message);
    	connectedUser = null;
   		System.exit(0);
    }

    @FXML
    void getExpiration(ActionEvent event) {

    }

    @FXML
    void getFirstName(ActionEvent event) {

    }

    @FXML
    void getHelpBtn(ActionEvent event) {
    	PopUpMessages.helpMessage("Please fill in all the fields to complete the registration!");
    }

    @FXML
    void getHomeBranch(ActionEvent event) {

    }

    @FXML
    void getIdNum(ActionEvent event) {

    }

    @FXML
    void getLastName(ActionEvent event) {

    }

    @FXML
    void getMonthlyMaxBudget(ActionEvent event) {

    }

    @FXML
    void getPassword(ActionEvent event) {

    }

    @FXML
    void getPhone(ActionEvent event) {

    }

    @FXML
    void getPosition(ActionEvent event) {

    }

    @FXML
    void getSaveBtn(ActionEvent event) {
    	if(checkAllFields()==true) {
    		ArrayList<Object> list = new ArrayList<>();
    		Branch homeBranch = getHomeBranch();
    		PositionType positionType = getPositionType();
    		BudgetType budgetType = getBudgetType();
    		CreditCard creditCard = new CreditCard(creditNumTxtField.getText(),expirationTxtField.getText(),cvvTxtField.getText());
    		Login login = new Login (userNameField.getText(),passwordField.getText());
    		if(positionType.equals(PositionType.REGULAR)) {
    			BusinessCustomer businessCustomer = new BusinessCustomer(idNumTxtField.getText(),ConfirmationStatus.PENDING_APPROVAL,firstNameTxtField.getText(),
    				lastNameTxtField.getText(),homeBranch,false,emailTxtField.getText(),phoneTxtField.getText(),
    				creditNumTxtField.getText(),companyNameCombo.getValue(),budgetType,positionType,Integer.parseInt(monthlyMaxBudgedTxtField.getText()));
    			list.add(businessCustomer);
    		}
    		else {
    			HrManager hrManager = new HrManager(idNumTxtField.getText(),ConfirmationStatus.PENDING_APPROVAL,firstNameTxtField.getText(),
        				lastNameTxtField.getText(),homeBranch,false,emailTxtField.getText(),phoneTxtField.getText(),
        				creditNumTxtField.getText(),companyNameCombo.getValue(),budgetType,positionType,Integer.parseInt(monthlyMaxBudgedTxtField.getText()));
    			list.add(hrManager);
    		}
    		list.add(login);
    		list.add(creditCard);
    		Message message = new Message(Task.REGISTER_BUSINESS_CUSTOMER,Answer.WAIT_RESPONSE,list);
    		sendToClient(message);
    		displayMessage.setText("All good !!");
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
    public void setCustomerRegistrationScreen(ActionEvent event) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				FXMLLoader loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/BM5CustomerRegistartionScreen.fxml").openStream());
					CustomerRegistartionScreenController CRSC = new CustomerRegistartionScreenController();
					CRSC.initPortalAgain();
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
    
    public Boolean checkAllFields() {
    	if(firstNameTxtField.getText().equals("") || lastNameTxtField.getText().equals("") || idNumTxtField.getText().equals("") || 
    			phoneTxtField.getText().equals("") || emailTxtField.getText().equals("") || confirmedEmailTxtField.getText().equals("") || 
    			monthlyMaxBudgedTxtField.getText().equals("") || creditNumTxtField.getText().equals("") || expirationTxtField.getText().equals("") ||
    					cvvTxtField.getText().equals("") || userNameField.getText().equals("") || passwordField.getText().equals("") || 
    					companyNameCombo.getValue().equals("Select company:") || positionCombo.getValue().equals("Select position:") ||
    					budgetTypeCombo.getValue().equals("Select Budget type:") ) {
    		displayMessage.setText("Please, Fill in all the fields !!");
 			return false;
    	}
    	else if(emailTxtField.getText().contains("@")==false) {
 			displayMessage.setText("Please, Fill in a correct Email (E-mail must contain a '@') !!");
 			return false;
 		}
 		else if(emailTxtField.getText().equals(confirmedEmailTxtField.getText())== false) {
 			displayMessage.setText("Please, fill the same Email address on both email fields!!");
 			return false;
 		}
 		return true;
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getCompanies();
		setHomeBranchCombo.setValue(connectedUser.getHomeBranch().toString());
		setHomeBranchCombo.getItems().addAll("North Branch", "Center Branch", "South Branch");
		companyNameCombo.setValue("Select company:");
		//for(String s: companies)
			companyNameCombo.getItems().addAll(companies);
		positionCombo.setValue("Select position:");
		positionCombo.getItems().addAll("Regular worker","Human Resources");
		budgetTypeCombo.setValue("Select Budget type:");
		budgetTypeCombo.getItems().addAll("Daily budget","Weekly budget","Monthly budget");
		displayMessage.setText("");
		AnalyzeMessageFromServer.addClientListener(new AnalyzeClientListener(){
			@Override
			public void clientBusinessUserIdExist() {
				setRelevantTextToDisplayMessageText("This ID already exist on system");
			}
			public void clientBusinessUserNameExist() {
				setRelevantTextToDisplayMessageText("This User Name already exist on system");
			}
			public void clientBusinessCustomerRegistrationSucceed() {
				setRelevantTextToDisplayMessageText("Registration Succeed!");
			}
		});
	}
	

}
