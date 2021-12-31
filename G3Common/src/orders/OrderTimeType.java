package orders;

/**
 * 
 * @author Ori, Malka.
 * 
 *  Enum description:
 *  This Enum is used to describe the order time type (for internal use, determine discount)
 *  
 *  @version 11/12/2021
 */
public enum OrderTimeType{
	REGULAR {
		@Override
		public String toString() {
			return "Regular";
		}
	},
	PRE {
		@Override
		public String toString() {
			return "Pre-Order";
		}
	}

}
