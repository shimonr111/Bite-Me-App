package users;

import java.io.Serializable;

/**
 * 
 * @author Lior, Guzovsky.
 * @author Alexander, Martinov.
 * 
 * Class description: 
 * This class is a class that represents
 * a credit card that belongs to a customer \ Business customer
 * HR manger and etc.
 * 
 * @version 08/12/2021
 */
public class CreditCard implements Serializable{

	/**
	 * Each credit card has its own credit card number
	 * which the customer \ Business customer
	 * \ HR manger and etc. enters in the registration
	 * phase.
	 */
	private String creditCardNumber;
	
	/**
	 * Each credit card has its own date of expiration
	 */
	private String creditCardDateOfExpiration;
	
	/**
	 * Each credit card has its own CVV code 
	 */
	private String creditCardCvvCode;
	
	/**
	 * Credit card Constructor
	 * 
	 * @param creditCardNumber
	 * @param creditCardDateOfExpiration
	 * @param creditCardCvvCode
	 */
	public CreditCard(String creditCardNumber, String creditCardDateOfExpiration, String creditCardCvvCode) {
		super();
		this.creditCardNumber = creditCardNumber;
		this.creditCardDateOfExpiration = creditCardDateOfExpiration;
		this.creditCardCvvCode = creditCardCvvCode;
	}
	
	/**
	 * This section is for the 
	 * Setters and Getters of the 
	 * Class User
	 */
	
	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getCreditCardDateOfExpiration() {
		return creditCardDateOfExpiration;
	}

	public void setCreditCardDateOfExpiration(String creditCardDateOfExpiration) {
		this.creditCardDateOfExpiration = creditCardDateOfExpiration;
	}

	public String getCreditCardCvvCode() {
		return creditCardCvvCode;
	}

	public void setCreditCardCvvCode(String creditCardCvvCode) {
		this.creditCardCvvCode = creditCardCvvCode;
	}
}
