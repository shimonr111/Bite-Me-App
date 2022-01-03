package controllers_gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * 
 * @author Alexander, Martinov.
 * 
 * Class description: This is a class for controlling the UI of adding
 * quarterly reports by BM
 * 
 * @version 27/12/2021
 */
public class AddQuarterlyReportsScreenController extends AbstractBiteMeController implements Initializable {

	/**
	 * Class members description:
	 */
	private static FXMLLoader loader;
	private static AddQuarterlyReportsScreenController addQuarterlyReportsScreenController;
	private static File quarterlyPDF = null;

	@FXML
	private Button btnBack;

	@FXML
	private Button btnExit;

	@FXML
	private Button btnHelp;

	@FXML
	private Text errorText;

	@FXML
	private Button saveBtn;

	@FXML
	private Button uploadReportBtn;

	/**
	 * Calls a function that switches to the previous screen namely the branch
	 * manager portal
	 * 
	 * @param event
	 */
	@FXML
	public void getBackBtn(ActionEvent event) {
		setBranchManagerPortal(event);
	}

	/**
	 * Clicking on exit button will log out the user then disconnect and exit.
	 * 
	 * @param event
	 */
	@FXML
	public void getExitBtn(ActionEvent event) {
		Message message = new Message(Task.LOGOUT, Answer.WAIT_RESPONSE, connectedUser);
		sendToClient(message);
		connectedUser = null;
		Message disconnectMessage = new Message(Task.CLIENT_DICONNECT, Answer.WAIT_RESPONSE, null);
		sendToClient(disconnectMessage);
		System.exit(0);
	}

	/**
	 * Display a help pop up message.
	 * 
	 * @param event
	 */
	@FXML
	public void getHelpBtn(ActionEvent event) {
		PopUpMessages.helpMessage("Please select the report file and click upload");
	}

	/**
	 * Converts picked report pdf file to byte array and sends to server file is
	 * sent with it's name and branch being uploaded from
	 * 
	 * @param event
	 */
	@FXML
	public void getSaveBtn(ActionEvent event) {
		if (quarterlyPDF != null) {
			Object[] branchFileAndName = new Object[3];
			String branchName = connectedUser.getHomeBranch().toString();
			byte[] pdfArray = null;
			try {
				pdfArray = Files.readAllBytes(Paths.get(quarterlyPDF.getAbsolutePath()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			branchFileAndName[0] = branchName;
			branchFileAndName[1] = pdfArray;
			branchFileAndName[2] = quarterlyPDF.getName();
			Message message = new Message(Task.UPLOAD_PDF, Answer.WAIT_RESPONSE, branchFileAndName);
			sendToClient(message);
			setRelevantTextToDisplayMessageText("Report uploaded to server");
			quarterlyPDF = null;
		} else
			setRelevantTextToDisplayMessageText("Please select a report to upload");
	}

	/**
	 * allows a user to pick a file to upload by using a file picker saves the file
	 * for later uploading
	 * 
	 * @param event
	 */
	@FXML
	public void getUploadReport(ActionEvent event) {
		quarterlyPDF = null;
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf"); 
		// allow only pdfs																					
		fileChooser.getExtensionFilters().add(extFilter);
		Stage stage = new Stage();
		File directory = fileChooser.showOpenDialog(stage);
		if(directory!=null) {
		quarterlyPDF = directory;
		setRelevantTextToDisplayMessageText(quarterlyPDF.getName()+" chosen");
		}
		else {
			setRelevantTextToDisplayMessageText("No File Selected");
		}
	}

	/**
	 * Displays a message at the bottom of the screen with the passed string
	 * 
	 * @param message
	 */
	private void setRelevantTextToDisplayMessageText(String message) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				addQuarterlyReportsScreenController.errorText.setText(message);
			}
		});
	}

	/**
	 * initializes the previous screen
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
	 * This is the initialization function for this screen when switched to.
	 * 
	 * @param message
	 */
	public void initAddQuarterlyReportsScreen() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/BM9AddQuarterlyReportsScreen.fxml").openStream());
					addQuarterlyReportsScreenController = loader.getController();
					Scene scene = new Scene(root);
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
						scene.setOnMouseDragged(dragEvent -> {
							Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
							Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
						});
					});
					scene.getStylesheets()
							.add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("View Reports");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Screen initialization method
	 * 
	 * @param arg0
	 * @param arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

}
