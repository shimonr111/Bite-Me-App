package testlogin;

import query.LoginQueries;
import query.Query;
import users.Login;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import bitemeserver.BiteMeServerUI;
import communication.Answer;
import communication.Message;
import communication.Task;
import jdbc.mySqlConnection;
import junit.framework.Assert;

/*This is the class under test in which we will check the login phase*/
public class LoginQueries_Test {
	
	/*Set connection to DB for testing*/
	public mySqlConnection sqlConnection = new mySqlConnection();
	
	/*DB name*/
	final public static String DEFAULT_DB_NAME = "jdbc:mysql://localhost/semesterialproject?serverTimezone=IST";
	
	/*DB user name*/
	final public static String DEFAULT_DB_USER = "root";
	
	/*DB password for specific user*/
	final public static String DEFAULT_DB_PASSWORD = "S6FW8Ps6fw8p!";
	
	/*Create login object*/
	public Login loginForTest;
	
	/*Message received form the test to check assertions*/
	public Message messageFromClassUnderTest;
	
	/*Create all the userName and Password for the testing*/
	
	/*User name and Password for regular customer*/
	private String userNameCustomer = "cu";
	private String passwordCustomer = "cu";
	
	/*User name and Password for business customer*/
	private String userNameBusinessCustomer = "bc";
	private String passwordBusinessCustomer = "bc";
	
	/*User name and Password for branch manager*/
	private String userNameBranchManager = "bmn";
	private String passwordBranchManager = "bmn";
	
	/*User name and Password for supplier worker*/
	private String userNameSupplierWorker = "phsw";
	private String passwordSupplierWorker = "phsw";
	
	/*User name and Password for supplier worker already logged in*/
	private String userNameSupplierWorkerLoggedIn = "phsrw";
	private String passwordSupplierWorkerLoggedIn = "phsrw";
	
	/*User name and Password for Ceo*/
	private String userNameCeo = "ceo";
	private String passwordCeo = "ceo";
	
	/*User name and Password for hrManager*/
	private String userNameHrManager = "intelhr";
	private String passwordHrManager = "intelhr";
	 
	/*User name and password for not confirmed user*/
	private String userNameForNotConfirmedStatusUser = "agadircc";
	private String passwordForNotConfirmedStatusUser = "agadircc";
	
	/*Before each test we will do all those actions for clean start in each test*/
	@BeforeEach
	void setUp() throws Exception {	
		sqlConnection.connectToDB(DEFAULT_DB_NAME, DEFAULT_DB_USER, DEFAULT_DB_PASSWORD, false);
		Query.setConnectionFromServerToDB(sqlConnection.getConnection());
		/*Set the message to Login Task, Answer Wait response for the server to change and the object returned to null*/
		messageFromClassUnderTest = new Message(Task.LOGIN,Answer.WAIT_RESPONSE,null);
	}
	
	@Test
	//Test Description: Check if regular customer with correct user name and password return the right answer from the server
	//Test Input: userNameCustomer = "cu" , passwordCustomer = "cu"
	//Test Expected Output: Message received with Answer.CREATE_USER_PORTAL_FOR_CUSTOMER
	void test_customer_log_in_with_correct_user_name_and_password() {	
		
		/*Expected result from LoginQuery.createLoginMessageForServer(Message messageFromClient)*/
		Answer answerExpectedForTest = Answer.CREATE_USER_PORTAL_FOR_CUSTOMER;
		Answer answerReceived = null;
		
		/*Put in the Login object the userName and password for customer */
		loginForTest = new Login(userNameCustomer,passwordCustomer); 
		
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
		assertTrue(answerReceived == answerExpectedForTest);
	}
	
	@Test
	//Test Description: Check if business customer with correct user name and password return the right answer from the server
	//Test Input: userNameCustomer = "bc" , passwordCustomer = "bc"
	//Test Expected Output: Message received with Answer.CREATE_USER_PORTAL_FOR_BUSINESS_CUSTOMER
	void test_business_customer_log_in_with_correct_user_name_and_password() {	
		
		/*Expected result from LoginQuery.createLoginMessageForServer(Message messageFromClient)*/
		Answer answerExpectedForTest = Answer.CREATE_USER_PORTAL_FOR_BUSINESS_CUSTOMER;
		Answer answerReceived = null;
		
		/*Put in the Login object the userName and password for business customer */
		loginForTest = new Login(userNameBusinessCustomer,passwordBusinessCustomer); 
		
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
		assertTrue(answerReceived == answerExpectedForTest);
	}
	
	@Test
	//Test Description: Check if branch manager with correct user name and password return the right answer from the server
	//Test Input: userNameCustomer = "bmn" , passwordCustomer = "bmn"
	//Test Expected Output: Message received with Answer.CREATE_USER_PORTAL_FOR_BRANCH_MANAGER
	void test_branch_manager_log_in_with_correct_user_name_and_password() {	
		
		/*Expected result from LoginQuery.createLoginMessageForServer(Message messageFromClient)*/
		Answer answerExpectedForTest = Answer.CREATE_USER_PORTAL_FOR_BRANCH_MANAGER;
		Answer answerReceived = null;
		
		/*Put in the Login object the userName and password for business customer */
		loginForTest = new Login(userNameBranchManager,passwordBranchManager); 
		
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
		assertTrue(answerReceived == answerExpectedForTest);
	}
	
	@Test
	//Test Description: Check if supplier worker with correct user name and password return the right answer from the server
	//Test Input: userNameCustomer = "phsw" , passwordCustomer = "phsw"
	//Test Expected Output: Message received with Answer.CREATE_USER_PORTAL_FOR_SUPPLIER
	void test_supplier_worker_log_in_with_correct_user_name_and_password() {	
		
		/*Expected result from LoginQuery.createLoginMessageForServer(Message messageFromClient)*/
		Answer answerExpectedForTest = Answer.CREATE_USER_PORTAL_FOR_SUPPLIER;
		Answer answerReceived = null;
		
		/*Put in the Login object the userName and password for business customer */
		loginForTest = new Login(userNameSupplierWorker,passwordSupplierWorker); 
		
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
		assertTrue(answerReceived == answerExpectedForTest);
	}
	
	@Test
	//Test Description: Check if ceo with correct user name and password return the right answer from the server
	//Test Input: userNameCustomer = "ceo" , passwordCustomer = "ceo"
	//Test Expected Output: Message received with Answer.CREATE_USER_PORTAL_FOR_CEO_BITE_ME
	void test_ceo_log_in_with_correct_user_name_and_password() {	
		
		/*Expected result from LoginQuery.createLoginMessageForServer(Message messageFromClient)*/
		Answer answerExpectedForTest = Answer.CREATE_USER_PORTAL_FOR_CEO_BITE_ME;
		Answer answerReceived = null;
		
		/*Put in the Login object the userName and password for business customer */
		loginForTest = new Login(userNameCeo,passwordCeo); 
		
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
		assertTrue(answerReceived == answerExpectedForTest);
	}
	
	@Test
	//Test Description: Check if hr manager with correct user name and password return the right answer from the server
	//Test Input: userNameCustomer = "intelhr" , passwordCustomer = "intelhr"
	//Test Expected Output: Message received with Answer.CREATE_USER_PORTAL_FOR_HR_MANAGER
	void test_hr_manager_in_with_correct_user_name_and_password() {	
		
		/*Expected result from LoginQuery.createLoginMessageForServer(Message messageFromClient)*/
		Answer answerExpectedForTest = Answer.CREATE_USER_PORTAL_FOR_HR_MANAGER;
		Answer answerReceived = null;
		
		/*Put in the Login object the userName and password for HR manager */
		loginForTest = new Login(userNameHrManager,passwordHrManager); 
		
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
		assertTrue(answerReceived == answerExpectedForTest);
	}
	
	@Test
	//Test Description: Check if user that not confirmed return the right answer from the server
	//Test Input: userNameForNotConfirmedStatusUser = "agadircc" , passwordForNotConfirmedStatusUser = "agadircc"
	//Test Expected Output: Message received with Answer.ERROR_USER_NOT_CONFIRMED
	void test_user_not_confirmed() {	
		
		/*Expected result from LoginQuery.createLoginMessageForServer(Message messageFromClient)*/
		Answer answerExpectedForTest = Answer.ERROR_USER_NOT_CONFIRMED;
		Answer answerReceived = null;
		
		/*Put in the Login object the userName and password for user not found */
		loginForTest = new Login(userNameForNotConfirmedStatusUser,passwordForNotConfirmedStatusUser); 
		
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
		assertTrue(answerReceived == answerExpectedForTest);
	}
	
	@Test
	//Test Description: Check if user that already logged in return the right answer from the server
	//Test Input: userNameSupplierWorkerLoggedIn = "phsrw" , passwordSupplierWorkerLoggedIn = "phsrw"
	//Test Expected Output: Message received with Answer.ERROR_ALREADY_LOGGED_IN
	void test_user_already_logged_in() {	
		
		/*Expected result from LoginQuery.createLoginMessageForServer(Message messageFromClient)*/
		Answer answerExpectedForTest = Answer.ERROR_ALREADY_LOGGED_IN;
		Answer answerReceived = null;
		
		/*Put in the Login object the userName and password for user already logged in */
		loginForTest = new Login(userNameSupplierWorkerLoggedIn,passwordSupplierWorkerLoggedIn); 
		
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
		assertTrue(answerReceived == answerExpectedForTest);
	}
	
	@Test
	//Test Description: Check if user that not found return the right answer from the server
	//Test Input: userNameCustomer = "" , passwordCustomer = ""
	//Test Expected Output: Message received with Answer.ERROR_USER_NOT_FOUND
	void test_user_not_found() {	
		
		/*Expected result from LoginQuery.createLoginMessageForServer(Message messageFromClient)*/
		Answer answerExpectedForTest = Answer.ERROR_USER_NOT_FOUND;
		Answer answerReceived = null;
		
		/*Put in the Login object the userName and password for user not found */
		loginForTest = new Login("",""); 
		
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
		assertTrue(answerReceived == answerExpectedForTest);
	}
		
}
