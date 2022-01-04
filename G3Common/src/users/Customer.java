package users;

import java.io.Serializable;

/**
 * Class description: 
 * This class is derived class from User class
 *  which defines the main attributes and functionalities
 * of a regular customer in our system.
 * 
 * @author Lior, Guzovsky.
 * @author Alexander, Martinov.
 * 
 * @version 09/12/2021
 */
public class Customer extends User implements Serializable{
	
	/**
	 * Class members description:
	 */	
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * This injective number defines  
	 * the user W4C replacement code.
	 */
	protected int privateW4cCodeNumber; 

	/**
	 * Each customer has its own Credit 
	 * card.
	 */
	protected String privateCreditCard;
	
	/**
	 * The customer account balance (not negative).
	 */
	protected double balance;
	
	/**
	 * This is the constructor used for passing the object.
	 * 
	 * @param userId Customer user ID.
	 * @param statusInSystem customer Status in system.
	 * @param userFirstName customer first name.
	 * @param userLastName customer last name.
	 * @param homeBranch customer home branch.
	 * @param isLoggedIn true if logged in, false if not.
	 * @param W4CCodeNumber customer w4c code number.
	 * @param userEmail customer email.
	 * @param phoneNumber customer phone number.
	 * @param privateCreditCard customer credit card number.
	 * @param balance customer balance (initialized to 0 )
	 */
	public Customer(String userId, ConfirmationStatus statusInSystem, String userFirstName,
			String userLastName, Branch homeBranch, boolean isLoggedIn, int W4CCodeNumber, String userEmail,
			String phoneNumber, String privateCreditCard, double balance) {
		super(userId, statusInSystem, userFirstName, userLastName, homeBranch, isLoggedIn,
				userEmail, phoneNumber);
		this.privateCreditCard = privateCreditCard;
		this.privateW4cCodeNumber= W4CCodeNumber;
		this.balance = balance;
	}
	
	/**
	 * This is the constructor for registering a new customer.
	 * 
	 * @param userId Customer user ID.
	 * @param statusInSystem customer Status in system.
	 * @param userFirstName customer first name.
	 * @param userLastName customer last name.
	 * @param homeBranch customer home branch.
	 * @param isLoggedIn true if logged in, false if not.
	 * @param userEmail customer email.
	 * @param phoneNumber customer phone number.
	 * @param privateCreditCard customer credit card number.
	 */
	public Customer(String userId, ConfirmationStatus statusInSystem, String userFirstName,
			String userLastName, Branch homeBranch, boolean isLoggedIn, String userEmail,
			String phoneNumber, String privateCreditCard) {
		super(userId, statusInSystem, userFirstName, userLastName, homeBranch, isLoggedIn,
				userEmail, phoneNumber);
		this.privateCreditCard = privateCreditCard;
		this.privateW4cCodeNumber= generateW4C();
		this.balance = 0.0;
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
	
	public int getPrivateW4cCodeNumber() {
		return privateW4cCodeNumber;
	}

	public void setPrivateW4cCodeNumber(int privateW4cCodeNumber) {
		this.privateW4cCodeNumber = privateW4cCodeNumber;
	}
	
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * This is the toString for this class
	 */
	@Override
	public String toString() {
		return "Customer: "  + userFirstName + " " + userLastName;
	}

	/** This method used to generate customer W4C, based on the user id, which is unique */
	private int generateW4C() {
		return  31 * Integer.parseInt(userId);
	}

}
