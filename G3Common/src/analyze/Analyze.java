package analyze;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import ocsf.server.*;
import communication.Answer;
import communication.Message;
import communication.Task;
import users.BusinessCustomer;
import users.CeoBiteMe;
import users.Customer;
import users.HrManager;
import users.Login;
import users.Supplier;
import users.User;

/**
 * @author Lior, Guzovsky.
 * @author Mousa, Srour.
 * @author Alexander, Martinov
 *
 *         Class description: This is a class which is a Wrapper for handeling
 *         all messages from the server.
 * @version 05/12/2021
 */
public class Analyze {
	/**
	 * Class members description:
	 */
	
	/**
	 * listener arrays for notifying relevant client gui classes of changes 
	 */
	private static final List<AnalyzeListener> clientListeners = new ArrayList<AnalyzeListener>();
	/**
	 * listener arrays for notifying relevant server gui classes of changes 
	 */
	private static final List<AnalyzeListener> serverListeners = new ArrayList<AnalyzeListener>();
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
			//changed controller method calls into listener notifications
			case CONFIRM_IP:
				switch (recievedAnswerFromServer) {
				case SUCCEED:
					// IPConfirmationAnalyze.userIPRecievedConfirmationSucceeded();
					for (AnalyzeListener listener : clientListeners) {
						listener.clientIpConfirmed();
					}
					break;
				case ERROR_FAILED:
					// IPConfirmationAnalyze.userIPRecievedConfirmationFailed();
					for (AnalyzeListener listener : clientListeners) {
						listener.clientIpNotConfirmed();
					}
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
				/* TBD: @Alexander to add listneres and print to screen */
			case PRINT_ERROR_TO_SCREEN:
				switch (recievedAnswerFromServer) {
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
	/**
	 * This is a function which analyzes all the messages from the client and than
	 * does logic accordingly.
	 * 
	 * @param message
	 * @param client
	 * @throws IOException
	 */
	public static void analyzeMessageFromClient(Object message, ConnectionToClient client) throws IOException {
		if (!(message instanceof Message)) {
			return;
		} else {
			Message recivedMessageFromClient = (Message) message;
			Task recivedTaskFromClient = recivedMessageFromClient.getTask();
			Answer recievedAnswerFromClient = recivedMessageFromClient.getAnswer();
			switch (recivedTaskFromClient) {
			case CONFIRM_IP:
				//changed controller call into listener notification
				for (AnalyzeListener listener : serverListeners) {
					listener.serverConfirmIp("status: connected " + client.getInetAddress().getHostName() + "  "
							+ client.getInetAddress().getHostAddress());
				}
				// serverGuiController = ServerGuiController.getLoader().getController();
				// serverGuiController.displayToConsoleInServerGui("status: connected " +
				// client.getInetAddress().getHostName()
				// + " " + client.getInetAddress().getHostAddress());
				recivedMessageFromClient.setAnswer(Answer.SUCCEED);
				break;

				//login case commented out, to be reimplemented
				
//			  case LOGIN: Login loginResult= LoginQueries.getLogin(((Login)(((Message)
//			  message).getObject())).getUserName(), connection); if(loginResult==null) {
//				  recivedMessageFromClient.setAnswer(Answer.ERROR_USER_NOT_FOUND);
//			  recivedMessageFromClient.setTask(Task.PRINT_ERROR_TO_SCREEN); }
//			  else if (!(loginResult.getPassword().equals(((Login)(((Message)
//			  message).getObject())).getPassword()))) {
//			  recivedMessageFromClient.setAnswer(Answer.ERROR_WRONG_PASSWORD);
//			  recivedMessageFromClient.setTask(Task.PRINT_ERROR_TO_SCREEN); }
//			  else {
//			  message=LoginAnalyze.getUserByType(loginResult.getUserType(),
//			  loginResult.getUserId(), connection, (Message)message); }
//			 
			}
		}

	}
//listener add methods
	
	/**
	 * add client listener to the array
	 * @param listener every listener is implemented in the class that's "listening"
	 */
	public static void addClientListener(AnalyzeListener listener) {
		clientListeners.add(listener);
	}
	/**
	 * add server listener to the array
	 * @param listener every listener is implemented in the class that's "listening"
	 */
	public static void addServerListener(AnalyzeListener listener) {
		serverListeners.add(listener);
	}

}
