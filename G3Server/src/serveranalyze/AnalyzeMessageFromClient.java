package serveranalyze;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import communication.Answer;
import communication.Message;
import communication.Task;
import ocsf.server.ConnectionToClient;
import query.LoginQueries;

/**
 * @author Lior, Guzovsky.
 * @author Mousa, Srour.
 * @author Alexander, Martinov
 *
 *  Class description:
 *  This is a class which is a Wrapper for handling
 *  all messages from the client.
 * @version 06/12/2021
 */
public class AnalyzeMessageFromClient {
	/**
	 * Class members description:
	 */
	
	/**
	 * listener arrays for notifying relevant server gui classes of changes 
	 */
	private static final List<AnalyzeServerListener> serverListeners = new ArrayList<AnalyzeServerListener>();
	
	/**
	 * This is a function which analyzes all the messages from the client and than
	 * does logic accordingly.
	 * 
	 * @param message
	 * @param client
	 * @throws SQLException 
	 * @throws IOException
	 */
	public static Message analyzeMessageFromClient(Object message, ConnectionToClient client) throws SQLException{
		if (!(message instanceof Message)) {
			return null;
		} else {
			Message recivedMessageFromClient = (Message) message;
			Task recivedTaskFromClient = recivedMessageFromClient.getTask();
			Answer recievedAnswerFromClient = recivedMessageFromClient.getAnswer();
			/**
			 * Switch case for getting Message from client side.
			 */
			switch (recivedTaskFromClient) {
			case CONFIRM_IP:
				//changed controller call into listener notification
				for (AnalyzeServerListener listener : serverListeners) {
					listener.displayToGuiServerConsole("status: connected " + client.getInetAddress().getHostName() + "  "
							+ client.getInetAddress().getHostAddress());
				}
				recivedMessageFromClient.setAnswer(Answer.SUCCEED);
				break;
				
			 case LOGIN:
				recivedMessageFromClient = LoginQueries.createLoginMessageForServer(recivedMessageFromClient);
				break;
			 case LOGOUT:
				 LoginQueries.logOutUser(recivedMessageFromClient);
				 break;
			default:
				break;
			}
			return recivedMessageFromClient;
		}
	}

	/**
	 * add server listener to the array
	 * @param listener every listener is implemented in the class that's "listening"
	 */
	public static void addServerListener(AnalyzeServerListener listener) {
		serverListeners.add(listener);
	}
	
	/**
	 * This is a method for publishing messages to the 
	 * gui server console.
	 * 
	 * @param message
	 */
	public static void displayToMessageConsole(String message) {
		for(AnalyzeServerListener listener : serverListeners) {
			listener.displayToGuiServerConsole(message);
		}
	}

}
