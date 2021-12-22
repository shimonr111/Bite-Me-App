package communication;

/**
 * @author Lior, Guzovsky.
 * @author Alexander, Martinov
 * Class description: 
 * 
 * This is an enum that
 * helps us to set the operation
 * needed to be done by the server and client.
 *
 * @version 15/12/2021
 */
public enum Task {
	
	CONFIRM_IP,
	
	LOGIN,
	CREATE_USER_PORTAL,
	PRINT_ERROR_TO_SCREEN,
	LOGOUT,
	SET_HOMESCREEN,
	REGISTER_PRIVATE_CUSTOMER,
	REGISTER_BUSINESS_CUSTOMER,
	REGISTER_SUPPLIER,
	REGISTER_COMPANY,
	DISPLAY_MESSAGE_TO_CLIENT,
	GET_COMPANIES_FROM_DB,
	DISPLAY_COMPANIES_INTO_COMBOBOX,
	GET_CUSTOMERS_FROM_DB,
	DISPLAY_CUSTOMERS_INTO_TABLE,
	UPDATE_CUSTOMER_STATUS,
	REMOVE_USER_FROM_DB,
	GET_RESTAURANTS_FOR_ORDER,
	GET_RESTAURANTS_FOR_SUPPLIER_REGISTRATION,
	GET_SUPPLIER_FOR_SUPPLIER_WORKER_REGISTRATION,
	GET_COMPANIES_FOR_CONFIRMATION,
	DISPLAY_UNCONFIRMED_COMPANIES,
	CONFIRM_COMPANY,
	DENY_COMPANY,
	GET_USERS_FOR_REGISTRATION,
	GET_SUPPLIERS_FOR_REGISTRATION,
	DISPLAY_SUPPLIERS_INTO_TABLE,
	DISPLAY_USERS_ON_REGISTRATION_LIST,
	CONFIRM_SUPPLIER,
	GET_ITEMS_FOR_ORDER_MENU, 
	CLIENT_DICONNECT,
	GET_BUSINESS_CUSTOMERS_FOR_CONFIRMATION,
	DISPLAY_BUSINESS_CUSTOMERS_INTO_TABLE,
	GET_SYSTEM_REPORTS,
	SENT_REPORT_SUPPLIERS_LIST,
	ORDER_FINISHED,
	SUPPLIER_WORKER_GET_ALL_RELEVANT_ORDERS
}
