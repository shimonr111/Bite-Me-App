package users;

import java.io.Serializable;

/**
 *  Class description: This class is derived class from
 *  Branch manager class which defines the main attributes and functionalities of a
 *  CEO of Bite Me in our system.
 * 
 * @author Lior, Guzovsky.
 * @author Alexander, Martinov.
 *  
 * @version 09/12/2021
 */
public class CeoBiteMe extends User implements Serializable{

	/**
	 * This is the constructor for class CeoBiteMe
	 * 
	 * @param userId CEO user ID.
	 * @param statusInSystem CEO status in system.
	 * @param userFirstName CEO first name.
	 * @param userLastName CEO last name.
	 * @param homeBranch CEO home Branch.
	 * @param isLoggedIn if CEO logged in so true,otherwise false.
	 * @param userEmail CEO Email Address.
	 * @param phoneNumber CEO phone number.
	 */
	public CeoBiteMe(String userId, ConfirmationStatus statusInSystem, String userFirstName, String userLastName,
			Branch homeBranch, boolean isLoggedIn, String userEmail, String phoneNumber) {
		super(userId, statusInSystem, userFirstName, userLastName, homeBranch, isLoggedIn, userEmail,
				phoneNumber);
	}
	
	public CeoBiteMe() {
		super();
	}

	/**
	 * This is the toString for this class
	 */
	@Override
	public String toString() {
		return "CeoBiteMe: " + userFirstName + " " + userLastName;
	}	
}
