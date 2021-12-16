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
 * @version 15/12/2021
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
	default void clientUserIdExist() {
		return;
	}
	default void clientUserNameExist() {
		return;
	}
	default void clientPrivateCustomerRegistrationSucceed() {
		return;
	}
	default void clientBusinessCustomerRegistrationSucceed() {
		return;
	}
	default void clientBusinessUserIdExist() {
		return;
	}
	default void clientBusinessUserNameExist() {
		return;
	}
	default void clientSupplierRegistrationSucceed() {
		return;
	}
	default void clientSupplierIdExist() {
		return;
	}
	default void clientSupplierUserNameExist() {
		return;
	}
	default void clientCompanyRegistrationFailed(String message) {
		return;
	}
	default void clientCompanyRegistrationSucceed() {
		return;
	}
}
