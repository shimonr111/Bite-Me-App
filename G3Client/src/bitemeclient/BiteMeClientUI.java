package bitemeclient;

import gui.EntryIPConfirmationFormController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Lior, Guzovsky.
 * Class description: 
 * 
 * This class is used for running the app
 * from the customers side
 *
 * @version 03/12/2021
 */
public class BiteMeClientUI extends Application {
	public static BiteMeClientController biteMeClientController;
	public EntryIPConfirmationFormController ipFormController;

	public static void main(String args[]) throws Exception {
		launch(args);
	} // end main

	@Override
	public void start(Stage primaryStage) throws Exception {
		ipFormController = new EntryIPConfirmationFormController(); /* create controller for first Frame */
		ipFormController.start(primaryStage); /* go to start method in the IPController */
	}

	public static void startController(String ipTxt) {
		biteMeClientController = new BiteMeClientController(ipTxt,
				5555); /* start running with the given IP from the IP frame entered by the client */
	}
}
