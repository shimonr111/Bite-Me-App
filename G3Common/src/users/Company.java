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
 * @version 08/12/2021
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
	private boolean isConfirmedCompanyInSystem;
	/**
	 * This is the address of the employer.
	 */
	private String address;
	/**
	 * This is the email address of the employer.
	 */
	private String email;
	
	/**
	 * This is the constructor of the class.
	 * 
	 * @param companyName
	 * @param isConfirmedCompanyInSystem
	 */
	public Company(String companyName, boolean isConfirmedCompanyInSystem, String address, String email) {
		super();
		this.companyName = companyName;
		this.isConfirmedCompanyInSystem = isConfirmedCompanyInSystem;
		this.address = address;
		this.email = email;
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

	public boolean isConfirmedCompanyInSystem() {
		return isConfirmedCompanyInSystem;
	}

	public void setConfirmedCompanyInSystem(boolean isConfirmedCompanyInSystem) {
		this.isConfirmedCompanyInSystem = isConfirmedCompanyInSystem;
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
	
	
}
