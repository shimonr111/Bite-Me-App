package controllers_gui;

import java.io.IOException;
import java.net.URL;
import java.time.*;
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
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import util.SupplierByReport;
 
/**
 *  * Class description: 
 * This is a class for 
 * controlling the UI of viewing system reports 
 * form.
 * 
 * @author Alexander, Martinov.
 * 
 * @version 21/12/2021
 */
public class ViewSystemReportsScreenCEOController extends AbstractBiteMeController implements Initializable{
	
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
	private static ViewSystemReportsScreenCEOController viewSystemReportsScreenCEOController;
	
	/**
	 * The List Of suppliers Report that we get from DB.
	 */
	public static SupplierByReport[] suppliers=null;
	
	@FXML
	/**
	 * The Message Label.
	 */
    private Label MessageLabel;

    @FXML
	/**
	 * ComboBox of Months.
	 */
    private ComboBox<String> ReportMonth;

    @FXML
    /**
	 * ComboBox of Report types.
	 */
    private ComboBox<String> ReportType;

    @FXML
	/**
	 * ComboBox of Years.
	 */
    private ComboBox<String> ReportYear;
    
    @FXML
    /**
     * Combo Box if branches.
     */
    private ComboBox<String> branchBox;

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
     * View Report Button.
     */
    private Button viewReportBtn;

	/**
	 * This method calls the setBranchManagerPortal to get back the previous screen
	 * 
	 * @param event ActionEvent of javaFX.
	 */
	@FXML
	public void getBackBtn(ActionEvent event) {
		setCeoPortal(event);
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
		PopUpMessages.helpMessage("On this screen you can view system reports by selecting the time range and report type.");
	}
	
	/**
	 * Asks server for selected report by date and type and displays it to the user
	 * 
	 * @param event ActionEvent of javaFX.
	 */
	@FXML
	public void getViewReport(ActionEvent event) {
		if(checkDateAndType()) {
			String date=ReportYear.getValue()+"-"+ReportMonth.getValue()+"-01";
			String[] branchAndDate=new String[3];
			branchAndDate[0]=getBranch();
			branchAndDate[1]=date; //prepare message to server, report date and branch
			branchAndDate[2]="monthly";
			Message message = new Message (Task.GET_SYSTEM_REPORTS,Answer.WAIT_RESPONSE,branchAndDate);
			sendToClient(message);
			if(suppliers==null) {//server should respond by now, if no reports are found it is displayed
				setRelevantTextToDisplayMessageText("No reports found for that time period");
			}
			else {//otherwise primes report generator, and generates reports by selected type
				ReportGenerator.setSuppliers(suppliers);
				if(branchBox.getValue().equals("All"))
					displayReport("All");
				else displayReport("One");
			}
		}
	}
	
	/**
	 *Checks year, month and report type fields were filled(selected)
	 *
	 *@return boolean true if all fields were selected, false otherwise
	 */
	public boolean checkDateAndType() {
		if(ReportYear.getValue().equals("Year")) {
			setRelevantTextToDisplayMessageText("Please fill all the required fields (*)!");
			return false;
		}
		else
		if(ReportMonth.getValue().equals("Month")) {
			setRelevantTextToDisplayMessageText("Please fill all the required fields (*)!");
			return false;
		}
		else
		if(branchBox.getValue().equals("Branch")) {
			setRelevantTextToDisplayMessageText("Please fill all the required fields (*)!");
			return false;
		}
		else
		if(ReportType.getValue().equals("Report Type")) {
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
					viewSystemReportsScreenCEOController.MessageLabel.setText(message);
				}
			});	
	}
	 	
	 /**
     * Loads the previous screen after clicking on back button.
     * 
     * @param event ActionEvent of javaFX.
     */
	public void setCeoPortal(ActionEvent event) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				FXMLLoader loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/UserPortalOfCEO.fxml").openStream());
					UserPortalOfCEOController userPortalOfCEOController = new UserPortalOfCEOController();
					userPortalOfCEOController.initCEOUserPortalAgain();
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
	 * This is the priming function for the fields
	 * 
	 * @param arg0
	 * @param arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ReportYear.setValue("Year");
		ReportMonth.setValue("Month");
		ReportType.setValue("Report Type");
		branchBox.setValue("Branch");
		branchBox.getItems().addAll(getBranches());
		 Year y = Year.now();
		for(int i=y.getValue();i>2000;i--) 
			ReportYear.getItems().add(""+i);
		for(int i=1;i<13;i++) {
			if(i<10)
			ReportMonth.getItems().add("0"+i);
			else
				ReportMonth.getItems().add(""+i);
		}
		ReportType.getItems().addAll(ReportGenerator.getReportTypes());
		
	}
	/**
	 * This is the initialization function for this 
	 * screen when switched to.
	 * 
	 */
	public void initViewSystemReportsCEOScreen() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/CEO6ViewSystemReportsCEOScreen.fxml").openStream());
					viewSystemReportsScreenCEOController = loader.getController();
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
     * This method primes the report display screen and initializes it
     * 
     * @param report string to be shown in report field
     */
	public void displaySingleReport(String report) {
		DisplayReportScreenController displayReportScreenController=new DisplayReportScreenController();
		displayReportScreenController.initDisplayReportScreen();
		displayReportScreenController.setReport(report);
		displayReportScreenController.showReport();
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
	
	 /**
     * This method sets the branch string for the message based on the combo box input
     * 
     * @return String message format of combo box selection
     */
	public String getBranch(){
		String branch=branchBox.getValue();
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
     * This method generates the appropriate report, and calls the display function
     * 
     */
	public void displayReport(String reportBranches) {
	String type=ReportType.getValue();
	//if all branches were selected, includes supplier branch in report
	if(reportBranches.equals("All")) {
	switch(type) {
	case "Incomes":
		displaySingleReport(ReportGenerator.generateIncomeReport("by branch"));
	break;
	case "Orders":
		displaySingleReport(ReportGenerator.generateOrderReport("by branch"));
		break;
	case "Performance":
		displaySingleReport(ReportGenerator.generatePerformanceReport("by branch"));
		break;
	default:
			break;
	}
	}
	else {
		switch(type) {
		case "Incomes":
			displaySingleReport(ReportGenerator.generateIncomeReport());
		break;
		case "Orders":
			displaySingleReport(ReportGenerator.generateOrderReport());
			break;
		case "Performance":
			displaySingleReport(ReportGenerator.generatePerformanceReport());
			break;
		default:
				break;
	}
	setRelevantTextToDisplayMessageText("");
	suppliers=null;//flushes supplier reports list to recieve next report
	}
	}
}


