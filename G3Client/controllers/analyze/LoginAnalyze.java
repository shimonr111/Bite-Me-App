package analyze;

import java.io.IOException;

import bitemeclient.BiteMeClientUI;
import bitemeclient.PopUpMessages;
import communication.Answer;
import communication.Message;
import communication.Task;
import gui.EntryIPConfirmationFormController;
import gui.EntryLoginScreenFormController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import users.Login;

/**
 * 
 * @author Mousa, Srour
 * Class description:
 * This class gets the input from the login form and analyzes it accordingly
 * @version 04/12/2021
 */
public class LoginAnalyze extends AbstractBiteMeController {
	
	private static ActionEvent event;
	
	public static void userLoginRequest(ActionEvent btnLogin, Login login) {
		event = btnLogin;
		checkInputFromUser(login);
	}
	
	/**
	 * gets a login object, checks if the input was filled correctly 
	 * and then we send the object with the relevant task to the server.
	 * @param login
	 */
	public static void checkInputFromUser(Login login) {
		if(login.getUserName().equals("") && login.getPassword().equals("")) { // checking if the user didn't enter both username and password.
			PopUpMessages.missingFieldMessage("Please enter your username and passowrd"); // displaying an error message using PopUpMessages.
		}
		else if(login.getUserName().equals("")) { // checking if the username field is empty.
			PopUpMessages.missingFieldMessage("Please enter your username"); // displaying an error message using PopUpMessages.
		}
		else if(login.getPassword().equals("")) { // checking if the password field is empty.
			PopUpMessages.missingFieldMessage("Please enter your password");// displaying an error message using PopUpMessages.
		}
		// checking if the username and the password are exist on DB.
		else { 
			Message message = new Message (Task.LOGIN,Answer.WAIT_RESPONSE,login);
			SendToServer(message);
			
		}
	}
	


}
