package clientanalyze;

/**
 * @author Lior, Guzovsky.
 * @author Mousa, Srour.
 * @author Alexander, Martinov
 * 
 *
 * Interface description: 
 * This is an interface which is used to implement the listeners(observable) design pattern
 *         
 * @version 06/12/2021
 */

public interface AnalyzeClientListener {
	//from here it's all client listener methods
	default void clientIpConfirmed() {
		return;
	}
	default void clientIpNotConfirmed() {
		return;
	}
	default void clientIsLogedIn() {
		return;
	}
	default void clientNotFoundInSystem() {
		return;
	}
	default void clientIsNotConfirmed() {
		return;
	}
	default void clientGotCustomerLogin() {
		return;
	}
	default void clientGotBusinessCustomerLogin() {
		return;
	}
	default void clientGotSupplierLogin() {
		return;
	}
	default void clientGotHRManagerLogin() {
		return;
	}
	default void clientGotBranchManagerLogin() {
		return;
	}
	default void clientGotCEOLogin() {
		return;
	}
}
