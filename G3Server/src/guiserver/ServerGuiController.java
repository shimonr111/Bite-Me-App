package guiserver;

import java.net.InetAddress;
import java.net.UnknownHostException;

import analyze.Analyze;
import analyze.AnalyzeListener;
import bitemeserver.BiteMeServerUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author Lior, Guzovsky.
 * Class description: 
 * 
 * This is a class for the UI 
 * server controller.
 * 
 * @version 03/12/2021
 */
public class ServerGuiController {
	/**
	 * Class members description:
	 */	
	
	/**
	* The default port number we connect to.
	*/
	final public static int DEFAULT_PORT = 5555;
	
	/**
	* The default db name.
	*/
	final public static String DEFAULT_DB_NAME = "jdbc:mysql://localhost/semesterialproject?serverTimezone=IST";
	
	/**
	* The db user name.
	*/
	final public static String DEFAULT_DB_USER = "root";
	
	/**
	* The db password.
	*/
	final public static String DEFAULT_DB_PASSWORD = "orimalka789";
	
	public static ServerGuiController sgc;
	
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
	private TextArea txtConsole;

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

	
	public void start(Stage primaryStage) throws Exception {
		loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/fxmls/ServerGui.fxml").openStream());
		ServerGuiController serverController = loader.getController();
		serverController.loadInfo();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/css/ServerGui.css").toExternalForm());
		primaryStage.setTitle("Server");
		primaryStage.setScene(scene);
		primaryStage.show();
		//add and implement listener to display connection message
		Analyze.addServerListener(new AnalyzeListener() {
			@Override
			public void serverConfirmIp(String string) {
				serverController.displayToConsoleInServerGui(string);
			}
		});
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
			displayToConsoleInServerGui("You must enter a port number!");

		} else {
			setVisabilityForUI(true);
			displayToConsoleInServerGui(BiteMeServerUI.runServer(p, getDbName(), getDbUserTxt(),
					getPassword())); /* connect to DB and start listening */
		}
	}

	@FXML
	public void getCloseBtn(ActionEvent event) throws Exception {
		System.out.println("Close Gui Server"); /* close GUI using close button */
		System.exit(0);
	}

	@FXML
	public void disconnectBtn(ActionEvent event) throws Exception {
		setVisabilityForUI(false);

		BiteMeServerUI.DisconnectServer(); // when button Disconnect is clicked than terminate server listening method
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

	public void displayToConsoleInServerGui(String message) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
			String currentText = txtConsole.getText();
			txtConsole.setText(currentText + "\n" + message);
			}
		});
	}

	public static FXMLLoader getLoader() {
		return loader;
	}

}
