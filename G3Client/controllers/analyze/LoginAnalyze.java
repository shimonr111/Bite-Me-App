package analyze;

import java.io.IOException;
import bitemeclient.BiteMeClientUI;
import bitemeclient.PopUpMessages;
import communication.Answer;
import communication.Message;
import communication.Task;
import gui.EntryIPConfirmationFormController;
import gui.EntryLoginScreenFormController;
import javafx.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import users.Login;

/**
 * @author Mousa, Srour.
 *
 * Class description:
 * This class gets the input from the login
 * form and analyzes it accordingly
 * @version 04/12/2021.
 */
public class LoginAnalyze extends AbstractBiteMeController {
	/**
	 * Class members description:
	 */

	/**
	 * this ActionEvent saves the Actions sent from the UI (its static because we work only with one at a time).
	 */
	private static ActionEvent event;
	/**
	 * Here we handle a user login  request by saving the event and check the input. 
	 * @param btnLogin: the event sent from the UI.
	 * @param login: login object to save the id and password in this phase.
	 */
	public static void userLoginRequest(ActionEvent btnLogin, Login login) {
		event = btnLogin;
		checkInputFromUser(login);
	}
	
	/**
	 * gets a login object, checks if the input was filled correctly 
	 * and then we send the object with the relevant task to the server.
	 * @param login: login object to save the id and password in this phase.
	 */
	public static void checkInputFromUser(Login login) {
		if(login.getUserName().equals("") || login.getPassword().equals("")) { // checking if the user didn't enter both username and password.
			PopUpMessages.missingFieldMessage("Please fill all the required fields (*)"); // displaying an error message using PopUpMessages.
		}
		// checking if the username and the password are exist on DB.
		else { 
			Message message = new Message (Task.LOGIN,Answer.WAIT_RESPONSE,login);
			SendToServer(message);
			
		}
	}
	


}
