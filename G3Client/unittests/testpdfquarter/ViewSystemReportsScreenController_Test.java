package testpdfquarter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clientanalyze.AnalyzeMessageFromServer;
import communication.Answer;
import communication.Message;
import communication.Task;
import controllers_gui.LoginScreenController;
import controllers_gui.ViewSystemReportsScreenController;
import javafx.event.ActionEvent;
import testlogin.Login_Test.StubGetLoginDetails;
import util.IGetQuarterlyReport;
import util.SupplierByReport;

public class ViewSystemReportsScreenController_Test {
	private ViewSystemReportsScreenController viewSystemReportsScreenController;
	private StubGetQuarterlyReport stubGetQuarterlyReport;
    public class StubGetQuarterlyReport implements IGetQuarterlyReport{
    	private String branchName;
    	private String returnMessage;
    	private SupplierByReport[][] returnedReportFromDb;
    	private String returnDate;
    	private File testFileDirectory;
		public File getTestFileDirectory() {
			return testFileDirectory;
		}

		public void setTestFileDirectory(File testFileDirectory) {
			this.testFileDirectory = testFileDirectory;
		}

		public String getReturnDate() {
			return returnDate;
		}

		public void setReturnDate(String returnDate) {
			this.returnDate = returnDate;
		}

		public SupplierByReport[][] getReturnedReportFromDb() {
			return returnedReportFromDb;
		}

		public void setReturnedReportFromDb(SupplierByReport[][] returnedReportFromDb) {
			this.returnedReportFromDb = returnedReportFromDb;
		}

		public void setReturnMessage(String returnMessage) {
			this.returnMessage = returnMessage;
		}

		public String getReturnMessage() {
			return returnMessage;
		}

		public String getBranchName() {
			return branchName;
		}

		public void setBranchName(String branchName) {
			this.branchName = branchName;
		}

		@Override
		public String setHomeBranchForQuarterlyReport() {
			return branchName;
		}

		@Override
		public void createReportInDb(Message message) {
			message.setObject(returnedReportFromDb);
			message.setAnswer(Answer.SENT_REPORT_QUARTERLY_LIST);
			try {
				AnalyzeMessageFromServer.analyzeMessageFromServer(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void setMessageToUser(String message) {
			returnMessage= message;
		}

		@Override
		public File savePdfFile(String date) {
			return getTestFileDirectory();
		}

		@Override
		public String getPdfDate() {
			return getReturnDate();
		}
    }
    
	/*Before each test we will create the Stub object*/
	@BeforeEach
	void setUp() throws Exception {	
		stubGetQuarterlyReport = new StubGetQuarterlyReport();
	}
	@AfterEach
	void tearDown() throws Exception {
		File testPdf = new File ("C://G3BiteMe//test.pdf");
		testPdf.delete();
	}
	
	
	@Test
	//Test Description: check null reports object returned from db handling
	//Test Input: branchName= "NORTH", returnDate="2021-03-01", returnedReportFromDb=null;
	//Test Expected Output: "No quarterly reports found" printed
	void test_null_suppliers_returned_from_db() {
		stubGetQuarterlyReport.setReturnedReportFromDb(null);	
		stubGetQuarterlyReport.setBranchName("NORTH");
		stubGetQuarterlyReport.setReturnDate("2021-03-01");
		viewSystemReportsScreenController = new ViewSystemReportsScreenController(stubGetQuarterlyReport);
		viewSystemReportsScreenController.doQuarterlyReportLogic();
		assertEquals("No quarterly reports found",stubGetQuarterlyReport.getReturnMessage());	
	}
	
	@Test
	//Test Description: check null reports array returned from db handling
	//Test Input: branchName= "NORTH", returnDate="2021-03-01", returnedReportFromDb[3]=null;
	//Test Expected Output: "No quarterly reports found" printed
	void test_null_reports_array_returned() {
		SupplierByReport[][] testReport = new SupplierByReport[4][];
		testReport[3]= null;
		stubGetQuarterlyReport.setReturnedReportFromDb(testReport);	
		stubGetQuarterlyReport.setBranchName("NORTH");
		stubGetQuarterlyReport.setReturnDate("2021-03-01");
		viewSystemReportsScreenController = new ViewSystemReportsScreenController(stubGetQuarterlyReport);
		viewSystemReportsScreenController.doQuarterlyReportLogic();
		assertEquals("No quarterly reports found",stubGetQuarterlyReport.getReturnMessage());	
	}
	
	@Test
	//Test Description: check if text in created pdf equals expected test when making report array manually
	//Test Input: branchName= "NORTH", returnDate="2021-04-01", returnedReportFromDb[3]=testReport;
	//Test Expected Output: expecteTextInPdf equals text read from pdf into resultTextInPdf
	void test_check_created_pdf_content() {
		
		String resultTextInPdf="";
		String expectedTextInPdf = ("Quarterly Income Report By Supplier\r\n"
				+ "Issued on: 2021-04-01\r\n"
				+ "Supplier ID: 1112 Supplier Name: Domino's Pizza\r\n"
				+ "Total Income: 545.0\r\n"
				+ "BM cut in percentage: 9.80\r\n"
				+ "Total net income of supplier: 491.59\r\n"
				+ "Total BM cut: 53.41\r\n"
				+ "Quarterly Order Report By Supplier\r\n"
				+ "Issued on: 2021-04-01\r\n"
				+ "Supplier ID: 1112 Supplier Name: Domino's Pizza\r\n"
				+ "Salads Ordered: 3\r\n"
				+ "Opening Dishes Ordered: 2\r\n"
				+ "Main Dishes Ordered: 2\r\n"
				+ "Desserts Ordered: 3\r\n"
				+ "Drinks Ordered: 5\r\n"
				+ "Quarterly Performance Report By Supplier\r\n"
				+ "Issued on: 2021-04-01\r\n"
				+ "Supplier ID: 1112 Supplier Name: Domino's Pizza\r\n"
				+ "Total Orders: 7\r\n"
				+ "Late Orders: 3\r\n"
				+ "Late Orders percentage: 42.9\r\n"
				+ "Average Supply Time: 00:42:34\r\n");
		SupplierByReport[][] testReport = new SupplierByReport[4][];
		testReport[3]= new SupplierByReport[1];
		testReport[3][0]= new SupplierByReport();
		testReport[3][0].setSupplierId("1112");
		testReport[3][0].setSupplierName("Domino's Pizza");
		testReport[3][0].setIncome("545.0");
		testReport[3][0].setSupplierBranch("NORTH");
		testReport[3][0].setSupplierFee(9.80);
		testReport[3][0].setIssueDate("2021-04-01");
		testReport[3][0].setReportType("quarterly");
		testReport[3][0].setTypeSums(new int[]{3,2,2,3,5});
		testReport[3][0].setTotalOrders(7);
		testReport[3][0].setLateOrders(3);
		testReport[3][0].setAverageSupplyTime("00:42:34");
		stubGetQuarterlyReport.setReturnedReportFromDb(testReport);	
		stubGetQuarterlyReport.setBranchName("NORTH");
		stubGetQuarterlyReport.setReturnDate("2021-04-01");
		stubGetQuarterlyReport.setTestFileDirectory(new File ("C://G3BiteMe//test.pdf"));
		viewSystemReportsScreenController = new ViewSystemReportsScreenController(stubGetQuarterlyReport);
		viewSystemReportsScreenController.doQuarterlyReportLogic();
		try {
			PDDocument doc;
			doc = PDDocument.load(stubGetQuarterlyReport.getTestFileDirectory());
			PDFTextStripper resultPdfStripper = new PDFTextStripper();
			resultTextInPdf = resultPdfStripper.getText(doc);
			doc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertEquals(resultTextInPdf,expectedTextInPdf);
	}
	
	@Test
	//Test Description: check if text in created pdf equals expected test when making report array manually
	//Test Input: branchName= "NORTH", returnDate="2021-04-01", returnedReportFromDb[3]=testReport, testReport[3][0]= null
	//Test Expected Output: expecteTextInPdf equals text read from pdf into resultTextInPdf, no exceptions thrown
	void test_check_null_report_pdf_content() {
		String resultTextInPdf="";
		String expectedTextInPdf = ("Quarterly Income Report By Supplier\r\n"
				+ "Quarterly Order Report By Supplier\r\n"
				+ "Quarterly Performance Report By Supplier\r\n");
		SupplierByReport[][] testReport = new SupplierByReport[4][];
		testReport[3]= new SupplierByReport[1];
		testReport[3][0]= null;
		stubGetQuarterlyReport.setReturnedReportFromDb(testReport);	
		stubGetQuarterlyReport.setBranchName("NORTH");
		stubGetQuarterlyReport.setReturnDate("2021-04-01");
		stubGetQuarterlyReport.setTestFileDirectory(new File ("C://G3BiteMe//test.pdf"));
		viewSystemReportsScreenController = new ViewSystemReportsScreenController(stubGetQuarterlyReport);
		viewSystemReportsScreenController.doQuarterlyReportLogic();
		try {
			PDDocument doc;
			doc = PDDocument.load(stubGetQuarterlyReport.getTestFileDirectory());
			PDFTextStripper resultPdfStripper = new PDFTextStripper();
			resultTextInPdf = resultPdfStripper.getText(doc);
			doc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(resultTextInPdf,expectedTextInPdf);
	}
	
	@Test
	//Test Description: check if text in created pdf equals expected test when making report array manually
	//Test Input: branchName= "NORTH", returnDate="2021-04-01", returnedReportFromDb[3]=testReport, testReport[3][0]= null
	//Test Expected Output: expecteTextInPdf equals text read from pdf into resultTextInPdf, no exceptions thrown
	void test_check_null_directory() {
		String resultTextInPdf="";
		String expectedTextInPdf = ("Quarterly Income Report By Supplier\r\n"
				+ "Quarterly Order Report By Supplier\r\n"
				+ "Quarterly Performance Report By Supplier\r\n");
		SupplierByReport[][] testReport = new SupplierByReport[4][];
		testReport[3]= new SupplierByReport[1];
		testReport[3][0]= null;
		stubGetQuarterlyReport.setReturnedReportFromDb(testReport);	
		stubGetQuarterlyReport.setBranchName("NORTH");
		stubGetQuarterlyReport.setReturnDate("2021-04-01");
		stubGetQuarterlyReport.setTestFileDirectory(null);
		viewSystemReportsScreenController = new ViewSystemReportsScreenController(stubGetQuarterlyReport);
		assertFalse(viewSystemReportsScreenController.doQuarterlyReportLogic());
	}
}
