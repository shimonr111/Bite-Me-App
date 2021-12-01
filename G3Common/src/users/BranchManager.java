package users;

import java.io.Serializable;

/**
 * 
 * @author Lior, Guzovsky.
 *  Class description: This class is derived class from
 *  User class which defines the main attributes and functionalities of a
 *  Branch manager in our system.
 * @version 29/11/2021
 */
public class BranchManager extends User implements Serializable{

	/**
	 * Class members description:
	 */

	/**
	 * This is the constructor of the class
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
	 */
	public BranchManager(String userId, boolean isConfirmedInSystem, String userFirstName, String userLastName,
			Branch homeBranch, boolean isLoggedIn, int privateW4cCodeNumber, String userEmail, String phoneNumber) {
		super(userId, isConfirmedInSystem, userFirstName, userLastName, homeBranch, isLoggedIn, privateW4cCodeNumber,
				userEmail, phoneNumber);
	}


	/**
	 * This is the toString for this class
	 */
	@Override
	public String toString() {
		return  homeBranch + " manager: " + userFirstName + " " + userLastName;
	}
}
