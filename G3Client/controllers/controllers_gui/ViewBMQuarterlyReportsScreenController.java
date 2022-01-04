package controllers_gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.Year;
import java.util.ResourceBundle;
import bitemeclient.PopUpMessages;
import communication.Answer;
import communication.Message;
import communication.Task;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
/**
 * Class description: 
 * This is a class for 
 * controlling the UI of downloading quarterly BM report pdfs.
 * 
 * @author Alexander, Martinov.
 * 
 * @version 27/12/2021
 */
public class ViewBMQuarterlyReportsScreenController extends AbstractBiteMeController implements Initializable{
	
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
	private static ViewBMQuarterlyReportsScreenController viewBMQuarterlyReportsScreenController;
	
	/**
	 * The pdfList content (String).
	 */
	public static String[][] pdfList=null;
	
	/**
	 * The PDF files  (Bytes).
	 */
	public static byte[] pdfFile=null;

	@FXML
	/**
	 * The Quearter numbers combo box.
	 */
	private ComboBox<String> ReportQuarter;

	@FXML
	/**
	 * ComboBox of Years .
	 */
	private ComboBox<String> ReportYear;

    @FXML
    /**
     * Column of Branch.
     */
    private TableColumn<String[], String> branchColumn=new TableColumn();

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
     * Column of date.
     */
    private TableColumn<String[], String> dateColumn=new TableColumn();

    @FXML
    /**
     * Download Button.
     */
    private Button downloadBtn;

    @FXML
	/**
	 * The text that we use to display messages to the user.
	 */
    private Text errorText;
    
    @FXML
    /**
     * Column of File Name.
     */
    private TableColumn<String[], String> filenameColumn=new TableColumn();
    
    @FXML
    /**
     * the table view for PDF Files.
     */
    private TableView<String[]> pdfTable=new TableView();

    @FXML
    /**
     * The Text field that we use as a search bar.
     */
    private Button searchBtn;

	@FXML
	/**
	 * View Report Button.
	 */
	private Button viewReportBtn;
	
	 /**
     * This method returns to the previous screen when the back button is pressed
     * 
     * @param event ActionEvent of javaFX.
     */
	@FXML
	void getBackBtn(ActionEvent event) {
		setCeoPortal(event);
	}

	/**
	 * Clicking on exit button will log out the user then disconnect and exit.
	 * 
	 * @param event ActionEvent of javaFX.
	 */
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
	 * Display a help pop up message.
	 * 
	 * @param event ActionEvent of javaFX.
	 */
	@FXML
	void getHelpBtn(ActionEvent event) {
		PopUpMessages.helpMessage("On this screen you can view uploaded branch reports by selecting the time range and report type.");
	}

	 /**
     * This method requests a file from the db by it's unique file Id 
     * Then saves it to a location specified with a file chooser
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    void getReport(ActionEvent event) {
    	if(pdfTable.getSelectionModel().getSelectedItem()!=null) {
    	String[] selectedItem=null;
    	selectedItem=pdfTable.getSelectionModel().getSelectedItem();
    	//selectedItem[0] has the fileId, brought in from the db after loading the available files for that date
    	Message message = new Message (Task.GET_PDF_FILE,Answer.WAIT_RESPONSE,selectedItem[0]);
		sendToClient(message);
		if(pdfFile==null) {
			setRelevantTextToDisplayMessageText("No file found in system");
		}
		else {
			FileChooser fileChooser = new FileChooser();
		    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
	        fileChooser.getExtensionFilters().add(extFilter);
	        //selectedItem[1] has the file name of the selected file, sets it as the default name when downloading
	        fileChooser.setInitialFileName(selectedItem[1]);
	        Stage stage= new Stage();
	        try {
	        	File directory =fileChooser.showSaveDialog(stage);
	        	if(directory!=null)
				Files.write(directory.toPath(),pdfFile);
		        pdfFile=null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    	}
    }

    /**
     * This method prepares a file list request from the server
     * then it calls the table filling fuction, and clears the list for the next request
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    void searchReport(ActionEvent event) {
    	if(checkDate()) {
			pdfTable.getItems().clear();
    		String year = ReportYear.getValue();
    		String yearPlusOne =""+(Integer.parseInt(year)+1);
    		String datefrom ="";
    		String dateby ="";
    		String quarter = ReportQuarter.getValue();
    		switch(quarter) {
    		case "1":
    			datefrom=""+year+"-01-01";
    			dateby=""+year+"-04-01";
    			break;
    		case "2":
    			datefrom=""+year+"-04-01";
    			dateby=""+year+"-07-01";
    			break;
    		case "3":
    			datefrom=""+year+"-07-01";
    			dateby=""+year+"-10-01";
    			break;
    		case "4":
    			datefrom=""+year+"-10-01";
    			dateby=""+yearPlusOne+"-01-01";
    			break;
    			default:
    				break;
    		}
    		String[] branchAndDate=new String[3];
    		branchAndDate[0]="NOT_APPLICABLE";
    		branchAndDate[1]=datefrom; //prepare message to server, report date and branch
    		branchAndDate[2]=dateby;
    		Message message = new Message (Task.GET_PDF_LIST,Answer.WAIT_RESPONSE,branchAndDate);
    		sendToClient(message);
    		if(pdfList==null) {//server should respond by now, if no pdfs are found it is displayed
    			setRelevantTextToDisplayMessageText("No reports found for that time period");
    		}
    		else {
    			fillFoundReports();
    			setRelevantTextToDisplayMessageText("");
    			pdfList=null;
    		}
    		}
    }
    	
    /**
     * This method checks whether a year and a quarter were selected to pull reports from
     * 
     * @return boolean true if all fields were filled, false otherwise
     */
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
        /**
         * This method prepares an observable list for the table view of the files available in the server
         * then it sets it into the table
         * 
         */
    	public void fillFoundReports() {
    		ObservableList<String[]> pdfStringList = FXCollections.observableArrayList();
			for(String[] i : pdfList) {
				pdfStringList.add(i);
			}
			pdfTable.setItems(pdfStringList);
			
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
			viewBMQuarterlyReportsScreenController.errorText.setText(message);
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
	 * This is the initialization function for this 
	 * screen when switched to.
	 */
	public void initViewBMQuarterlyReportsScreen() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/CEO2ViewBMQuarterlyReportsScreen.fxml").openStream());
					viewBMQuarterlyReportsScreenController = loader.getController();
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
     * This method primes the year and quarter selection fields
     * then it sets the cell value factories for the name, branch and date columns
     * 
     * @param arg0
     * @param arg1
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ReportYear.setValue("Year");
		ReportQuarter.setValue("Quarter");
		 Year y = Year.now();
		for(int i=y.getValue();i>2000;i--) 
			ReportYear.getItems().add(""+i);
		for(int i=1;i<5;i++) {
			ReportQuarter.getItems().add(""+i);
	}
		//overrides the property value factory to set the correct string to the correct column
		//filename, branch and date are passed as a string array
		//filename is at index 1
		filenameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> row) {
		        String[] rowValues = row.getValue();
		        if (rowValues != null && rowValues.length>0) {
		            return new SimpleStringProperty(rowValues[1]);
		        } else {
		            return new SimpleStringProperty("");
		        }
		    }
		});
		//branch is at index 2
		branchColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> row) {
		        String[] rowValues = row.getValue();
		        if (rowValues != null && rowValues.length>0) {
		            return new SimpleStringProperty(rowValues[2]);
		        } else {
		            return new SimpleStringProperty("");
		        }
		    }
		});
		//date is at index 3
		dateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> row) {
		        String[] rowValues = row.getValue();
		        if (rowValues != null && rowValues.length>0) {
		            return new SimpleStringProperty(rowValues[3]);
		        } else {
		            return new SimpleStringProperty("");
		        }
		    }
		});
	}

}
