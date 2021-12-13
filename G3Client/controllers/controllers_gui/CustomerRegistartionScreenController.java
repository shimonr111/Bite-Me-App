package controllers_gui;


import java.io.IOException;
import java.net.URL;

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
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * 
 * @author Mousa, Srour
 * Class description: 
 * This is a class for 
 * controlling the UI of CustomerResitrationScreen that appears immediately after clicking
 * on Customer registration from the branch portal.
 * form.
 * 
 * @version 10/12/2021
 */
public class CustomerRegistartionScreenController extends AbstractBiteMeController {
	    private static FXMLLoader loader;
	    private static CustomerRegistartionScreenController customerRegistrationScreenController;
	    
	    @FXML
	    private Button btnExit;

	    @FXML
	    private Button backBtn;

	    @FXML
	    private Button btnHelp;

	    @FXML
	    private Button privateCustomerBtn;

	    @FXML
	    private Button businessCustomerBtn;
	    


	    @FXML
	    void getBackBtn(ActionEvent event) {
	    	setBranchManagerPortal(event);
	    }

	    @FXML
	    void getBusinessCustomer(ActionEvent event) {
	    	((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
	    	BusinessCustomerRegistartionController BCRC = new BusinessCustomerRegistartionController();
	    	BCRC.initBusinessCustomerRegistrationScreen();
	    }

	    @FXML
	    void getExitBtn(ActionEvent event) {
	    	Message message = new Message(Task.LOGOUT,Answer.WAIT_RESPONSE,connectedUser);
			sendToClient(message);
			connectedUser = null;
			System.exit(0);
	    }

	    @FXML
	    void getHelpBtn(ActionEvent event) {
	    	PopUpMessages.helpMessage("Please choose which customer you want to register!");
	    }

	    @FXML
	    void getPrivateCustomer(ActionEvent event) {
	    	((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
	    	PrivateCustomerRegistartionController PCRC = new PrivateCustomerRegistartionController();
	    	PCRC.initPrivateCustomerRegistrationScreen();
	    }
	    
	    /**
	     * 
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
						Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
							@Override
							public void handle(WindowEvent event) { 
								event.consume();
								Stage.close();
							}
						});
						//scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
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
		 * 
		 * @param event
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
		 * 
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
			//scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
			primaryStage.setTitle("Main menu");
			primaryStage.setScene(scene);
			primaryStage.show();/* show the new screen */
		}

}
