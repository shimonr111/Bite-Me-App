package users;

import java.io.Serializable;

/**
 * 
 * @author Mousa, Srour
 *  Class description:
 *  This class is for login form witch contains the username and password
 *  that the user enters, in addition there are 2 more parameters in this class
 *  the userID and the userType that we get them from DB.
 *  @version 04/12/2021
 */
public class Login implements Serializable{
	/**
	 * Class members description:
	 */	
	
	/**
	 * each user has userName.
	 */
	private String userName;
	
	/**
	 * each user has password .
	 */
	private String password;
	
	/**
	 * each user has his own userID , it is the same userID from User's Class 
	 * witch means it is a FK for User.
	 */
	private String userId;
	
	/**
	 * each user has a type.
	 */
	private String userType;
	
	/**
	 * This is a constructor for the Login class
	 * we receive from the customer only an userName and password
	 * we create an Login object using these 2 parameters and we initialize other parameters to null
	 * we need only there 2 parameters before sending the object to the server.
	 * @param userName
	 * @param password
	 */
	public Login(String userName,String password) {
		this.userName=userName;
		this.password = password;
		this.userId=null;
		this.userType=null;
	}
	
	/**
	 * This is a constructor for the Login class
	 * we receive all the parameters from the DB then we use this constructor ( from the server side)
	 * @param userName
	 * @param password
	 * @param userId
	 * @param userType
	 */
	public Login(String userName,String password,String userId,String userType) {
		this.userName=userName;
		this.password = password;
		this.userId=userId;
		this.userType=userType;
	}
	
	
	/**
	 * This section is for the 
	 * Setters and Getters of the 
	 * Login Customer
	 */
	public String getUserName(){
		return userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public String getUserType() {
		return userType;
	}
	
	public void setUserName(String userName) {
		this.userName=userName;
	}
	
	public void setPassword(String password) {
		this.password=password;
	}
	
	public void setUserId(String userId) {
		this.userId=userId;
	}
	
	public void setUserType(String userType) {
		this.userType=userType;
	}
	
	/**
	 * this is a toString for the login class
	 */
	@Override
	public String toString() {
		return "UserName: " + userName +"\nPassword: " + password + "\nuserId: " + userId + "\nuserType: " + userType;
	}
}
