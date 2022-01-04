package controllers_gui;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.SupplierByReport;

/**
 *  Class description: This is a class for displaying a quarterly CEO
 * report
 */

/**
 * 
 * @author Alexander, Martinov.
 * 
 * @version 29/12/2021
 */
public class DisplayHistogramReportController extends AbstractBiteMeController implements Initializable {

	/**
	 * Class members description:
	 */
	
	/**
	 * The FXMLLoader of the current screen.
	 */
	private static FXMLLoader loader;
	
	/**
	 * A static Stage of the current screen.
	 */
	private static Stage Stage;
	
	/**
	 * The FXMLLoader of the current screen.
	 */
	public static DisplayHistogramReportController displayHistogramReportController;
	
	/**
	 * Static list of suppliers that we get from DB.
	 */
	public static SupplierByReport[][] suppliers = null;

	@FXML
	/**
	 * The Anchor Pane .
	 */
	private AnchorPane anchorPane;

	@FXML
	/**
	 * The Exit Button.
	 */
	private Button exitBtn;

	@FXML
	/**
	 * The Category Axis.
	 */
	private CategoryAxis Category;

	@FXML
	/**
	 * The Number Axis.
	 */
	private NumberAxis Numbers;

	@FXML
	/**
	 * BarChart of Number and String.
	 */
	private BarChart<Number, String> reportHistogram;

	@FXML
	/**
	 * The text that we display the report name into it.
	 */
	private Text reportNameText;

	@FXML
	/**
	 * The second Number Axis.
	 */
	private NumberAxis Numbers1;

	@FXML
	/**
	 * The second Category Axis.
	 */
	private CategoryAxis Category1;

	@FXML
	/**
	 * BarChart of Number and String.
	 */
	private BarChart<Number, String> valueHistogram;

	@FXML
	/**
	 * The Text Area that we display the report into it.
	 */
	private TextArea reportTextField;
	
	/**
	 * This is the initialization function for this screen when switched to.
	 * @param report the reports that we get from the previous screen.
	 */
	public void initDisplayReportScreen(SupplierByReport[][] report) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				ScrollPane root;
				Stage = new Stage();
				suppliers = report;
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
					scene.getStylesheets()
							.add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("View Report");
					Stage.setScene(scene);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * clicking the exit button will close the currently viewed report
	 * 
	 * @param event ActionEvent of javaFX.
	 */
	@FXML
	public void getExitBtn(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
	}

	/**
	 * This method prepares a title, an orders histogram, 
	 * a monetary value histogram, and an income report, then it displays it
	 * 
	 * @param event ActionEvent of javaFX.
	 */
	public void showReport(String[] branchAndDate) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				String reportNameText = "";
				Calendar c = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					//prepares a title for the report
					c.setTime(sdf.parse(branchAndDate[1]));
					reportNameText = reportNameText + "Quarterly report ";
					reportNameText = reportNameText + c.get(Calendar.YEAR) + " Q" 
							+ (sdf.parse(branchAndDate[1]).getMonth() / 3 + 1);

				} catch (Exception e) {
					e.printStackTrace();
				}
				// gets an array with the orders amount and their values by month
				ReportGenerator.setQuarter(suppliers);
				ReportGenerator.setSuppliers(suppliers[3]);
				int[][] ordersAndValuesByMonth = ReportGenerator.getOrdersValue();
				//prepares two histograms
				XYChart.Series orders = new XYChart.Series<>();
				XYChart.Series value = new XYChart.Series<>();
				//when data is added, the month is converted from MM format, to month name (i.e January)
				orders.getData().add(new XYChart.Data<>(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH),
						ordersAndValuesByMonth[0][0]));
				value.getData().add(new XYChart.Data<>(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH),
						ordersAndValuesByMonth[0][1]));
				c.add(Calendar.MONTH, 1);
				orders.getData().add(new XYChart.Data<>(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH),
						ordersAndValuesByMonth[1][0]));
				value.getData().add(new XYChart.Data<>(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH),
						ordersAndValuesByMonth[1][1]));
				c.add(Calendar.MONTH, 1);
				orders.getData().add(new XYChart.Data<>(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH),
						ordersAndValuesByMonth[2][0]));
				value.getData().add(new XYChart.Data<>(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH),
						ordersAndValuesByMonth[2][1]));
				orders.setName("Orders");
				value.setName("Value in NIS");
				displayHistogramReportController.reportHistogram.getData().addAll(orders);
				displayHistogramReportController.valueHistogram.getData().addAll(value);
				if (branchAndDate[0].equals("NOT_APPLICABLE")) { 
					//calls report function with branch specification on print if all branches were requested
					displayHistogramReportController.reportTextField
							.setText(ReportGenerator.generateIncomeReport("by branch"));
					displayHistogramReportController.reportNameText.setText(reportNameText + " All branches");
				} else {
					displayHistogramReportController.reportTextField.setText(ReportGenerator.generateIncomeReport());
					displayHistogramReportController.reportNameText
							.setText(reportNameText + " " + branchAndDate[0].toLowerCase() + " branch");
				}
				displayHistogramReportController.Stage.show();
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
