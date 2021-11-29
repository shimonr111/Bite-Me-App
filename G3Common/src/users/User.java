package users;

/**
 * 
 * @author Lior, Guzovsky.
 * Class description: 
 * This class is the base class which defines the main attributes and functionalities
 * of a user in our system.
 * @version 29/11/2021
 */
public class User {

	/**
	 * Class members description:
	 */
	
	/**
	 * Each user has its own Id, which means that it's the PK of the entity.
	 */
	private String userId;
	
	/**
	 * Each user has password.
	 */
	private String password;
	
	/**
	 * When user is confirmed he can access the system,
	 * else, he is still pending for confirmation. 
	 */
	private boolean isConfirmedInSystem;
	
	/**
	 * Each user has a first name.
	 */
	private String userFirstName;
	
	/**
	 * Each user has last name
	 */
	private String userLastName;
	
	/**
	 * Each user has his own default home branch, which 
	 * can be changed via the Gui.
	 */
	private Branch homeBranch;
	
	/**
	 * Each user has log in state which says 
	 * if he can be logged in multiple times.
	 */
	private boolean isLoggedIn;
	
	/**
	 * This injective number defines  
	 * the user W4C replacement code.
	 */
	private int idNumber; 
	
	/**
	 * This is the user registered email
	 */
	private String userEmail;
	
	/**
	 * This is the user's phone number
	 * registered in the Bite Me system.
	 */
	private String phoneNumber;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isConfirmedInSystem() {
		return isConfirmedInSystem;
	}

	public void setConfirmedInSystem(boolean isConfirmedInSystem) {
		this.isConfirmedInSystem = isConfirmedInSystem;
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

	public int getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(int idNumber) {
		this.idNumber = idNumber;
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
