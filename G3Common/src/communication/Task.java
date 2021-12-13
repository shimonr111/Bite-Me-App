package communication;

/**
 * @author Lior, Guzovsky.
 * Class description: 
 * 
 * This is an enum that
 * helps us to set the operation
 * needed to be done by the server and client.
 *
 * @version 11/12/2021
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
	DISPLAY_MESSAGE_TO_CLIENT,
	GET_COMPANIES_FROM_DB,
	DISPLAY_COMPANIES_INTO_COMBOBOX,
	GET_CUSTOMERS_FROM_DB,
	DISPLAY_CUSTOMERS_INTO_TABLE
}
