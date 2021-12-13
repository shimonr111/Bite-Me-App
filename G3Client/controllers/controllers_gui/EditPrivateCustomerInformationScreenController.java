package controllers_gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import bitemeclient.PopUpMessages;
import communication.Answer;
import communication.Message;
import communication.Task;
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
import javafx.stage.WindowEvent;
import users.Branch;
import users.ConfirmationStatus;
import users.Customer;
import javafx.fxml.Initializable;
/**
 * 
 * @author Mousa, Srour
 * Class description: 
 * This is a class for 
 * controlling the UI of Edit Customer that appears immediately after clicking
 * on Edit while selecting Customer.
 * form.
 * 
 * @version 13/12/2021
 */
public class EditPrivateCustomerInformationScreenController extends AbstractBiteMeController implements Initializable {
	
	private static FXMLLoader loader;
    private static EditPrivateCustomerInformationScreenController editPrivateCustomerInformationScreenController;
    public static Customer customer;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnclose;

    @FXML
    private Button btnclose1;

    @FXML
    private Button btnHelp;

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
    private ComboBox<String> setHomeBranchCombo;

    @FXML
    private ComboBox<String> setStatusComboBox;

    @FXML
    private Text errorText;

    @FXML
    private TextField passwordField;

    @FXML
    void Send(ActionEvent event) {

    }

    @FXML
    void getBackBtn(ActionEvent event) {
    	setEditCustomerInformationScreen(event);
    }


    @FXML
    void getEmail(ActionEvent event) {

    }

    @FXML
    void getExitBtn(ActionEvent event) {
		Message message = new Message(Task.LOGOUT,Answer.WAIT_RESPONSE,connectedUser);
		sendToClient(message);
		connectedUser = null;
		System.exit(0);
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
    void getSetStatusComboBox(ActionEvent event) {

    }
    
    @FXML
    void getHelpBtn(ActionEvent event) {
    	PopUpMessages.helpMessage("You can change the customer's Status, change status then clock save and go back to see changes.");

    }
    
    /**
     * sets the screen , it will be called from previous screen.
     */
	public void initEditCustomerInformationScreen() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
				//	primaryStage.hide(); 
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/BM11EditPrivateCustomerInformationScreen.fxml").openStream());
					editPrivateCustomerInformationScreenController = loader.getController();
					Scene scene = new Scene(root);
					Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) { 
							event.consume();
							Stage.close();
						}
					});
					Stage.setTitle("Edit Private Customer");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 *  this method for the back button , it sets the previous screen.
	 * @param event
	 */
	public void setEditCustomerInformationScreen(ActionEvent event) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					FXMLLoader loader = new FXMLLoader();
					Pane root;
					try {
						Stage Stage = new Stage();
						Stage.setResizable(false);
						root = loader.load(getClass().getResource("/fxmls/BM10EditCustomerInformationScreen.fxml").openStream());
						EditCustomerInformationScreenController ECISC = new EditCustomerInformationScreenController();
						ECISC.initPortalAgain();
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
		firstNameTxtField.setText(customer.getUserFirstName()); lastNameTxtField.setText(customer.getUserLastName()); idNumTxtField.setText(customer.getUserId()); 
		phoneTxtField.setText(customer.getPhoneNumber());  emailTxtField.setText(customer.getUserEmail()); 
		if(customer.getHomeBranch().equals(Branch.NORTH))
			setHomeBranchCombo.setValue("North Branch");
		else if(customer.getHomeBranch().equals(Branch.CENTER))
			setHomeBranchCombo.setValue("Center Branch");
		else
			setHomeBranchCombo.setValue("South Branch");
		setStatusComboBox.setValue(customer.getStatusInSystem().toString());
		setStatusComboBox.getItems().addAll(ConfirmationStatus.CONFIRMED.toString(),ConfirmationStatus.FROZEN.toString(),ConfirmationStatus.PENDING_APPROVAL.toString());
		
	}

}
