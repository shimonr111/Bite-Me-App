package controllers_gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import users.BudgetType;
import users.BusinessCustomer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import bitemeclient.PopUpMessages;
import communication.Answer;
import communication.Message;
import communication.Task;
import javafx.fxml.Initializable;

/**
 * 
 * @author Mousa, Srour
 * class description: 
 * This is a class for 
 * controlling the UI of ConfirmBusinessASkForDetails that appears immediately after clicking
 * on Confirm button from the BusinessCustomerConfirmation screen from HR Manager .
 * @version 29/12/2021
 */
public class ConfirmBusinessAskForDetailsController extends AbstractBiteMeController implements Initializable {
	
	/**
	 * class members description:
	 */
	
	private static FXMLLoader loader;
    private static ConfirmBusinessAskForDetailsController confirmBusinessAskForDetailsController;
    
    @FXML
    private TextField budgetAmountTxtField;

    @FXML
    private TextField workerIdTxtField;

    @FXML
    private ComboBox<BudgetType> budgetTypeCombo;

    @FXML
    private Button exitBtn;

    @FXML
    private Button helpBtn;

    @FXML
    private Button completeRegistrationBtn;

    @FXML
    private Button backBtn;

    @FXML
    private TextField workerLastNameTxtField;

    @FXML
    private TextField workerFirstNameTxtField;

    @FXML
    private Text displayText;

    @FXML
    void getBackBtn(ActionEvent event) {
    	setConfirmationMainScreen(event);
    }

    @FXML
    void getCompleterRegistrationBtn(ActionEvent event) {
    	budgetAmountTxtField.setStyle(null);
    	if(budgetTypeCombo.getValue() == null)
    		displayText.setText("Choose budget type for worker");
    	else if(budgetAmountTxtField.getText().equals("")) {
    		displayText.setText("Please fill all the required fields");
    		budgetAmountTxtField.setStyle("-fx-border-color: red");
    	}
    	else if(!(isInt(budgetAmountTxtField)) || Integer.parseInt(budgetAmountTxtField.getText())<0){
    		displayText.setText("Budget Amount must be a number");
    		budgetAmountTxtField.setStyle("-fx-border-color: red");
    	}
    	else {
    		ArrayList<Object> objectToServer = new ArrayList<>();
    		objectToServer.add(budgetTypeCombo.getValue());
    		objectToServer.add(budgetAmountTxtField.getText());
    		objectToServer.add(workerIdTxtField.getText());
    		Message message = new Message(Task.CONFIRM_BUSINESS_CUSTOMER,Answer.WAIT_RESPONSE,objectToServer);
    		sendToClient(message);
    		displayText.setText("Worker " + workerFirstNameTxtField.getText() +" is confirmed in system");
    	}
    }

    @FXML
    void getExitBtn(ActionEvent event) {
		Message message = new Message(Task.LOGOUT,Answer.WAIT_RESPONSE,connectedUser);
		sendToClient(message);
		connectedUser = null;
		Message disconnectMessage= new Message(Task.CLIENT_DICONNECT,Answer.WAIT_RESPONSE,null);
		sendToClient(disconnectMessage);
		System.exit(0);
    }

    @FXML
    void getHelpBtn(ActionEvent event) {
    	PopUpMessages.helpMessage("Enter the budget type and the budget amount for the worker to complete registration");
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
     * this method loads the current screen, it will be called from the previous screen.
     */
	public void initConfirmBusinessAskForDeatilsScreen() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/HRConfirmBusinessAskForDetails.fxml").openStream());
					confirmBusinessAskForDetailsController = loader.getController();
					Scene scene = new Scene(root);
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("Business Customer Confirmation");
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
	public void setConfirmationMainScreen(ActionEvent event) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					FXMLLoader loader = new FXMLLoader();
					Pane root;
					try {
						Stage Stage = new Stage();
						Stage.setResizable(false);
						root = loader.load(getClass().getResource("/fxmls/HR2BusinessCustomerConfirmationScreen.fxml").openStream());
						BusinessCustomerConfirmationScreenController BCCSC = new BusinessCustomerConfirmationScreenController();
						BCCSC.initPortalAgain();
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
	public void initialize(URL arg0, ResourceBundle arg1) {
		BusinessCustomer businessCustomer = BusinessCustomerConfirmationScreenController.businessCustomerForConfirmation;
		workerIdTxtField.setText(businessCustomer.getUserId()); workerIdTxtField.setDisable(true); workerIdTxtField.setEditable(false);
		workerFirstNameTxtField.setText(businessCustomer.getUserFirstName()); workerFirstNameTxtField.setDisable(true); workerFirstNameTxtField.setEditable(false);
		workerLastNameTxtField.setText(businessCustomer.getUserLastName()); workerLastNameTxtField.setDisable(true); workerLastNameTxtField.setEditable(false);
		
		budgetTypeCombo.getItems().addAll(BudgetType.values());
		displayText.setText("");
		
	}

}
