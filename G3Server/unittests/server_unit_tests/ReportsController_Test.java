package server_unit_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import communication.Answer;
import communication.Message;
import communication.Task;
import jdbc.mySqlConnection;
import query.Query;
import reports.ReportsController;
import util.SupplierByReport;

public class ReportsController_Test {
	/* Set connection to DB for testing */
	public mySqlConnection sqlConnection = new mySqlConnection();

	/* DB name */
	final public static String DEFAULT_DB_NAME = "jdbc:mysql://localhost/semesterialproject?serverTimezone=IST";

	/* DB user name */
	final public static String DEFAULT_DB_USER = "root";

	/* DB password for specific user */
	final public static String DEFAULT_DB_PASSWORD = "09000772Mm-";

	/* Message received form the test to check assertions */
	public Message messageFromClassUnderTest;

	/* String received from client for report pulling */
	String[] branchAndDateTesting = new String[3];

	/* String received from client for report pulling */
	SupplierByReport[] suppliersTesting = new SupplierByReport[1];

	///////////////////////////////////////////////////////////////////////////////////////////////////

	/* Before each test we will do all those actions for clean start in each test */
	@Before
    public void setUp() throws Exception {
		sqlConnection.connectToDB(DEFAULT_DB_NAME, DEFAULT_DB_USER, DEFAULT_DB_PASSWORD, false);
		Query.setConnectionFromServerToDB(sqlConnection.getConnection());
		/*
		 * Set the message to Get System Reports Task, Answer Wait response for the
		 * server to change and the object returned to null
		 */
		messageFromClassUnderTest = new Message(Task.GET_SYSTEM_REPORTS, Answer.WAIT_RESPONSE, null);
		suppliersTesting[0] = new SupplierByReport();
	}

	@Test
	// Test Description: Check if report type string is null a message with a null
	// object is returned
	// Test Input: branchAndDateTesting[0] = "NORTH", branchAndDateTesting[1] =
	// "2021-10-01", branchAndDateTesting[2] = null
	// Test Expected Output: Message received with null object
    public void test_report_type_string_null() {
		Message resultFromDb;

		/*
		 * Expected result from ReportsController.getSuppliersByBranch(Message messageFromClient)
		 */
		Message answerExpectedForTest = new Message(Task.GET_SYSTEM_REPORTS, Answer.SENT_REPORT_QUARTERLY_LIST, null);
		
		/* Put in the branch and date string data for testing*/
		branchAndDateTesting[0] = "NORTH";
		branchAndDateTesting[1] = "2021-10-01";
		branchAndDateTesting[2] = null;

		/*
		 * Set the object in the message we send to the class under test ReportsController in
		 * the function getSuppliersByBranch()
		 */
		messageFromClassUnderTest.setObject(branchAndDateTesting);
		resultFromDb = ReportsController.getSuppliersByBranch(messageFromClassUnderTest);

		/* Check if the returned message object is null, as expected - use Assert true */
		assertTrue(resultFromDb.getObject() == answerExpectedForTest.getObject());
	}

	@Test
	// Test Description: Check if report date string is null a message with a null
	// object is returned
	// Test Input: branchAndDateTesting[0] = "NORTH", branchAndDateTesting[1] =
	// null, branchAndDateTesting[2] = "quarterly"
	// Test Expected Output: Message received with null object
    public void test_report_date_null() {
		Message resultFromDb;

		/*
		 * Expected result from ReportsController.getSuppliersByBranch(Message messageFromClient)
		 */
		Message answerExpectedForTest = new Message(Task.GET_SYSTEM_REPORTS, Answer.SENT_REPORT_QUARTERLY_LIST, null);

		/* Put in the branch and date string data for testing*/
		branchAndDateTesting[0] = "NORTH";
		branchAndDateTesting[1] = null;
		branchAndDateTesting[2] = "quarterly";

		/*
		 * Set the object in the message we send to the class under test ReportsController in
		 * the function getSuppliersByBranch()
		 */
		messageFromClassUnderTest.setObject(branchAndDateTesting);
		resultFromDb = ReportsController.getSuppliersByBranch(messageFromClassUnderTest);

		/* Check if the returned message object is null, as expected - use Assert true */
		assertTrue(resultFromDb.getObject() == answerExpectedForTest.getObject());
	}

	@Test
	// Test Description: Check if report date string is null a message with a null
	// object is returned
	// Test Input: branchAndDateTesting[0] = null, branchAndDateTesting[1] =
	// "2021-10-01", branchAndDateTesting[2] = "quarterly"
	// Test Expected Output: Message received with null object
    public void test_report_branch_null() {
		Message resultFromDb;

		/*
		 * Expected result from ReportsController.getSuppliersByBranch(Message messageFromClient)
		 */
		Message answerExpectedForTest = new Message(Task.GET_SYSTEM_REPORTS, Answer.SENT_REPORT_QUARTERLY_LIST, null);

		/* Put in the branch and date string data for testing*/
		branchAndDateTesting[0] = null;
		branchAndDateTesting[1] = "2021-10-01";
		branchAndDateTesting[2] = "quarterly";

		/*
		 * Set the object in the message we send to the class under test ReportsController in
		 * the function getSuppliersByBranch()
		 */
		messageFromClassUnderTest.setObject(branchAndDateTesting);
		resultFromDb = ReportsController.getSuppliersByBranch(messageFromClassUnderTest);

		/* Check if the returned message object is null, as expected - use Assert true */
		assertTrue(resultFromDb.getObject() == answerExpectedForTest.getObject());
	}

	@Test
	// Test Description: Check if report request string is null, a null object is
	// returned
	// Test Input: branchAndDateTesting=null
	// Test Expected Output: Message received with null object
    public void test_report_request_string_array_null() {
		Message resultFromDb;

		/*
		 * Expected result from ReportsController.getSuppliersByBranch(Message messageFromClient)
		 */
		Message answerExpectedForTest = new Message(Task.GET_SYSTEM_REPORTS, Answer.SENT_REPORT_QUARTERLY_LIST, null);

		/* Put in the branch and date string data for testing*/
		branchAndDateTesting = null;

		/*
		 * Set the object in the message we send to the class under test ReportsController in
		 * the function getSuppliersByBranch()
		 */
		messageFromClassUnderTest.setObject(branchAndDateTesting);
		resultFromDb = ReportsController.getSuppliersByBranch(messageFromClassUnderTest);

		/* Check if the returned message object is null, as expected - use Assert true */
		assertTrue(resultFromDb.getObject() == answerExpectedForTest.getObject());
	}

	@Test
	// Test Description: Check if report date string is null a message with a null
	// object is returned
	// Test Input: branchAndDateTesting[0] = "NORTH", branchAndDateTesting[1] =
	// null, branchAndDateTesting[2] = "quarterly"
	// Test Expected Output: Message received with null object
    public void test_report_date_wrong_format() {
		Message resultFromDb;

		/*
	     * Expected result from ReportsController.getSuppliersByBranch(Message messageFromClient)
	     */
		Message answerExpectedForTest = new Message(Task.GET_SYSTEM_REPORTS, Answer.SENT_REPORT_QUARTERLY_LIST, null);

		/* Put in the branch and date string data for testing*/
		branchAndDateTesting[0] = "NORTH";
		branchAndDateTesting[1] = "gibberish";
		branchAndDateTesting[2] = "quarterly";

	/*
	 * Set the object in the message we send to the class under test ReportsController in
	 * the function getSuppliersByBranch()
	 */
		messageFromClassUnderTest.setObject(branchAndDateTesting);
		resultFromDb = ReportsController.getSuppliersByBranch(messageFromClassUnderTest);

		/* Check if the returned message object is null, as expected - use Assert true */
		assertTrue(resultFromDb.getObject() == answerExpectedForTest.getObject());
	}

	@Test
	//Test Description: Check if pulled report data is equal to expected report data
	//Test Input: 	branchAndDateTesting[0] = "NORTH", branchAndDateTesting[1] = "2021-04-01", branchAndDateTesting[2] = "quarterly"
	//Test Expected Output: both objects have the same values (both supliersTesting, and returned array from db)
    public void test_get_quarterly_report() {
		Message resultFromDb;
		branchAndDateTesting[0] = "NORTH";
		branchAndDateTesting[1] = "2021-04-01";
		branchAndDateTesting[2] = "quarterly";
		messageFromClassUnderTest.setObject(branchAndDateTesting);

		Message answerExpectedForTest = new Message(Task.GET_SYSTEM_REPORTS, Answer.SENT_REPORT_QUARTERLY_LIST, null);
		suppliersTesting[0].setSupplierId("1112");
		suppliersTesting[0].setSupplierName("Domino's Pizza");
		suppliersTesting[0].setIncome("545.0");
		suppliersTesting[0].setSupplierBranch("NORTH");
		suppliersTesting[0].setSupplierFee(9.80);
		suppliersTesting[0].setIssueDate("2021-04-01");
		suppliersTesting[0].setReportType("quarterly");
		suppliersTesting[0].setTypeSums(new int[]{3,2,2,3,5});
		suppliersTesting[0].setTotalOrders(7);
		suppliersTesting[0].setLateOrders(3);
		suppliersTesting[0].setAverageSupplyTime("00:42:34");

		SupplierByReport[] expected= ((SupplierByReport[][]) ReportsController.getSuppliersByBranch(messageFromClassUnderTest).getObject())[3];

		/* Check if the answer is correct - use Assert true */
		assertEquals(suppliersTesting[0].getSupplierId(), expected[0].getSupplierId());
		assertEquals(suppliersTesting[0].getSupplierName(), expected[0].getSupplierName());
		assertEquals(suppliersTesting[0].getIncome(), expected[0].getIncome());
		assertEquals(suppliersTesting[0].getSupplierBranch(), expected[0].getSupplierBranch());
		assertEquals(suppliersTesting[0].getSupplierFee(), expected[0].getSupplierFee());
		assertEquals(suppliersTesting[0].getReportType(), expected[0].getReportType());
		for(int i=0;i<suppliersTesting[0].getTypeSums().length;i++) {
			assertEquals(suppliersTesting[0].getTypeSums()[i],expected[0].getTypeSums()[i]);
		}
		assertEquals(suppliersTesting[0].getTotalOrders(), expected[0].getTotalOrders());
		assertEquals(suppliersTesting[0].getLateOrders(), expected[0].getLateOrders());
		assertEquals(suppliersTesting[0].getAverageSupplyTime(), expected[0].getAverageSupplyTime());
	}

}
