package reports;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import communication.Answer;
import communication.Message;
import query.Query;
import query.ReportQueries;
import util.SupplierByReport;
/**
 * @author Alexander, Martinov
 * Class description: 
 * This is a class for handling supplier report objects
 * allows creating, storing and retrieving reports
 * also creates a return message when polled from the client
 * 
 * @version 21/12/2021
 */
public class ReportsController {
	/**
	 * Class members description:
	 */
	private static SupplierByReport[] suppliers;
    /**
     * Runs and saves all necessary queries to local array for report documenting in db
     * @param fromDate date when report starts
     * @param toDate date when report ends
     * @param type report type (monthly/quarterly)
     */
	public static void pullData(String fromDate,String toDate, String type) {
		suppliers=ReportQueries.getSuppliersForReport();
		ReportQueries.getIncomeForSuppliers(suppliers,fromDate, toDate);
		ReportQueries.buildMenusByType(suppliers,fromDate, toDate);
		ReportQueries.getOrdersByType(suppliers,fromDate, toDate);
		ReportQueries.getLateSuppliesAmount(suppliers,fromDate, toDate);
		ReportQueries.getOrderAmounts(suppliers,fromDate, toDate);
		ReportQueries.setIssueDates(suppliers, fromDate);
		ReportQueries.setReportType(suppliers, type);
	}
    /**
     *uploads currently stores reports list to db
     */
	public static void uploadReports() {
		for(int i=0;i<suppliers.length;i++)
		Query.saveReportToDb(suppliers[i]);
	}
	/**
     *downloads specified reports list to working report list
     * @param fromDate date when report starts
     * @param toDate date when report ends
     * @param type report type (monthly/quarterly)
     */
	public static void downloadReports(String fromDate,String branch, String type) {
		suppliers=ReportQueries.getSuppliersFromDb(fromDate,branch, type);
	}
    /**
     *prepares message with reports list to for client
     *@param messageFromClient message received from client
     */
	public static Message getSuppliersByBranch(Message messageFromClient) {
		Message returnMessageToClient = messageFromClient;
		String[] branchAndDate = (String[])messageFromClient.getObject();
		if(branchAndDate[2].equals("quarterly")) {
			returnMessageToClient.setObject(getQuarterReports(branchAndDate));
			returnMessageToClient.setAnswer(Answer.SENT_REPORT_QUARTERLY_LIST);
		}
		else {
		SupplierByReport[] sentSuppliers=ReportQueries.getSuppliersFromDb(branchAndDate[1],branchAndDate[0], branchAndDate[2]);
		returnMessageToClient.setObject(sentSuppliers);
		returnMessageToClient.setAnswer(Answer.SENT_REPORT_SUPPLIERS_LIST);
		}
		return returnMessageToClient;
	}
	/**
	 * gets the three reports needed for a quarter starting from sent date
	 * @param branchAndDate branch and date of first report in quarter
	 */
	public static SupplierByReport[][] getQuarterReports(String[] branchAndDate){
		SupplierByReport[][] nextThreeMonths=new SupplierByReport[4][];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(branchAndDate[1]));
		} catch (Exception e) {
			e.printStackTrace();
		}
		c.add(Calendar.MONTH, 1);
		String nextMonth = sdf.format(c.getTime());
		c.add(Calendar.MONTH, 1);
		String twoMonths = sdf.format(c.getTime());
		nextThreeMonths[0]=ReportQueries.getSuppliersFromDb(branchAndDate[1],branchAndDate[0], "monthly");
		nextThreeMonths[3]=ReportQueries.getSuppliersFromDb(branchAndDate[1],branchAndDate[0], "quarterly");
		nextThreeMonths[1]=ReportQueries.getSuppliersFromDb(nextMonth,branchAndDate[0], "monthly");
		nextThreeMonths[2]=ReportQueries.getSuppliersFromDb(twoMonths,branchAndDate[0], "monthly");
		return nextThreeMonths;
		
	}
    /**
     *prepares message notifying of successful file upload
     *@param messageFromClient message received from client
     *@param type report type being sent
     */
	public static Message uploadQuarterlyReport(Message messageFromClient) {
		Message returnMessageToClient = messageFromClient;
		Query.savePdfToDb((Object[])messageFromClient.getObject());
		returnMessageToClient.setObject(null);
		returnMessageToClient.setAnswer(Answer.REPORT_UPLOADED);
		return returnMessageToClient;
	}
    /**
     *prepares message with pdf list to for client
     *@param messageFromClient message received from client
     *@param type report type being sent
     */
	public static Message getUploadedList(Message messageFromClient) {
		Message returnMessageToClient = messageFromClient;
		String[] branchAndDate = (String[])messageFromClient.getObject();
		String[][] pdfList=null;
		pdfList=ReportQueries.getPdfList(branchAndDate);
		returnMessageToClient.setObject(pdfList);
		returnMessageToClient.setAnswer(Answer.PDF_LIST_SENT);
		return returnMessageToClient;
	}
    /**
     *prepares message with pdf file byte array for client
     *@param messageFromClient message received from client
     *@param type report type being sent
     */
	public static Message getPdfFile(Message messageFromClient) {
		Message returnMessageToClient = messageFromClient;
		String pdfId = (String)messageFromClient.getObject();
		byte[] pdfFile=null;
		pdfFile=ReportQueries.getPdfFileFromDb(pdfId);
		returnMessageToClient.setObject(pdfFile);
		returnMessageToClient.setAnswer(Answer.PDF_FILE_SENT);
		return returnMessageToClient;
	}
}
