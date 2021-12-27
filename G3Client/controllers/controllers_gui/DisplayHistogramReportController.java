package controllers_gui;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.SupplierByReport;

	public class DisplayHistogramReportController extends AbstractBiteMeController implements Initializable {

	    @FXML
	    private BarChart<Number, String> orderHistogram;
	    private static FXMLLoader loader;
	    private static Stage Stage;
	    public static DisplayHistogramReportController displayHistogramReportController;
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
//			setRelevantTextToDisplayMessageText(report);
		}
	 	private void setRelevantTextToDisplayMessageText(SupplierByReport[] message) {
//			Platform.runLater(new Runnable() {
//				@Override
//				public void run() {
//					Stage = new Stage();
//					Stage.setResizable(false);
//				    CategoryAxis yAxis = new CategoryAxis();
//				    NumberAxis xAxis = new NumberAxis();
//				    orderHistogram = new BarChart<Number, String>(xAxis, yAxis);
//				    XYChart.Series series1 = new XYChart.Series();
//				        xAxis.setLabel("Restaurant");
//				        ArrayList<String> names = new ArrayList<String>();
//				        for(SupplierByReport supplier: report)
//				        	names.add(supplier.getSupplierId());
//				        yAxis.setCategories(FXCollections.<String>observableArrayList(names));
//				        xAxis.setLabel("Orders");
//				        xAxis.setTickLabelRotation(90);
//				        series1.setName("Orders");
//				        for(SupplierByReport supplier: report)
//				        	series1.getData().add(new XYChart.Data(supplier.getTotalOrders(),supplier.getSupplierId()));
//				        orderHistogram.getData().add(series1);
//				        Scene scene = new Scene(orderHistogram, 800, 600);
//				        Stage.setScene(scene);
//				}
//			});
	}
	 	public void initDisplayReportScreen(SupplierByReport[] report) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				Stage = new Stage();
				Stage.setResizable(false);
//					DisplayHistogramReportController displayHistogramReportController=new DisplayHistogramReportController();
//					root = loader.load(getClass().getResource("/fxmls/CEO7DisplayHistogramReport.fxml").openStream());
//					displayHistogramReportController.displayHistogramReportController = loader.getController();
//					Scene scene = new Scene(root);
//				Stage.initStyle(StageStyle.UNDECORATED);
//				scene.setOnMousePressed(pressEvent -> {
//				    scene.setOnMouseDragged(dragEvent -> {
//				    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
//				    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
//				    });
//				});
//					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
//					Stage.setTitle("View Report");
				 CategoryAxis yAxis = new CategoryAxis();
				    NumberAxis xAxis = new NumberAxis();
				    orderHistogram = new BarChart<Number, String>(xAxis, yAxis);
				    XYChart.Series series1 = new XYChart.Series();
				    XYChart.Series series2 = new XYChart.Series();
				        xAxis.setLabel("Restaurant");
				        ArrayList<String> names = new ArrayList<String>();
				        for(SupplierByReport supplier: report)
				        	names.add(supplier.getSupplierId());
				        yAxis.setCategories(FXCollections.<String>observableArrayList(names));
				        xAxis.setLabel("Orders");
				        xAxis.setTickLabelRotation(90);
				        series1.setName("Orders");
				        series2.setName("Total income");
				        for(SupplierByReport supplier: report) {
				        	series1.getData().add(new XYChart.Data(supplier.getTotalOrders(),supplier.getSupplierId()));
				        	series2.getData().add(new XYChart.Data((int)Double.parseDouble(supplier.getIncome()),supplier.getSupplierId()));
				        }
				        orderHistogram.getData().addAll(series1,series2);
				        Scene scene = new Scene(orderHistogram, 800, 600);
				Stage.setScene(scene);
				Stage.show();
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

