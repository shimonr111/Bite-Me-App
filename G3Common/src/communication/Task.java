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
	DISPLAY_MESSAGE_TO_CLIENT
}
