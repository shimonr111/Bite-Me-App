package users;

import java.io.Serializable;

/**
 * Class description: 
 * This class is a class which
 * defines the main attributes and functionalities of a supplier worker in our
 * system.
 * 
 * @author Alexander, Martinov.
 * 
 * @version 15/12/2021
 */
public class SupplierWorker extends User implements Serializable{

	/**
	 * Class members description:
	 */

	/**
	 * Each supplier has a supplier business name such as Dominos ,Mcdonalds.
	 */
	private Supplier supplier;

	/**
	 * This is the revenue that the supplier gives to the Bite Me company per month
	 * from their monthly outcomes, 
	 * Please note that this is a number in rage [0,1]
	 */
	private WorkerPosition workerPosition;

	/**
	 * This is the constructor of the class SupplierWorker
	 * 
	 * @param userId Supplier worker user ID.
	 * @param statusInSystem Supplier worker status in system.
	 * @param userFirstName Supplier worker first name.
	 * @param userLastName Supplier worker last name.
	 * @param homeBranch Supplier worker home branch.
	 * @param isLoggedIn True if logged in, false if not.
	 * @param userEmail Supplier worker email.
	 * @param phoneNumber Supplier worker phone number.
	 * @param supplier Supplier worker's restaurant.
	 * @param workerPosition Supplier worker position [Regular,Certified,Manager].
	 */
	public SupplierWorker(String userId, ConfirmationStatus statusInSystem, String userFirstName, String userLastName,
			Branch homeBranch, boolean isLoggedIn, String userEmail, String phoneNumber,
			Supplier supplier, WorkerPosition workerPosition) {
		super(userId, statusInSystem, userFirstName, userLastName, homeBranch, isLoggedIn,
				userEmail, phoneNumber);
		this.supplier = supplier;
		this.workerPosition = workerPosition;
	}
	
	public SupplierWorker() {
		super();
	}

	/**
	 * This section is for the Setters and Getters of the Class SupplierWorker
	 */
	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public WorkerPosition getWorkerPosition() {
		return workerPosition;
	}

	public void setWorkerPosition(WorkerPosition workerPosition) {
		this.workerPosition = workerPosition;
	}

	/**
	 * This is the toString for this class
	 */
	@Override
	public String toString() {
		return "SupplierWorker: " + " " + supplier + " belogns to:" + homeBranch;
	}

}
