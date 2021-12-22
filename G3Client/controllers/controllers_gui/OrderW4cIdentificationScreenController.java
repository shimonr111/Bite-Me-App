package controllers_gui;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.Popup;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import users.Branch;
import users.BusinessCustomer;
import users.Customer;
import users.HrManager;

/**
 * 
 * @author Lior, Guzovsky
 * @author Shimon, Rubin
 * Class description: 
 * This is a class for 
 * controlling the UI of w4c identification of 
 * private customer.
 * 
 * @version 13/12/2021
 */
public class OrderW4cIdentificationScreenController extends AbstractBiteMeController implements Initializable{
	/**
	 * Class members description:
	 */
	
	private static FXMLLoader loader;
    private static OrderW4cIdentificationScreenController orderW4cIdentificationScreenController;
    
    @FXML
    private TextField codeTxtField;

    @FXML
    private Button btnExit;

    @FXML
    private Button nextBtn;

    @FXML
    private Button qrBtn;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnHelp;

    @FXML
    private Text errorText;

    @FXML
    private ComboBox<String> homeBranchCombo;
    
    @FXML
    private Label companyCodeLabel;
    
    @FXML
    private TextField companyCodeTextField;

    /**
     * This is a function for returning
     * to the previous screen of the customer 
     * main window.
     * 
     * @param event
     */
    @FXML
    void getBackBtn(ActionEvent event) {
    	Platform.runLater(new Runnable() {
			@Override
			public void run() {
				FXMLLoader loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/UserPortalOfCustomer.fxml").openStream());
					Scene scene = new Scene(root);
					Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) { 
							event.consume();
							Stage.close();
						}
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("Main menu");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
				((Node) event.getSource()).getScene().getWindow().hide();
			}
		});
    }

    /**
     * This is the function for exiting 
     * the system of Bite me
     * 
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
     * This is a button for 
     * getting information about the current screen
     * 
     * @param event
     */
    @FXML
    void getHelpBtn(ActionEvent event) {
    	PopUpMessages.helpMessage("Please enter your W4C identification card and choose from which branch"
    			+ " you want to order");
    }

    /**
     * This function move us to the next screen if
     * the data received form the user 
     * is ok.
     * 
     * @param event
     */
    @FXML
    void getNextBtn(ActionEvent event) {
    	/*if the user has entered a wrong input set message accordingly*/
    	if(!isW4cTextFieldNotEmpty(codeTxtField.getText())) {
    		//put error string to the user and mark the invalid field 
    		errorText.setText("Please, enter a w4c code!!");
    		errorText.setFill(Color.RED);
    		codeTxtField.setStyle("-fx-border-color: red");
    	}
    	else {
    		/*check if the w4c code equals to w4c code
    		*if the w4c code is ok than we need to update the branch in the user thats located in the 
    		*Analyze class*/
    		if(connectedUser instanceof Customer) {
    			//in the DB and in our code we save the w4c number as int where as in the GUI we get it as string
    			//so we convert it here
    			if(!codeTxtField.getText().equals(String.valueOf(((Customer) connectedUser).getPrivateW4cCodeNumber()))) {
    				errorText.setText("Wrong W4c code, please try again!!");
    	    		errorText.setFill(Color.RED);
    	    		codeTxtField.setStyle("-fx-border-color: red");
    			}
    			else {
    				//check if the user is business user and than check if he enters with the company code number if so check validity
    				if(connectedUser instanceof HrManager) {
    					if(!isCompanyCodeNumberEmpty(companyCodeTextField.getText())) {
    						//check validity of the company code number
    						if(((HrManager) connectedUser).getcompanyOfBusinessCustomer().getcompanyCode() ==  Integer.parseInt(companyCodeTextField.getText())) {
        						((HrManager) connectedUser).setLoggedInAsBusinessAccount(true);
    						}
    						else {
    							errorText.setText("Wrong company code number, please enter other one!!");
    		    	    		errorText.setFill(Color.RED);
    		    	    		companyCodeTextField.setStyle("-fx-border-color: red");
    						}
    					}
    				} else if(connectedUser instanceof BusinessCustomer) {
    					if(!isCompanyCodeNumberEmpty(companyCodeTextField.getText())) {
    						if(((BusinessCustomer) connectedUser).getcompanyOfBusinessCustomer().getcompanyCode() ==  Integer.parseInt(companyCodeTextField.getText())) {
    							((BusinessCustomer) connectedUser).setLoggedInAsBusinessAccount(true); 
    						}
    						else {
    							errorText.setText("Wrong company code number, please enter other one!!");
    		    	    		errorText.setFill(Color.RED);
    		    	    		companyCodeTextField.setStyle("-fx-border-color: red");
    						}
    					}
    				}
    				//check if the user is a businessCustomer or HR and tried to enter company code with wrong input else continue
    				if(connectedUser instanceof HrManager) {
    					if(!isCompanyCodeNumberEmpty(companyCodeTextField.getText())) {
    						if(((HrManager) connectedUser).getLoggedInAsBusinessAccount()) { 
    							setHomeBranchAccordingToUsersChoiseAndChangeScreen(event);
    						}
    					}
    					else {
    						setHomeBranchAccordingToUsersChoiseAndChangeScreen(event);
    					}
    				}
    				else if(connectedUser instanceof BusinessCustomer){
    					if(!isCompanyCodeNumberEmpty(companyCodeTextField.getText())) {
    						if(((BusinessCustomer) connectedUser).getLoggedInAsBusinessAccount()) { 
    							setHomeBranchAccordingToUsersChoiseAndChangeScreen(event);
    						}
    					}else {
							setHomeBranchAccordingToUsersChoiseAndChangeScreen(event);
						}
    				}
    				else {
    				//The w4c code is right, now we need to update if necessary the branch 
    				//from which the customer wants to order
    				setHomeBranchAccordingToUsersChoiseAndChangeScreen(event);
    				}
    			}
    		}
    	}
    	
    }

    /**
     * This is a method for changing the screen
     * and putting the relevant branch according the the users
     * choise.
     * 
     * @param event
     */
	private void setHomeBranchAccordingToUsersChoiseAndChangeScreen(ActionEvent event) {
		Branch homeBranch;
		if(homeBranchCombo.getValue().equals("South Branch")) 
			homeBranch = Branch.SOUTH;
		
		else if(homeBranchCombo.getValue().equals("North Branch"))
			homeBranch = Branch.NORTH;
		else
			homeBranch = Branch.CENTER;
		connectedUser.setHomeBranch(homeBranch); //set the new branch and don't change it in the DB
		
		//now we need to change this screen to the next one
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		OrderChooseResturantInOrderScreenController orderChooseResturantInOrderScreenController = new OrderChooseResturantInOrderScreenController();
		orderChooseResturantInOrderScreenController.initChooseRestaurantScreen(); // call the init of the next screen
	}
    
    /**
     * This method handles the QR code simulation.
     * 
     * @param event
     */
    @FXML
    void getQrBtn(ActionEvent event) {
    	TextInputDialog dialog = new TextInputDialog("Put here your W4C code");
    	dialog.setHeaderText("Put your W4C (QR Simulation)");
    	dialog.setTitle("G#3 QR Simulation");
    	dialog.getDialogPane().getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
    	Optional<String> result = dialog.showAndWait();
    	  if(result.isPresent()) {
    		  if (result.get().matches("[0-9]+") && result.get().length() > 2) //check that it contains only numbers
    			  codeTxtField.setText(result.get());
    	  }
    	  
    }
    
    /**
     * This is a function for uploading 
     * the w4c identification window 
     * from the previous screen which is 
     * the main window. 
     */
    public void initW4cIdentificationScreen() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/ORD1W4C_Identification_Screen.fxml").openStream());
					orderW4cIdentificationScreenController = loader.getController();
					Scene scene = new Scene(root);
					Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) { 
							event.consume();
							Stage.close();
						}
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("W4C Identification");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

    /**
     * This is a function for initialization of 
     * the screen.
     * We use it to set the branch of the user according to
     * the DB before 
     * uploading the screen .
     * 
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/*check if the connected user is type of customer only for 
		protection*/
		if(connectedUser instanceof Customer) {
			/*make the company code text and label invisible for customer*/
			companyCodeLabel.setVisible(false);
			companyCodeTextField.setVisible(false);
		Branch homeBranch = connectedUser.getHomeBranch();
		if(homeBranch.equals(Branch.NORTH))
			homeBranchCombo.setValue("North Branch");
		else if(homeBranch.equals(Branch.CENTER))
			homeBranchCombo.setValue("Center Branch");
		else
			homeBranchCombo.setValue("South Branch");
		homeBranchCombo.getItems().addAll("North Branch", "Center Branch", "South Branch");	
		}
		
		/*make the company code text and label visible for business and HR*/
		if(connectedUser instanceof BusinessCustomer) {
			companyCodeLabel.setVisible(true);
			companyCodeTextField.setVisible(true);
		}
	}
    
	/**
	 * This function is used for
	 * Identifying  if the user 
	 * has entered an empty input 
	 * for the W4c code.
	 * 
	 * @param w4cInsertText
	 * @return boolean
	 */
	public boolean isW4cTextFieldNotEmpty(String w4cInsertText) {
		if(w4cInsertText.equals("")) {
			return false;
		}
		return true;
	}
	
	/**
	 * This function is used for
	 * Identifying  if the user (business customer \ hr manager) 
	 * has entered an empty input 
	 * for the company Code Number.
	 * 
	 * @param companyCodeNumber
	 * @return
	 */
	public boolean isCompanyCodeNumberEmpty(String companyCodeNumber) {
		if(companyCodeNumber.equals("")) {
			return true;
		}
		return false;
	}
    
}
