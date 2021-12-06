package bitemeclient;

import java.io.IOException;
import communication.Message;

/**
 * This class constructs the UI for a chat client.
 *
 * @author Ori, Malka.
 * @author Lior, Guzovsky,
 * @version 4/12/2021
 */
public class BiteMeClientController{
	/**
	 * The default port to connect on.
	 */
	public static int DEFAULT_PORT;

	/**
	 * The instance of the BiteMeClientCommunication with the server
	 */
	public BiteMeClientCommunication client;

	
	/**
	 * Constructs an instance of the ClientConsole UI.
	 *
	 * @param host The host to connect to.
	 * @param port The port to connect on.
	 */
	public BiteMeClientController(String host, int port) {
		try {
			client = new BiteMeClientCommunication(host, port);
		} catch (IOException exception) {
			System.out.println("Error: Can't setup connection!" + " Terminating client.");
			System.exit(1);
		}
	}
	

	/**
	 * This method waits for input from the console. Once it is received, it sends
	 * it to the client's message handler.
	 * @param message the message sent from the client UI to the client logic.
	 */
	public void accept(Message message) {
		client.handleMessageFromClientUI(message);
	}


}
