package controllers_gui;

import java.io.IOException;
import bitemeclient.PopUpMessages;
import communication.Answer;
import communication.Message;
import communication.Task;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * Class description: 
 * This is a class for 
 * controlling the UI of CustomerResitrationScreen that appears immediately after clicking
 * on Customer registration from the branch portal.
 * form.
 */
/**
 * 
 * @author Mousa, Srour.
 * 
 * @version 10/12/2021
 */
public class CustomerRegistartionScreenController extends AbstractBiteMeController{
	
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
	    private static CustomerRegistartionScreenController customerRegistrationScreenController;
	    
	    @FXML
	    /**
	     * The Exit Button.
	     */
	    private Button btnExit;

	    @FXML
	    /**
	     * The Back Button.
	     */
	    private Button backBtn;

	    @FXML
	    /**
	     * The Help Button.
	     */
	    private Button btnHelp;

	    @FXML
	    /**
	     * The Private Customer Button.
	     */
	    private Button privateCustomerBtn;

	    @FXML
	    /**
	     * The Business Customer Button.
	     */
	    private Button businessCustomerBtn;
	    
	    /**
	     * Call the method that loads the previous screen.
	     * 
	     * @param event ActionEvent of javaFX.
	     */
	    @FXML
	    public void getBackBtn(ActionEvent event) {
	    	setBranchManagerPortal(event);
	    }

	    /**
	     * This method will run after clicking on the business
	     * button while selecting a row from the table,
	     * in this method we create a new object and we call 
	     * the init of the next screen.
	     * 
	     * @param event  ActionEvent of javaFX.
	     */
	    @FXML
	    public void getBusinessCustomer(ActionEvent event) {
	    	((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
	    	BusinessCustomerRegistartionController BCRC = new BusinessCustomerRegistartionController();
	    	BCRC.initBusinessCustomerRegistrationScreen();
	    }

	    /**
	     * This method will disconnect the client 
	     * then log out the connected user and exit.
	     * 
	     * @param event  ActionEvent of javaFX.
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
	     * This method will display a pop up message to the user.
	     * 
	     * @param event  ActionEvent of javaFX.
	     */
	    @FXML
	    public void getHelpBtn(ActionEvent event) {
	    	PopUpMessages.helpMessage("On this screen you can choose which customer you want to register.");
	    }

	    /**
	     * This method will run after clicking on the private
	     * button while selecting a row from the table,
	     * in this method we create a new object and we call 
	     * the Init of the next screen.
	     * 
	     * @param event ActionEvent of javaFX.
	     */
	    @FXML
	    public void getPrivateCustomer(ActionEvent event) {
	    	((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
	    	PrivateCustomerRegistartionController PCRC = new PrivateCustomerRegistartionController();
	    	PCRC.initPrivateCustomerRegistrationScreen();
	    }
	    
	    /**
	     * This method loads the current screen.
	     */
		public void initCustomerRegistrationScreen() {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					loader = new FXMLLoader();
					Pane root;
					try {
					//	primaryStage.hide(); 
						Stage Stage = new Stage();
						Stage.setResizable(false);
						root = loader.load(getClass().getResource("/fxmls/BM5CustomerRegistartionScreen.fxml").openStream());
						customerRegistrationScreenController = loader.getController();
						Scene scene = new Scene(root);
						Stage.initStyle(StageStyle.UNDECORATED);
						scene.setOnMousePressed(pressEvent -> {
						    scene.setOnMouseDragged(dragEvent -> {
						    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
						    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
						    });
						});
						scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
						Stage.setTitle("Customer registration");
						Stage.setScene(scene);
						Stage.show();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
		
		/**
		 * This method loads the branch manager portal screen ( previous screen)
		 * 
		 * @param event ActionEvent of javaFX.
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
		 * This method loads this screen after clicking back from next one.
		 */
		public void initPortalAgain() {
			loader = new FXMLLoader();
			Stage primaryStage = new Stage();
			Pane root = null;
			try {
				root = loader.load(getClass().getResource("/fxmls/BM5CustomerRegistartionScreen.fxml").openStream());
				customerRegistrationScreenController = loader.getController();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Scene scene = new Scene(root);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			scene.setOnMousePressed(pressEvent -> {
			    scene.setOnMouseDragged(dragEvent -> {
			    	primaryStage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
			    	primaryStage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
			    });
			});
			scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
			primaryStage.setTitle("Main menu");
			primaryStage.setScene(scene);
			primaryStage.show();/* show the new screen */
		}

}
