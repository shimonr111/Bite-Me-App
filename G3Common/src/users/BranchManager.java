package users;

import java.io.Serializable;

/**
 *  Class description: This class is derived class from
 *  User class which defines the main attributes and functionalities of a
 *  Branch manager in our system.
 */

/**
 * 
 * @author Lior, Guzovsky.
 *  
 * @version 29/11/2021
 */
public class BranchManager extends User implements Serializable{

	/**
	 * Class members description:
	 */

	/**
	 * This is the constructor of the class
	 * 
	 * @param userId Branch manager  User ID.
	 * @param statusInSystem Status In System.
	 * @param userFirstName Branch manager First Name.
	 * @param userLastName Branch  manager Last name.
	 * @param homeBranch Home Branch.
	 * @param isLoggedIn false if not logged in, true if is logged in.
	 * @param userEmail  Branch manager Email.
	 * @param phoneNumber Branch manager phone number.
	 */
	public BranchManager(String userId, ConfirmationStatus statusInSystem, String userFirstName, String userLastName,
			Branch homeBranch, boolean isLoggedIn, String userEmail, String phoneNumber) {
		super(userId, statusInSystem, userFirstName, userLastName, homeBranch, isLoggedIn,
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
