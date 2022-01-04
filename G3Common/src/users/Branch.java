package users;

/** Enum description:
 *  This Enum is used to describe the branches in the 
 *  Bite Me system.
/**
 * 
 * @author Lior, Guzovsky.
 *  
 *  @version 29/11/2021
 */
public enum Branch{
	NORTH {
		@Override
		public String toString() {
			return "NORTH";
		}
	},
	CENTER {
		@Override
		public String toString() {
			return "CENTER";
		}
	},
	SOUTH {
		@Override
		public String toString() {
			return "SOUTH";
		}
	},
	NOT_APPLICABLE{
		@Override
		public String toString() {
			return "NOT_APPLICABLE";
		}
	}
}
