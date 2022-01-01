package testlogin;

import query.LoginQueries;
import query.Query;
import users.Login;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;

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
	mySqlConnection sqlConnection;
	
	
	/*Create login object*/
	public Login loginForTest;
	
	/*Message received form the test to check assertions*/
	public Message messageFromClassUnderTest;
	
	/*Create all the userName and Password for the testing*/
	
	/*User name and Password for regular customer*/
	private String userNameCustomer = "cu";
	private String passwordCustomer = "cu";
	
	/*User name and Password for regular customer*/
	private String userNameBusinessCustomer = "bc";
	private String passwordBusinessCustomer = "bc";
	
	/*User name and Password for regular branch manager*/
	private String userNameBranchManager = "bmn";
	private String passwordBranchManager = "bmn";
	
	/*User name and Password for supplier worker*/
	private String userNameSupplierWorker = "phsw";
	private String passwordSupplierWorker = "phsw";
	
	/*User name and Password for Ceo*/
	private String userNameCeo = "ceo";
	private String passwordCeo = "ceo";
	
	/*User name and Password for hrManager*/
	private String userNameHrManager = "intelhr";
	private String passwordHrManager = "intelhr";
	 
		
	/*Before each test we will do all those actions for clean start in each test*/
	@BeforeEach
	void setUp() throws Exception {		
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
	
}
