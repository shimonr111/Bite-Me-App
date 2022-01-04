package controllers_gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.*;
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
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import util.SupplierByReport;

import org.apache.pdfbox.pdmodel.PDDocument;  
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


/**
 * Class description: 
 * This is a class for 
 * controlling the UI of viewing system reports 
 * form.
 */

/**
 * 
 * @author Alexander, Martinov.
 * 
 * @version 21/12/2021
 */
public class ViewSystemReportsScreenController extends AbstractBiteMeController implements Initializable{
	
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
	private static ViewSystemReportsScreenController viewSystemReportsScreenController;
	
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
     * ComboBox of monthes.
     */
    private ComboBox<String> ReportMonth;

    @FXML
    /**
     * ComboBox of report types.
     */
    private ComboBox<String> ReportType;

    @FXML
	/**
	 * ComboBox of years.
	 */
    private ComboBox<String> ReportYear;

    @FXML
    /**
     *  Back Button.
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
     * The text to display messages for the user.
     */
    private Text errorText;

    @FXML
    /**
     * Quarterly Button.
     */
    private Button quarterlyBtn;

    @FXML
    /**
     * View Report Button.
     */
    private Button viewReportBtn;

	/**
	 * this method calls the setBranchManagerPortal to get back the previous screen
	 * 
	 * @param event ActionEvent of javaFX.
	 */
	@FXML
	public void getBackBtn(ActionEvent event) {
		setBranchManagerPortal(event);
	}

	/**
	 * Clicking on exit button will log out the user then disconnect and exit.
	 * 
	 * @param event ActionEvent of javaFX
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
	 * @param event ActionEvent of javaFX
	 */
	@FXML
	public void getHelpBtn(ActionEvent event) {
		PopUpMessages.helpMessage("On this screen you can view system reports by selecting the time range and report type.");
	}
	
	/**
     * This method prepares a quarterly report request from the server
     * and calls a save to file function if a report was returned from the server
     * 
     * @param event ActionEvent of javaFX
     */
    @FXML
    public void getQuarterly(ActionEvent event) {
    		Year y = Year.now();
    		LocalDate myLocal = LocalDate.now();
    		String date="";
    		int quarter = myLocal.get(IsoFields.QUARTER_OF_YEAR);
    		switch(quarter) {
    		case 1:
    			date=""+(Integer.parseInt(y.toString())-1)+"-10-01";
    			break;
    		case 2:
    			date=""+y+"-01-01";
    			break;
    		case 3:
    			date=""+y+"-04-01";
    			break;
    		case 4:
    			date=""+y+"-07-01";
    			break;
    			default:
    				break;
    		}
			String[] branchAndDate=new String[3];
			branchAndDate[0]=connectedUser.getHomeBranch().toString();
			branchAndDate[1]=date; //prepare message to server, report date and branch
			branchAndDate[2]="quarterly";
			Message message = new Message (Task.GET_SYSTEM_REPORTS,Answer.WAIT_RESPONSE,branchAndDate);
			sendToClient(message);
			if(suppliers==null) {//server should respond by now, if no reports are found it is displayed
				setRelevantTextToDisplayMessageText("No quarterly reports found");
			}
			else {//otherwise primes report generator, and generates reports by selected type
				ReportGenerator.setSuppliers(suppliers);
				saveQuarterlyReport(date);
				setRelevantTextToDisplayMessageText("Report Generated");
				suppliers=null;
				}
    }
    
	/**
	 * Asks server for selected report by date and type and displays it to the user
	 * 
	 * @param event ActionEvent of javaFX
	 */
	@FXML
	public void getViewReport(ActionEvent event) {
		if(checkDateAndType()) {
			String date=ReportYear.getValue()+"-"+ReportMonth.getValue()+"-01";
			String[] branchAndDate=new String[3];
			branchAndDate[0]=connectedUser.getHomeBranch().toString();
			branchAndDate[1]=date; //prepare message to server, report date and branch
			branchAndDate[2]="monthly";
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
				suppliers=null;//flushes supplier reports list to receive next report
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
					viewSystemReportsScreenController.MessageLabel.setText(message);
				}
			});
	}
	 	
	 /**
     * Loads the previous screen after clicking on back button.
     * 
     * @param event ActionEvent of javaFX
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
	 * 
	 * @param arg0
	 * @param arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ReportYear.setValue("Year");
		ReportMonth.setValue("Month");
		ReportType.setValue("Report Type");
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
     * This method creates a pdf file based on a pulled quarterly report
     * then saves it to a filechooser picked location
     * 
     * @param date date of quarterly report pdf, needed for naming the file
     */
	public void saveQuarterlyReport(String date) {
		DecimalFormat twoPlacesDouble= new DecimalFormat("#0.00");
		String replacer;
		try {
		PDDocument report= new PDDocument();
		PDPage incomePage = new PDPage();
		PDPage orderPage = new PDPage();
		PDPage performancePage = new PDPage();
		report.addPage(incomePage);
		report.addPage(orderPage);
		report.addPage(performancePage);
		PDPageContentStream contentStream = new PDPageContentStream(report, incomePage);
		contentStream.beginText();
		contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
		contentStream.newLineAtOffset(25, 700);
		contentStream.showText("Quarterly Income Report By Supplier");
		contentStream.newLineAtOffset(0, -15);
		contentStream.showText("Issued on: "+suppliers[0].getIssueDate()+"");
		contentStream.newLineAtOffset(0, -15);
		for(SupplierByReport supplier:suppliers) {
			if(supplier!=null) {
			contentStream.newLineAtOffset(0, -15);
			contentStream.showText("Supplier ID: "+supplier.getSupplierId()+" Supplier Name: "+supplier.getSupplierName()+"");
			contentStream.newLineAtOffset(0, -15);
			contentStream.showText("Total Income: "+supplier.getIncome()+"");
			contentStream.newLineAtOffset(0, -15);
			contentStream.showText("BM cut in percentage: "+(twoPlacesDouble.format(supplier.getSupplierFee())));
			contentStream.newLineAtOffset(0, -15);
			contentStream.showText("Total net income of supplier: "+(twoPlacesDouble.format(calculateNetIncome(supplier))));
			contentStream.newLineAtOffset(0, -15);
			contentStream.showText("Total BM cut: "+(twoPlacesDouble.format(calculateBmCut(supplier))));
			contentStream.newLineAtOffset(0, -15);
			}
		}
	    contentStream.endText();
	    contentStream.close();
	    contentStream = new PDPageContentStream(report, orderPage);
	    contentStream.beginText();
		contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
		contentStream.newLineAtOffset(25, 700);
		contentStream.showText("Quarterly Order Report By Supplier");
		contentStream.newLineAtOffset(0, -15);
		contentStream.showText("Issued on: "+suppliers[0].getIssueDate()+"");
		contentStream.newLineAtOffset(0, -15);
		for(SupplierByReport supplier:suppliers) {
			if(supplier!=null) {
			contentStream.newLineAtOffset(0, -15);
			contentStream.showText("Supplier ID: "+supplier.getSupplierId()+" Supplier Name: "+supplier.getSupplierName()+"");
			contentStream.newLineAtOffset(0, -15);
			contentStream.showText("Salads Ordered: "+supplier.getTypeSums()[0]);
			contentStream.newLineAtOffset(0, -15);
			contentStream.showText("Opening Dishes Ordered: "+supplier.getTypeSums()[1]);
			contentStream.newLineAtOffset(0, -15);
			contentStream.showText("Main Dishes Ordered: "+supplier.getTypeSums()[2]);
			contentStream.newLineAtOffset(0, -15);
			contentStream.showText("Desserts Ordered: "+supplier.getTypeSums()[3]);
			contentStream.newLineAtOffset(0, -15);
			contentStream.showText("Drinks Ordered: "+supplier.getTypeSums()[4]);
			contentStream.newLineAtOffset(0, -15);
			}
		}  
	    contentStream.endText();
	    contentStream.close();
	    contentStream = new PDPageContentStream(report, performancePage);
	    contentStream.beginText();
		contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
		contentStream.newLineAtOffset(25, 700);
		contentStream.showText("Quarterly Performance Report By Supplier");
		contentStream.newLineAtOffset(0, -15);
		contentStream.showText("Issued on: "+suppliers[0].getIssueDate()+"");
		contentStream.newLineAtOffset(0, -15);
		for(SupplierByReport supplier:suppliers) {
			if(supplier!=null) {
			contentStream.newLineAtOffset(0, -15);
			contentStream.showText("Supplier ID: "+supplier.getSupplierId()+" Supplier Name: "+supplier.getSupplierName()+"");
			contentStream.newLineAtOffset(0, -15);
			contentStream.showText("Total Orders: "+supplier.getTotalOrders());
			contentStream.newLineAtOffset(0, -15);
			contentStream.showText("Late Orders: "+supplier.getLateOrders());
			contentStream.newLineAtOffset(0, -15);
			contentStream.showText("Late Orders percentage: "+(new DecimalFormat("#0.0").format((supplier.getLateOrders()/(double)supplier.getTotalOrders())*100)));
			contentStream.newLineAtOffset(0, -15);
			contentStream.showText("Average Supply Time: "+ supplier.getAverageSupplyTime());
			contentStream.newLineAtOffset(0, -15);
		}  
		}
	    contentStream.endText();
	    contentStream.close();
	    FileChooser fileChooser = new FileChooser();
	    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName(date+" Quarterly Report.pdf");
        Stage stage= new Stage();
        File directory = fileChooser.showSaveDialog(stage);
        if(directory!=null)
        report.save(directory); 
		report.close();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
    /**
     * calculates the bm cut of an income report for a supplier
     * @param supplier the supplier a cut is being calculated for
     * @return string representation of bm cut
     */
	private static double calculateBmCut(SupplierByReport supplier) {
		return (((supplier.getSupplierFee())/100))*Double.parseDouble(supplier.getIncome());
	}
    /**
     * calculates the net income for a supplier in an income report
     * @param supplier the supplier the net income is being calculated for
     * @return string representation of net income
     */
	private static double calculateNetIncome(SupplierByReport supplier) {
		return (1-((supplier.getSupplierFee())/100))*Double.parseDouble(supplier.getIncome());
	}
}

