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
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import users.Company;
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
 * Class description: 
 * This is a class for 
 * controlling the UI of Company Registration Management Screen that appears immediately after clicking
 * on company registration management from the branch portal.
 * form.
 * 
 * @author Mousa, Srour.
 * 
 * @version 16/12/2021
 */
public class CompanyRegistartionManagementScreenController extends AbstractBiteMeController implements Initializable{
	
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
    private static CompanyRegistartionManagementScreenController companyRegistartionManagementScreenController;
    
    /**
     * A static ArrayList of Companies that we get from the DB.
     */
    public static ArrayList<Company> companiesWaitingForConfirmation = new ArrayList<>();
    
    @FXML
    /**
     * The Exit Button.
     */
    private Button btnExit;

    @FXML
    /**
     * The Confirm Button.
     */
    private Button confirmBtn;

    @FXML
    /**
     * The Back Button.
     */
    private Button btnBack;

    @FXML
    /**
     * The Deny Button.
     */
    private Button denyBtn;

    @FXML
    /**
     * The TableView of companies.
     */
    private TableView<Company> companiesTable;

    @FXML
    /**
     * The column that contains the company name.
     */
    private TableColumn<Company, String> companyNameCol;

    @FXML
    /**
     * The Help Button.
     */
    private Button btnHelp;

    @FXML
    /**
     * The empty text that we show messages to the user threw it.
     */
    private Text displayText;
    
    @FXML
    /**
     * A Text Field that we use as a search bar.
     */
    private TextField searchField;
    
	/**
     * Calls the method that loads the previous screen.
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getBackBtn(ActionEvent event) {
    	setBranchManagerPortal(event);
    }
    
    /**
     * Displays a message to the user.
     * 
     * @param event ActionEvent of javaFX. 
     */
    @FXML
    public void getHelpBtn(ActionEvent event) {
    	PopUpMessages.helpMessage("On this screen you can confirm or deny the company registration requests by clicking on the buttons while selecting company at table.");
    }
    
    /**
     * Get the selected company and send it to the server for confirmation.
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getConfirmBtn(ActionEvent event) {
    	Company selectedCompany = companiesTable.getSelectionModel().getSelectedItem();
    	if(selectedCompany != null) {
    		Optional<ButtonType> result = PopUpMessages.confirmationMessage("Sure you want to confirm:"+ selectedCompany.getCompanyName() +"?");
    		if(result.get() == ButtonType.OK) {
    			ArrayList<Object> objectToMessage = new ArrayList<>();
    			objectToMessage.add(selectedCompany);
        		Message message = new Message(Task.CONFIRM_COMPANY,Answer.WAIT_RESPONSE,selectedCompany);
        		sendToClient(message);
        		displayText.setText("Company:"+ selectedCompany.getCompanyName() +"confirmed.");
        		initialize(null, null);	
    		}
    	}
    	else {
    		displayText.setText("Select company to confirm!");
    	}
    }
    
    /**
     * We get the selected company and we send it to the server.
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    void getDenyBtn(ActionEvent event) {
       	Company selectedCompany = companiesTable.getSelectionModel().getSelectedItem();	
    	if(selectedCompany != null) {
    		Optional<ButtonType> result = PopUpMessages.confirmationMessage("Denying the confirmation will lead to remove:" +selectedCompany.getCompanyName() + "!");
    		if(result.get() == ButtonType.OK) {
    		Message message = new Message(Task.DENY_COMPANY,Answer.WAIT_RESPONSE,selectedCompany);
    		sendToClient(message);
    		displayText.setText("Company: "+selectedCompany.getCompanyName()+" was removed.");
    		initialize(null, null);	
    		}
    	}
    }
    
    /**
     * Log out and then exit.
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
     * This method loads the current screen, it will be called from the previous screen.
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
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
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
	 * This method loads the previous screen.
	 * 
	 * @param event ActionEvent of javaFX.
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
	 * 
	 * @param arg0
	 * @param arg1
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
