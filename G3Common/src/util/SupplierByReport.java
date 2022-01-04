package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class description: 
 * This is a which saves relevant data for reports, by supplier.
 */
/**
 * @author Alexander, Martinov.
 * 
 * @version 21/12/2021
 */
public class SupplierByReport implements Serializable{
	
	/**
	 * Class members description:
	 * Stores relevant queried supplier information for report
	 */
	
	/**
	 * Supplier ID.
	 */
	private String supplierId;
	
	/**
	 * Supplier Name.
	 */
	private String supplierName;
	
	/**
	 * supplier Branch.
	 */
	private String supplierBranch;
	
	/**
	 * Issue Date.
	 */
	private String issueDate;
	
	/**
	 * Report Type.
	 */
	private String reportType;
	
	/***
	 * Income initialized to 0.
	 */
	private String income="0";
	/**
	 * string lists for storing menu by category.
	 */
	private ArrayList<String> salads=new ArrayList<String>();
	
	/**
	 * string lists for storing menu by category.
	 */
	private ArrayList<String> firsts=new ArrayList<String>();
	
	/**
	 * string lists for storing menu by category.
	 */
	private ArrayList<String> mains=new ArrayList<String>();
	
	/**
	 * string lists for storing menu by category.
	 */
	private ArrayList<String> desserts=new ArrayList<String>();
	
	/**
	 * string lists for storing menu by category.
	 */
	private ArrayList<String> drinks=new ArrayList<String>();
	/**
	 * counted appearances of item categories in report (times ordered)
	 * salads, firsts, mains, desserts, drinks
	 */
	private int[] typeSums={0,0,0,0,0};
	private int totalOrders=0;
	private int lateOrders=0;
	private String averageSupplyTime;
	private Double supplierFee;
	
	/**
     *Only has getter and setter methods
     *and one to string method
     */
	public String getAverageSupplyTime() {
		return averageSupplyTime;
	}
	
	public void setAverageSupplyTime(String averageSupplyTime) {
		this.averageSupplyTime = averageSupplyTime;
	}
	
	public Double getSupplierFee() {
		return supplierFee;
	}
	
	public void setSupplierFee(Double supplierFee) {
		this.supplierFee = supplierFee;
	}
	
	public int getTotalOrders() {
		return totalOrders;
	}
	
	public void setTotalOrders(int totalOrders) {
		this.totalOrders = totalOrders;
	}
	
	public int getLateOrders() {
		return lateOrders;
	}
	
	public void setLateOrders(int lateOrders) {
		this.lateOrders = lateOrders;
	}
	
	public String getSupplierId() {
		return supplierId;
	}
	
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	
	public String getSupplierName() {
		return supplierName;
	}
	
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	public String getSupplierBranch() {
		return supplierBranch;
	}
	
	public void setSupplierBranch(String supplierBranch) {
		this.supplierBranch = supplierBranch;
	}
	
	public String getIncome() {
		return income;
	}
	
	public void setIncome(String income) {
		this.income = income;
	}
	
	public ArrayList<String> getSalads() {
		return salads;
	}
	
	public void setSalads(ArrayList<String> salads) {
		this.salads = salads;
	}
	
	public ArrayList<String> getFirsts() {
		return firsts;
	}
	
	public void setFirsts(ArrayList<String> firsts) {
		this.firsts = firsts;
	}
	
	public ArrayList<String> getMains() {
		return mains;
	}
	
	public void setMains(ArrayList<String> mains) {
		this.mains = mains;
	}
	
	public ArrayList<String> getDesserts() {
		return desserts;
	}
	
	public void setDesserts(ArrayList<String> desserts) {
		this.desserts = desserts;
	}
	
	public ArrayList<String> getDrinks() {
		return drinks;
	}
	
	public void setDrinks(ArrayList<String> drinks) {
		this.drinks = drinks;
	}
	
	public int[] getTypeSums() {
		return typeSums;
	}
	
	public void setTypeSums(int[] total) {
		this.typeSums = total;
	}
	
	public String toString() {
		return Arrays.toString(typeSums)+" "+income+" "+lateOrders+" "+totalOrders;
	}
	
	public String getIssueDate() {
		return issueDate;
	}
	
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	
	public String getReportType() {
		return reportType;
	}
	
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	
}
