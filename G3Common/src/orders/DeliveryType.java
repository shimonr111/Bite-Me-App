package orders;

/**
 * 
 * @author Ori, Malka.
 *  Enum description:
 *  This Enum is used to describe the delivery types.
 *  @version 11/12/2021
 */
public enum DeliveryType {
	REGULAR {
		@Override
		public String toString() {
			return "Regular";
		}
	},
	MULTI {
		@Override
		public String toString() {
			return "Multi-Participants";
		}
	},
	ROBOTIC {
		@Override
		public String toString() {
			return "Robotic";
		}
	}

}