package serveranalyze;

/**
 *  Interface description: 
 *  This is an interface which is used to implement the listeners(observers) design pattern
 * 
 * @author Lior, Guzovsky.
 * @author Mousa, Srour.
 * @author Alexander, Martinov.
 *        
 * @version 06/12/2021
 */
public interface AnalyzeServerListener {
	default void callInitializeMethod() {
		return;
	}
}
