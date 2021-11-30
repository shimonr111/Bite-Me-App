// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package bitemeserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import guiserver.ServerGuiController;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import orders.Order;

/*
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *
 */

public class BiteMeServerCommunication extends AbstractServer {
	private Connection connection;
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
	 * @param msg: The message received from the client.
	 * 
	 * @param client: The connection from which the message originated.
	 */

	public void handleMessageFromClient(Object msg, ConnectionToClient client) {

		/*
		 * in this case parsedMsgFromClient[0] = update parsedMsgFromClient[1] =
		 * ResturantName parsedMsgFromClient[2] = OrderNumber
		 */
		serverGuiController = ServerGuiController.getLoader().getController();
		String[] parsedMsgFromClient = ((String) msg).split(" "); /* string array for data parsing */
		if (parsedMsgFromClient[0].equals("update")) {
			Order findCurrentOrder = findOrder(parsedMsgFromClient[2]); /* find in DB if the order exist */
			boolean isUpdateSuccessful;
			findCurrentOrder.setOrderAddress(parsedMsgFromClient[5]); /* update the order address */
			findCurrentOrder.setOrderType(parsedMsgFromClient[6]); /* update the order delivery type */
			isUpdateSuccessful = updateOrder(findCurrentOrder);

			if (isUpdateSuccessful) {
				sentToSpecificClient(client, "update completed successfully");
			} else {
				sentToSpecificClient(client, "update not completed");
			}

		}
		/*
		 * in this case parsedMsgFromClient[0] = find parsedMsgFromClient[1] =
		 * OrderNumber
		 */
		else if (parsedMsgFromClient[0].equals("find")) {
			Order currentOrder = findOrder(parsedMsgFromClient[1]);

			if (currentOrder != null) {
				System.out.println("Order found");
				sentToSpecificClient(client, currentOrder.toString());
			} else {
				System.out.println("Order Not Found");
				sentToSpecificClient(client, "Error");
			}
		}
		/*
		 * In this case parsedMsgFromClient[0] = confirm
		 */
		else if (parsedMsgFromClient[0].equals("confirm")) {
			serverGuiController.displayToConsoleInServerGui("status: connected " + client.getInetAddress().getHostName()
					+ "  " + client.getInetAddress().getHostAddress());
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

	/* query for find the order in the DB */
	public Order findOrder(String OrderNumber) {
		PreparedStatement stmt;
		Order returnedOrderFromDB = null;
		try {
			stmt = connection.prepareStatement("SELECT * FROM semesterialproject.order WHERE OrderNumber = ?");
			stmt.setString(1, OrderNumber);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				returnedOrderFromDB = new Order(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(6), rs.getString(5));
				rs.close();
				return returnedOrderFromDB;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/* Update the order in the DB according to the delivery type and the address */
	public boolean updateOrder(Order newOrder) {
		PreparedStatement stmt;
		try {
			Order result = findOrder(newOrder.getOrderNumber());
			if (result == null) {
				System.out.println("Error!, The new OrderNumber doesn't match to the current one.");
				System.out.println("No update was done at the DB");
				return false;
			}
			stmt = connection.prepareStatement(
					"UPDATE semesterialproject.order SET TypeOfOrder = ?, OrderAddress = ? WHERE OrderNumber = ?");
			stmt.setString(1, newOrder.getOrderType());
			stmt.setString(2, newOrder.getOrderAddress());
			stmt.setString(3, newOrder.getOrderNumber());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return true;
	}

	/* send message to specific client rather than all clients */
	public void sentToSpecificClient(ConnectionToClient client, Object msg) {
		try {
			client.sendToClient(msg);
		} catch (Exception ex) {
		}
	}

}
//End of EchoServer class
