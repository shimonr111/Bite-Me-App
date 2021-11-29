package users;

/**
 * 
 * @author Lior, Guzovsky.
 * Class description: 
 * This class is derived class from User class
 *  which defines the main attributes and functionalities
 * of a regular customer in our system.
 * @version 29/11/2021
 */
public class Customer extends User{


	/**
	 * Class members description:
	 */
	
	
	/**
	 * Each customer has its own account code.
	 */
	protected String accountCode;
	
	/**
	 * Each customer has its own Credit 
	 * card.
	 */
	protected CreditCard privateCreditCard;
	
	/**
	 * This is the constructor for the class Customer
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
	 */
	public Customer(String userId, String password, boolean isConfirmedInSystem, String userFirstName,
			String userLastName, Branch homeBranch, boolean isLoggedIn, int idNumber, String userEmail,
			String phoneNumber, String accountCode, CreditCard privateCreditCard) {
		super(userId, password, isConfirmedInSystem, userFirstName, userLastName, homeBranch, isLoggedIn, idNumber,
				userEmail, phoneNumber);
		this.accountCode = accountCode;
		this.privateCreditCard = privateCreditCard;
	}
	
	/**
	 * This section is for the 
	 * Setters and Getters of the 
	 * Class Customer
	 */
	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public CreditCard getPrivateCreditCard() {
		return privateCreditCard;
	}

	public void setPrivateCreditCard(CreditCard privateCreditCard) {
		this.privateCreditCard = privateCreditCard;
	}

	
}
