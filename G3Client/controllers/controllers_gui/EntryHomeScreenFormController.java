package controllers_gui;

import bitemeclient.PopUpMessages;
import communication.Answer;
import communication.Message;
import communication.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 * Class description: 
 * This class present the Home Screen and 
 * responsible for the functionality of this screen 
 * 
 * @author Shimon Rubin
 *
 * @version 02/12/2021
 */

public class EntryHomeScreenFormController extends AbstractBiteMeController{
	
	/**
	 * Class members description:
	 */
	
	/**
	 * A private appearance of the current class.
	 */
	private LoginScreenController entryLoginScreenFormController = new LoginScreenController();
	
	@FXML
	/**
	 * The Enter Button.
	 */
	private Button btnEnter = null;
	
	@FXML
	private Button importBtn = null;
	
	@FXML
	/**
	 * The Exit Button.
	 */
	private Button btnExit = null;
	
	@FXML
	/**
	 * The Help Button. 
	 */
	private Button btnHelp = null;

	/**
	 * Using enter button hide 
	 * window and go login 
	 * screen 
	 * 
	 * @param event ActionEvent of javaFX.
	 * @throw Exception
	 */
	public void getEnterBtn(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		entryLoginScreenFormController.initLoginScreen();
	}

	/**
	 * Using help button in order 
	 * to show instructions for the 
	 * current screen 
	 * 
	 * @param event  ActionEvent of javaFX.
	 * @throw Exception
	 */
	public void getHelpBtn(ActionEvent event) throws Exception {
		PopUpMessages.helpMessage("This is Bite-Me system welcome screen.\nPress 'I am Hungry' to proceed to the login screen.");
	}
	
	/**
	 * Using exit button 
	 * in order to exit 
	 * from the App .
	 * 
	 * @param event  ActionEvent of javaFX.
	 * @throw Exception
	 */
	public void getExitBtn(ActionEvent event) throws Exception {
		Message message= new Message(Task.CLIENT_DICONNECT,Answer.WAIT_RESPONSE,null);
		sendToClient(message);
		System.exit(0);
	}
	
}

