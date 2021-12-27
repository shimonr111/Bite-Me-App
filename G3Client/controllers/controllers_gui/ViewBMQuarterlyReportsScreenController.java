package controllers_gui;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import orders.Item;
import orders.ItemWithPicture;
/**
 * 
 * @author Alexander, Martinov
 * Class description: 
 * This is a class for 
 * controlling the UI of downloading quarterly BM report pdfs
 * 
 * @version 27/12/2021
 */
public class ViewBMQuarterlyReportsScreenController extends AbstractBiteMeController implements Initializable {
	/**
	 * Class members description:
	 */

	@FXML
	private ComboBox<String> ReportQuarter;

	@FXML
	private ComboBox<String> ReportYear;

    @FXML
    private TableColumn<String[], String> branchColumn=new TableColumn();

	@FXML
	private Button btnBack;

	@FXML
	private Button btnExit;

	@FXML
	private Button btnHelp;

    @FXML
    private TableColumn<String[], String> dateColumn=new TableColumn();

    @FXML
    private Button downloadBtn;

    @FXML
    private Text errorText;
    @FXML
    private TableColumn<String[], String> filenameColumn=new TableColumn();
    
    @FXML
    private TableView<String[]> pdfTable=new TableView();

    @FXML
    private Button searchBtn;

	@FXML
	private Button viewReportBtn;
	private static FXMLLoader loader;
	private static ViewBMQuarterlyReportsScreenController viewBMQuarterlyReportsScreenController;
	public static String[][] pdfList=null;
	public static byte[] pdfFile=null;
	@FXML
	void getBackBtn(ActionEvent event) {
		setCeoPortal(event);
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
		PopUpMessages.helpMessage("On this screen you can view uploaded branch reports by selecting the time range and report type.");
	}

    @FXML
    void getReport(ActionEvent event) {
    	if(pdfTable.getSelectionModel().getSelectedItem()!=null) {
    	String[] selectedItem=null;
    	selectedItem=pdfTable.getSelectionModel().getSelectedItem();
    	Message message = new Message (Task.GET_PDF_FILE,Answer.WAIT_RESPONSE,selectedItem[0]);
		sendToClient(message);
		if(pdfFile==null) {
			setRelevantTextToDisplayMessageText("No file found in system");
		}
		else {
			FileChooser fileChooser = new FileChooser();
		    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
	        fileChooser.getExtensionFilters().add(extFilter);
	        fileChooser.setInitialFileName(selectedItem[1]);
	        Stage stage= new Stage();
	        try {
				Files.write(fileChooser.showSaveDialog(stage).toPath(),pdfFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
	        pdfFile=null;
		}
    	}
    }

    @FXML
    void searchReport(ActionEvent event) {
    	if(checkDate()) {
			pdfTable.getItems().clear();
    		String y = ReportYear.getValue();
    		String yplusone =""+(Integer.parseInt(y)+1);
    		String datefrom ="";
    		String dateby ="";
    		String quarter = ReportQuarter.getValue();
    		switch(quarter) {
    		case "1":
    			datefrom=""+y+"-01-01";
    			dateby=""+y+"-04-01";
    			break;
    		case "2":
    			datefrom=""+y+"-04-01";
    			dateby=""+y+"-07-01";
    			break;
    		case "3":
    			datefrom=""+y+"-07-01";
    			dateby=""+y+"-10-01";
    			break;
    		case "4":
    			datefrom=""+y+"-10-01";
    			dateby=""+yplusone+"-01-01";
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
    	public void fillFoundReports() {
    		ObservableList<String[]> pdfStringList = FXCollections.observableArrayList();
			for(String[] i : pdfList) {
				pdfStringList.add(i);
			}
			pdfTable.setItems(pdfStringList);
			
    	}
    	/**
    	 *sets message to small text field on the bottom
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
     * loads the previous screen after clicking on back button.
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
	 * 
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
