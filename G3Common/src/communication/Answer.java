package communication;

/**
 * 
 *  @author Lior, Guzovsky.
 *  @author Ori, Malka.
 *  @author Alexander, Martinov.
 *  
 *  Class description: 
 *  This is an enum
 *  for all the answers we get from the 
 *  server according to the 
 *  Task that has been sent.
 *  
 *  @version 21/12/2021
 */
public enum Answer{
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
	LOGOUT_SUCCEED,
	
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
	ERROR_ALREADY_LOGGED_IN,
	ERROR_USER_NOT_FOUND,
	USER_ID_ALREADY_EXIST,
	USER_ID_ALREADY_EXIST_BUSINESS,
	USER_NAME_ALREADY_EXIST,
	USER_NAME_ALREADY_EXIST_BUSINESS,
	CUSTOMER_REGISTRATION_SUCCEED,
	BUSINESS_CUSTOMER_REGISTRATION_SUCCEED,
	SUPPLIER_NAME_EXIST,
	SUPPLIER_REGISTRATION_SUCCEED,
	COMPANY_REGISTRATION_SUCCEED,
	USER_ID_ALREADY_EXIST_SUPPLIER,
	USER_NAME_ALREADY_EXIST_SUPPLIER,
	COMPANY_NAME_ALREADY_EXIST,
	COMPANY_CODE_ALREADY_EXIST,
	
	/**
	 * Answers related to Order
	 * Operation.
	 */
	RESTAURANTS_EXIST_FOR_THIS_BRANCH,
	RESTAURANTS_NOT_EXIST_IN_THIS_BRANCH,
	SUPPLIER_HAS_NO_MENU,
	SUPPLIER_MENU_FOUND,
	ORDER_SUCCEEDED_WRITING_INTO_DB,
	
	/**
	 * Answers related to Registration
	 * Operation.
	 */
	SUPPLIER_FOUND_IN_DB,
  
	/**
	 * Answers related to Report
	 * Operation.
	 */
	SENT_REPORT_SUPPLIERS_LIST,
	SENT_REPORT_QUARTERLY_LIST,
	REPORT_UPLOADED,
	PDF_LIST_SENT,
	PDF_FILE_SENT, 
	
	/**
	 * Answers related to manage 
	 * menu operation.
	 */
	SUPPLIER_WORKER_NO_ORDERS_FOUND,
	SUPPLIER_WORKER_ORDERS_FOUND,
	MANAGE_MENU_SUCCEEDED_WRITING_INTO_DB,
	MANAGE_ORDER_SUCCEEDED_WRITING_INTO_DB,
}
