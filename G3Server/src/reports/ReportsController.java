package reports;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import communication.Answer;
import communication.Message;
import query.Query;
import query.ReportQueries;
import util.SupplierByReport;

/**
 * Class description: 
 * This is a class for handling supplier report objects
 * allows creating, storing and retrieving reports
 * also creates a return message when polled from the client
 * 
 * @author Alexander, Martinov.
 * 
 * @version 21/12/2021
 */

public class ReportsController{
	
	/**
	 * Class members description:
	 */
	
	/**
	 * Supplier list
	 */
	private static SupplierByReport[] suppliers;
	
    /**
     * Runs and saves all necessary queries to local array for report documenting in db
     * 
     * @param fromDate date when report starts
     * @param toDate date when report ends
     * @param type report type (monthly/quarterly)
     */
	public static void pullData(String fromDate,String toDate, String type) {
		suppliers=ReportQueries.getSuppliersForReport();
		if(suppliers!=null) {
		ReportQueries.getIncomeForSuppliers(suppliers,fromDate, toDate);
		ReportQueries.buildMenusByType(suppliers,fromDate, toDate);
		ReportQueries.getOrdersByType(suppliers,fromDate, toDate);
		ReportQueries.getLateSuppliesAmount(suppliers,fromDate, toDate);
		ReportQueries.getOrderAmounts(suppliers,fromDate, toDate);
		ReportQueries.getAverageSupplyTime(suppliers,fromDate, toDate);
		ReportQueries.setIssueDates(suppliers, fromDate);
		ReportQueries.setReportType(suppliers, type);
		}
	}
	
    /**
     *Uploads currently stores reports list to db
     */
	public static void uploadReports() {
		if(suppliers!=null) {
		for(int i=0;i<suppliers.length;i++)
		Query.saveReportToDb(suppliers[i]);
		}
	}
	
	/**
     *Downloads specified reports list to working report list
     *
     * @param fromDate date when report starts
     * @param toDate date when report ends
     * @param type report type (monthly/quarterly)
     */
	public static void downloadReports(String fromDate,String branch, String type) {
		suppliers=ReportQueries.getSuppliersFromDb(fromDate,branch, type);
	}
	
    /**
     *Prepares message with reports list to for client
     *
     *@param messageFromClient message received from client
     */
	public static Message getSuppliersByBranch(Message messageFromClient) {
		
		Message returnMessageToClient = messageFromClient;
		String[] branchAndDate = (String[])messageFromClient.getObject();
		if(branchAndDate==null||branchAndDate[0]==null||branchAndDate[1]==null||branchAndDate[2]==null||!isDateFormatValid(branchAndDate[1])) {
			returnMessageToClient.setObject(null);
			returnMessageToClient.setAnswer(Answer.SENT_REPORT_SUPPLIERS_LIST);
		}
		else if(branchAndDate[2].equals("quarterly")) {
			returnMessageToClient.setObject(getQuarterReports(branchAndDate));
			returnMessageToClient.setAnswer(Answer.SENT_REPORT_QUARTERLY_LIST);
		}
		else if(branchAndDate[2].equals("receipt")){
			returnMessageToClient.setObject(getSupplierIdReport(branchAndDate));
			returnMessageToClient.setAnswer(Answer.SENT_REPORT_SUPPLIERS_LIST);
		}
		else {
		SupplierByReport[] sentSuppliers=ReportQueries.getSuppliersFromDb(branchAndDate[1],branchAndDate[0], branchAndDate[2]);
		returnMessageToClient.setObject(sentSuppliers);
		returnMessageToClient.setAnswer(Answer.SENT_REPORT_SUPPLIERS_LIST);
		}
		return returnMessageToClient;
	}
	
	private static boolean isDateFormatValid(String date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date d;
		try {
			d = dateFormat.parse(date);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	private static Object getSupplierIdReport(String[] supplierIdAndDate) {
		ResultSet rs;
		int i=0;
		SupplierByReport[] supplier=new SupplierByReport[1];
		rs=Query.getRowsFromTableInDB("reports", "supplier='"+supplierIdAndDate[0]+"' and issueDate='"+supplierIdAndDate[1]+"' and reportType='monthly'");
		try {
		while(rs.next()) {
				if(i==0) {
					byte[] st = (byte[]) rs.getObject(6);
				      ByteArrayInputStream baip = new ByteArrayInputStream(st);
				      ObjectInputStream ois = new ObjectInputStream(baip);
				      supplier[i] = (SupplierByReport) ois.readObject();
				      i++;
				}
		}
			} catch (Exception e) {
				e.printStackTrace();
			}
		if(i==0)
			return null;
		return supplier;
	}
	
	/**
	 * Gets the three reports needed for a quarter starting from sent date
	 * 
	 * @param branchAndDate branch and date of first report in quarter
	 * @return supplier reports of next three months
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
     *Prepares message notifying of successful file upload
     *
     *@param messageFromClient message received from client
     *@param type report type being sent
     *@return message answer to the client side
     */
	public static Message uploadQuarterlyReport(Message messageFromClient) {
		Message returnMessageToClient = messageFromClient;
		Query.savePdfToDb((Object[])messageFromClient.getObject());
		returnMessageToClient.setObject(null);
		returnMessageToClient.setAnswer(Answer.REPORT_UPLOADED);
		return returnMessageToClient;
	}
	
    /**
     *Prepares message with pdf list to for client
     *
     *@param messageFromClient message received from client
     *@param type report type being sent
     *@return message answer to the client side
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
     *Prepares message with pdf file byte array for client
     *
     *@param messageFromClient message received from client
     *@param type report type being sent
     *@return message answer to the client side
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
