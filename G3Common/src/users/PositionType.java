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
public enum PositionType {

	HR{
		@Override
		public String toString() {
			return "Human Resources";
		}
	},
	REGULAR{
		@Override
		public String toString() {
			return "Regular worker";
		}
	
	}
}
