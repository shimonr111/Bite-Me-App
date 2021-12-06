package controllers_analyze;

import java.io.IOException;
import bitemeclient.BiteMeClientUI;
import communication.Answer;
import communication.Message;
import communication.Task;
import controllers_gui.EntryIPConfirmationFormController;
import javafx.event.ActionEvent;

/**
 * @author Lior, Guzovsky & Ori, Malka.
 * Class description: 
 * 
 * This is a class that acts as a 
 * Wrapper for the IP confirmation
 * process.
 * 
 * @version 03/12/2021
 */
public class IPConfirmationAnalyze extends AbstractBiteMeController {
	/**
	 * Class members description:
	 */
	
	/**
	 * event from the IP confirmation
	 * Controller.
	 */
	private static ActionEvent event;

	/**
	 * This function sets request
	 * for the server to join
	 * to the net.
	 * 
	 * @param btnConfirmIP
	 * @param IP
	 */
	public static void userIPConfirmationRequest(ActionEvent btnConfirmIP, String IP) {
		event = btnConfirmIP;
		Task task = Task.CONFIRM_IP;
		Answer answer = Answer.WAIT_RESPONSE;
		Message message = new Message(task, answer, null);
		BiteMeClientUI.startController(IP); /* get the entered IP by the client */
		sendToClient(message);
	}

	/**
	 * This is a function
	 * which works if the IP
	 * confirmation was fine.
	 * 
	 * @throws IOException
	 */
	public static void userIPRecievedConfirmationSucceeded() throws IOException {
		//EntryIPConfirmationFormController.ipConfirmationFormController.setLoginScreen(event);
		

	}
	
	/**
	 * This is as function
	 * that works if the IP
	 * confirmation failed.
	 * 
	 * @throws IOException
	 */
	public static void userIPRecievedConfirmationFailed() throws IOException{
		EntryIPConfirmationFormController.ipConfirmationFormController.setErrorToClientUI(event);
	}
}
