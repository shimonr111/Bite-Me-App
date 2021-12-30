package controllers_gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import bitemeclient.PopUpMessages;
import communication.Answer;
import communication.Message;
import communication.Task;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import users.UserForRegistration;

public class UsersRegistrationScreenController extends AbstractBiteMeController implements Initializable{
	
	/**
	 * Class members description:
	 */
	private static FXMLLoader loader;
	private static UsersRegistrationScreenController usersRegistrationScreenController;
	public static ArrayList<UserForRegistration> usersList = new ArrayList<>();
	public static UserForRegistration userForRegistration;
	
	@FXML
    private Button backButton;

    @FXML
    private Button businessCustomerButton;

    @FXML
    private Text displayMessage;

    @FXML
    private Button exitBtn;

    @FXML
    private Button helpBtn;

    @FXML
    private Button privateCustomerButton;
    
    @FXML
    private TextField searchField;

    @FXML
    private TableColumn<UserForRegistration, String> userFirstNameCol;

    @FXML
    private TableColumn<UserForRegistration, String>  userIdCol;

    @FXML
    private TableColumn <UserForRegistration, String>  userLastName;

    @FXML
    private TableView<UserForRegistration> usersTable;

    /**
     * This method....
     * 
     * @param event
     */
    @FXML
    public void getBackBtn(ActionEvent event) {
    	setBranchManagerPortal(event);
    }

    /**
     * This method....
     * 
     * @param event
     */
    @FXML
    public void getBusinessCustomer(ActionEvent event) {
    	UserForRegistration selectedUser = usersTable.getSelectionModel().getSelectedItem();
    	if(selectedUser != null) {
    		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
    		 userForRegistration=selectedUser;
    		 BusinessCustomerRegistartionController BCRC = new BusinessCustomerRegistartionController();
    		 BCRC.initBusinessCustomerRegistrationScreen();
    	}
    	else {
    		displayMessage.setText("Select user first!");
    	}
    }

    /**
     * This method....
     * 
     * @param event
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
     * This method....
     * 
     * @param event
     */
    @FXML
    public void getHelpBtn(ActionEvent event) {
    	PopUpMessages.helpMessage("On this screen you can register users by clicking on the desierd user on the table and the register as button.");
    }

    /**
     * This method....
     * 
     * @param event
     */
    @FXML
    public void getPrivateCustomer(ActionEvent event) {
    	UserForRegistration selectedUser = usersTable.getSelectionModel().getSelectedItem();
    	if(selectedUser != null) {
    	 	((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
    	 	userForRegistration=selectedUser;
        	PrivateCustomerRegistartionController PCRC = new PrivateCustomerRegistartionController();
        	PCRC.initPrivateCustomerRegistrationScreen();
    		
    	}
    	else {
    		displayMessage.setText("Select user first!");
    	}
    }

    /**
     * This method loads the current screen.
     */
	public void initUserRegistrationScreen() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
				//	primaryStage.hide(); 
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/BM13UsersRegistrationScreen.fxml").openStream());
					usersRegistrationScreenController = loader.getController();
					Scene scene = new Scene(root);
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("User registration");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * This method loads the branch manager portal screen ( previous screen) 
	 * 
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
	 * This method loads this screen after clicking back from next one.
	 */
	public void initPortalAgain() {
		loader = new FXMLLoader();
		Stage primaryStage = new Stage();
		Pane root = null;
		try {
			root = loader.load(getClass().getResource("/fxmls/BM13UsersRegistrationScreen.fxml").openStream());
			usersRegistrationScreenController = loader.getController();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		scene.setOnMousePressed(pressEvent -> {
		    scene.setOnMouseDragged(dragEvent -> {
		    	primaryStage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
		    	primaryStage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
		    });
		});
		scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
		primaryStage.setTitle("Main menu");
		primaryStage.setScene(scene);
		primaryStage.show();/* show the new screen */
	}

	 /**
     * This method....
     * 
     * @param arg0
     * @param arg1
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<UserForRegistration> userObservableList = FXCollections.observableArrayList();
		Message message = new Message(Task.GET_USERS_FOR_REGISTRATION,Answer.WAIT_RESPONSE,connectedUser);
		sendToClient(message);
		if(usersList.isEmpty())
			displayMessage.setText("No users waiting for registration!");
		userIdCol.setCellValueFactory(new PropertyValueFactory<UserForRegistration,String>("userID"));
		userFirstNameCol.setCellValueFactory(new PropertyValueFactory<UserForRegistration,String>("firstName"));
		userLastName.setCellValueFactory(new PropertyValueFactory<UserForRegistration,String>("lastName"));
		userObservableList.addAll(usersList);
		usersTable.setItems(userObservableList);
		
		FilteredList<UserForRegistration> filteredData = new FilteredList<UserForRegistration>(userObservableList , b -> true);
		searchField.textProperty().addListener((observable, oldValue, newValue)->{
			filteredData.setPredicate(UserForRegistration ->{//newValue.isBlank() ||
				if(newValue.isEmpty() ||  newValue == null) {
					return true;
				}
				
				String searchKeyWord = newValue.toLowerCase();
				
				if(UserForRegistration.getUserID().toLowerCase().indexOf(searchKeyWord)>-1) {
					return true;
				}
				else if(UserForRegistration.getFirstName().toLowerCase().indexOf(searchKeyWord)>-1) {
					return true;
				}
				else if(UserForRegistration.getLastName().toLowerCase().indexOf(searchKeyWord)>-1) {
					return true;
				}
				else
					return false; // nothing to display
				
				
			});
		});
		
		SortedList<UserForRegistration>  sortedData = new SortedList<>(filteredData);
		
		sortedData.comparatorProperty().bind(usersTable.comparatorProperty());
		
		usersTable.setItems(sortedData);
		
	}
}
