package bitemeserver;

import java.sql.Connection;
import java.sql.SQLException;
import communication.Message;
import communication.Task;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import query.Query;
import serveranalyze.AnalyzeMessageFromClient;

/**
 * Class description: 
 * This class overrides some of the
 * methods in the abstract superclass in order
 * to give more functionality to the server.
 */

/**
 * 
 * @author Lior, Guzovsky.
 * @author Mousa, Srour.
 * 
 * @version 04/12/2021
 */
public class BiteMeServerCommunication extends AbstractServer{
	
	/**
	 * Class members description:
	 */

	/**
	 * Connection instance to save the DB connection.
	 */
	private Connection connection;
	
	/**
	 * ServerGuiController instance to have access to the controller.
	 */

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port: The port number to connect on.
	 */
	public BiteMeServerCommunication(int port) {
		super(port);
	}

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param message: The message received from the client.
	 * @param client: The connection from which the message originated.
	 */
	public void handleMessageFromClient(Object message, ConnectionToClient client) {
		if(message instanceof Message) {
		Message recivedMessageFromClient = (Message)message;
		try {
			if(recivedMessageFromClient.getTask() == Task.ORDER_FINISHED) { //used only for broadcast messages between users
				sendToAllClients(AnalyzeMessageFromClient.analyzeMessageFromClient(recivedMessageFromClient,client));
			}else {
				sentToSpecificClient(client,AnalyzeMessageFromClient.analyzeMessageFromClient(recivedMessageFromClient,client));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort()+"\n");
		this.connection = BiteMeServerUI.connectionToDB.getConnection();
		Query.setConnectionFromServerToDB(connection); //set connection for query Class
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.\n");
	}
	
	/**
	 * send message to specific
	 * client rather than all clients
	 * 
	 * @param client
	 * @param message
	 */
	public void sentToSpecificClient(ConnectionToClient client, Object message) {
		try {
			client.sendToClient(message);
		} catch (Exception ex) {
		}
	}

}
