package bitemeclient;

import controllers_gui.EntryIPConfirmationFormController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Lior, Guzovsky.
 * @author Ori, Malka.
 * Class description: 
 * 
 * This class is used for running the app
 * from the customers side
 *
 * @version 03/12/2021
 */
public class BiteMeClientUI extends Application {
	/**
	 * Class members description:
	 */

	/**
	 * An istance of the BiteMeClientController to start the controller.
	 */
	public static BiteMeClientController biteMeClientController;
	
	/**
	 * Instance of the first screen.
	 */
	public EntryIPConfirmationFormController ipFormController;
	
	public static void main(String args[]) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		/** 
		* create controller for first Frame 
		*/
		ipFormController = new EntryIPConfirmationFormController(); 
		/**
		* go to start method in the IPController 
		*/
		ipFormController.start(primaryStage);
	}

	public static void startController(String ipTxt) {
		/**
		* start running with the given IP from the IP frame entered by the client 
		*/
		biteMeClientController = new BiteMeClientController(ipTxt, 5555);
	}
}
