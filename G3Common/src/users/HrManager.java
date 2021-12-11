package users;

import java.io.Serializable;
/**
 * 
 * @author Lior, Guzovsky.
 * @author Mousa, Srour.
 * @author Alexander, Martinov.
 * Class description: 
 * This class is derived class from
 * Business customer class which defines the main attributes and
 * functionalities of a HR manager in our system.
 * @version 08/12/2021
 */
public class HrManager extends BusinessCustomer implements Serializable{

	/**
	 * Class members description:
	 */
	
	/**
	 * This is the constructor of the class for login
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
	public HrManager(String userId, ConfirmationStatus statusInSystem, String userFirstName, String userLastName,
			Branch homeBranch, boolean isLoggedIn, int W4CCodeNumber, String userEmail, String phoneNumber,
			String privateCreditCard, Company companyOfBusinessCustomer, BudgetType budgetOfBusinessCustomer,
			PositionType businessCustomerPosition, int budgetMaxAmount) {
		super(userId, statusInSystem, userFirstName, userLastName, homeBranch, isLoggedIn, W4CCodeNumber, userEmail,
				phoneNumber, privateCreditCard, companyOfBusinessCustomer, budgetOfBusinessCustomer, businessCustomerPosition,
				budgetMaxAmount);
	}
	
	/**
	 * This is the constructor of the class for registering.
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
	public HrManager(String userId, ConfirmationStatus statusInSystem, String userFirstName, String userLastName,
			Branch homeBranch, boolean isLoggedIn, String userEmail, String phoneNumber,
			String privateCreditCard, String companyOfBusinessCustomer, BudgetType budgetOfBusinessCustomer,
			PositionType businessCustomerPosition, int budgetMaxAmount) {
		super(userId, statusInSystem, userFirstName, userLastName, homeBranch, isLoggedIn, userEmail,
				phoneNumber, privateCreditCard, companyOfBusinessCustomer, budgetOfBusinessCustomer, businessCustomerPosition,
				budgetMaxAmount);
	}

	/**
	 * This is the toString for this class
	 */
	@Override
	public String toString() {
		return "HR Manager: " + userFirstName  + " "+userLastName + ", Company name: " + companyOfBusinessCustomer.getCompanyName();
	}

}
