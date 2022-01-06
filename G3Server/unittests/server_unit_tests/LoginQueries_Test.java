package server_unit_tests;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import communication.Answer;
import communication.Message;
import communication.Task;
import jdbc.mySqlConnection;
import query.LoginQueries;
import query.Query;
import users.Customer;
import users.Login;

/*This is the class under test in which we will check the login phase*/
public class LoginQueries_Test{

	/*Set connection to DB for testing*/
	public mySqlConnection sqlConnection = new mySqlConnection();

	/*DB name*/
	final public static String DEFAULT_DB_NAME = "jdbc:mysql://localhost/semesterialproject?serverTimezone=IST";

	/*DB user name*/
	final public static String DEFAULT_DB_USER = "root";

	/*DB password for specific user*/
	final public static String DEFAULT_DB_PASSWORD = "09000772Mm-";

	/*Create login object*/
	public Login loginForTest;

	/*Message received form the test to check assertions*/
	public Message messageFromClassUnderTest;

	//////////////////////////////////////////////////////////////////////////////////////////////////////
	/**Create all the userName and Password for the testing**/

	/*User name and Password and ID for regular customer*/
	private String userNameCustomer = "cu";
	private String passwordCustomer = "cu";
	private String idForRC = "1000";

	/*User name and Password and ID for business customer*/
	private String userNameBusinessCustomer = "bc";
	private String passwordBusinessCustomer = "bc";
	private String idForBC = "1002";

	/*User name and Password and ID for branch manager*/
	private String userNameBranchManager = "bmn";
	private String passwordBranchManager = "bmn";
	private String idForBM = "1041";

	/*User name and Password and ID for supplier worker*/
	private String userNameSupplierWorker = "phsw";
	private String passwordSupplierWorker = "phsw";
	private String idForSW = "2000";

	/*User name and Password for supplier worker already logged in*/
	private String userNameSupplierWorkerLoggedIn = "phsrw";
	private String passwordSupplierWorkerLoggedIn = "phsrw";

	/*User name and Password and ID for CEO*/
	private String userNameCeo = "ceo";
	private String passwordCeo = "ceo";
	private String idForCeo = "1001";

	/*User name and Password and ID for hrManager*/
	private String userNameHrManager = "intelhr";
	private String passwordHrManager = "intelhr";
	private String idForHR = "1222";

	/*User name and password for pending approval user account*/
	private String userNameForPendingApprovalStatusUser = "mousa";
	private String passwordForPendingApprovalStatusUser = "mousa";

	/*User name and password and ID for Frozen user account*/
	private String userNameForFrozenStatusUser = "lior";
	private String passwordForFrozenStatusUser = "lior";
	private String idForFrozenStatusUser = "10004";

	///////////////////////////////////////////////////////////////////////////////////////////////////

	/*Before each test we will do all those actions for clean start in each test*/
	@Before
   public void setUp() throws Exception {
		sqlConnection.connectToDB(DEFAULT_DB_NAME, DEFAULT_DB_USER, DEFAULT_DB_PASSWORD, false);
		Query.setConnectionFromServerToDB(sqlConnection.getConnection());
		/*Set the message to Login Task, Answer Wait response for the server to change and the object returned to null*/
		messageFromClassUnderTest = new Message(Task.LOGIN, Answer.WAIT_RESPONSE, null);
	}

	@Test
	//Test Description: Check if regular customer with correct user name and password return the right answer from the server
	//Test Input: userNameCustomer = "cu" , passwordCustomer = "cu"
	//Test Expected Output: Message received with Answer.CREATE_USER_PORTAL_FOR_CUSTOMER
   public void test_customer_log_in_with_correct_user_name_and_password() {

		/*Expected result from LoginQuery.createLoginMessageForServer(Message messageFromClient)*/
		Answer answerExpectedForTest = Answer.CREATE_USER_PORTAL_FOR_CUSTOMER;
		Answer answerReceived = null;

		/*Put in the Login object the userName and password for customer */
		loginForTest = new Login(userNameCustomer, passwordCustomer);

		/*Set the object in the message we send to the class under test LoginQuery in the function
		 * createLoginMessageForServer()*/
		messageFromClassUnderTest.setObject(loginForTest);
		LoginQueries.logOutUserWithIdAndType("RC", idForRC);
		try {
			answerReceived = (LoginQueries.createLoginMessageForServer(messageFromClassUnderTest)).getAnswer();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*Check if the answer is correct - use Assert true*/
		assertEquals(answerReceived, answerExpectedForTest);
	}

	@Test
	//Test Description: Check if business customer with correct user name and password return the right answer from the server
	//Test Input: userNameCustomer = "bc" , passwordCustomer = "bc"
	//Test Expected Output: Message received with Answer.CREATE_USER_PORTAL_FOR_BUSINESS_CUSTOMER
   public void test_business_customer_log_in_with_correct_user_name_and_password() {

		/*Expected result from LoginQuery.createLoginMessageForServer(Message messageFromClient)*/
		Answer answerExpectedForTest = Answer.CREATE_USER_PORTAL_FOR_BUSINESS_CUSTOMER;
		Answer answerReceived = null;

		/*Put in the Login object the userName and password for business customer */
		loginForTest = new Login(userNameBusinessCustomer, passwordBusinessCustomer);

		/*Set the object in the message we send to the class under test LoginQuery in the function
		 * createLoginMessageForServer()*/
		messageFromClassUnderTest.setObject(loginForTest);
		LoginQueries.logOutUserWithIdAndType("BC", idForBC);

		try {
			answerReceived = (LoginQueries.createLoginMessageForServer(messageFromClassUnderTest)).getAnswer();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*Check if the answer is correct - use Assert true*/
		assertEquals(answerReceived, answerExpectedForTest);
	}

	@Test
	//Test Description: Check if branch manager with correct user name and password return the right answer from the server
	//Test Input: userNameCustomer = "bmn" , passwordCustomer = "bmn"
	//Test Expected Output: Message received with Answer.CREATE_USER_PORTAL_FOR_BRANCH_MANAGER
   public void test_branch_manager_log_in_with_correct_user_name_and_password() {

		/*Expected result from LoginQuery.createLoginMessageForServer(Message messageFromClient)*/
		Answer answerExpectedForTest = Answer.CREATE_USER_PORTAL_FOR_BRANCH_MANAGER;
		Answer answerReceived = null;

		/*Put in the Login object the userName and password for branch manager*/
		loginForTest = new Login(userNameBranchManager, passwordBranchManager);

		/*Set the object in the message we send to the class under test LoginQuery in the function
		 * createLoginMessageForServer()*/
		messageFromClassUnderTest.setObject(loginForTest);
		LoginQueries.logOutUserWithIdAndType("BM", idForBM);

		try {
			answerReceived = (LoginQueries.createLoginMessageForServer(messageFromClassUnderTest)).getAnswer();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*Check if the answer is correct - use Assert true*/
		assertEquals(answerReceived, answerExpectedForTest);
	}

	@Test
	//Test Description: Check if supplier worker with correct user name and password return the right answer from the server
	//Test Input: userNameCustomer = "phsw" , passwordCustomer = "phsw"
	//Test Expected Output: Message received with Answer.CREATE_USER_PORTAL_FOR_SUPPLIER
   public void test_supplier_worker_log_in_with_correct_user_name_and_password() {

		/*Expected result from LoginQuery.createLoginMessageForServer(Message messageFromClient)*/
		Answer answerExpectedForTest = Answer.CREATE_USER_PORTAL_FOR_SUPPLIER;
		Answer answerReceived = null;

		/*Put in the Login object the userName and password for supplier worker */
		loginForTest = new Login(userNameSupplierWorker, passwordSupplierWorker);

		/*Set the object in the message we send to the class under test LoginQuery in the function
		 * createLoginMessageForServer()*/
		messageFromClassUnderTest.setObject(loginForTest);
		LoginQueries.logOutUserWithIdAndType("SW", idForSW);

		try {
			answerReceived = (LoginQueries.createLoginMessageForServer(messageFromClassUnderTest)).getAnswer();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*Check if the answer is correct - use Assert true*/
		assertEquals(answerReceived, answerExpectedForTest);
	}

	@Test
	//Test Description: Check if ceo with correct user name and password return the right answer from the server
	//Test Input: userNameCustomer = "ceo" , passwordCustomer = "ceo"
	//Test Expected Output: Message received with Answer.CREATE_USER_PORTAL_FOR_CEO_BITE_ME
   public void test_ceo_log_in_with_correct_user_name_and_password() {

		/*Expected result from LoginQuery.createLoginMessageForServer(Message messageFromClient)*/
		Answer answerExpectedForTest = Answer.CREATE_USER_PORTAL_FOR_CEO_BITE_ME;
		Answer answerReceived = null;

		/*Put in the Login object the userName and password for ceo */
		loginForTest = new Login(userNameCeo, passwordCeo);

		/*Set the object in the message we send to the class under test LoginQuery in the function
		 * createLoginMessageForServer()*/
		messageFromClassUnderTest.setObject(loginForTest);
		LoginQueries.logOutUserWithIdAndType("CEO", idForCeo);

		try {
			answerReceived = (LoginQueries.createLoginMessageForServer(messageFromClassUnderTest)).getAnswer();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*Check if the answer is correct - use Assert true*/
		assertEquals(answerReceived, answerExpectedForTest);
	}

	@Test
	//Test Description: Check if hr manager with correct user name and password return the right answer from the server
	//Test Input: userNameCustomer = "intelhr" , passwordCustomer = "intelhr"
	//Test Expected Output: Message received with Answer.CREATE_USER_PORTAL_FOR_HR_MANAGER
   public void test_hr_manager_in_with_correct_user_name_and_password() {

		/*Expected result from LoginQuery.createLoginMessageForServer(Message messageFromClient)*/
		Answer answerExpectedForTest = Answer.CREATE_USER_PORTAL_FOR_HR_MANAGER;
		Answer answerReceived = null;

		/*Put in the Login object the userName and password for HR manager */
		loginForTest = new Login(userNameHrManager, passwordHrManager);

		/*Set the object in the message we send to the class under test LoginQuery in the function
		 * createLoginMessageForServer()*/
		messageFromClassUnderTest.setObject(loginForTest);
		LoginQueries.logOutUserWithIdAndType("HR", idForHR);
		try {
			answerReceived = (LoginQueries.createLoginMessageForServer(messageFromClassUnderTest)).getAnswer();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*Check if the answer is correct - use Assert true*/
		assertEquals(answerReceived, answerExpectedForTest);
	}

	@Test
	//Test Description: Check if user that not confirmed return the right answer from the server
	//Test Input: userNameForPendingApprovalStatusUser = "mousa" , passwordForPendingApprovalStatusUser = "mousa"
	//Test Expected Output: Message received with Answer.ERROR_USER_NOT_CONFIRMED
   public void test_user_pending_approval() {

		/*Expected result from LoginQuery.createLoginMessageForServer(Message messageFromClient)*/
		Answer answerExpectedForTest = Answer.ERROR_USER_NOT_CONFIRMED;
		Answer answerReceived = null;

		/*Put in the Login object the userName and password for user in status pending approval */
		loginForTest = new Login(userNameForPendingApprovalStatusUser, passwordForPendingApprovalStatusUser);

		/*Set the object in the message we send to the class under test LoginQuery in the function
		 * createLoginMessageForServer()*/
		messageFromClassUnderTest.setObject(loginForTest);

		try {
			answerReceived = (LoginQueries.createLoginMessageForServer(messageFromClassUnderTest)).getAnswer();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*Check if the answer is correct - use Assert true*/
		assertEquals(answerReceived, answerExpectedForTest);
	}

	@Test
	//Test Description: Check if user that already logged in return the right answer from the server
	//Test Input: userNameSupplierWorkerLoggedIn = "phsrw" , passwordSupplierWorkerLoggedIn = "phsrw"
	//Test Expected Output: Message received with Answer.ERROR_ALREADY_LOGGED_IN
   public void test_user_already_logged_in() {

		/*Expected result from LoginQuery.createLoginMessageForServer(Message messageFromClient)*/
		Answer answerExpectedForTest = Answer.ERROR_ALREADY_LOGGED_IN;
		Answer answerReceived = null;

		/*Put in the Login object the userName and password for user already logged in */
		loginForTest = new Login(userNameSupplierWorkerLoggedIn, passwordSupplierWorkerLoggedIn);

		/*Set the object in the message we send to the class under test LoginQuery in the function
		 * createLoginMessageForServer()*/
		messageFromClassUnderTest.setObject(loginForTest);

		try {
			answerReceived = (LoginQueries.createLoginMessageForServer(messageFromClassUnderTest)).getAnswer();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*Check if the answer is correct - use Assert true*/
		assertEquals(answerReceived, answerExpectedForTest);
	}

	@Test
	//Test Description: Check if user that not found return the right answer from the server
	//Test Input: userNameCustomer = "" , passwordCustomer = ""
	//Test Expected Output: Message received with Answer.ERROR_USER_NOT_FOUND
   public void test_user_not_found() {

		/*Expected result from LoginQuery.createLoginMessageForServer(Message messageFromClient)*/
		Answer answerExpectedForTest = Answer.ERROR_USER_NOT_FOUND;
		Answer answerReceived = null;

		/*Put in the Login object the userName and password for user not found */
		loginForTest = new Login("", "");

		/*Set the object in the message we send to the class under test LoginQuery in the function
		 * createLoginMessageForServer()*/
		messageFromClassUnderTest.setObject(loginForTest);

		try {
			answerReceived = (LoginQueries.createLoginMessageForServer(messageFromClassUnderTest)).getAnswer();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*Check if the answer is correct - use Assert true*/
		assertEquals(answerReceived, answerExpectedForTest);
	}

	@Test
	//Test Description: Check if user that has frozen status return Answer.ERROR_USER_NOT_CONFIRMED; from the server
	//Test Input:userNameForFrozenStatusUser = "lior" , passwordForFrozenStatusUser = "lior"
	//Test Expected Output: Message received with Answer.CREATE_USER_PORTAL_FOR_SUPPLIER
   public void test_user_frozen() {

		/*Expected result from LoginQuery.createLoginMessageForServer(Message messageFromClient)*/
		Answer answerExpectedForTest = Answer.CREATE_USER_PORTAL_FOR_CUSTOMER;
		Answer answerReceived = null;

		/*Put in the Login object the userName and password for user frozen */
		loginForTest = new Login(userNameForFrozenStatusUser, passwordForFrozenStatusUser);

		/*Set the object in the message we send to the class under test LoginQuery in the function
		 * createLoginMessageForServer()*/
		messageFromClassUnderTest.setObject(loginForTest);
		LoginQueries.logOutUserWithIdAndType("RC", idForFrozenStatusUser);
		try {
			answerReceived = (LoginQueries.createLoginMessageForServer(messageFromClassUnderTest)).getAnswer();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*Check if the answer is correct - use Assert true*/
		assertEquals(answerReceived, answerExpectedForTest);
	}

	@Test
	//Test Description: Check if when sending null message, Answer.ERROR_USER_NOT_CONFIRMED; from the server
	//Test Input:username = "userNameForNull" , password = "PasswordForNull"
	//Test Expected Output: Message received with Answer.MESSAGE_IS_NULL
   public void test_message_is_null() {

		/*Expected result from LoginQuery.createLoginMessageForServer(Message messageFromClient)*/
		Answer answerExpectedForTest = Answer.MESSAGE_IS_NULL;
		Answer answerReceived = null;

		/*Put in the Login object the userName and password for null message */
		loginForTest = new Login("userNameForNull", "PasswordForNull");

		/*Set the object in the message we send to the class under test LoginQuery in the function
		 * createLoginMessageForServer()*/
		messageFromClassUnderTest.setObject(loginForTest);

		try {
			answerReceived = (LoginQueries.createLoginMessageForServer(null)).getAnswer();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*Check if the answer is correct - use Assert true*/
		assertEquals(answerReceived, answerExpectedForTest);
	}

	@Test
	//Test Description: Check if when sending message with object that is not login (ex: null) , Answer.OBJECT_IS_NOT_LOGIN; from the server
	//Test Input:username = "userNameForNotLogin" , password = "PasswordForNotLogin"
	//Test Expected Output: Message received with Answer.OBJECT_IS_NOT_LOGIN
   public void test_object_of_message_is_not_login() {

		/*Expected result from LoginQuery.createLoginMessageForServer(Message messageFromClient)*/
		Answer answerExpectedForTest = Answer.OBJECT_IS_NOT_LOGIN;
		Answer answerReceived = null;

		/*Put in the Login object the userName and password for non login object */
		loginForTest = new Login("userNameForNotLogin", "PasswordForNotLogin");

		/*Set the object in the message we send to the class under test LoginQuery in the function
		 * createLoginMessageForServer()*/
		messageFromClassUnderTest.setObject(null);

		try {
			answerReceived = (LoginQueries.createLoginMessageForServer(messageFromClassUnderTest)).getAnswer();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*Check if the answer is correct - use Assert true*/
		assertEquals(answerReceived, answerExpectedForTest);
	}
	
}
