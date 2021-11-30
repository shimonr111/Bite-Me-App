package users;

/**
 * 
 * @author Lior, Guzovsky.
 *  Class description: This class sets data for
 *  each company in the system.
 *  This class defines the main attributes and functionalities
 *  of a Company in our system.
 * @version 29/11/2021
 */
public class Company {
	
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
	 * This is the constructor of the class.
	 * 
	 * @param companyName
	 * @param isConfirmedCompanyInSystem
	 */
	public Company(String companyName, boolean isConfirmedCompanyInSystem) {
		super();
		this.companyName = companyName;
		this.isConfirmedCompanyInSystem = isConfirmedCompanyInSystem;
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
	
	
}
