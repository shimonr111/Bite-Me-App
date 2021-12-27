package controllers_gui;

	import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

	public class DisplayReportScreenController extends AbstractBiteMeController implements Initializable  {

	    @FXML
	    private TextArea TextField;
	    private static String report;
	    private static FXMLLoader loader;
	    private static Stage Stage;
	    public static DisplayReportScreenController displayReportScreenController;
		public static String getReport() {
			return report;
		}
		public static void setReport(String report) {
			DisplayReportScreenController.report = report;
		}
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			setRelevantTextToDisplayMessageText(report);
			
		}
	 	private void setRelevantTextToDisplayMessageText(String message) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					displayReportScreenController.TextField.setText(message);
				}
			});
	}
	 	public void initDisplayReportScreen() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
					Stage = new Stage();
					Stage.setResizable(false);
					DisplayReportScreenController displayReportScreenController=new DisplayReportScreenController();
					root = loader.load(getClass().getResource("/fxmls/BM15DisplaySingleReportScreen.fxml").openStream());
					displayReportScreenController.displayReportScreenController = loader.getController();
					Scene scene = new Scene(root);
					Stage.initStyle(StageStyle.UNDECORATED);
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
					displayReportScreenController.Stage.show();;
				}
			});
	 	}
	}

