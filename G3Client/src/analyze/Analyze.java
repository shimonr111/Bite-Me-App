package analyze;

import java.io.IOException;
import communication.Answer;
import communication.Message;
import communication.Task;

/**
 * @author Lior, Guzovsky.
 *  Class description: 
 *  This is a class which is a Wrapper
 *  for handeling all messages from the server.
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
			default:
				break;
			}
		}
	}
}
