package users;

/**
* 	Enum description:
*  This Enum is used to describe the type of 
*  budget the business customer
*  gets from his employer in the 
*  Bite Me system.
* 
* @author Lior, Guzovsky.
*
*  @version 29/11/2021
*/
public enum BudgetType{

	DAILY{
		@Override
		public String toString() {
			return "DAILY";
		}
	},
	WEEKLY{
		@Override
		public String toString() {
			return "WEEKLY";
		}
	},
	MONTHLY{
		@Override
		public String toString() {
			return "MONTHLY";
		}
	}
}
