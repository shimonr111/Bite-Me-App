package bitemeserver;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import guiserver.ClientDoc;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import jdbc.mySqlConnection;
import query.ImportDataAnalyze;
import query.Query;
import users.Branch;
import users.BranchManager;
import users.BudgetType;
import users.CeoBiteMe;
import users.Company;
import users.ConfirmationStatus;
import users.CreditCard;
import users.HrManager;
import users.Login;
import users.PositionType;
import users.Supplier;
import users.SupplierWorker;
import users.WorkerPosition;

/**
 * @author  Lior, Guzovsky.
 * Class description: 
 * 
 * This is a class that works as an
 * UI for the server.
 * 
 * @version 03/12/2021
 */
public class BiteMeServerUI extends Application implements Initializable {
	/**
	 * Class members description:
	 */
	
	/**
	 * BiteMeServerCommunication instance to start to have a communication channel between the serverUI to the server.
	 */
	public static BiteMeServerCommunication serverCommunication;
	
	/**
	 * ServerGuiController instance to have an access to the server controller.
	 */
	
	/**
	 * mySqlConnection instance to save the SQl connection of the server.
	 */
	public static mySqlConnection connectionToDB;
	public static mySqlConnection connectionToExternalDB;
	
	/**
	* The default port number we connect to.
	*/
	final public static int DEFAULT_PORT = 5555;
	
	/**
	* The default db name.
	*/
	final public static String DEFAULT_DB_NAME = "jdbc:mysql://localhost/semesterialproject?serverTimezone=IST";
	final public static String EXTERNAL_DB_NAME = "jdbc:mysql://localhost/externaldb?serverTimezone=IST";
	
	/**
	* The db user name.
	*/
	final public static String DEFAULT_DB_USER = "root";
	
	/**
	* The db password.
	*/
	final public static String DEFAULT_DB_PASSWORD = "09000772Mm-";
	
	public static ArrayList<String> console=new ArrayList<>();
	public static ObservableList<ClientDoc> clients = FXCollections.observableArrayList();
	private boolean isImportButtonClicked = false;
	/**
	* An FXML loader instance.
	*/
	public static FXMLLoader loader;

	@FXML
	private TextField portxt;
	
	@FXML
	private TextField ipTxt;
	
	@FXML
	private TextField DbNameTxt;
	
	@FXML
	private TextField DbUserTxt;
	
	@FXML
	private PasswordField DbPasswordTxt;
	
	@FXML
	private Button connectBtn;
	
	@FXML
	private Button disconnectBtn;
	
	@FXML
	private Button closeBtn;
	
	@FXML
	private Button importBtn;
	
	@FXML
	private TextArea txtConsole;
	

    @FXML
    private TableView<ClientDoc> clientsTable;

    @FXML
    private TableColumn<ClientDoc, String> ipAddressCol;

    @FXML
    private TableColumn<ClientDoc, String> hostNameCol;

    @FXML
    private TableColumn<ClientDoc, String> statusCol;
    

	private String getport() {
		return portxt.getText();
	}

	public String getDbName() {
		return DbNameTxt.getText();
	}

	public String getDbUserTxt() {
		return DbUserTxt.getText();
	}

	public String getPassword() {
		return DbPasswordTxt.getText();
	}

	public static void main(String args[]) throws Exception {
		launch(args);
	} // end main

	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane root = FXMLLoader.load(getClass().getResource("/fxmls/Server.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
		primaryStage.setTitle("Server");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/* insert all the information for the Server configuration screen */
	private void loadInfo() {
		this.portxt.setText(String.valueOf(DEFAULT_PORT));
		try {
			this.ipTxt.setText(InetAddress.getLocalHost().getHostAddress());
			this.ipTxt.setDisable(true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		this.DbNameTxt.setText(DEFAULT_DB_NAME);
		this.DbUserTxt.setText(DEFAULT_DB_USER);
		this.DbPasswordTxt.setText(DEFAULT_DB_PASSWORD);
		disconnectBtn.setDisable(true);
	}

	@FXML
	public void connect(ActionEvent event) throws Exception {
		String p;

		p = getport();
		if (p.trim().isEmpty()) {
			console.add("You must enter a port number!\n");

		} else {
			setVisabilityForUI(true);
			console.add(runServer(p, getDbName(), getDbUserTxt(),
					getPassword())+"\n"); /* connect to DB and start listening */
			displayToConsoleInServerGui();
			
		}
	if(!isImportButtonClicked)
		importBtn.setDisable(false);
	}

	@FXML
	public void getCloseBtn(ActionEvent event) throws Exception {
		System.out.println("Close Gui Server"); /* close GUI using close button */
		System.exit(0);
	}

	@FXML
	public void disconnectBtn(ActionEvent event) throws Exception {
		setVisabilityForUI(false);
		DisconnectServer(); // when button Disconnect is clicked than terminate server listening method
		displayToConsoleInServerGui();
	}
	
	@FXML
	/**
	 * after clicking on import button , we get all the data from the external db
	 * after making a new connection to the external DB.
	 * @param event
	 * @throws Exception
	 */
	public void getImportBtn(ActionEvent event) throws Exception {
		if(!isImportButtonClicked) {
			isImportButtonClicked=true;
			connectionToExternalDB = new mySqlConnection();
			if (connectionToExternalDB.connectToDB(EXTERNAL_DB_NAME, getDbUserTxt(), getPassword(),false)) {
				Query.setConnectionFromServerToExternalDB(connectionToExternalDB.getConnection());
				ImportDataAnalyze.getDataFromExternalDB();
				displayToConsoleInServerGui();
				importBtn.setDisable(true);
				
			}
			
		}
	}
	
	/**
	* @param isVisable to control the visiablity of the UI
	*/
	private void setVisabilityForUI(boolean isVisable) {
		disconnectBtn.setDisable(!isVisable);
		portxt.setDisable(isVisable);
		DbNameTxt.setDisable(isVisable);
		DbUserTxt.setDisable(isVisable);
		DbPasswordTxt.setDisable(isVisable);
		connectBtn.setDisable(isVisable);
	}

	/**
	 * This is a method used 
	 * to display to the console of the server.
	 * 
	 * @param message
	 */
	public void displayToConsoleInServerGui() {
		String displayToConsole="";
		for(String s : console)
			displayToConsole+=s;
		txtConsole.setText(displayToConsole);
	
				
	}

	/*
	 * This function goes to the 
	 * EchoServer method of stop listening 
	 * when button Disconnect is clicked in
	 *  the Server Controller.
	 */
	public  void DisconnectServer() {
		console.add("The server is Disconnected\n");
		for(int i=0;i<BiteMeServerUI.clients.size();i++) {
			clients.remove(i);
		}
		serverCommunication.stopListening();

	}

	public String runServer(String p, String dbName, String dbUserTxt, String password) {
		String returnMsgFromServer = "Error! Connection failed.\n";
		int port = 0; // Port to listen on
		try {
			port = Integer.parseInt(p); // Set port to 5555

		} catch (Throwable t) {

		}
		if (serverCommunication == null)
			serverCommunication = new BiteMeServerCommunication(port);
		connectionToDB = new mySqlConnection();
		if (connectionToDB.connectToDB(dbName, dbUserTxt, password,true))
			 returnMsgFromServer=("Connection to server succeed\n");
		try {
			serverCommunication.listen(); // Start listening for connections
		} catch (Exception ex) {
			returnMsgFromServer = "Error! Could't listen for clients!\n";
		}
		return returnMsgFromServer;
	}
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadInfo();
		importBtn.setDisable(true);
		ipAddressCol.setCellValueFactory(new PropertyValueFactory<ClientDoc,String>("ipAddress"));
		hostNameCol.setCellValueFactory(new PropertyValueFactory<ClientDoc,String>("hostName"));
		statusCol.setCellValueFactory(new PropertyValueFactory<ClientDoc,String>("status"));
		clientsTable.setItems(clients);
	}
	
}
	
	


