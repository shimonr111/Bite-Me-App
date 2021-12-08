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
	public HrManager(String userId, boolean isConfirmedInSystem, String userFirstName, String userLastName,
			Branch homeBranch, boolean isLoggedIn, int privateW4cCodeNumber, String userEmail, String phoneNumber,
			String privateCreditCard, Company companyOfBusinessCustomer, BudgetType budgetOfBusinessCustomer,
			PositionType businessCustomerPosition, int budgetMaxAmount, int businessW4cCodeNumber) {
		super(userId, isConfirmedInSystem, userFirstName, userLastName, homeBranch, isLoggedIn, privateW4cCodeNumber, userEmail,
				phoneNumber, privateCreditCard, companyOfBusinessCustomer, budgetOfBusinessCustomer, businessCustomerPosition,
				budgetMaxAmount, businessW4cCodeNumber);
	}

	/**
	 * This is the toString for this class
	 */
	@Override
	public String toString() {
		return "HR Manager: " + userFirstName  + " "+userLastName + ", Company name: " + companyOfBusinessCustomer.getCompanyName();
	}

}
