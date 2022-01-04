package orders;

import java.io.Serializable;
/**
 *  
 *  Class description: 
 *  This Abstract class is a base class
 *  for supply method.
 */

/**
 * 
 * @author Ori, Malka.
 *  
 * @version 11/12/2021
 */
public class TakeAwaySupplyMethod extends AbatractSupplyMethod implements Serializable{
	/**
	 * Class members description:
	 */
	
	private static final long serialVersionUID = 1L;

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
	public TakeAwaySupplyMethod(int supplyId, int orderNumber, String receiverFirstName, String receiverLastName,
			String receiverPhoneNumber) {
		super(supplyId, orderNumber, receiverFirstName, receiverLastName, receiverPhoneNumber);
		// TODO Auto-generated constructor stub
	}
	
}
