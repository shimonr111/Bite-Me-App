package users;

/**
* 
* @author Lior, Guzovsky
*  Enum description:
*  This Enum is used to describe the type of 
*  budget the business customer
*  gets from his employer in the 
*  Bite Me system.
*  @version 29/11/2021
*/
public enum BudgetType {

	DAILY{
		@Override
		public String toString() {
			return "Daily budget";
		}
	},
	WEEKLY{
		@Override
		public String toString() {
			return "Weekly budget";
		}
	},
	MONTHLY{
		@Override
		public String toString() {
			return "Monthly budget";
		}
	}
}
