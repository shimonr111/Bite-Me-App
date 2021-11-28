// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package biteMeClient;

import java.io.IOException;

import ChatIFC.ChatIF;
import logic.Order;
import ocsf.client.AbstractClient;
import order.EntryOrderNumberFormController;
import order.EntryDisplayAndUpdateInfoForOrderFormController;

/**
 * This class overrides some of the methods defined in the abstract superclass
 * in order to give more functionality to the client.
 */
public class BiteMeClientCommunication extends AbstractClient {
	// Instance variables **********************************************

	/**
	 * The interface type variable. It allows the implementation of the display
	 * method in the client.
	 */
	ChatIF clientUI;
	public static Order displayedOrder = new Order(null, null, null, null, null, null); // create new order for sending
																						// data to server
	public static boolean awaitResponse = false; // wait for response from server

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the chat client.
	 *
	 * @param host     The server to connect to.
	 * @param port     The port number to connect on.
	 * @param clientUI The interface type variable.
	 */

	public BiteMeClientCommunication(String host, int port, ChatIF clientUI) throws IOException {
		super(host, port); // Call the superclass constructor
		this.clientUI = clientUI;
		openConnection(); // create socket and open connection
	}

	// Instance methods ************************************************

	/**
	 * This method handles all data that comes in from the server.
	 *
	 * @param msg The message from the server.
	 */
	public void handleMessageFromServer(Object msg) {
		awaitResponse = false;
		String messageFromServer;
		messageFromServer = msg.toString();
		String[] parsedMessageFromServer = messageFromServer.split(" ");
		if (parsedMessageFromServer[0].equals("Error")) { // if the order doesn't exist delete the old one to avoid
															// garbage data
			displayedOrder = new Order(null, null, null, null, null, null); // clean the old order one if the order is
																			// not found
		} else if (parsedMessageFromServer[0].equals("update")) { // if update button was pressed
			EntryDisplayAndUpdateInfoForOrderFormController orderIntialFrameController = EntryOrderNumberFormController
					.getClientMainFrameLoader().getController(); // get the controller of OrderMainForm
			orderIntialFrameController.displayUpdateMessage(messageFromServer); // display text in GUI
		} else {
			setOrderInfoUpdate(parsedMessageFromServer); // set the new info in the GUI screen
		}
	}

	private void setOrderInfoUpdate(String[] parsedMessageFromServer) {
		displayedOrder.setResturantName(parsedMessageFromServer[0]); /* Restaurant name */
		displayedOrder.setOrderNumber(parsedMessageFromServer[1]); /* Order number */
		displayedOrder.setOrderTime(parsedMessageFromServer[2]); /* Order Time */
		displayedOrder.setPhoneNumber(parsedMessageFromServer[3]); /* Order Phone number */
		displayedOrder.setOrderType(parsedMessageFromServer[5]); /* Order Type Delivery */
		displayedOrder.setOrderAddress(parsedMessageFromServer[4]); /* Order address location */
	}

	/**
	 * This method handles all data coming from the UI e
	 *
	 * @param message The message from the UI.
	 */

	public void handleMessageFromClientUI(String message) {
		try {
			awaitResponse = true;
			String[] parsedMessageFromClientUI = ((String) message).split(" "); /* parse the data */
			openConnection();// in order to send more than one message
			if (message.equals("confirm")) // if confirm button pressed
				awaitResponse = false;
			if (parsedMessageFromClientUI[0].equals("update")) { // if the user pressed update button
				awaitResponse = false;
			}
			sendToServer(message); // send the msg to the server
			// wait for response
			while (awaitResponse) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			clientUI.display("Could not send message to server: Terminating client." + e);
			quit();
		}
	}

	/**
	 * This method terminates the client.
	 */
	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);
	}
}
//End of ChatClient class
