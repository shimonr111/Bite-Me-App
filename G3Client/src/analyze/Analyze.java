package analyze;

import java.io.IOException;
import communication.Answer;
import communication.Message;
import communication.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Lior, Guzovsky.
 * @author Mousa, Srour.
 *
 * Class description:
 * This is a class which is a Wrapper
 * for handeling all messages from the server.
 * @version 03/12/2021
 */
public class Analyze {
	/**
	 * Class members description:
	 */

	/**
	 * This is a function which analyzes all the messages from the server and than
	 * does logic accordingly.
	 * 
	 * @param message
	 * @throws IOException
	 */
	public static void analyzeMessageFromServer(Object message) throws IOException {
		if (!(message instanceof Message)) {
			return;
		} else {
			Message recivedMessageFromServer = (Message) message;
			Task recivedTaskFromServer = recivedMessageFromServer.getTask();
			Answer recievedAnswerFromServer = recivedMessageFromServer.getAnswer();
			switch (recivedTaskFromServer) {
			case CONFIRM_IP:
				switch (recievedAnswerFromServer) {
				case SUCCEED:
					IPConfirmationAnalyze.userIPRecievedConfirmationSucceeded();
					break;
				case ERROR_FAILED:
					IPConfirmationAnalyze.userIPRecievedConfirmationFailed();
					break;
				default:
					break;
				}
				/* TBD: @Alexander to add listneres and open all the nessacry userportal */
			case CREATE_USER_PORTAL:
				switch (recievedAnswerFromServer) {
				case CREATE_USER_PORTAL_FOR_CUSTOMER:
					System.out.println(((Message) message).getObject().toString());
					break;
				case CREATE_USER_PORTAL_FOR_HR_MANAGER:
					System.out.println(((Message) message).getObject().toString());
					break;
				case CREATE_USER_PORTAL_FOR_BUSINESS_CUSTOMER:
					System.out.println(((Message) message).getObject().toString());
					break;
				case CREATE_USER_PORTAL_FOR_CEO_BITE_ME:
					System.out.println(((Message) message).getObject().toString());
					break;
				case CREATE_USER_PORTAL_FOR_SUPPLIER:
					System.out.println(((Message) message).getObject().toString());
					break;
				default:
					break;
				}
				/* TBD: @Alexander to add listneres and print to screen*/
			case PRINT_ERROR_TO_SCREEN:
				switch(recievedAnswerFromServer) {
				case ERROR_USER_NOT_FOUND:
					System.out.println("user not found");
					break;
				case ERROR_ALREADY_LOGGED_IN:
					System.out.println("user already logged in");
					break;
				case ERROR_USER_NOT_CONFIRMED:
					System.out.println("user not confirmed");
				case ERROR_WRONG_PASSWORD:
					System.out.println("Wrong password");
					default:
						break;
				}
			default:
				break;
			}
		}

	}
}
