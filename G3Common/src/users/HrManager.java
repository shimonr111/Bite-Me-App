package users;

import java.io.Serializable;
/**
 * 
 * @author Lior, Guzovsky.
 * @author Mousa, Srour.
 * @author Alexander, Martinov.
 * 
 * Class description: 
 * This class is derived class from
 * Business customer class which defines the main attributes and
 * functionalities of a HR manager in our system.
 * 
 * @version 08/12/2021
 */
public class HrManager extends User implements Serializable{

	/**
	 * Class members description:
	 */
	private Company company;;
	
/**
 * This is the constructor used for passing the object.
 * 
 * @param userId
 * @param statusInSystem
 * @param userFirstName
 * @param userLastName
 * @param homeBranch
 * @param isLoggedIn
 * @param companyCode
 * @param userEmail
 * @param phoneNumber
 * @param company
 */
	public HrManager(String userId, ConfirmationStatus statusInSystem, String userFirstName, String userLastName,
			Branch homeBranch, boolean isLoggedIn, String userEmail, String phoneNumber,
			 Company company) {
		super(userId, statusInSystem, userFirstName, userLastName, homeBranch, isLoggedIn, userEmail,
				phoneNumber);
		this.company=company;
	}
	
	/**
	 * 
	 * @return
	 */
	public Company getCompany() {
		return company;
	}
	
	/**
	 * 
	 * @param company
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * This is the toString for this class
	 */
	@Override
	public String toString() {
		return "HR Manager: " + userFirstName  + " "+userLastName + ", Company name: " + company.getCompanyName();
	}

}
