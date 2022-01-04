package orders;

/**
 * 	Enum description:
 *  This Enum is used to describe the item size varieties.
 */
/**
 * @author Ori, Malka. 
 *   
 *  @version 11/12/2021
 */
public enum ItemSize {
	SMALL {
		@Override
		public String toString() {
			return "Small";
		}
	},
	REGULAR {
		@Override
		public String toString() {
			return "Regular";
		}
	},
	LARGE {
		@Override
		public String toString() {
			return "Large";
		}
	}

}
