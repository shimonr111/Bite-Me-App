package users;

import java.io.Serializable;

/**
 * 
 * @author Mousa,Srour.
 * 
 * Class description: 
 * This class is derived class from User For Registration class
 *  which means that we still don't know this user is going to be registered as
 *  it contains most of the normal User Info but in addition it has an addition members.
 * 
 * @version 12/12/2021
 */
public class UserForRegistration implements Serializable{
	
	/**
	 * Class members description:
	 */	
	private String userID;
	private ConfirmationStatus statusInSystem;
	private String firstName;
	private String lastName;
	private Branch homeBranch;
	private boolean isLoggedIn;
	private String email;
	private String phoneNumber;
	private String creditCardNumber;
	private String creditCardCvvCode;
	private String creditCardDateOfExpiration;
	private String username;
	private String password;
	
	/**
	 * This is a constructor for this class.
	 * @param userID
	 * @param statusInSystem
	 * @param firstName
	 * @param lastName
	 * @param homeBranch
	 * @param isLoggedIn
	 * @param email
	 * @param phoneNumber
	 * @param creditCardNumber
	 * @param creditCardCvvCode
	 * @param creditCardDateOfExpiration
	 * @param username
	 * @param password
	 */
	public UserForRegistration(String userID, ConfirmationStatus statusInSystem, String firstName, String lastName,
			Branch homeBranch, boolean isLoggedIn, String email, String phoneNumber, String creditCardNumber,
			String creditCardCvvCode, String creditCardDateOfExpiration, String username, String password) {
		super();
		this.userID = userID;
		this.statusInSystem = statusInSystem;
		this.firstName = firstName;
		this.lastName = lastName;
		this.homeBranch = homeBranch;
		this.isLoggedIn = isLoggedIn;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.creditCardNumber = creditCardNumber;
		this.creditCardCvvCode = creditCardCvvCode;
		this.creditCardDateOfExpiration = creditCardDateOfExpiration;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Getters and Setters:
	 */
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public ConfirmationStatus getStatusInSystem() {
		return statusInSystem;
	}

	public void setStatusInSystem(ConfirmationStatus statusInSystem) {
		this.statusInSystem = statusInSystem;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Branch getHomeBranch() {
		return homeBranch;
	}

	public void setHomeBranch(Branch homeBranch) {
		this.homeBranch = homeBranch;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getCreditCardCvvCode() {
		return creditCardCvvCode;
	}

	public void setCreditCardCvvCode(String creditCardCvvCode) {
		this.creditCardCvvCode = creditCardCvvCode;
	}

	public String getCreditCardDateOfExpiration() {
		return creditCardDateOfExpiration;
	}

	public void setCreditCardDateOfExpiration(String creditCardDateOfExpiration) {
		this.creditCardDateOfExpiration = creditCardDateOfExpiration;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
