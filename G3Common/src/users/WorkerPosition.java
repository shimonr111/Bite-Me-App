package users;

/**
* 
* @author Alexander, Martinov.
* 
*  Enum description:
*  This Enum is used to describe the position
*  of the worker in the supplier, differentiating 
*  between supplier manager and regular worker in the
*  Bite Me system.
*  
*  @version 15/12/2021
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
	
	}
}
