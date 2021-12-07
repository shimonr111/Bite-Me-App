package bitemeserver;

import java.sql.Connection;
import java.sql.SQLException;

import communication.Message;
import guiserver.ServerGuiController;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import query.Query;
import serveranalyze.AnalyzeMessageFromClient;

/**
 * @author Lior, Guzovsky.
 * @author Mousa, Srour.
 *
 * Class description: 
 * This class overrides some of the
 * methods in the abstract superclass in order
 * to give more functionality to the server.
 * 
 * @version 04/12/2021
 */
public class BiteMeServerCommunication extends AbstractServer {
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
	private ServerGuiController serverGuiController;

	/*
	 * Constructs an instance of the echo server.
	 *
	 * @param port: The port number to connect on.
	 */
	public BiteMeServerCommunication(int port) {
		super(port);
	}

	/*
	 * This method handles any messages received from the client.
	 *
	 * @param message: The message received from the client.
	 * 
	 * @param client: The connection from which the message originated.
	 */

	public void handleMessageFromClient(Object message, ConnectionToClient client) {
		if(message instanceof Message) {
		Message recivedMessageFromClient = (Message)message;
		try {
			sentToSpecificClient(client,AnalyzeMessageFromClient.analyzeMessageFromClient(recivedMessageFromClient,client));
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
		AnalyzeMessageFromClient.displayToMessageConsole("Server listening for connections on port " + getPort());
		this.connection = BiteMeServerUI.connectionToDB.getConnection();
		Query.setConnectionFromServerToDB(connection); //set connection for query Class
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		AnalyzeMessageFromClient.displayToMessageConsole("Server has stopped listening for connections.");
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
