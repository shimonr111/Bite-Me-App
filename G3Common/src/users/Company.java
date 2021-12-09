package users;

import java.io.Serializable;

/**
 * 
 * @author Lior, Guzovsky.
 * @author Alexander, Martinov.
 *  Class description: This class sets data for
 *  each company in the system.
 *  This class defines the main attributes and functionalities
 *  of a Company in our system.
 * @version 09/12/2021
 */
public class Company implements Serializable{
	
	/**
	 * Class members description:
	 */
	
	/**
	 * This is the name of the employer such as Intel and etc.
	 */
	private String companyName;
	
	/**
	 * This is the confirmation status of the company in the system 
	 */
	private ConfirmationStatus companyStatusInSystem;
	/**
	 * This is the address of the employer.
	 */
	private String address;
	/**
	 * This is the email address of the employer.
	 */
	private String email;
	/**
	 * This injective number defines  
	 * the business W4C replacement code.
	 */
	protected int businessW4cCodeNumber; 
	
	/**
	 * This is the constructor of the class.
	 * 
	 * @param companyName
	 * @param companyStatusInSystem
	 * @param address
	 * @param email
	 * @param businessW4cCodeNumber
	 */
	public Company(String companyName, ConfirmationStatus companyStatusInSystem, String address, String email, int businessW4cCodeNumber) {
		super();
		this.companyName = companyName;
		this.companyStatusInSystem = companyStatusInSystem;
		this.address = address;
		this.email = email;
		this.businessW4cCodeNumber=businessW4cCodeNumber;
	}
	
	/**
	 * This section is for the Setters and Getters of the Class Supplier
	 */
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public ConfirmationStatus getStatusCompanyInSystem() {
		return companyStatusInSystem;
	}

	public void setCompanyStatusInSystem(ConfirmationStatus newStatusInSystem) {
		this.companyStatusInSystem = newStatusInSystem;
	}

	@Override
	public String toString() {
		return "Company: " + companyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getBusinessW4cCodeNumber() {
		return businessW4cCodeNumber;
	}

	public void setBusinessW4cCodeNumber(int businessW4cCodeNumber) {
		this.businessW4cCodeNumber = businessW4cCodeNumber;
	}
	
	
}
