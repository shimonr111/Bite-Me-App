// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package bitemeserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import communication.*;
import guiserver.ServerGuiController;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import orders.Order;

/**
 * @author Lior, Guzovsky.
 * Class description: 
 * 
 * This class overrides some of the
 * methods in the abstract superclass in order
 * to give more functionality to the server.
 * 
 * @version 03/12/2021
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
		Task recieveTask = recivedMessageFromClient.getTask();
		switch(recieveTask) {
		case CONFIRM_IP:
			serverGuiController = ServerGuiController.getLoader().getController();
			serverGuiController.displayToConsoleInServerGui("status: connected " + client.getInetAddress().getHostName()
					+ "  " + client.getInetAddress().getHostAddress());
			recivedMessageFromClient.setAnswer(Answer.SUCCEED);
			break;
		default:
			break;
		}
		sentToSpecificClient(client,recivedMessageFromClient);
		}
	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		ServerGuiController serverGuiController = ServerGuiController.getLoader().getController();
		serverGuiController.displayToConsoleInServerGui("Server listening for connections on port " + getPort());
		this.connection = BiteMeServerUI.connectionToDB.getConnection();
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		ServerGuiController serverGuiController = ServerGuiController.getLoader().getController();
		serverGuiController.displayToConsoleInServerGui("Server has stopped listening for connections.");
	}

									/*Dont delete!!! For reference!!!!*/
	///////////////////////////////////////////////////////////////////////////////////////////////////////
//	/* query for find the order in the DB */
//	public Order findOrder(String OrderNumber) {
//		PreparedStatement stmt;
//		Order returnedOrderFromDB = null;
//		try {
//			stmt = connection.prepareStatement("SELECT * FROM semesterialproject.order WHERE OrderNumber = ?");
//			stmt.setString(1, OrderNumber);
//			ResultSet rs = stmt.executeQuery();
//			if (rs.next()) {
//				returnedOrderFromDB = new Order(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
//						rs.getString(6), rs.getString(5));
//				rs.close();
//				return returnedOrderFromDB;
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

//	/* Update the order in the DB according to the delivery type and the address */
//	public boolean updateOrder(Order newOrder) {
//		PreparedStatement stmt;
//		try {
//			Order result = findOrder(newOrder.getOrderNumber());
//			if (result == null) {
//				System.out.println("Error!, The new OrderNumber doesn't match to the current one.");
//				System.out.println("No update was done at the DB");
//				return false;
//			}
//			stmt = connection.prepareStatement(
//					"UPDATE semesterialproject.order SET TypeOfOrder = ?, OrderAddress = ? WHERE OrderNumber = ?");
//			stmt.setString(1, newOrder.getOrderType());
//			stmt.setString(2, newOrder.getOrderAddress());
//			stmt.setString(3, newOrder.getOrderNumber());
//			stmt.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return true;
//	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
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
//End of EchoServer class
