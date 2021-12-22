package controllers_gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import users.BusinessCustomer;
import users.Company;
import users.Supplier;
import users.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import bitemeclient.PopUpMessages;
import communication.Answer;
import communication.Message;
import communication.Task;
import javafx.fxml.Initializable;

/**
 * 
 * @author Mousa, Srour
 * Class description: 
 * This is a class for 
 * controlling the UI of Company Registration Management Screen that appears immediately after clicking
 * on company registration management from the branch portal.
 * form.
 * 
 * @version 16/12/2021
 */
public class CompanyRegistartionManagementScreenController extends AbstractBiteMeController implements Initializable {
	/**
	 * Class members description:
	 */
	private static FXMLLoader loader;
    private static CompanyRegistartionManagementScreenController companyRegistartionManagementScreenController;
    public static ArrayList<Company> companiesWaitingForConfirmation = new ArrayList<>();
    
    @FXML
    private Button btnExit;

    @FXML
    private Button confirmBtn;

    @FXML
    private Button btnBack;

    @FXML
    private Button denyBtn;

    @FXML
    private TableView<Company> companiesTable;

    @FXML
    private TableColumn<Company, String> companyNameCol;

    @FXML
    private Button btnHelp;

    @FXML
    private Text displayText;
    
    @FXML
    private TextField searchField;
    
	/**
     * calls the method that loads the previous screen.
     * @param event
     */
    @FXML
    void getBackBtn(ActionEvent event) {
    	setBranchManagerPortal(event);
    }
    
    /**
     * displays a message to the user.
     * @param event
     */
    @FXML
    void getHelpBtn(ActionEvent event) {
    	PopUpMessages.helpMessage("Confirm or Deny the company registration requests by clicking on the buttons while selecting company from table");
    }
    
    /**
     * get the selected company and send it to the server for confirmation.
     * @param event
     */
    @FXML
    void getConfirmBtn(ActionEvent event) {
    	Company selectedCompany = companiesTable.getSelectionModel().getSelectedItem();
    	if(selectedCompany != null) {
    		Optional<ButtonType> result = PopUpMessages.confirmationMessage("Click OK if you are sure you want to confirm "+ selectedCompany.getCompanyName());
    		if(result.get() == ButtonType.OK) {
    			ArrayList<Object> objectToMessage = new ArrayList<>();
    			objectToMessage.add(selectedCompany);
        		Message message = new Message(Task.CONFIRM_COMPANY,Answer.WAIT_RESPONSE,selectedCompany);
        		sendToClient(message);
        		displayText.setText("Company: "+ selectedCompany.getCompanyName() +" was confirmed.");
        		initialize(null, null);	
    		}
    	}
    	else {
    		displayText.setText("Please Select One Company to Confirm.");
    	}
    }
    
    /**
     * we get the selected company and we send it to the server.
     * @param event
     */
    @FXML
    void getDenyBtn(ActionEvent event) {
       	Company selectedCompany = companiesTable.getSelectionModel().getSelectedItem();	
    	if(selectedCompany != null) {
    		Optional<ButtonType> result = PopUpMessages.confirmationMessage("Denying the confirmation will lead to remove" +selectedCompany.getCompanyName());
    		if(result.get() == ButtonType.OK) {
    		Message message = new Message(Task.DENY_COMPANY,Answer.WAIT_RESPONSE,selectedCompany);
    		sendToClient(message);
    		displayText.setText("Company: "+selectedCompany.getCompanyName()+" was removed.");
    		initialize(null, null);	
    		}
    	}
    }
    
    /**
     * log out and then exit.
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
     * this method loads the current screen, it will be called from the previous screen.
     */
	public void initCompanyRegistrationManagementScreen() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/BM3CompanyRegistartionManagementScreen.fxml").openStream());
					companyRegistartionManagementScreenController = loader.getController();
					Scene scene = new Scene(root);
					Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) { 
							event.consume();
							Stage.close();
						}
					});
					//scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
					Stage.setTitle("Company Confirmation");
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
	public void setBranchManagerPortal(ActionEvent event) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					FXMLLoader loader = new FXMLLoader();
					Pane root;
					try {
						Stage Stage = new Stage();
						Stage.setResizable(false);
						root = loader.load(getClass().getResource("/fxmls/UserPortalOfBranchManager.fxml").openStream());
						UserPortalOfBranchManagerController UOBMC = new UserPortalOfBranchManagerController();
						UOBMC.initPortalAgain();
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
	 * Initialize the table after getting all the companies that were not confirmed yet.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<Company> companies = FXCollections.observableArrayList();
		Message message = new Message (Task.GET_COMPANIES_FOR_CONFIRMATION,Answer.WAIT_RESPONSE,null);
		sendToClient(message);
		for(Company company : companiesWaitingForConfirmation)
			companies.add(company);
		companyNameCol.setCellValueFactory(new PropertyValueFactory<Company,String>("companyName"));
		companiesTable.setItems(companies);
		
		FilteredList<Company> filteredData = new FilteredList<Company>(companies , b -> true);
		searchField.textProperty().addListener((observable, oldValue, newValue)->{
			filteredData.setPredicate(Company ->{//newValue.isBlank() ||
				if(newValue.isEmpty() ||  newValue == null) {
					return true;
				}
				
				String searchKeyWord = newValue.toLowerCase();
				
				if(Company.getCompanyName().toLowerCase().indexOf(searchKeyWord)>-1) {
					return true;
				}
				else
					return false; // nothing to display
				
				
			});
		});
		
		SortedList<Company>  sortedData = new SortedList<>(filteredData);
		
		sortedData.comparatorProperty().bind(companiesTable.comparatorProperty());
		
		companiesTable.setItems(sortedData);
		
	}

	
}
