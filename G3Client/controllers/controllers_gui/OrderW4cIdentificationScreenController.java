package controllers_gui;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import bitemeclient.PopUpMessages;
import communication.Answer;
import communication.Message;
import communication.Task;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
import javafx.stage.StageStyle;
import users.Branch;
import users.BusinessCustomer;
import users.Customer;
/**
 *  Class description: 
 * This is a class for 
 * controlling the UI of w4c identification of 
 * private customer.
 * 
 * @author Lior, Guzovsky
 * @author Shimon, Rubin
 * 
 * @version 13/12/2021
 */
public class OrderW4cIdentificationScreenController extends AbstractBiteMeController implements Initializable{
	
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
    private static OrderW4cIdentificationScreenController orderW4cIdentificationScreenController;
    /**
     * This member indicate if its the first order of this customer, used to save the actual home branch in the first order.
     */
    private static boolean isFirstOrder = true;
    /**
     * The actual home branch that we will display as the home branch every time cusomer enters the W4C identification screen.
     */
    private static Branch actualHomeBranch;
    
    @FXML
    /**
     * Text Field of W4C code.
     */
    private TextField codeTxtField;

    @FXML
    /**
     * The Exit Button.
     */
    private Button btnExit;

    @FXML
    /**
     * The Next Button.
     */
    private Button nextBtn;

    @FXML
    /**
     * The QR Button.
     */
    private Button qrBtn;

    @FXML
    /**
     * The Back Button.
     */
    private Button btnBack;

    @FXML
    /**
     * The Help Button.
     */
    private Button btnHelp;

    @FXML
    /**
     * The empty text that we use to display messages to the user.
     */
    private Text errorText;

    @FXML
    /**
     * ComboBox of Branches.
     */
    private ComboBox<String> homeBranchCombo;
    
    @FXML
    /**
     * Label of company code if it is business customer.
     */
    private Label companyCodeLabel;
    
    @FXML
    /**
     * Text Field of Company Code for business customers.
     */
    private TextField companyCodeTextField;

    /**
     * This is a function for returning
     * to the previous screen of the customer 
     * main window.
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getBackBtn(ActionEvent event) {
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
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
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
     * This is a button for 
     * getting information about the current screen
     * 
     * @param event
     */
    @FXML
    public void getHelpBtn(ActionEvent event) {
    	PopUpMessages.helpMessage("On this screen you identify with your W4C (Enter the number or scan QR code).");
    }

    /**
     * This function move us to the next screen if
     * the data received form the user 
     * is ok.
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getNextBtn(ActionEvent event) {
    	/*if the user has entered a wrong input set message accordingly*/
    	if(!isW4cTextFieldNotEmpty(codeTxtField.getText())) {
    		//put error string to the user and mark the invalid field 
    		errorText.setText("Please fill all the required fields (*)!");
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
    				errorText.setText("Wrong W4C code!, try agian.");
    	    		errorText.setFill(Color.RED);
    	    		codeTxtField.setStyle("-fx-border-color: red");
    			}
    			else {
    				//check if the user is business user and than check if he enters with the company code number if so check validity
    				if(connectedUser instanceof BusinessCustomer) {
    					if(!isCompanyCodeNumberEmpty(companyCodeTextField.getText())) {
    						try {
    						if(((BusinessCustomer) connectedUser).getcompanyOfBusinessCustomer().getcompanyCode() ==  Integer.parseInt(companyCodeTextField.getText())) {
    							((BusinessCustomer) connectedUser).setLoggedInAsBusinessAccount(true); 
    						}
    						else {
    							errorText.setText("Wrong company code!, try agian.");
    		    	    		errorText.setFill(Color.RED);
    		    	    		companyCodeTextField.setStyle("-fx-border-color: red");
    						}
    						}catch(Exception e) { //for case the users enters chars and other letters rather than numbers
    							errorText.setText("Invalid company code!, try agian.");
    		    	    		errorText.setFill(Color.RED);
    		    	    		companyCodeTextField.setStyle("-fx-border-color: red");
    						}
    					}
    				}
    				//check if the user is a businessCustomer and if he tried to enter company code with wrong input, else continue
    				if(connectedUser instanceof BusinessCustomer){
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
     * @param event ActionEvent of javaFX.
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
    public void getQrBtn(ActionEvent event) {
    	TextInputDialog dialog = new TextInputDialog("~Code Here~");
    	dialog.setHeaderText("Put your W4C (QR Simulation)");
    	dialog.setTitle("G#3 QR Simulation");
    	dialog.getDialogPane().getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
    	PopUpMessages.centerButtons(dialog.getDialogPane());
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
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
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
     * @param arg0
     * @param arg1
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/*check if the connected user is type of customer only for 
		protection*/
		if(connectedUser instanceof Customer) {
			/*make the company code text and label invisible for customer*/
			companyCodeLabel.setVisible(false);
			companyCodeTextField.setVisible(false);
			
		//To save the actual home branch in the beginning so it will always displayed in the identification process.
		if(isFirstOrder) {
			actualHomeBranch = connectedUser.getHomeBranch();
			isFirstOrder = false;
		}
		Branch homeBranch = actualHomeBranch;
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
	 * @param w4cInsertText The entered w4c code.
	 * @return boolean True if it is the customer's code , false if not.
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
	 * @param companyCodeNumber the entered company's code.
	 * @return boolean True if it is the company's code , false if not.
	 */
	public boolean isCompanyCodeNumberEmpty(String companyCodeNumber) {
		if(companyCodeNumber.equals("")) {
			return true;
		}
		return false;
	}
    
}
