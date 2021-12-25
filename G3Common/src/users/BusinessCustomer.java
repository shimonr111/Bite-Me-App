package users;

import java.io.Serializable;

/**
 * 
 * @author Lior, Guzovsky.
 * @author Mousa, Srour.
 * @author Alexander, Martinov.
 *  Class description: 
 * This class is derived class from
 *  Customer class which defines the main attributes and functionalities
 *  of a Business customer in our system.
 * @version 09/12/2021
 */
public class BusinessCustomer extends Customer implements Serializable{

	/**
	 * Class members description:
	 */

	/**
	 * This is the data about the company in which the user works in.
	 */
	protected Company companyOfBusinessCustomer;
	
	/**
	 *  This is the data about the company in which the user works in. for REGISTRATION !.
	 */
	protected String companyOfBusinessCustomerString;

	/**
	 * Each Business customer is given a specific type of budget, it can be budget
	 * per day, week and month.
	 */
	protected BudgetType budgetOfBusinessCustomer;

	/**
	 * This is the customers position in the company.
	 */
	protected PositionType businessCustomerPosition;

	/**
	 * Each business customer has a budget that he cannot exceed.
	 */
	protected int budgetMaxAmount;
	
	/**
	 * This is a field for understanding if the user 
	 * logged in with his company code number in addition 
	 * to the W4C and this is relevant for the Payment stage
	 * in the order process.
	 * 
	 */
	protected boolean isLoggedInAsBusinessAccount;
	
	/**
	 * This is a field for the amount 
	 * of budget that the Business customer 
	 * had used according to the budget type.
	 * 
	 */
	protected double budgetUsed;

	/**
	 * This is the constructor used for passing the object.
	 * 
	 * @param userId
	 * @param statusInSystem
	 * @param userFirstName
	 * @param userLastName
	 * @param homeBranch
	 * @param isLoggedIn
	 * @param W4CCodeNumber
	 * @param userEmail
	 * @param phoneNumber
	 * @param privateCreditCard
	 * @param companyOfBusinessCustomer
	 * @param budgetOfBusinessCustomer
	 * @param businessCustomerPosition
	 * @param budgetMaxAmount
	 */
	public BusinessCustomer(String userId, ConfirmationStatus statusInSystem, String userFirstName, String userLastName,
			Branch homeBranch, boolean isLoggedIn, int W4CCodeNumber, String userEmail, String phoneNumber,
			String privateCreditCard, double balance, Company companyOfBusinessCustomer, BudgetType budgetOfBusinessCustomer,
			PositionType businessCustomerPosition, int budgetMaxAmount) {
		super(userId, statusInSystem, userFirstName, userLastName, homeBranch, isLoggedIn, W4CCodeNumber,
				userEmail, phoneNumber, privateCreditCard, balance);
		this.companyOfBusinessCustomer = companyOfBusinessCustomer;
		this.budgetOfBusinessCustomer = budgetOfBusinessCustomer;
		this.businessCustomerPosition = businessCustomerPosition;
		this.budgetMaxAmount = budgetMaxAmount;
		this.isLoggedInAsBusinessAccount = false;
		this.budgetUsed = 0;
	}
	
	/**
	 * This is the constructor for first creation;
	 * 
	 * @param userId
	 * @param statusInSystem
	 * @param userFirstName
	 * @param userLastName
	 * @param homeBranch
	 * @param isLoggedIn
	 * @param userEmail
	 * @param phoneNumber
	 * @param privateCreditCard
	 * @param companyOfBusinessCustomer
	 * @param budgetOfBusinessCustomer
	 * @param businessCustomerPosition
	 * @param budgetMaxAmount
	 */
	public BusinessCustomer(String userId, ConfirmationStatus statusInSystem, String userFirstName, String userLastName,
			Branch homeBranch, boolean isLoggedIn, String userEmail, String phoneNumber,
			String privateCreditCard, String companyOfBusinessCustomer, BudgetType budgetOfBusinessCustomer,
			PositionType businessCustomerPosition, int budgetMaxAmount) {
		super(userId, statusInSystem, userFirstName, userLastName, homeBranch, isLoggedIn,
				userEmail, phoneNumber, privateCreditCard);
		this.companyOfBusinessCustomerString = companyOfBusinessCustomer;
		this.budgetOfBusinessCustomer = budgetOfBusinessCustomer;
		this.businessCustomerPosition = businessCustomerPosition;
		this.budgetMaxAmount = budgetMaxAmount;
		this.isLoggedInAsBusinessAccount = false;
		this.budgetUsed = 0;
	}

	/**
	 * This is the section of the setters and getters of the class business
	 * customer.
	 */

	public BudgetType getBudgetOfBusinessCustomer() {
		return budgetOfBusinessCustomer;
	}
	
	public PositionType getPositionType() {
		return  businessCustomerPosition;
	}

	public void setBudgetOfBusinessCustomer(BudgetType budgetOfBusinessCustomer) {
		this.budgetOfBusinessCustomer = budgetOfBusinessCustomer;
	}

	public int getBudgetMaxAmount() {
		return budgetMaxAmount;
	}

	public void setBudgetMaxAmount(int budgetMaxAmount) {
		this.budgetMaxAmount = budgetMaxAmount;
	}

	public Company getcompanyOfBusinessCustomer() {
		return companyOfBusinessCustomer;
	}
	
	public void setCompany(Company companyOfBusinessCustomer) {
		this.companyOfBusinessCustomer=companyOfBusinessCustomer;
	}
	
	public String getCompanyOfBusinessCustomerString() {
		return companyOfBusinessCustomerString;
	}
	
	public boolean isLoggedInAsBusinessAccount() {
		return isLoggedInAsBusinessAccount;
	}

	public void setLoggedInAsBusinessAccount(boolean isLoggedInAsBusinessAccount) {
		this.isLoggedInAsBusinessAccount = isLoggedInAsBusinessAccount;
	}
	
	public boolean getLoggedInAsBusinessAccount() {
		return isLoggedInAsBusinessAccount;
	}

	public double getBudgetUsed() {
		return budgetUsed;
	}

	public void setBudgetUsed(double budgetUsed) {
		this.budgetUsed = budgetUsed;
	}
	/**
	 * This is the toString for this class
	 */
	@Override
	public String toString() {
		return "Business Customer: "  + userFirstName + " " + userLastName + " " + getCompanyOfBusinessCustomerString()  ;
	}	
}
