package controllers_gui;

import java.io.IOException;
import java.net.URL;
import java.time.Year;
import java.util.ResourceBundle;
import bitemeclient.PopUpMessages;
import communication.Answer;
import communication.Message;
import communication.Task;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.SupplierByReport;
/**
* Class description: 
* This is a class for 
* controlling the UI of viewing quarterly reports 
* form.
 */
/**
* @author Alexander, Martinov.
* 
* @version 14/12/2021
*/
public class ViewQuarterlyReportsScreenController extends AbstractBiteMeController implements Initializable{
	
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
	private static ViewQuarterlyReportsScreenController viewQuarterlyReportsScreenController;
	
	/**
	 * The suppliers reports that we get from DB.
	 */
	public static SupplierByReport[][] suppliers=null;
	
    @FXML
    /**
     * ComboBox for Quarter number.
     */
    private ComboBox<String> ReportQuarter;

    @FXML
    /**
     * ComboBox  for Year.
     */
    private ComboBox<String> ReportYear;

    @FXML
    /**
     * ComboBox of Branches.
     */
    private ComboBox<String> ReportBranch;
    
	@FXML
	/**
	 * Back Button.
	 */
	private Button btnBack;

	@FXML
	/**
	 * Exit Button.
	 */
	private Button btnExit;

	@FXML
	/**
	 * Help Button.
	 */
	private Button btnHelp;

	@FXML
	/**
	 * The text that we use to display messages to the user.
	 */
	private Text errorText;

	@FXML
	/**
	 * View Report Button.
	 */
	private Button viewReportBtn;

	/**
     * Loads the previous screen after clicking on back button.
     * 
     * @param event ActionEvent of javaFX.
     */
	@FXML
	public void getBackBtn(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		UserPortalOfCEOController UPCC = new UserPortalOfCEOController();
		UPCC.initCEOUserPortalAgain();
	}

	/**
	 * Clicking on exit button will log out the user then disconnect and exit.
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
	 * This is pop message for the help button.
	 * 
	 * @param event ActionEvent of javaFX.
	 */
	@FXML
	public void getHelpBtn(ActionEvent event) {
		PopUpMessages.helpMessage("On this screen you can view system reports by selecting the year and quarter.");
	}

	 /**
     * This method prepares a report request message
     * based on the requested branch and report year and quarter
     * 
     * @param event ActionEvent of javaFX.
     */
	@FXML
	public void getReport(ActionEvent event) {
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
		branchAndDate[0]=getBranch();
		branchAndDate[1]=date; //prepare message to server, report date and branch
		branchAndDate[2]="quarterly";//report type
		Message message = new Message (Task.GET_SYSTEM_REPORTS,Answer.WAIT_RESPONSE,branchAndDate);
		sendToClient(message);
		if(suppliers==null) {//server should respond by now, if no reports are found it is displayed
			setRelevantTextToDisplayMessageText("No reports found for that time period");
		}
		else {//otherwise primes report generator, and generates reports by selected type
			if((suppliers[0]!=null||suppliers[1]!=null||suppliers[2]!=null)&&suppliers[3]!=null) {
			displaySingleReport(branchAndDate);
			suppliers=null;
			}
			else
				setRelevantTextToDisplayMessageText("No reports found for that time period");
		}
		}
	}
	
	 /**
     * This method passes the report list to the display screen, and initializes it
     * 
     * @param branchAndDate string for specifying report year, quarter and branch
     */
	public void displaySingleReport(String[] branchAndDate) {
		DisplayHistogramReportController displayHistogramReportController=new DisplayHistogramReportController();
		displayHistogramReportController.initDisplayReportScreen(suppliers);
		displayHistogramReportController.showReport(branchAndDate);
}
	
	/**
	 * This is the initialization function for this 
	 * screen when switched to.
	 */
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

	 /**
     * This method....
     * 
     * @param location
     * @param recources
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ReportYear.setValue("Year");
		ReportQuarter.setValue("Quarter");
		ReportBranch.setValue("Branch");
		ReportBranch.getItems().addAll(getBranches());
		 Year y = Year.now();
		for(int i=y.getValue();i>2000;i--) 
			ReportYear.getItems().add(""+i);
		for(int i=1;i<5;i++) {
			ReportQuarter.getItems().add(""+i);
	}
	}
	
	 /**
     * This method checks whether a year, a quarter and a branch were selected to pull reports from
     * 
     * @return boolean true if all fields were selected, false otherwise
     */
	public boolean checkDate() {
		if(ReportYear.getValue().equals("Year")) {
			setRelevantTextToDisplayMessageText("Please fill all the required fields (*)!");
			return false;
		}
		else
		if(ReportBranch.getValue().equals("Branch")) {
			setRelevantTextToDisplayMessageText("Please fill all the required fields (*)!");
			return false;
		}
		else
		if(ReportQuarter.getValue().equals("Quarter")) {
			setRelevantTextToDisplayMessageText("Please fill all the required fields (*)!");
			return false;
		}
		return true;
	}
	
	/**
	 *Sets message to small text field on the bottom
	 *
	 *@param message string to be displayed
	 */
 	private void setRelevantTextToDisplayMessageText(String message) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				viewQuarterlyReportsScreenController.errorText.setText(message);
			}
		});
}
 	
 	 /**
     * This method sets the branch string for the message based on the combo box input
     * 
     * @return String message format of combo box selection
     */
	public String getBranch(){
		String branch=ReportBranch.getValue();
		switch (branch) {
		case "North":
			return "NORTH";
		case "Center":
			return "CENTER";
		case "South":
			return "SOUTH";
		case "All":
			return "NOT_APPLICABLE";
			default:
				return "";
		}
	}
	
	 /**
     * This method prepares a string array for the branch selection combo box
     * 
     * @return String[] string array with all branches 
     */
	public String[] getBranches() {
		String[] branches= new String[4];
		branches[0]="North";
		branches[1]="Center";
		branches[2]="South";
		branches[3]="All";
		return branches;
	}
}

