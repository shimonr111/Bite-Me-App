package controllers_gui;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

import communication.Answer;
import communication.Message;
import communication.Task;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.SupplierByReport;

	public class DisplayHistogramReportController extends AbstractBiteMeController implements Initializable {

	    @FXML
	    private AnchorPane anchorPane;
	    @FXML
	    private Button exitBtn;
	    
	    @FXML
	    private CategoryAxis Category;

	    @FXML
	    private NumberAxis Numbers;

	    @FXML
	    private BarChart<Number, String> reportHistogram;

	    @FXML
	    private Text reportNameText;
	    
	    @FXML
	    private NumberAxis Numbers1;
	    
	    @FXML
	    private CategoryAxis Category1;
	    
	    @FXML
	    private BarChart<Number, String> valueHistogram;

	    @FXML
	    private TextArea reportTextField;
	    private static FXMLLoader loader;
	    private static Stage Stage;
	    public static DisplayHistogramReportController displayHistogramReportController;
	    public static SupplierByReport[][] suppliers=null;
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
		}
	 	public void initDisplayReportScreen(SupplierByReport[][] report) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				ScrollPane root;
				Stage = new Stage();
				suppliers=report;
					try {
						root = loader.load(getClass().getResource("/fxmls/CEO7DisplayHistogramReport.fxml").openStream());
					displayHistogramReportController = loader.getController();
					Scene scene = new Scene(root);
				scene.setOnMousePressed(pressEvent -> {
				    scene.setOnMouseDragged(dragEvent -> {
				    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
				    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
				    });
				});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("View Report");
					Stage.setScene(scene);
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		});
	}
	 	
	 	@FXML
		public void getExitBtn(ActionEvent event) {
	 		((Node) event.getSource()).getScene().getWindow().hide();
		}
	 	public void showReport(String[] branchAndDate) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					String reportNameText="";
					Calendar c= Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					try {
						
						c.setTime(sdf.parse(branchAndDate[1]));
						reportNameText =reportNameText+"Quarterly report ";
						reportNameText=reportNameText+c.get(Calendar.YEAR)+" Q"+(sdf.parse(branchAndDate[1]).getMonth()/3+1);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					ReportGenerator.setQuarter(suppliers);
					ReportGenerator.setSuppliers(suppliers[3]);
					int[][] chart=ReportGenerator.getOrdersValue();
					XYChart.Series orders= new XYChart.Series<>();
					XYChart.Series value= new XYChart.Series<>();
					orders.getData().add(new XYChart.Data<>(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH),chart[0][0]));
					value.getData().add(new XYChart.Data<>(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH),chart[0][1]));
					c.add(Calendar.MONTH,1);
					orders.getData().add(new XYChart.Data<>(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH),chart[1][0]));
					value.getData().add(new XYChart.Data<>(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH),chart[1][1]));
					c.add(Calendar.MONTH,1);
					orders.getData().add(new XYChart.Data<>(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH),chart[2][0]));
					value.getData().add(new XYChart.Data<>(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH),chart[2][1]));
					orders.setName("Orders");
					value.setName("Value in NIS");
					displayHistogramReportController.reportHistogram.getData().addAll(orders);
					displayHistogramReportController.valueHistogram.getData().addAll(value);
					if(branchAndDate[0].equals("NOT_APPLICABLE")) {
					displayHistogramReportController.reportTextField.setText(ReportGenerator.generateIncomeReport("by branch"));
					displayHistogramReportController.reportNameText.setText(reportNameText+" All branches");
					}
					else
					{
						displayHistogramReportController.reportTextField.setText(ReportGenerator.generateIncomeReport());
						displayHistogramReportController.reportNameText.setText(reportNameText+" "+branchAndDate[0].toLowerCase()+" branch");
					}
					displayHistogramReportController.Stage.show();
				}
			});
	 	}
	}

