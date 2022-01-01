package users;

/**
* 
* @author Alexander, Martinov.
* @author Mousa, Srour.
* 
*  Enum description:
*  This Enum is used to describe the position
*  of the worker in the supplier, differentiating 
*  between supplier manager and regular worker in the
*  Bite Me system.
*  
*  @version 1/1/2022
*/
public enum WorkerPosition {

	CERTIFIED{
		@Override
		public String toString() {
			return "CERTIFIED";
		}
	},
	REGULAR{
		@Override
		public String toString() {
			return "REGULAR";
		}
	
	},
	MANAGER{
		@Override
		public String toString() {
			return "MANAGER";
		}
	}
}
