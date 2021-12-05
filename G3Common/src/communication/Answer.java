package communication;

/**
 * 
 *  @author Lior, Guzovsky.
 *  @author Ori, Malka.
 *  Class description: 
 *  
 *  This is an enum
 *  for all the answers we get from the 
 *  server according to the 
 *  Task that has been sent.
 *  
 *  @version 03/12/2021
 *
 */
public enum Answer {
	/**
	 * This is our default value
	 * to be set upon start before the server 
	 * returns any answer!
	 */
	WAIT_RESPONSE,
	
	/**
	 * Common Answers
	 */
	SUCCEED,
	ERROR_FAILED,
	
	/**
	 * Answer enum related to Login 
	 * operation.
	 */
	CREATE_USER_PORTAL_FOR_CUSTOMER,
	CREATE_USER_PORTAL_FOR_BUSINESS_CUSTOMER,
	CREATE_USER_PORTAL_FOR_HR_MANAGER,
	CREATE_USER_PORTAL_FOR_SUPPLIER,
	CREATE_USER_PORTAL_FOR_BRANCH_MANAGER,
	CREATE_USER_PORTAL_FOR_CEO_BITE_ME,
	ERROR_USER_NOT_CONFIRMED,
	ERROR_WRONG_PASSWORD,
	ERROR_ALREADY_LOGGED_IN,
	ERROR_USER_NOT_FOUND
	

}
