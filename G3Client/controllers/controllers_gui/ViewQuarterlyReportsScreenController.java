package controllers_gui;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.IsoFields;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import util.SupplierByReport;

/**
* @author Alexander, Martinov
* Class description: 
* This is a class for 
* controlling the UI of viewing quarterly reports 
* form.
* 
* @version 14/12/2021
*/
public class ViewQuarterlyReportsScreenController extends AbstractBiteMeController implements Initializable {
    @FXML
    private ComboBox<String> ReportQuarter;

    @FXML
    private ComboBox<String> ReportYear;
	@FXML
	private Button btnBack;

	@FXML
	private Button btnExit;

	@FXML
	private Button btnHelp;

	@FXML
	private Text errorText;

	@FXML
	private Button viewReportBtn;
	

	private static FXMLLoader loader;
	private static ViewQuarterlyReportsScreenController viewQuarterlyReportsScreenController;
	public static SupplierByReport[] suppliers=null;

	/**
     * loads the previous screen after clicking on back button.
     */
	@FXML
	void getBackBtn(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		UserPortalOfCEOController UPCC = new UserPortalOfCEOController();
		UPCC.initCEOUserPortalAgain();
	}

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
	 * This is pop message for the help button.
	 * @param event
	 */
	@FXML
	void getHelpBtn(ActionEvent event) {
		PopUpMessages.helpMessage("On this screen you can view system reports by selecting the year and quarter.");
	}

	@FXML
	void getReport(ActionEvent event) {
		if(checkDate()) {
		String y = ReportYear.getValue();
		String date ="";
		String quarter = ReportQuarter.getValue();
		switch(quarter) {
		case "1":
			date=""+y+"-01-01";
			break;
		case "2":
			date=""+y+"-04-01";
			break;
		case "3":
			date=""+y+"-07-01";
			break;
		case "4":
			date=""+y+"-10-01";
			break;
			default:
				break;
		}
		String[] branchAndDate=new String[3];
		branchAndDate[0]="NOT_APPLICABLE";
		branchAndDate[1]=date; //prepare message to server, report date and branch
		branchAndDate[2]="quarterly";
		Message message = new Message (Task.GET_SYSTEM_REPORTS,Answer.WAIT_RESPONSE,branchAndDate);
		sendToClient(message);
		if(suppliers==null) {//server should respond by now, if no reports are found it is displayed
			setRelevantTextToDisplayMessageText("No reports found for that time period");
		}
		else {//otherwise primes report generator, and generates reports by selected type
			ReportGenerator.setSuppliers(suppliers);
			displaySingleReport(ReportGenerator.generateIncomeReport("by branch"));
		}
		}
	}
	
	public void displaySingleReport(String report) {
		DisplayHistogramReportController displayHistogramReportController=new DisplayHistogramReportController();
		displayHistogramReportController.initDisplayReportScreen(suppliers);
}
	public void initViewQuarterlyReportsScreen() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/CEO1ViewQuarterlyReportsScreen.fxml").openStream());
					viewQuarterlyReportsScreenController = loader.getController();
					Scene scene = new Scene(root);
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("View Reports");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ReportYear.setValue("Year");
		ReportQuarter.setValue("Quarter");
		 Year y = Year.now();
		for(int i=y.getValue();i>2000;i--) 
			ReportYear.getItems().add(""+i);
		for(int i=1;i<5;i++) {
			ReportQuarter.getItems().add(""+i);
	}
	}
	public boolean checkDate() {
		if(ReportYear.getValue().equals("Year")) {
			setRelevantTextToDisplayMessageText("Please Select A Year");
			return false;
		}
		else
		if(ReportQuarter.getValue().equals("Quarter")) {
			setRelevantTextToDisplayMessageText("Please Select A Quarter");
			return false;
		}
		return true;
	}
 	private void setRelevantTextToDisplayMessageText(String message) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				viewQuarterlyReportsScreenController.errorText.setText(message);
			}
		});
}
}

