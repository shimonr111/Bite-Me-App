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
	 * This is the constructor of the class.
	 * 
	 * @param userId
	 * @param statusInSystem
	 * @param userFirstName
	 * @param userLastName
	 * @param homeBranch
	 * @param isLoggedIn
	 * @param businessW4cCodeNumber
	 * @param userEmail
	 * @param phoneNumber
	 * @param privateCreditCard
	 * @param companyOfBusinessCustomer
	 * @param budgetOfBusinessCustomer
	 * @param businessCustomerPosition
	 * @param budgetMaxAmount
	 */
	public BusinessCustomer(String userId, ConfirmationStatus statusInSystem, String userFirstName, String userLastName,
			Branch homeBranch, boolean isLoggedIn, int businessW4cCodeNumber, String userEmail, String phoneNumber,
			String privateCreditCard, Company companyOfBusinessCustomer, BudgetType budgetOfBusinessCustomer,
			PositionType businessCustomerPosition, int budgetMaxAmount) {
		super(userId, statusInSystem, userFirstName, userLastName, homeBranch, isLoggedIn, businessW4cCodeNumber,
				userEmail, phoneNumber, privateCreditCard);
		this.companyOfBusinessCustomer = companyOfBusinessCustomer;
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
	
	/**
	 * This is the toString for this class
	 */
	@Override
	public String toString() {
		return "Business Customer: "  + userFirstName + " " + userLastName + ", Company name: " + companyOfBusinessCustomer.getCompanyName();
	}	
}
