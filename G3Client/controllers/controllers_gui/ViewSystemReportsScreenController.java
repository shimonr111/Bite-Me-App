package controllers_gui;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Map.Entry;

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
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import util.SupplierByReport;

/**
 * 
 * @author Alexander, Martinov
 * Class description: 
 * This is a class for 
 * controlling the UI of viewing system reports 
 * form.
 * 
 * @version 21/12/2021
 */
public class ViewSystemReportsScreenController extends AbstractBiteMeController implements Initializable {
	
	/**
	 * Class members description:
	 */
	@FXML
    private Label MessageLabel;

    @FXML
    private ComboBox<String> ReportMonth;

    @FXML
    private ComboBox<String> ReportType;

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
    private Button quarterlyBtn;

    @FXML
    private Button viewReportBtn;


	private static FXMLLoader loader;
	private static ViewSystemReportsScreenController viewSystemReportsScreenController;
	public static SupplierByReport[] suppliers=null;

	/**
	 * this method calls the setBranchManagerPortal to get back the previous screen
	 * this method works immedietly after clicking on back button.
	 * @param event
	 */
	@FXML
	void getBackBtn(ActionEvent event) {
		setBranchManagerPortal(event);
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

	@FXML
	void getHelpBtn(ActionEvent event) {
		PopUpMessages.helpMessage("Select a date and a type to view a report");
	}
	/**
	 * Asks server for selected report by date and type and displays it to the user
	 */
	@FXML
	void getViewReport(ActionEvent event) {
		if(checkDateAndType()) {
			String date=ReportYear.getValue()+"-"+ReportMonth.getValue()+"-01";
			String[] branchAndDate=new String[2];
			branchAndDate[0]=connectedUser.getHomeBranch().toString();
			branchAndDate[1]=date; //prepare message to server, report date and branch
			Message message = new Message (Task.GET_SYSTEM_REPORTS,Answer.WAIT_RESPONSE,branchAndDate);
			sendToClient(message);
			if(suppliers==null) {//server should respond by now, if no reports are found it is displayed
				setRelevantTextToDisplayMessageText("No reports found for that time period");
			}
			else {//otherwise primes report generator, and generates reports by selected type
				ReportGenerator.setSuppliers(suppliers);
				String type=ReportType.getValue();
				switch(type) {
				case "Incomes":
					PopUpMessages.helpMessage(ReportGenerator.generateIncomeReport());
					setRelevantTextToDisplayMessageText("");
					//System.out.print(ReportGenerator.generateIncomeReport());
					suppliers=null;//flushes supplier reports list to recieve next report
				break;
				case "Orders":
					PopUpMessages.helpMessage(ReportGenerator.generateOrderReport());
					setRelevantTextToDisplayMessageText("");
					//System.out.print(ReportGenerator.generateOrderReport());
					suppliers=null;
					break;
				case "Performance":
					PopUpMessages.helpMessage(ReportGenerator.generatePerformanceReport());
					setRelevantTextToDisplayMessageText("");
					//System.out.print(ReportGenerator.generatePerformanceReport());
					suppliers=null;
					break;
				default:
					suppliers=null;
						break;
				}
			}
		}
	}
	/**
	 *Checks year, month and report type fields were filled(selected)
	 */
	public boolean checkDateAndType() {
		if(ReportYear.getValue().equals("Year")) {
			setRelevantTextToDisplayMessageText("Please Select A Year");
			return false;
		}
		else
		if(ReportMonth.getValue().equals("Month")) {
			setRelevantTextToDisplayMessageText("Please Select A Month");
			return false;
		}
		else
		if(ReportType.getValue().equals("Report Type")) {
			setRelevantTextToDisplayMessageText("Please Select A Report Type");
			return false;
		}
		return true;
	}
	/**
	 *sets message to small text field on the bottom
	 */
	 	private void setRelevantTextToDisplayMessageText(String message) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					viewSystemReportsScreenController.MessageLabel.setText(message);
				}
			});
			
		
	}
	  /**
     * loads the previous screen after clicking on back button.
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
	 * This is the priming function for the fields
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ReportYear.setValue("Year");
		ReportMonth.setValue("Month");
		ReportType.setValue("Report Type");
		 Year y = Year.now();
		for(int i=2000;i<=y.getValue();i++) 
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
	public void initViewSystemReportsScreen() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/BM1ViewSystemReportsScreen.fxml").openStream());
					viewSystemReportsScreenController = loader.getController();
					Scene scene = new Scene(root);
					Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) { 
							event.consume();
							Stage.close();
						}
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

}


