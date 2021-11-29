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
	 * This is the constructor of the class 
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
	 * @param branchName
	 */
	public CeoBiteMe(String userId, String password, boolean isConfirmedInSystem, String userFirstName,
			String userLastName, Branch homeBranch, boolean isLoggedIn, int idNumber, String userEmail,
			String phoneNumber, Branch branchName) {
		super(userId, password, isConfirmedInSystem, userFirstName, userLastName, homeBranch, isLoggedIn, idNumber, userEmail,
				phoneNumber, branchName);
		
	}

	
}
