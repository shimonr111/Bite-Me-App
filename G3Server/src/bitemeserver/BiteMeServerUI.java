package bitemeserver;

import guiserver.ServerGuiController;
import javafx.application.Application;
import javafx.stage.Stage;
import jdbc.mySqlConnection;

/**
 * @author  Lior, Guzovsky.
 * Class description: 
 * 
 * This is a class that works as an
 * UI for the server.
 * 
 * @version 03/12/2021
 */
public class BiteMeServerUI extends Application {
	/**
	 * Class members description:
	 */
	
	/**
	 * BiteMeServerCommunication instance to start to have a communication channel between the serverUI to the server.
	 */
	public static BiteMeServerCommunication serverCommunication;
	
	/**
	 * ServerGuiController instance to have an access to the server controller.
	 */
	public static ServerGuiController serverGuiController;
	
	/**
	 * mySqlConnection instance to save the SQl connection of the server.
	 */
	public static mySqlConnection connectionToDB;

	public static void main(String args[]) throws Exception {
		launch(args);
	} // end main

	@Override
	public void start(Stage primaryStage) throws Exception {
		serverGuiController = new ServerGuiController(); // create server controller
		serverGuiController.start(primaryStage);
	}

	/*
	 * This function goes to the 
	 * EchoServer method of stop listening 
	 * when button Disconnect is clicked in
	 *  the Server Controller.
	 */
	public static void DisconnectServer() {
		System.out.println("The server is Disconnected");
		serverCommunication.stopListening();

	}

	public static String runServer(String p, String dbName, String dbUserTxt, String password) {
		String returnMsgFromServer = "Error! Connection failed.";
		int port = 0; // Port to listen on
		try {
			port = Integer.parseInt(p); // Set port to 5555

		} catch (Throwable t) {

		}
		if (serverCommunication == null)
			serverCommunication = new BiteMeServerCommunication(port);
		connectionToDB = new mySqlConnection();
		if (connectionToDB.connectToDB(dbName, dbUserTxt, password))
			returnMsgFromServer = "Connection to server succeded";
		try {
			serverCommunication.listen(); // Start listening for connections
		} catch (Exception ex) {
			returnMsgFromServer = "Error! Could't listen for clients!";
		}
		return returnMsgFromServer;
	}

}
