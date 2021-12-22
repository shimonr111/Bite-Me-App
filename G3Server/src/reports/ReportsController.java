package reports;

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
     *@param type report type being sent
     */
	public static Message getSuppliersByBranch(Message messageFromClient) {
		Message returnMessageToClient = messageFromClient;
		String[] branchAndDate = (String[])messageFromClient.getObject();
		SupplierByReport[] sentSuppliers=ReportQueries.getSuppliersFromDb(branchAndDate[1],branchAndDate[0], branchAndDate[2]);
		returnMessageToClient.setObject(sentSuppliers);
		returnMessageToClient.setAnswer(Answer.SENT_REPORT_SUPPLIERS_LIST);
		return returnMessageToClient;
	}
}
