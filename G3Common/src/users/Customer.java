package users;

import java.io.Serializable;

/**
 * 
 * @author Lior, Guzovsky.
 * Class description: 
 * This class is derived class from User class
 *  which defines the main attributes and functionalities
 * of a regular customer in our system.
 * 
 * @version 29/11/2021
 */
public class Customer extends User implements Serializable{
	/**
	 * Class members description:
	 */	
	
	private static final long serialVersionUID = 1L;

	/**
	 * Each customer has its own Credit 
	 * card.
	 */
	protected String privateCreditCard;
	
	/**
	 * This is the constructor for the class Customer
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
	 * @param accountCode
	 * @param privateCreditCard
	 */
	public Customer(String userId, boolean isConfirmedInSystem, String userFirstName,
			String userLastName, Branch homeBranch, boolean isLoggedIn, int privateW4cCodeNumber, String userEmail,
			String phoneNumber, String privateCreditCard) {
		super(userId, isConfirmedInSystem, userFirstName, userLastName, homeBranch, isLoggedIn, privateW4cCodeNumber,
				userEmail, phoneNumber);
		this.privateCreditCard = privateCreditCard;
	}
	
	/**
	 * This section is for the 
	 * Setters and Getters of the 
	 * Class Customer
	 */
	public String getPrivateCreditCard() {
		return privateCreditCard;
	}

	public void setPrivateCreditCard(String privateCreditCard) {
		this.privateCreditCard = privateCreditCard;
	}

	/**
	 * This is the toString for this class
	 */
	@Override
	public String toString() {
		return "Customer: "  + userFirstName + " " + userLastName;
	}	
}