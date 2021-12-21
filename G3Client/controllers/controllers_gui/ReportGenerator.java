package controllers_gui;

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
		}
		return report;
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
}
