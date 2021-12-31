package orders;

/**
 * 
 * @author Ori, Malka.
 * 
 *  Enum description:
 *  This Enum is used to describe the order supply method.
 *  
 *  @version 11/12/2021
 */
public enum SupplyType{
	TAKE_AWAY {
		@Override
		public String toString() {
			return "Take-Away";
		}
	},
	DELIVERY {
		@Override
		public String toString() {
			return "Delivery";
		}
	}

}
