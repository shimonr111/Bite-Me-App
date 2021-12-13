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
	
	public String getCompanyOfBusinessCustomerString() {
		return companyOfBusinessCustomerString;
	}
	
	/**
	 * This is the toString for this class
	 */
	@Override
	public String toString() {
		return "Business Customer: "  + userFirstName + " " + userLastName + ", Company name: " + companyOfBusinessCustomer.getCompanyName();
	}	
}
