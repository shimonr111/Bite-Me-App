package controllers_gui;

import bitemeclient.BiteMeClientUI;
import communication.Message;

/**
 * @author Lior, Guzovsky.
 * Class description: 
 * 
 * This Abstract class will 
 * be key designed for all controllers
 * 
 * @version 03/12/2021
 */
public abstract class AbstractBiteMeController {
	/**
	 * Class members description:
	 */
	
	/**
	 * This method get message from 
	 * client and send it to the server.
	 * 
	 * @param message
	 */
	public static void sendToClient(Message message) {
		BiteMeClientUI.biteMeClientController.accept(message);
	}

	/**
	 * Constructor for the class.
	 */
	public AbstractBiteMeController() {
		
	}

}
