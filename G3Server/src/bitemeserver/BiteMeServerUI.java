package bitemeserver;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
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
import javafx.stage.StageStyle;
import jdbc.mySqlConnection;
import query.ImportDataQueries;
import query.Query;
import serveranalyze.Scheduler;
import util.Constans;

/**
 * Class description: 
 * This is a class that works as an
 * UI for the server.
 */

/**
 * 
 * @author Lior, Guzovsky.
 * 
 * @version 26/12/2021
 */
public class BiteMeServerUI extends Application implements Initializable{
	
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
	
	/**
	* The external db name.
	*/
	final public static String EXTERNAL_DB_NAME = "jdbc:mysql://localhost/externaldb?serverTimezone=IST";
	
	/**
	* The db user name.
	*/
	final public static String DEFAULT_DB_USER = "root";
	
	/**
	* The db password.
	*/
	final public static String DEFAULT_DB_PASSWORD = "09000772Mm-";
	
	/**
	* The array of the strings we present in the console
	*/
	public static ArrayList<String> console=new ArrayList<>();
	
	/**
	* The array of clients
	*/
	public static ObservableList<ClientDoc> clients = FXCollections.observableArrayList();
	
	/**
	* Boolean of if we pressed of the import
	*/
	private boolean isImportButtonClicked = false;
	
	/**
	* An FXML loader instance.
	*/
	public static FXMLLoader loader;

	/**
	* Text field for the port
	*/
	@FXML
	private TextField portxt;
	
	/**
	* Text field for the ip
	*/
	@FXML
	private TextField ipTxt;
	
	/**
	* Text field for the DB name
	*/
	@FXML
	private TextField DbNameTxt;
	
	/**
	* Text field for the DB user
	*/
	@FXML
	private TextField DbUserTxt;
	
	/**
	* Password field of DB
	*/
	@FXML
	private PasswordField DbPasswordTxt;
	
	/**
	* Button of the connect
	*/
	@FXML
	private Button connectBtn;
	
	/**
	* Button of the disconnect
	*/
	@FXML
	private Button disconnectBtn;
	
	/**
	* Button of the back button
	*/
	@FXML
	private Button closeBtn;
	
	/**
	* Button of the import button
	*/
	@FXML
	private Button importBtn;
	
	/**
	* Text area of the console
	*/
	@FXML
	private TextArea txtConsole;
	
	/**
	* Table of the clients
	*/
    @FXML
    private TableView<ClientDoc> clientsTable;

    /**
	* Column of the ip
	*/
    @FXML
    private TableColumn<ClientDoc, String> ipAddressCol;

    /**
	* Column of the host name
	*/
    @FXML
    private TableColumn<ClientDoc, String> hostNameCol;

    /**
	* Column of the status
	*/
    @FXML
    private TableColumn<ClientDoc, String> statusCol;
    
    /**
     * This is the getters from the server GUI
     * 
     * @return String from the text field
     */
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

	/**
     * This is the main function of the class
     */
	public static void main(String args[]) throws Exception {
		launch(args);
	} 

	/**
     * This method initialize the fxml and css of the screen
     */
	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane root = FXMLLoader.load(getClass().getResource("/fxmls/Server.fxml"));
		Scene scene = new Scene(root);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		scene.setOnMousePressed(pressEvent -> {
		    scene.setOnMouseDragged(dragEvent -> {
		        primaryStage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
		        primaryStage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
		    });
		});
		scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
		primaryStage.setTitle("Server");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/** 
	 * Insert all the information for the Server configuration screen 
	 */
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

	/**
	 * This method connect to the DB
	 * 
	 * @param event
	 * @throws Exception
	 */
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
	
    /**
     * This method exit from the GUI
     * 
     * @param event
     * @throws Exception
     */
	@FXML
	public void getCloseBtn(ActionEvent event) throws Exception {
		System.out.println("Close Gui Server"); /* close GUI using close button */
		System.exit(0);
	}

	/**
	 * This method disconnect from the DB
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	public void disconnectBtn(ActionEvent event) throws Exception {
		setVisabilityForUI(false);
		DisconnectServer(); // when button Disconnect is clicked than terminate server listening method
		displayToConsoleInServerGui();
	}
	
	@FXML
	/**
	 * After clicking on import button , we get all the data from the external db
	 * After making a new connection to the external DB.
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void getImportBtn(ActionEvent event) throws Exception {
		if(!isImportButtonClicked) {
			isImportButtonClicked=true;
			connectionToExternalDB = new mySqlConnection();
			if (connectionToExternalDB.connectToDB(EXTERNAL_DB_NAME, getDbUserTxt(), getPassword(),false)) {
				Query.setConnectionFromServerToExternalDB(connectionToExternalDB.getConnection());
				ImportDataQueries.getDataFromExternalDB();
				displayToConsoleInServerGui();
				importBtn.setDisable(true);
				
			}
			
		}
	}
	
	/**
	 * Thid method set the fields and buttons visible 
	 * 
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

	/**
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
   
	/**
	 * This method connect to DB after getting the field that the user filled
	 * 
	 * @param p
	 * @param dbName
	 * @param dbUserTxt
	 * @param password
	 * @return
	 */
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
		if (connectionToDB.connectToDB(dbName, dbUserTxt, password,true)) {
			 returnMsgFromServer=("Connection to server succeed\n");
			 Timer timer = new Timer();
			 Scheduler scheduler = new Scheduler();
			 timer.scheduleAtFixedRate(scheduler, 0, Constans.TEN_MINUTES_IN_MILLESECONDS);
		}
		try {
			serverCommunication.listen(); // Start listening for connections
		} catch (Exception ex) {
			returnMsgFromServer = "Error! Could't listen for clients!\n";
		}
		return returnMsgFromServer;
	}
	
    /**
     * This method initialize the fxml vars
     */
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
	
	


