package users;

/**
 * 
 * @author Lior, Guzovsky.
 * Class description: 
 * This class is derived class from Customer class
 *  which defines the main attributes and functionalities
 * of a Business customer in our system.
 * @version 29/11/2021
 */
public class BusinessCustomer extends Customer{
	
	/**
	 * Class members description:
	 */
	
	/**
	 * This is the name of the employer such as Intel 
	 * and etc.
	 */
	protected String employerName;
	
	/**
	 * Each Business customer is 
	 * given a specific type of budget,
	 * it can be budget per day, week and month.
	 */
	protected BudgetType budgetOfBusinessCustomer;
	
	
	/**
	 * This is the customers position in the company.
	 */
	protected String businessCustomerPosition;
	
	
	/**
	 * Each Business customer has a budget max
	 * that he can use
	 */
	
	protected int budgetMaxAmount;

	
	/**
	 * This is the Business customer constructor
	 * 
	 * @param userId
	 * @param password
	 * @param isConfirmedInSystem
	 * @param userFirstName
	 * @param userLastName
	 * @param homeBranch
	 * @param isLoggedIn
	 * @param idNumber
	 * @param userEmail
	 * @param phoneNumber
	 * @param accountCode
	 * @param privateCreditCard
	 * @param employerName
	 * @param budgetOfBusinessCustomer
	 * @param businessCustomerPosition
	 * @param budgetMaxAmount
	 */
	public BusinessCustomer(String userId, String password, boolean isConfirmedInSystem, String userFirstName,
			String userLastName, Branch homeBranch, boolean isLoggedIn, int idNumber, String userEmail,
			String phoneNumber, String accountCode, CreditCard privateCreditCard, String employerName,
			BudgetType budgetOfBusinessCustomer, String businessCustomerPosition, int budgetMaxAmount) {
		super(userId, password, isConfirmedInSystem, userFirstName, userLastName, homeBranch, isLoggedIn, idNumber,
				userEmail, phoneNumber, accountCode, privateCreditCard);
		this.employerName = employerName;
		this.budgetOfBusinessCustomer = budgetOfBusinessCustomer;
		this.businessCustomerPosition = businessCustomerPosition;
		this.budgetMaxAmount = budgetMaxAmount;
	}

	/**
	 * This is the section
	 * of the setters and 
	 * getters.
	 */
	public String getEmployerName() {
		return employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

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

}
