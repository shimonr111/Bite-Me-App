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
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import users.Company;
import users.ConfirmationStatus;
import users.HrManager;
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
 * Class description: 
 * This is a class for 
 * controlling the UI of Company Registration from the HR manager portal
 * after clicking on that button.
 * 
 * 
 * @author Mousa, Srour
 * 
 * @version 16/12/2021
 */
public class CompanyRegistartionScreenController extends AbstractBiteMeController implements Initializable {

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
    private static CompanyRegistartionScreenController companyRegistartionScreenController;
    
    /**
     * A static listener of this screen , so when we finish working on this screen we can stop it.
     */
    private static AnalyzeClientListener listener;
    
    /**
     * ArrayList of Text Fields so we can check them into a loop.
     */
    private ArrayList<TextField> textFields = new ArrayList<>();
    
    /**
     * The Chosen company for registration.
     */
    private Company companyAfterRegistration;
    
    @FXML
    /**
     * Company Text Field.
     */
    private TextField companyNameTxtField;

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
     * The Company's E-Mail Text Field.
     */
    private TextField companyEmailTxtField;

    @FXML
    /**
     * The Company's Address Text Field.
     */
    private TextField companyAddTxtField;

    @FXML
    /**
     * The Help Button.
     */
    private Button btnHelp;

    @FXML
    /**
     * The Confirm E-mail Text Field.
     */
    private TextField confirmEmailTxtField;

    @FXML
    /**
     * The empty text that we show our messages to the user threw it.
     */
    private Text errorText;

    @FXML
    /**
     * Company Name Text Field.
     */
    private TextField companyNameTxtField1;
    
    /**
     * Call the method that loads the previous screen.
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getBackBtn(ActionEvent event) {
    	AnalyzeMessageFromServer.removeClientListener(listener);
    	setHrManagerPortal(event);
    }
    
    /**
     * This method will log out , disconnect and then exist .
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
     * Display relevant pop up message.
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getHelpBtn(ActionEvent event) {
    	PopUpMessages.helpMessage("On this screen you register companies.");
    }
    
    /**
     * This method will check if all the input from textFields were correctly filled in , 
     * and then it sends the relevant message to the server to Add the company to DB.
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getBtnSave(ActionEvent event) {
    	if(checkAllFields()==true) {
    		companyAfterRegistration = new Company(companyNameTxtField.getText(),ConfirmationStatus.PENDING_APPROVAL,companyAddTxtField.getText()
    				,companyEmailTxtField.getText(),Integer.parseInt(companyNameTxtField1.getText()));
    		Message message = new Message(Task.REGISTER_COMPANY,Answer.WAIT_RESPONSE,companyAfterRegistration);
    		sendToClient(message);
    	}
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
 			errorText.setText("Please fill all the required fields (*)!");
 			return false;
 		}
 		if(!isInt(companyNameTxtField1)) {
 			companyNameTxtField1.setStyle("-fx-border-color: red");
 			errorText.setText("Marked field must contain only numbers!");
 			return false;
 			}
 		
 		if(companyEmailTxtField.getText().contains("@")==false) {
 			companyEmailTxtField.setStyle("-fx-border-color: red");
 			errorText.setText("Incorrect email format, email must contain '@'!");
 			return false;
 		}
 		else if(companyEmailTxtField.getText().equals(confirmEmailTxtField.getText())== false) {
 			confirmEmailTxtField.setStyle("-fx-border-color: red");
 			errorText.setText("Emails doesn't match!");
 			return false;
 		}
 		return true;
 	}
 	
 	/**
 	 * This method checks if the textField contains numbers only.
 	 * 
 	 * @param txtField teh text field that we want to check.
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
 	 * This method sets a message from the listeners to the displayMessage .
 	 * 
 	 * @param message the message that we want to display to the user.
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
     * This method loads the current screen, 
     * it will be called from the previous screen.
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
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
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
	 * 
	 * @param event ActionEvent of javaFX.
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
     * 
     * @param arg0
     * @param arg1
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		textFields.add(companyNameTxtField); textFields.add(companyNameTxtField1); textFields.add(companyEmailTxtField); textFields.add(confirmEmailTxtField); textFields.add(companyAddTxtField);
		errorText.setText("");
		Company company = ((HrManager)connectedUser).getCompany();
		companyNameTxtField.setText(company.getCompanyName()); companyNameTxtField1.setText(Integer.toString(company.getcompanyCode())); companyEmailTxtField.setText(company.getEmail());
		confirmEmailTxtField.setText(company.getEmail()); companyAddTxtField.setText(company.getAddress());
		for(TextField txt : textFields) {
			txt.setDisable(true); txt.setEditable(false);
		}
		AnalyzeMessageFromServer.addClientListener(listener=new AnalyzeClientListener(){
			@Override
			public void clientCompanyRegistrationFailed(String message) {
				setRelevantTextToDisplayMessageText(message);
			}
			@Override
			public void clientCompanyRegistrationSucceed() {
				((HrManager) connectedUser).setCompany(companyAfterRegistration);
				setRelevantTextToDisplayMessageText("Registration succeed, now you are pending confirmation of the Branch Manager!");
			}
		});
		
	}

}
