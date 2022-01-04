package users;

import java.io.Serializable;
/**
 *  Class description: 
 * This class is the base class which defines the main attributes and functionalities
 * of a user in our system.
 * 
 * @author Lior, Guzovsky.
 * @author Alexander, Martinov.
 * 
 * @version 09/12/2021
 */
public abstract class User implements Serializable{

	/**
	 * Class members description:
	 */

	/**
	 * Each user has its own Id, which means that it's the PK of the entity.
	 */
	protected String userId;
	
	/**
	 * When user is confirmed he can access the system,
	 * else, he is still pending for confirmation. 
	 */
	protected ConfirmationStatus statusInSystem;
	
	/**
	 * Each user has a first name.
	 */
	protected String userFirstName;
	
	/**
	 * Each user has last name
	 */
	protected String userLastName;
	
	/**
	 * Each user has his own default home branch, which 
	 * can be changed via the Gui.
	 */
	protected Branch homeBranch;
	
	/**
	 * Each user has login state which says 
	 * if he can be logged in multiple times.
	 */
	protected boolean isLoggedIn;
	
	/**
	 * This is the user registered email
	 */
	protected String userEmail;
	
	/**
	 * This is the user's phone number
	 * registered in the Bite Me system.
	 */
	protected String phoneNumber;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	/**
	 * This is the constructor of the class User
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
	public User(String userId, ConfirmationStatus statusInSystem, String userFirstName, String userLastName,
			Branch homeBranch, boolean isLoggedIn, String userEmail, String phoneNumber) {
		super();
		this.userId = userId;
		this.statusInSystem = statusInSystem;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.homeBranch = homeBranch;
		this.isLoggedIn = isLoggedIn;
		this.userEmail = userEmail;
		this.phoneNumber = phoneNumber;
	}
	
	/**
	 * This section is for the 
	 * Setters and Getters of the 
	 * Class User
	 */
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public ConfirmationStatus getStatusInSystem() {
		return statusInSystem;
	}

	public void setStatusInSystem(ConfirmationStatus newStatus) {
		this.statusInSystem=newStatus;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
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

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
