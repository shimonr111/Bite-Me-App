package serveranalyze;

/**
 * @author Lior, Guzovsky.
 * @author Mousa, Srour.
 * @author Alexander, Martinov
 *
 *  Interface description: 
 *  This is an interface which is used to implement the listeners(observers) design pattern
 *         
 * @version 06/12/2021
 */
public interface AnalyzeServerListener {
	default void displayToGuiServerConsole(String messageToGuiServerCosole) {
		return;
	}
}
