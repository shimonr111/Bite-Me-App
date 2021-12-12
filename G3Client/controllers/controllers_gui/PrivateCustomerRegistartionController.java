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
import users.ConfirmationStatus;
import users.CreditCard;
import users.Customer;
import users.Login;

/**
 * 
 * @author Mousa, Srour
 * lass description: 
 * This is a class for 
 * controlling the UI of PrivateCustomerResitrationScreen that appears immediately after clicking
 * on Private Customer registration from the CustomerRegistrationScreen form.
 * @version 10/12/2021
 */
public class PrivateCustomerRegistartionController extends AbstractBiteMeController implements Initializable  {
	private static FXMLLoader loader;
    private static PrivateCustomerRegistartionController privateCustomerRegistrationScreenController;
    
    private ArrayList<TextField> textFields = new ArrayList<>();
    private ArrayList<TextField> integerFields = new ArrayList<>();
    @FXML
    private TextField firstNameTxtField;

    @FXML
    private TextField lastNameTxtField;

    @FXML
    private TextField phoneTxtField;

    @FXML
    private TextField emailTxtField;

    @FXML
    private Button btnExit;

    @FXML
    private Button saveBtn;

    @FXML
    private Button btnBack;

    @FXML
    private TextField confirmedEmailTxtField;

    @FXML
    private Button btnHelp;

    @FXML
    private ComboBox<String> setHomeBranchCombo;

    @FXML
    private TextField cvvTxtField;

    @FXML
    private TextField creditNumTxtField;

    @FXML
    private TextField expirationTxtField;

    @FXML
    private Text errorText;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField idNumTxtField;
    
    @FXML
    private Text displayMessage;
    
    @FXML
    private TextField userNameTxtField;
    
    @FXML
    void getCVV(ActionEvent event) {

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
    void getExpiration(ActionEvent event) {

    }

    @FXML
    void getFirstName(ActionEvent event) {

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
    void getPassword(ActionEvent event) {

    }

    @FXML
    void getPhoneNum(ActionEvent event) {

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
    	PopUpMessages.helpMessage("Please fill in all the fields to complete the registration!");
    }

    /**
     * We get the details from the GUI then we check them with checkAllFields method
     * if they all the fields were correctly filled so we create a new message 
     * the object in this message is an arrayList of customer,login and credit card
     * we need these 3 objects on the server side .
     * @param event
     */
    @FXML
    void getSaveBtn(ActionEvent event) {
    	if(checkAllFields()==true) {
    		Branch homeBranch;
    		if(setHomeBranchCombo.getValue().equals("South Branch")) 
    			homeBranch = Branch.SOUTH;
    		
    		else if(setHomeBranchCombo.getValue().equals("North Branch"))
    			homeBranch = Branch.NORTH;
    		else
    			homeBranch = Branch.CENTER;
    		Customer customer = new Customer(idNumTxtField.getText(),ConfirmationStatus.PENDING_APPROVAL,firstNameTxtField.getText(),
    				lastNameTxtField.getText(),homeBranch,false,emailTxtField.getText(),phoneTxtField.getText(),
    				creditNumTxtField.getText());
    		CreditCard creditCard = new CreditCard(creditNumTxtField.getText(),expirationTxtField.getText(),cvvTxtField.getText());
    		Login login = new Login (userNameTxtField.getText(),passwordField.getText());
    		ArrayList<Object> list = new ArrayList<>();
    		list.add(customer);
    		list.add(login);
    		list.add(creditCard);
    		Message message = new Message(Task.REGISTER_PRIVATE_CUSTOMER,Answer.WAIT_RESPONSE,list);
    		sendToClient(message);
    	}
    }
    
    @FXML
    void getBackBtn(ActionEvent event) {
    	setCustomerRegistrationScreen(event);
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
 	 * this method sets a message from the listeners to the displayMessage .
 	 * @param message
 	 */
 	private void setRelevantTextToDisplayMessageText(String message) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				privateCustomerRegistrationScreenController.displayMessage.setText(message);
			}
		});
		
	}

    
    /**
     * This method initialize the private customer registration form.
     */
    public void initPrivateCustomerRegistrationScreen() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/BM7PrivateCustomerRegistartion.fxml").openStream());
					privateCustomerRegistrationScreenController = loader.getController();
					Scene scene = new Scene(root);
					Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) { 
							event.consume();
							Stage.close();
						}
					});
					scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
					Stage.setTitle("Private customer registration");
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
    

	@Override
	/**
	 * Here we initialize also the listeners.
	 */
	public void initialize(URL arg0, ResourceBundle arg1) {
		textFields.add(confirmedEmailTxtField); textFields.add(creditNumTxtField); textFields.add(cvvTxtField) ; textFields.add(emailTxtField); textFields.add(expirationTxtField);
		textFields.add(firstNameTxtField); textFields.add(idNumTxtField); textFields.add(lastNameTxtField); textFields.add(passwordField); textFields.add(phoneTxtField);
		textFields.add(userNameTxtField);
		integerFields.add(creditNumTxtField); integerFields.add(cvvTxtField) ; integerFields.add(idNumTxtField); integerFields.add(phoneTxtField); 
		
		Branch homeBranch = connectedUser.getHomeBranch();
		if(homeBranch.equals(Branch.NORTH))
			setHomeBranchCombo.setValue("North Branch");
		else if(homeBranch.equals(Branch.CENTER))
			setHomeBranchCombo.setValue("Center Branch");
		else
			setHomeBranchCombo.setValue("South Branch");
		setHomeBranchCombo.getItems().addAll("North Branch", "Center Branch", "South Branch");
		displayMessage.setText("");
		AnalyzeMessageFromServer.addClientListener(new AnalyzeClientListener(){
			@Override
			public void clientUserIdExist() {
				setRelevantTextToDisplayMessageText("This ID already exist on system");
			}
			public void clientUserNameExist() {
				setRelevantTextToDisplayMessageText("This User Name already exist on system");
			}
			public void clientPrivateCustomerRegistrationSucceed() {
				setRelevantTextToDisplayMessageText("Registration Succeed!");
			}
		});
	}

}

