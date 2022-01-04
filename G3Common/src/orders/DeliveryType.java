package orders;
/**
 *  
 *  Enum description:
 *  This Enum is used to describe the delivery types.
 */
/**
 * 
 * @author Ori, Malka.
 *  
 *  @version 11/12/2021
 */
public enum DeliveryType{

	NA {
		@Override
		public String toString() {
			return "Not applicable";
		}
	},
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
	JOIN_MULTI{
		@Override
		public String toString() {
			return "Join Multi-Participants";
		}
	},
	ROBOTIC {
		@Override
		public String toString() {
			return "Robotic";
		}
	}

}
