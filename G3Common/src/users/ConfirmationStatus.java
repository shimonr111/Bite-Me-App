package users;

/**
* 
* @author Alexander, Martinov.
*  Enum description:
*  This Enum is used to describe the position
*  of the worker in the business, differentiating 
*  between hr manager and regular worker in the
*  Bite Me system.
*  @version 08/12/2021
*/
public enum ConfirmationStatus {

	CONFIRMED{
		@Override
		public String toString() {
			return "Confirmed";
		}
	},
	PENDING_APPROVAL{
		@Override
		public String toString() {
			return "Pending Approval";
		}
	},
	FROZEN{
			@Override
			public String toString() {
				return "Frozen";
			}
	
	}
}
