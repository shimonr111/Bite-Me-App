package users;

import java.io.Serializable;

/**
 * Class description: 
 * This class is derived class from
 * Business customer class which defines the main attributes and
 * functionalities of a HR manager in our system.
 * 
 * @author Lior, Guzovsky.
 * @author Mousa, Srour.
 * @author Alexander, Martinov.
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
 * @param userId HR manger user ID.
 * @param statusInSystem HR manger Status in system.
 * @param userFirstName  HR manger first name.
 * @param userLastName  HR manger last name.
 * @param homeBranch  HR manger home branch.
 * @param isLoggedIn true if logged in , false if not.
 * @param companyCode HR manager company code.
 * @param userEmail HR manager Email.
 * @param phoneNumber HR manager phone number.
 * @param company HR manager company.
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
