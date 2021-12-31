package users;

import java.io.Serializable;

/**
 * 
 * @author Lior, Guzovsky.
 * @author Alexander, Martinov.
 * 
 *  Class description: This class is derived class from
 *  Branch manager class which defines the main attributes and functionalities of a
 *  CEO of Bite Me in our system.
 *  
 * @version 09/12/2021
 */
public class CeoBiteMe extends User implements Serializable{

	/**
	 * This is the constructor for class CeoBiteMe
	 * 
	 * @param userId
	 * @param statusInSystem
	 * @param userFirstName
	 * @param userLastName
	 * @param homeBranch
	 * @param isLoggedIn
	 * @param userEmail
	 * @param phoneNumber
	 */
	public CeoBiteMe(String userId, ConfirmationStatus statusInSystem, String userFirstName, String userLastName,
			Branch homeBranch, boolean isLoggedIn, String userEmail, String phoneNumber) {
		super(userId, statusInSystem, userFirstName, userLastName, homeBranch, isLoggedIn, userEmail,
				phoneNumber);
	}

	/**
	 * This is the toString for this class
	 */
	@Override
	public String toString() {
		return "CeoBiteMe: " + userFirstName + " " + userLastName;
	}	
}
