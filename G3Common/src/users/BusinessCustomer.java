package users;

import java.io.Serializable;

/**
 * 
 * @author Lior, Guzovsky.
 *  Class description: This class is derived class from
 *  Customer class which defines the main attributes and functionalities
 *  of a Business customer in our system.
 * @version 29/11/2021
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
	 * Each Business customer is given a specific type of budget, it can be budget
	 * per day, week and month.
	 */
	protected BudgetType budgetOfBusinessCustomer;

	/**
	 * This is the customers position in the company.
	 */
	protected String businessCustomerPosition;

	/**
	 * Each business customer has a budget that he cannot exceed.
	 */
	protected int budgetMaxAmount;

	/**
	 * This injective number defines the business W4C replacement code In addition
	 * to the User w4c code.
	 */
	protected int businessW4cCodeNumber;

	/**
	 * This is the constructor of the class.
	 * 
	 * @param userId
	 * @param isConfirmedInSystem
	 * @param userFirstName
	 * @param userLastName
	 * @param homeBranch
	 * @param isLoggedIn
	 * @param privateW4cCodeNumber
	 * @param userEmail
	 * @param phoneNumber
	 * @param privateCreditCard
	 * @param companyOfBusinessCustomer
	 * @param budgetOfBusinessCustomer
	 * @param businessCustomerPosition
	 * @param budgetMaxAmount
	 * @param businessW4cCodeNumber
	 */
	public BusinessCustomer(String userId, boolean isConfirmedInSystem, String userFirstName, String userLastName,
			Branch homeBranch, boolean isLoggedIn, int privateW4cCodeNumber, String userEmail, String phoneNumber,
			String privateCreditCard, Company companyOfBusinessCustomer, BudgetType budgetOfBusinessCustomer,
			String businessCustomerPosition, int budgetMaxAmount, int businessW4cCodeNumber) {
		super(userId, isConfirmedInSystem, userFirstName, userLastName, homeBranch, isLoggedIn, privateW4cCodeNumber,
				userEmail, phoneNumber, privateCreditCard);
		this.companyOfBusinessCustomer = companyOfBusinessCustomer;
		this.budgetOfBusinessCustomer = budgetOfBusinessCustomer;
		this.businessCustomerPosition = businessCustomerPosition;
		this.budgetMaxAmount = budgetMaxAmount;
		this.businessW4cCodeNumber = businessW4cCodeNumber;
	}

	/**
	 * This is the section of the setters and getters of the class business
	 * customer.
	 */

	public BudgetType getBudgetOfBusinessCustomer() {
		return budgetOfBusinessCustomer;
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

	public int getBusinessW4cCode() {
		return businessW4cCodeNumber;
	}

	public void setBusinessW4cCode(int businessW4cCodeNumber) {
		this.businessW4cCodeNumber = businessW4cCodeNumber;
	}
	
	/**
	 * This is the toString for this class
	 */
	@Override
	public String toString() {
		return "Business Customer: "  + userFirstName + " " + userLastName + ", Company name: " + companyOfBusinessCustomer.getCompanyName();
	}	
}
