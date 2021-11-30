package users;

/**
 * 
 * @author Lior, Guzovsky. Class description: This class is derived class from
 *         Branch manager class which defines the main attributes and functionalities of a
 *         CEO of Bite Me in our system.
 * @version 29/11/2021
 */
public class CeoBiteMe extends BranchManager{

	/**
	 * This is the constructor for the CEO Bite Me class
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
	 * @param branchName
	 */
	public CeoBiteMe(String userId, boolean isConfirmedInSystem, String userFirstName, String userLastName,
			Branch homeBranch, boolean isLoggedIn, int privateW4cCodeNumber, String userEmail, String phoneNumber,
			Branch branchName) {
		super(userId, isConfirmedInSystem, userFirstName, userLastName, homeBranch, isLoggedIn, privateW4cCodeNumber, userEmail,
				phoneNumber, branchName);
		
	}

	/**
	 * This is the toString for this class
	 */
	@Override
	public String toString() {
		return "CeoBiteMe: " + userFirstName + " " + userLastName + " CEO ID: " + userId;
	}	
}
