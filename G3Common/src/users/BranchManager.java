package users;

/**
 * 
 * @author Lior, Guzovsky. Class description: This class is derived class from
 *         User class which defines the main attributes and functionalities of a
 *         Branch manager in our system.
 * @version 29/11/2021
 */
public class BranchManager extends User {

	/**
	 * Class members description:
	 */

	/**
	 * This is the Branch name: North branch and etc.
	 */
	protected Branch branchName;

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
	 * @param branchName
	 */
	public BranchManager(String userId, boolean isConfirmedInSystem, String userFirstName, String userLastName,
			Branch homeBranch, boolean isLoggedIn, int privateW4cCodeNumber, String userEmail, String phoneNumber,
			Branch branchName) {
		super(userId, isConfirmedInSystem, userFirstName, userLastName, homeBranch, isLoggedIn, privateW4cCodeNumber,
				userEmail, phoneNumber);
		this.branchName = branchName;
	}

	/**
	 * This section is for the Setters and Getters of the Class Branch manager
	 */
	public Branch getBranchName() {
		return branchName;
	}

	public void setBranchName(Branch branchName) {
		this.branchName = branchName;
	}

	/**
	 * This is the toString for this class
	 */
	@Override
	public String toString() {
		return "Branch manager: "  + userFirstName + " " + userLastName + ", user ID = " + userId;
	}
}
