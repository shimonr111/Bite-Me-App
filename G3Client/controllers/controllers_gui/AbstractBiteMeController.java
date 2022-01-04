package controllers_gui;

import bitemeclient.BiteMeClientUI;
import communication.Message;
import users.User;

/**
 * 
 * Class description: 
 * This Abstract class will 
 * be key designed for all controllers
 * 
 */

/**
 * 
 * @author Lior, Guzovsky.
 * @author Mousa, Srour.
 * @version 08/12/2021
 */
public abstract class AbstractBiteMeController{
	/**
	 * Class members description:
	 */
	
	/**
	 * This is the connected user, every client has only 1 or 0 connected users 
	 * every time an user logged in we set the connectedUser as the connected user
	 * after the logout the connectedUser will be null.
	 */
	public static User connectedUser;
	
	/**
	 * This method get message from 
	 * client and send it to the server.
	 * 
	 * @param message
	 */
	public static void sendToClient(Message message){
		BiteMeClientUI.biteMeClientController.accept(message);
	}

	/**
	 * Constructor for the class.
	 */
	public AbstractBiteMeController(){
		
	}

}
