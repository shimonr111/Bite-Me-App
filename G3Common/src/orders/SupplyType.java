package orders;
/**
 *  
 *  Enum description:
 *  This Enum is used to describe the order supply method
 * 
 * @author Ori, Malka..
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
