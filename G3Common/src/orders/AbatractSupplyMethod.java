package orders;

import java.io.Serializable;

/**
 * 
 * @author Ori, Malka.
 * 
 *  Class description:
 *  This Abstract class is a base class for supply method.
 *  
 * @version 11/12/2021
 */	
public abstract class AbatractSupplyMethod implements Serializable{
	
	/**
	 * Class members:
	 */

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This supply id, unique identifier - PK.
	 */
	public int supplyId;
	
	/**
	 * The orderNumbe which the is it supply method.
	 */
	public int orderNumber;
	
	/**
	 * The receiver first name.
	 */
	public String receiverFirstName; 
	
	/**
	 * The receiver last name.
	 */
	public String receiverLastName;
	
	/**
	 * The receiver phone number.
	 */
	public String receiverPhoneNumber;
	
	/**
	 * Constructors:
	 */
	
	/**
	 * @param supplyId
	 * @param orderNumber
	 * @param receiverFirstName
	 * @param receiverLastName
	 * @param receiverPhoneNumber
	 */
	public AbatractSupplyMethod(int supplyId, int orderNumber, String receiverFirstName, String receiverLastName,
			String receiverPhoneNumber) {
		super();
		this.supplyId = supplyId;
		this.orderNumber = orderNumber;
		this.receiverFirstName = receiverFirstName;
		this.receiverLastName = receiverLastName;
		this.receiverPhoneNumber = receiverPhoneNumber;
	}

	/**
	 * Methods:
	 */
	
	/**
	 * Getters and Setters:
	 */
	
	public int getSupplyId() {
		return supplyId;
	}

	public void setSupplyId(int supplyId) {
		this.supplyId = supplyId;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getReceiverFirstName() {
		return receiverFirstName;
	}

	public void setReceiverFirstName(String receiverFirstName) {
		this.receiverFirstName = receiverFirstName;
	}

	public String getReceiverLastName() {
		return receiverLastName;
	}

	public void setReceiverLastName(String receiverLastName) {
		this.receiverLastName = receiverLastName;
	}

	public String getReceiverPhoneNumber() {
		return receiverPhoneNumber;
	}

	public void setReceiverPhoneNumber(String receiverPhoneNumber) {
		this.receiverPhoneNumber = receiverPhoneNumber;
	}
	
}
