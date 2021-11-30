package users;

/**
 * 
 * @author Lior, Guzovsky.
 * Class description: This class is derived class from
 * Business customer class which defines the main attributes and
 * functionalities of a HR manager in our system.
 * @version 29/11/2021
 */
public class HrManager extends BusinessCustomer {

	/**
	 * This is the constructor for the class HrManager
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
	 * @param employerName
	 * @param budgetOfBusinessCustomer
	 * @param businessCustomerPosition
	 * @param budgetMaxAmount
	 * @param businessW4cCodeNumber
	 */
	public HrManager(String userId, boolean isConfirmedInSystem, String userFirstName, String userLastName,
			Branch homeBranch, boolean isLoggedIn, int privateW4cCodeNumber, String userEmail, String phoneNumber,
			CreditCard privateCreditCard, String employerName, BudgetType budgetOfBusinessCustomer,
			String businessCustomerPosition, int budgetMaxAmount, int businessW4cCodeNumber) {
		super(userId, isConfirmedInSystem, userFirstName, userLastName, homeBranch, isLoggedIn, privateW4cCodeNumber,
				userEmail, phoneNumber, privateCreditCard, employerName, budgetOfBusinessCustomer,
				businessCustomerPosition, budgetMaxAmount, businessW4cCodeNumber);

	}

	/**
	 * This is the toString for this class
	 */
	@Override
	public String toString() {
		return "HrManager: " + userFirstName + ", userLastName= " + userLastName + ", Company name = " + employerName;
	}

}
