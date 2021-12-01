package users;

/**
 * 
 * @author Lior, Guzovsky
 *  Enum description:
 *  This Enum is used to describe the branches in the 
 *  Bite Me system.
 *  @version 29/11/2021
 */
public enum Branch {
	NORTH {
		@Override
		public String toString() {
			return "North Branch";
		}
	},
	CENTER {
		@Override
		public String toString() {
			return "Center Branch";
		}
	},
	SOUTH {
		@Override
		public String toString() {
			return "South Branch";
		}
	},
	NOT_APPLICABLE{
		@Override
		public String toString() {
			return "N/A";
		}
	}
}
