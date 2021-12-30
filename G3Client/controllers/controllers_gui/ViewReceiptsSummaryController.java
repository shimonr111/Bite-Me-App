package controllers_gui;

import java.io.IOException;
import java.net.URL;
import java.time.Year;
import java.util.ResourceBundle;

import bitemeclient.PopUpMessages;
import clientanalyze.AnalyzeMessageFromServer;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import util.SupplierByReport;
import users.SupplierWorker;

/**
 * 
 * @author Alexander, Martinov Class description: This is a class for
 *         controlling the UI of viewing supplier receipt form.
 * 
 * @version 30/12/2021
 */
public class ViewReceiptsSummaryController extends AbstractBiteMeController implements Initializable {

	/**
	 * Class members description:
	 */
	@FXML
	private Label MessageLabel;

	@FXML
	private ComboBox<String> ReportMonth;

	@FXML
	private ComboBox<String> ReportYear;

	@FXML
	private Button btnBack;

	@FXML
	private Button btnExit;

	@FXML
	private Button btnHelp;

	@FXML
	private Button viewReportBtn;
	private static ViewReceiptsSummaryController viewReceiptsSummaryController;
	public static SupplierByReport[] suppliers = null;
	private static FXMLLoader loader;

	@FXML
	void getBackBtn(ActionEvent event) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				FXMLLoader loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/UserPortalOfSupplier.fxml").openStream());
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
					Stage.setTitle("Main Screen");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
				((Node) event.getSource()).getScene().getWindow().hide();
			}
		});
	}

	@FXML
	void getExitBtn(ActionEvent event) {
		Message message = new Message(Task.LOGOUT, Answer.WAIT_RESPONSE, connectedUser);
		sendToClient(message);
		connectedUser = null;
		Message disconnectMessage = new Message(Task.CLIENT_DICONNECT, Answer.WAIT_RESPONSE, null);
		sendToClient(disconnectMessage);
		System.exit(0);
	}

	@FXML
	void getHelpBtn(ActionEvent event) {
		PopUpMessages.helpMessage("On this screen you can view your monthly bill by selecting the bill month and year");
	}

	@FXML
	void getViewReport(ActionEvent event) {
		if(checkDateAndType()) {
			String date=ReportYear.getValue()+"-"+ReportMonth.getValue()+"-01";
			String[] branchAndDate=new String[3];
			branchAndDate[0]=((SupplierWorker)connectedUser).getSupplier().getSupplierId();
			branchAndDate[1]=date; //prepare message to server, report date and branch
			branchAndDate[2]="receipt";
			Message message = new Message (Task.GET_SYSTEM_REPORTS,Answer.WAIT_RESPONSE,branchAndDate);
			sendToClient(message);
			if(suppliers==null) {//server should respond by now, if no reports are found it is displayed
				setRelevantTextToDisplayMessageText("No reports found for that time period");
			}
			else {//otherwise primes report generator, and generates reports by selected type
				ReportGenerator.setSuppliers(suppliers);
					displaySingleReport(ReportGenerator.generateBill());
					setRelevantTextToDisplayMessageText("");
					suppliers=null;//flushes supplier reports list to recieve next report
			}
		}
	}

	public void initViewReceiptsSummaryScreen() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/SUP3ViewReceiptsSummary.fxml").openStream());
					viewReceiptsSummaryController = loader.getController();
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

	public void displaySingleReport(String report) {
		DisplayReportScreenController displayReportScreenController = new DisplayReportScreenController();
		displayReportScreenController.initDisplayReportScreen();
		displayReportScreenController.setReport(report);
		displayReportScreenController.showReport();
	}

	/**
	 * sets message to small text field on the bottom
	 */
	private void setRelevantTextToDisplayMessageText(String message) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				viewReceiptsSummaryController.MessageLabel.setText(message);
			}
		});

	}

	/**
	 * Checks year, month and report type fields were filled(selected)
	 */
	public boolean checkDateAndType() {
		if (ReportYear.getValue().equals("Year")) {
			setRelevantTextToDisplayMessageText("Please fill all the required fields (*)!");
			return false;
		} else if (ReportMonth.getValue().equals("Month")) {
			setRelevantTextToDisplayMessageText("Please fill all the required fields (*)!");
			return false;
		}
		return true;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ReportYear.setValue("Year");
		ReportMonth.setValue("Month");
		Year y = Year.now();
		for (int i = y.getValue(); i > 2000; i--)
			ReportYear.getItems().add("" + i);
		for (int i = 1; i < 13; i++) {
			if (i < 10)
				ReportMonth.getItems().add("0" + i);
			else
				ReportMonth.getItems().add("" + i);
		}
	}
}
