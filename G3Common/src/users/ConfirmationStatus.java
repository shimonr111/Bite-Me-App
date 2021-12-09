package users;

/**
* 
* @author Alexander, Martinov.
*  Enum description:
*  This Enum is used to describe status of a user or a company in the system
*  each user and company need to be confirmed before they can access the system
*  @version 09/12/2021
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
