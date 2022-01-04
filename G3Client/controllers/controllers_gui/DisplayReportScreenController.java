package controllers_gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Class description: 
 * This is a class for displaying a system report in text format
 */

/**
 * 
 * @author Alexander, Martinov.
 * 
 * @version 26/12/2021
 */
public class DisplayReportScreenController extends AbstractBiteMeController implements Initializable  {

	    /**
	     * Class members description:
	     */
	
		/**
		 * The String that we put the report into it.
		 */
	    private static String report;
	    
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
	    public static DisplayReportScreenController displayReportScreenController;
	    
	    @FXML
	    /**
	     * The Text Field .
	     */
	    private TextArea TextField;
	    
	    @FXML
	    /**
	     * The Exit Button.
	     */
		private Button btnExit = null;
		
		
		/**
	     * setter for the report to be displayed.
	     * 
	     * @param report report in string format.
	     */
		public static void setReport(String report) {
			DisplayReportScreenController.report = report;
		}
		
		/**
	     * Screen initialization method
	     * calls report text setter.
	     * 
	     * @param arg0
	     * @param arg1
	     */
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			setReportInTextField(report);
		}
		
		/**
	     * Sets the text field to the passed report string
	     * 
	     * @param message string message to be set
	     */
	 	private void setReportInTextField(String message) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					displayReportScreenController.TextField.setText(message);
				}
			});
	}
	 	
		/**
		 * This is the initialization method for the report screen
		 * 
		 */
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
	 	
	 	/**
	     * The setting and showing of a report are split so that
	     * a report is only shown after its set
	     */
	 	public void showReport() {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					displayReportScreenController.Stage.show();;
				}
			});
	 	}
	 	
	 	/**
	     * This method closes the current report display screen
	     * 
	     * @param event ActionEvent of javaFX.
	     * @throw Exception
	     */
		public void getExitBtn(ActionEvent event) throws Exception {
			((Node) event.getSource()).getScene().getWindow().hide();
		}
	}
	


