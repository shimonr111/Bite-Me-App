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
import users.ConfirmationStatus;
import users.CreditCard;
import users.Customer;
import users.Login;
import users.UserForRegistration;
/**
 * 
 * Class description: 
 * This is a class for 
 * controlling the UI of PrivateCustomerResitrationScreen that appears immediately after clicking
 * on Private Customer registration from the CustomerRegistrationScreen form.
 * 
 * 
 * @author Mousa, Srour.
 * @version 10/12/2021
 */
public class PrivateCustomerRegistartionController extends AbstractBiteMeController implements Initializable{
	
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
    private static PrivateCustomerRegistartionController privateCustomerRegistrationScreenController;
    
    /**
     * Listener for the current page , we stop it if we finished working on this screen.
     */
    private static AnalyzeClientListener listener;
    
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

    @FXML
    /**
     * Last Name Text Field.
     */
    private TextField lastNameTxtField;

    @FXML
    /**
     * Phone number Text Field.
     */
    private TextField phoneTxtField;

    @FXML
    /**
     * Email Text Field.
     */
    private TextField emailTxtField;

    @FXML
    /**
     * The Exit Button.
     */
    private Button btnExit;

    @FXML
    /**
     * The Save Button.
     */
    private Button saveBtn;

    @FXML
    /**
     * The Back Button.
     */
    private Button btnBack;

    @FXML
    /**
     * Confirm Email Text Field.
     */
    private TextField confirmedEmailTxtField;

    @FXML
    /**
     * The Help Button.
     */
    private Button btnHelp;

    @FXML
    /**
     * ComboBox of Branches.
     */
    private ComboBox<String> setHomeBranchCombo;
    
    @FXML
    /**
     * CVV of Credit Card Text Field.
     */
    private TextField cvvTxtField;

    @FXML
    /**
     * Number of Credit Card Text Field.
     */
    private TextField creditNumTxtField;

    @FXML
    /**
     * Expiration DAte of Credit Card Text Field.
     */
    private TextField expirationTxtField;

    @FXML
    /**
     * The empty text that we display a messages to the customer threw it.
     */
    private Text errorText;

    @FXML
    /**
     * Password for Login  Text Field.
     */
    private TextField passwordField;

    @FXML
    /**
     * User ID  Text Field.
     */
    private TextField idNumTxtField;
    
    @FXML
    /**
     * The empty text that we display a messages to the customer threw it.
     */
    private Text displayMessage;
    
    @FXML
    /**
     * User Name for Login  Text Field.
     */
    private TextField userNameTxtField;
    
    /**
     * This method will disconnect the client,
     * log out the connected user and then exit.
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
     * This method will display a pop up message to the user.
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getHelpBtn(ActionEvent event) {
    	PopUpMessages.helpMessage("On this screen you can register private customers!");
    }

    /**
     * We get the details from the GUI then we check them with checkAllFields method
     * if they all the fields were correctly filled so we create a new message 
     * the object in this message is an arrayList of customer,login and credit card
     * we need these 3 objects on the server side .
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getSaveBtn(ActionEvent event) {
    	if(checkAllFields()==true) {
    		Branch homeBranch;
    		if(setHomeBranchCombo.getValue().equals("South Branch")) 
    			homeBranch = Branch.SOUTH;
    		
    		else if(setHomeBranchCombo.getValue().equals("North Branch"))
    			homeBranch = Branch.NORTH;
    		else
    			homeBranch = Branch.CENTER;
    		Customer customer = new Customer(idNumTxtField.getText(),ConfirmationStatus.CONFIRMED,firstNameTxtField.getText(),
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
    
    /**
     * This method will call the method that calls
     * the previous screen.
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getBackBtn(ActionEvent event) {
		AnalyzeMessageFromServer.removeClientListener(listener);
		setUserRegistrationScreen(event);
    }
    
    /**
     * This method checks if all the fields are filled
     * in addition here we check if the input were correct.
     * 
     * @return boolean: true if fill all fields
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
 	 * this method checks if the textField contains numbers only.
 	 * 
 	 * @param txtField The text field that we want to check.
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
 	 * this method sets a message from the listeners to the displayMessage .
 	 * 
 	 * @param message the message that we want to display to the user.
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
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
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
	 * Here we initialize also the listeners.
	 * 
	 * @param arg0
	 * @param arg1
	 */
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		textFields.add(confirmedEmailTxtField); textFields.add(creditNumTxtField); textFields.add(cvvTxtField) ; textFields.add(emailTxtField); textFields.add(expirationTxtField);
		textFields.add(firstNameTxtField); textFields.add(idNumTxtField); textFields.add(lastNameTxtField); textFields.add(passwordField); textFields.add(phoneTxtField);
		textFields.add(userNameTxtField);
		integerFields.add(creditNumTxtField); integerFields.add(cvvTxtField) ; integerFields.add(idNumTxtField); integerFields.add(phoneTxtField); 
		 UserForRegistration userForRegistration = UsersRegistrationScreenController.userForRegistration;
		confirmedEmailTxtField.setText(userForRegistration.getEmail()); creditNumTxtField.setText(userForRegistration.getCreditCardNumber()); cvvTxtField.setText(userForRegistration.getCreditCardCvvCode());
		emailTxtField.setText(userForRegistration.getEmail()); expirationTxtField.setText(userForRegistration.getCreditCardDateOfExpiration()); firstNameTxtField.setText(userForRegistration.getFirstName());
		idNumTxtField.setText(userForRegistration.getUserID()); lastNameTxtField.setText(userForRegistration.getLastName()); passwordField.setText(userForRegistration.getPassword());
		phoneTxtField.setText(userForRegistration.getPhoneNumber()); userNameTxtField.setText(userForRegistration.getUsername());
		idNumTxtField.setDisable(true); idNumTxtField.setEditable(false);
		
		Branch homeBranch = connectedUser.getHomeBranch();
		if(homeBranch.equals(Branch.NORTH))
			setHomeBranchCombo.setValue("North Branch");
		else if(homeBranch.equals(Branch.CENTER))
			setHomeBranchCombo.setValue("Center Branch");
		else
			setHomeBranchCombo.setValue("South Branch");
		displayMessage.setText("");
		
		AnalyzeMessageFromServer.addClientListener(listener=new AnalyzeClientListener(){
			@Override
			public void clientUserIdExist() {
				setRelevantTextToDisplayMessageText("This ID already exists on system!");
			}
			public void clientUserNameExist() {
				setRelevantTextToDisplayMessageText("This username already exists on system!");
			}
			public void clientPrivateCustomerRegistrationSucceed() {
				setRelevantTextToDisplayMessageText("Registration Succeed!");
			}
		});
	}
	
	

}

