package clientanalyze;

import orders.Order;

/**
 * 
 * Interface description: 
 * This is an interface which is used to implement the listeners(observable) design pattern
 * 
 * @author Lior, Guzovsky.
 * @author Mousa, Srour.
 * @author Alexander, Martinov
 *         
 * @version 15/12/2021
 */

public interface AnalyzeClientListener {
	/**
	 * The listener that will work if the connection to the given IP succeed.
	 */
	default void clientIpConfirmed() {
		return;
	}
	
	/**
	 * The listener that will work if the connection didn't succeed.
	 */
	default void clientIpNotConfirmed() {
		return;
	}
	
	/**
	 * The listener that will work if the user who is trying to log in
	 *  is already logged in.
	 */
	default void clientIsLogedIn() {
		return;
	}
	
	/**
	 * The listener that will work if the user who is trying to log in
	 *  is not exist in system.
	 */
	default void clientNotFoundInSystem() {
		return;
	}
	
	/**
	 * The listener that will work if the user who is trying to log in 
	 * is not confirmed in system.
	 */
	default void clientIsNotConfirmed() {
		return;
	}
	
	/**
	 * The listener that will work if the user logged in
	 *  is a private customer.
	 */
	default void clientGotCustomerLogin() {
		return;
	}
	
	/**
	 * The listener that will work if the user logged in
	 *  is a business customer.
	 */
	default void clientGotBusinessCustomerLogin() {
		return;
	}
	
	/**
	 * The listener that will work if the user logged in
	 *  is a supplier worker.
	 */
	default void clientGotSupplierLogin() {
		return;
	}
	
	/**
	 * The listener that will work if the user logged in
	 *  is a HR Manager.
	 */
	default void clientGotHRManagerLogin() {
		return;
	}
	
	/**
	 * The listener that will work if the user logged in
	 *  is a Branch Manager.
	 */
	default void clientGotBranchManagerLogin() {
		return;
	}
	
	/**
	 * The listener that will work if the user logged in
	 *  is a CEO.
	 */
	default void clientGotCEOLogin() {
		return;
	}
	
	/**
	 * The listener that will work if in the private customer registration form
	 *  we try to register a user with an user id that already
	 *  exist in system.
	 */
	default void clientUserIdExist() {
		return;
	}
	
	/**
	 * The listener that will work if in the private customer registration form
	 *  we try to register a user with an user name that already
	 *  exist in system.
	 */
	default void clientUserNameExist() {
		return;
	}
	
	/**
	 * The listener that will work if in the registration of Private
	 * customer succeed.
	 */
	default void clientPrivateCustomerRegistrationSucceed() {
		return;
	}
	
	/**
	 * The listener that will work if in the registration of Business
	 * customer succeed.
	 */
	default void clientBusinessCustomerRegistrationSucceed() {
		return;
	}
	
	/**
	 * The listener that will work if in the business customer registration form
	 *  we try to register a user with an user id that already
	 *  exist in system.
	 */
	default void clientBusinessUserIdExist() {
		return;
	}
	
	/**
	 * The listener that will work if in the private customer registration form
	 *  we try to register a user with an user name that already
	 *  exist in system.
	 */
	default void clientBusinessUserNameExist() {
		return;
	}
	
	/**
	 * The listener that will work if in the registration of a Supplier succeed.
	 */
	default void clientSupplierRegistrationSucceed() {
		return;
	}
	
	/**
	 * The listener that will work if in the supplier registration form
	 *  we try to register a supplier with a supplier id that already
	 *  exist in system.
	 */
	default void clientSupplierIdExist() {
		return;
	}
	
	/**
	 * The listener that will work if in the supplier registration form
	 *  we try to register a supplier with a supplier name that already
	 *  exist in system.
	 */
	default void clientSupplierUserNameExist() {
		return;
	}
	
	/**
	 * The listener that will work if in the company registration failed.
	 */
	default void clientCompanyRegistrationFailed(String message) {
		return;
	}
	
	/**
	 * The listener that will work if in the company registration succeed.
	 */
	default void clientCompanyRegistrationSucceed() {
		return;
	}
	
	/**
	 * This listener will work when a customer finishes his order
	 * so we update it immediately on the supplier's worker manager orders screen
	 * Without any need to refresh.
	 * @param order The finished order.
	 */
	default void addOrderToSupplierTable(Order order) {
		return;
	}
}
