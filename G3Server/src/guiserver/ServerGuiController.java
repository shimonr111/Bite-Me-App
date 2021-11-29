package guiserver;

import java.net.InetAddress;
import java.net.UnknownHostException;

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

public class ServerGuiController {
	final public static int DEFAULT_PORT = 5555;
	final public static String DEFAULT_DB_NAME = "jdbc:mysql://localhost/semesterialproject?serverTimezone=IST";
	final public static String DEFAULT_DB_USER = "root";
	final public static String DEFAULT_DB_PASSWORD = "password";
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

	/* create main form for the server */
	public void start(Stage primaryStage) throws Exception {
		loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/guiServer/ServerGui.fxml").openStream());
		ServerGuiController serverController = loader.getController();
		serverController.loadInfo();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/guiServer/ServerGui.css").toExternalForm());
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
			// TODO Auto-generated catch block
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

	/* set disability to the server form */
	private void setVisabilityForUI(boolean isVisable) {
		disconnectBtn.setDisable(!isVisable);
		portxt.setDisable(isVisable);
		DbNameTxt.setDisable(isVisable);
		DbUserTxt.setDisable(isVisable);
		DbPasswordTxt.setDisable(isVisable);
		connectBtn.setDisable(isVisable);
	}

	public void displayToConsoleInServerGui(String msg) {
		Platform.runLater(() -> {
			String currentText = txtConsole.getText();
			this.txtConsole.setText(currentText + "\n" + msg);
		});
	}

	public static FXMLLoader getLoader() {
		return loader;
	}

}