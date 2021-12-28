package controllers_gui;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.SupplierByReport;

	public class DisplayHistogramReportController extends AbstractBiteMeController implements Initializable {

	    @FXML
	    private BarChart<Number, String> reportHistogram;

	    @FXML
	    private Label reportName;

	    @FXML
	    private TextArea reportTextField;
	    private static FXMLLoader loader;
	    private static Stage Stage;
	    public static DisplayHistogramReportController displayHistogramReportController;
	    public static SupplierByReport[] suppliers=null;
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			setRelevantTextToDisplayMessageText();
		}
	 	private void setRelevantTextToDisplayMessageText() {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					int totalOrders=0;
					int totalRevenue=0;
				    CategoryAxis yAxis = new CategoryAxis();
				    NumberAxis xAxis = new NumberAxis();
				    reportHistogram = new BarChart<Number, String>(xAxis, yAxis);
				    XYChart.Series series1 = new XYChart.Series();
				        xAxis.setLabel("Period");
				        ArrayList<String> months = new ArrayList<String>();
				        months.add("m1");
				        months.add("m2");
				        months.add("m3");
				        yAxis.setCategories(FXCollections.<String>observableArrayList(months));
				        xAxis.setLabel("Orders");
				        series1.setName("Orders");
				        for(SupplierByReport supplier: suppliers)
				        	series1.getData().add(new XYChart.Data(supplier.getTotalOrders(),supplier.getSupplierId()));
				        reportHistogram.getData().add(series1);
				}
			});
	}
	 	public void initDisplayReportScreen(SupplierByReport[] report) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				ScrollPane root;
				Stage = new Stage();
				Stage.setResizable(false);
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
	 	public void showReport() {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					displayHistogramReportController.Stage.show();;
				}
			});
	 	}
	}

