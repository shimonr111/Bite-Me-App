package controllers_gui;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import util.SupplierByReport;
/**
 * 
 * @author Alexander, Martinov
 * Class description: 
 * This is a class for generating reports from SupplierByReport arrays
 * 
 * @version 21/12/2021
 */
public class ReportGenerator {
	/**
	 * Class members description:
	 */
	private static SupplierByReport[] suppliers;
	private static SupplierByReport[][] quarter;
	public static String[] getReportTypes() {
		String[] reportTypes= new String[3];
		reportTypes[0]="Incomes";
		reportTypes[1]="Orders";
		reportTypes[2]="Performance";
		return reportTypes;
	}
    /**
     * Performance report string generator
     * orders by supplierid, name and income
     */
	public static String generateIncomeReport() {
		String report="Income Report By Supplier\nIssued on: "+suppliers[0].getIssueDate()+"\n";
		for(SupplierByReport supplier:suppliers) {
			report=report+"Supplier ID: "+supplier.getSupplierId()+" Supplier Name: "+supplier.getSupplierName()+"\n";
			report=report+"Total Income: "+supplier.getIncome()+"\n";
			report=report+"BM cut in percentage: "+(new DecimalFormat("#0.00").format(suppliers[0].getSupplierFee()))+"\n";
			report=report+"Total net income of supplier: "+(new DecimalFormat("#0.00").format((1-((suppliers[0].getSupplierFee())/100))*Double.parseDouble(suppliers[0].getIncome())))+"\n";
			report=report+"Total BM cut: "+(new DecimalFormat("#0.00").format((((suppliers[0].getSupplierFee())/100))*Double.parseDouble(suppliers[0].getIncome())))+"\n";
		}
		return report;
	}
	public static String generateIncomeReport(String all) {
		String report="Income Report By Supplier\nIssued on: "+suppliers[0].getIssueDate()+"\n";
		for(SupplierByReport supplier:suppliers) {
			report=report+"Supplier ID: "+supplier.getSupplierId()+" Name: "+supplier.getSupplierName()+" Branch: "+supplier.getSupplierBranch().toLowerCase()+"\n";
			report=report+"Total Income: "+supplier.getIncome()+"\n";
			report=report+"BM cut in percentage: "+(new DecimalFormat("#0.00").format(suppliers[0].getSupplierFee()))+"\n";
			report=report+"Total net income of supplier: "+(new DecimalFormat("#0.00").format((1-((suppliers[0].getSupplierFee())/100))*Double.parseDouble(suppliers[0].getIncome())))+"\n";
			report=report+"Total BM cut: "+(new DecimalFormat("#0.00").format((((suppliers[0].getSupplierFee())/100))*Double.parseDouble(suppliers[0].getIncome())))+"\n";
		}
		return report;
	}
    /**
     * Performance report string generator
     * orders by supplierid, name and type of dish
     */
	public static String generateOrderReport() {
		String report="Order Report By Supplier and Dish Type\nIssued on: "+suppliers[0].getIssueDate()+"\n";
		for(SupplierByReport supplier:suppliers) {
			report=report+"Supplier ID: "+supplier.getSupplierId()+" Supplier Name: "+supplier.getSupplierName()+"\n";
			report=report+"Salads Ordered: "+supplier.getTypeSums()[0]+"\n";
			report=report+"Opening Dishes Ordered: "+supplier.getTypeSums()[1]+"\n";
			report=report+"Main Dishes Ordered: "+supplier.getTypeSums()[2]+"\n";
			report=report+"Desserts Ordered: "+supplier.getTypeSums()[3]+"\n";
			report=report+"Drinks Ordered: "+supplier.getTypeSums()[4]+"\n";
		}
		return report;
	}
	public static String generateOrderReport(String all) {
		String report="Order Report By Supplier and Dish Type\nIssued on: "+suppliers[0].getIssueDate()+"\n";
		for(SupplierByReport supplier:suppliers) {
			report=report+"Supplier ID: "+supplier.getSupplierId()+" Name: "+supplier.getSupplierName()+" Branch: "+supplier.getSupplierBranch().toLowerCase()+"\n";
			report=report+"Salads Ordered: "+supplier.getTypeSums()[0]+"\n";
			report=report+"Opening Dishes Ordered: "+supplier.getTypeSums()[1]+"\n";
			report=report+"Main Dishes Ordered: "+supplier.getTypeSums()[2]+"\n";
			report=report+"Desserts Ordered: "+supplier.getTypeSums()[3]+"\n";
			report=report+"Drinks Ordered: "+supplier.getTypeSums()[4]+"\n";
		}
		return report;
	}
    /**
     * Performance report string generator
     * orders by supplierid, name, total orders and late orders
     */
	public static String generatePerformanceReport() {
		String report="Performance Report By Supplier\nIssued on: "+suppliers[0].getIssueDate()+"\n";
		for(SupplierByReport supplier:suppliers) {
			report=report+"Supplier ID: "+supplier.getSupplierId()+" Supplier Name: "+supplier.getSupplierName()+"\n";
			report=report+"Total Orders: "+supplier.getTotalOrders()+"\n";
			report=report+"Late Orders: "+supplier.getLateOrders()+"\n";
			report=report+"Late Orders percentage: "+(new DecimalFormat("#0.0").format((supplier.getLateOrders()/(double)supplier.getTotalOrders())*100))+"\n";
			report=report+"Average supply time: "+supplier.getAverageSupplyTime()+"\n";
		}
		return report;
	}
	public static String generatePerformanceReport(String all) {
		String report="Performance Report By Supplier\nIssued on: "+suppliers[0].getIssueDate()+"\n";
		for(SupplierByReport supplier:suppliers) {
			report=report+"Supplier ID: "+supplier.getSupplierId()+" Name: "+supplier.getSupplierName()+" Branch: "+supplier.getSupplierBranch().toLowerCase()+"\n";
			report=report+"Total Orders: "+supplier.getTotalOrders()+"\n";
			report=report+"Late Orders: "+supplier.getLateOrders()+"\n";
			report=report+"Late Orders percentage: "+(new DecimalFormat("#0.0").format((supplier.getLateOrders()/(double)supplier.getTotalOrders())*100))+"\n";
			report=report+"Average supply time: "+supplier.getAverageSupplyTime()+"\n";
		}
		return report;
	}
	public static String generateBill() {
		String bill="Your monthly bill for ";
		Calendar c= Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			
			c.setTime(sdf.parse(suppliers[0].getIssueDate()));
			bill=bill+c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)+" ";
			bill=bill+c.get(Calendar.YEAR);
		} catch (Exception e) {
			e.printStackTrace();
		}
		bill=bill+"\nTotal Order Revenue: "+suppliers[0].getIncome()+" NIS\n";
		bill=bill+"BM cut in percentage: "+(new DecimalFormat("#0.00").format(suppliers[0].getSupplierFee()));
		bill=bill+"\nTotal net income: "+(new DecimalFormat("#0.00").format((1-((suppliers[0].getSupplierFee())/100))*Double.parseDouble(suppliers[0].getIncome())));
		return bill;
	}
    /**
     * getter method for supplier reports array
     */
	public static SupplierByReport[] getSuppliers() {
		return suppliers;
	}
    /**
     * setter method for supplier reports array
     * @param suppliers
     */
	public static void setSuppliers(SupplierByReport[] suppliers) {
		ReportGenerator.suppliers = suppliers;
	}
	public static SupplierByReport[][] getQuarter() {
		return quarter;
	}
	public static void setQuarter(SupplierByReport[][] quarter) {
		ReportGenerator.quarter = quarter;
	}
	public static int[][] getOrdersValue(){
		int[][] ordersAndValue={{0,0},{0,0},{0,0}};
		for(int i=0;i<3;i++) {
			if(quarter!=null&&quarter[i]!=null) {
			for(int j=0;j<quarter[i].length;j++) {
				if(quarter[i][j]!=null) {
				ordersAndValue[i][0]+=quarter[i][j].getTotalOrders();
				ordersAndValue[i][1]+=(int)Double.parseDouble(quarter[i][j].getIncome());
				}
				}
		}
		}
		return ordersAndValue;
	}
}
