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
import users.Company;
import users.ConfirmationStatus;
import users.CreditCard;
import users.Customer;
import users.HrManager;
import users.Login;

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
 * controlling the UI of Company Registration from the HR manager portal
 * after clicking on that button.
 * 
 * @version 16/12/2021
 */
public class CompanyRegistartionScreenController extends AbstractBiteMeController implements Initializable {

	/**
	 * Class members description:
	 */
	private static FXMLLoader loader;
    private static CompanyRegistartionScreenController companyRegistartionScreenController;
    private static AnalyzeClientListener listener;
    private ArrayList<TextField> textFields = new ArrayList<>();
    private Company company;
    
    @FXML
    private TextField companyNameTxtField;

    @FXML
    private Button btnExit;

    @FXML
    private Button saveBtn;

    @FXML
    private Button btnBack;

    @FXML
    private TextField companyEmailTxtField;

    @FXML
    private TextField companyAddTxtField;

    @FXML
    private Button btnHelp;

    @FXML
    private TextField confirmEmailTxtField;

    @FXML
    private Text errorText;

    @FXML
    private TextField companyNameTxtField1;
    
    /**
     * Call the method that loads the previous screen.
     * @param event
     */
    @FXML
    void getBackBtn(ActionEvent event) {
    	AnalyzeMessageFromServer.removeClientListener(listener);
    	setHrManagerPortal(event);
    }
    
    /**
     * This method will log out , disconnect and then exist .
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
     * Display relevant pop up message.
     * @param event
     */
    @FXML
    void getHelpBtn(ActionEvent event) {
    	PopUpMessages.helpMessage("Plesae, fill in all the fields then click save");
    }
    
    /**
     * This method will check if all the input from textFields were correctly filled in , 
     * and then it sends the relevant message to the server to Add the company to DB.
     * @param event
     */
    @FXML
    void getBtnSave(ActionEvent event) {
    	if(checkAllFields()==true) {
    		 company = new Company(companyNameTxtField.getText(),ConfirmationStatus.PENDING_APPROVAL,companyAddTxtField.getText()
    				,companyEmailTxtField.getText(),Integer.parseInt(companyNameTxtField1.getText()));
    		ArrayList<Object> list = new ArrayList<>();
    		list.add(company);
    		list.add(connectedUser);
    		Message message = new Message(Task.REGISTER_COMPANY,Answer.WAIT_RESPONSE,list);
    		sendToClient(message);
    	}
    }

    @FXML
    void getCompanyAdd(ActionEvent event) {

    }

    @FXML
    void getCompanyEmail(ActionEvent event) {

    }

    @FXML
    void getCompanyName(ActionEvent event) {

    }

    @FXML
    void getConfirmEmail(ActionEvent event) {

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
 			errorText.setText("Please, Fill in all the marked fields !!");
 			return false;
 		}
 		if(!isInt(companyNameTxtField1)) {
 			companyNameTxtField1.setStyle("-fx-border-color: red");
 			errorText.setText("Marked field must contain only numbers !");
 			return false;
 			}
 		
 		if(companyEmailTxtField.getText().contains("@")==false) {
 			companyEmailTxtField.setStyle("-fx-border-color: red");
 			errorText.setText("Please, Fill in a correct Email (E-mail must contain a '@') !!");
 			return false;
 		}
 		else if(companyEmailTxtField.getText().equals(confirmEmailTxtField.getText())== false) {
 			confirmEmailTxtField.setStyle("-fx-border-color: red");
 			errorText.setText("Please, fill the same Email address on both email fields!!");
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
				companyRegistartionScreenController.errorText.setText(message);
			}
		});
		
	}
    
    /**
     * this method loads the current screen, it will be called from the previous screen.
     */
	public void initCompanyRegistrationScreen() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/HR1CompanyRegistartionScreen.fxml").openStream());
					companyRegistartionScreenController = loader.getController();
					Scene scene = new Scene(root);
					Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) { 
							event.consume();
							Stage.close();
						}
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("Company Registration");
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
	public void setHrManagerPortal(ActionEvent event) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					FXMLLoader loader = new FXMLLoader();
					Pane root;
					try {
						Stage Stage = new Stage();
						Stage.setResizable(false);
						root = loader.load(getClass().getResource("/fxmls/UserPortalOfHRManager.fxml").openStream());
						UserPortalOfHRManagerController UPOHRMC = new UserPortalOfHRManagerController();
						UPOHRMC.initPortalAgain();
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
     * Initialize Listener to handle the answer from the server.
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		textFields.add(companyNameTxtField); textFields.add(companyNameTxtField1); textFields.add(companyEmailTxtField); textFields.add(confirmEmailTxtField); textFields.add(companyAddTxtField);
		errorText.setText("");
		AnalyzeMessageFromServer.addClientListener(listener=new AnalyzeClientListener(){
			@Override
			public void clientCompanyRegistrationFailed(String message) {
				setRelevantTextToDisplayMessageText(message);
			}
			@Override
			public void clientCompanyRegistrationSucceed() {
				((HrManager) connectedUser).setCompany(company);
				setRelevantTextToDisplayMessageText("Registration succeed, you have to wait for Confirmation from the Branch Manager");
			}
		});
		
	}

}
