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
    /**
     * Returns which report types are currently available
     * used when picking report to display (maybe more will be added later)
     * @return reportTypes string array with currently available report types
     */
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
     * also displays bm cut and supplier cut
     * @return report processed report in string format
     */
	public static String generateIncomeReport() {
		DecimalFormat twoPlacesDouble= new DecimalFormat("#0.00");
		String report="Income Report By Supplier\nIssued on: "+suppliers[0].getIssueDate()+"\n";
		for(SupplierByReport supplier:suppliers) {
			if(supplier!=null) {
			report=report+"Supplier ID: "+supplier.getSupplierId()+" Supplier Name: "+supplier.getSupplierName()+"\n";
			report=report+"Total Income: "+supplier.getIncome()+"\n";
			report=report+"BM cut in percentage: "+(twoPlacesDouble.format(supplier.getSupplierFee()))+"\n";
			report=report+"Total net income of supplier: "+(twoPlacesDouble.format(calculateNetIncome(supplier)))+"\n";
			report=report+"Total BM cut: "+(twoPlacesDouble.format(calculateBmCut(supplier)))+"\n";
		}
		}
		return report;
	}
    /**
     * Performance report string generator
     * orders by supplierid, name and income
     * also displays bm cut and supplier cut
     * specifies branch for each supplier, needed when a report for all branches is requested
     * @param all branches are printed when a string is passed
     * @return report processed report in string format
     */
	public static String generateIncomeReport(String all) {
		DecimalFormat twoPlacesDouble= new DecimalFormat("#0.00");
		String report="Income Report By Supplier\nIssued on: "+suppliers[0].getIssueDate()+"\n";
		for(SupplierByReport supplier:suppliers) {
			if(supplier!=null) {
			report=report+"Supplier ID: "+supplier.getSupplierId()+" Name: "+supplier.getSupplierName()+" Branch: "+supplier.getSupplierBranch().toLowerCase()+"\n";
			report=report+"Total Income: "+supplier.getIncome()+"\n";
			report=report+"BM cut in percentage: "+(twoPlacesDouble.format(supplier.getSupplierFee()))+"\n";
			report=report+"Total net income of supplier: "+(twoPlacesDouble.format(calculateNetIncome(supplier)))+"\n";
			report=report+"Total BM cut: "+(twoPlacesDouble.format(calculateBmCut(supplier)))+"\n";
		}
		}
		return report;
	}
    /**
     * calculates the bm cut of an income report for a supplier
     * @param supplier the supplier a cut is being calculated for
     * @return string representation of bm cut
     */
	private static double calculateBmCut(SupplierByReport supplier) {
		return (((supplier.getSupplierFee())/100))*Double.parseDouble(supplier.getIncome());
	}
    /**
     * calculates the net income for a supplier in an income report
     * @param supplier the supplier the net income is being calculated for
     * @return string representation of net income
     */
	private static double calculateNetIncome(SupplierByReport supplier) {
		return (1-((supplier.getSupplierFee())/100))*Double.parseDouble(supplier.getIncome());
	}
    /**
     * Performance report string generator
     * orders by supplierid, name and type of dish
     * @return report processed report in string format
     */
	public static String generateOrderReport() {
		String report="Order Report By Supplier and Dish Type\nIssued on: "+suppliers[0].getIssueDate()+"\n";
		for(SupplierByReport supplier:suppliers) {
			if(supplier!=null) {
			report=report+"Supplier ID: "+supplier.getSupplierId()+" Supplier Name: "+supplier.getSupplierName()+"\n";
			report=report+"Salads Ordered: "+supplier.getTypeSums()[0]+"\n";
			report=report+"Opening Dishes Ordered: "+supplier.getTypeSums()[1]+"\n";
			report=report+"Main Dishes Ordered: "+supplier.getTypeSums()[2]+"\n";
			report=report+"Desserts Ordered: "+supplier.getTypeSums()[3]+"\n";
			report=report+"Drinks Ordered: "+supplier.getTypeSums()[4]+"\n";
			}
		}
		return report;
	}
    /**
     * Performance report string generator
     * orders by supplierid, name and type of dish
     * @param all branches are printed when a string is passed
     * @return report processed report in string format
     */
	public static String generateOrderReport(String all) {
		String report="Order Report By Supplier and Dish Type\nIssued on: "+suppliers[0].getIssueDate()+"\n";
		for(SupplierByReport supplier:suppliers) {
			if(supplier!=null) {
			report=report+"Supplier ID: "+supplier.getSupplierId()+" Name: "+supplier.getSupplierName()+" Branch: "+supplier.getSupplierBranch().toLowerCase()+"\n";
			report=report+"Salads Ordered: "+supplier.getTypeSums()[0]+"\n";
			report=report+"Opening Dishes Ordered: "+supplier.getTypeSums()[1]+"\n";
			report=report+"Main Dishes Ordered: "+supplier.getTypeSums()[2]+"\n";
			report=report+"Desserts Ordered: "+supplier.getTypeSums()[3]+"\n";
			report=report+"Drinks Ordered: "+supplier.getTypeSums()[4]+"\n";
		}
		}
		return report;
	}
    /**
     * Performance report string generator
     * orders by supplierid, name, total orders and late orders
     * also gives a late orders percentage and an average supply time
     * @return report processed report in string format
     */
	public static String generatePerformanceReport() {
		String report="Performance Report By Supplier\nIssued on: "+suppliers[0].getIssueDate()+"\n";
		for(SupplierByReport supplier:suppliers) {
			if(supplier!=null) {
			report=report+"Supplier ID: "+supplier.getSupplierId()+" Supplier Name: "+supplier.getSupplierName()+"\n";
			report=report+"Total Orders: "+supplier.getTotalOrders()+"\n";
			report=report+"Late Orders: "+supplier.getLateOrders()+"\n";
			//calculates late orders as a percentage: lates/orders*100
			report=report+"Late Orders percentage: "+(new DecimalFormat("#0.0").format((supplier.getLateOrders()/(double)supplier.getTotalOrders())*100))+"\n";
			report=report+"Average supply time: "+supplier.getAverageSupplyTime()+"\n";
		}
		}
		return report;
	}
    /**
     * Performance report string generator
     * orders by supplierid, name, total orders and late orders
     * also gives a late orders percentage and an average supply time
     * @param all branches are printed when a string is passed
     * @return report processed report in string format
     */
	public static String generatePerformanceReport(String all) {
		String report="Performance Report By Supplier\nIssued on: "+suppliers[0].getIssueDate()+"\n";
		for(SupplierByReport supplier:suppliers) {
			{
			report=report+"Supplier ID: "+supplier.getSupplierId()+" Name: "+supplier.getSupplierName()+" Branch: "+supplier.getSupplierBranch().toLowerCase()+"\n";
			report=report+"Total Orders: "+supplier.getTotalOrders()+"\n";
			report=report+"Late Orders: "+supplier.getLateOrders()+"\n";
			//calculates late orders as a percentage: lates/orders*100
			report=report+"Late Orders percentage: "+(new DecimalFormat("#0.0").format((supplier.getLateOrders()/(double)supplier.getTotalOrders())*100))+"\n";
			report=report+"Average supply time: "+supplier.getAverageSupplyTime()+"\n";
		}
		}
		return report;
	}
    /**
     * Supplier bill generator
     * shows supplier revenue and net income after BM cut
     * @return bill processed bill in string format
     */
	public static String generateBill() {
		DecimalFormat twoPlacesDouble= new DecimalFormat("#0.00");
		String bill="Your monthly bill for ";
		Calendar c= Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// changes month from MM format to month name (i.e January)
		try {
			
			c.setTime(sdf.parse(suppliers[0].getIssueDate()));
			bill=bill+c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)+" ";
			bill=bill+c.get(Calendar.YEAR);
		} catch (Exception e) {
			e.printStackTrace();
		}
		bill=bill+"\nTotal Order Revenue: "+suppliers[0].getIncome()+" NIS\n";
		bill=bill+"BM cut in percentage: "+(twoPlacesDouble.format(suppliers[0].getSupplierFee()));
		bill=bill+"\nTotal net income: "+(twoPlacesDouble.format(calculateNetIncome(suppliers[0])));
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
    /**
     * getter method for quarterly supplier reports array
     */
	public static SupplierByReport[][] getQuarter() {
		return quarter;
	}
    /**
     * setter method for quarterly supplier reports array
     * @param suppliers
     */
	public static void setQuarter(SupplierByReport[][] quarter) {
		ReportGenerator.quarter = quarter;
	}
    /**
     * setter method for quarterly supplier reports array
     * @returns ordersAndValue an array specifying the amount of orders and their value
     * in each of the three months in the quarter
     */
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
