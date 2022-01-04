package util;

import java.text.DecimalFormat;

import users.BusinessCustomer;

/**
*  Class description:
*  This is a util class for calculating the 
*  money left for any customer who is at least
*  a business customer
 */

/**
* 
* @author Lior, Guzovsky.
*  
*  @version 25/12/2021
*/
public class OrderBusinessBudgetCalculation{
	/**
	 * Class members description:
	 */
	
	/**
	 * Decimal format for saving only two
	 * places after the dot.
	 */
    public static final DecimalFormat df = new DecimalFormat("0.00");

	/**
	 * This is a method for calculating 
	 * the amount of budget left for the user to 
	 * use in his company budget.
	 * 
	 * @param client
	 * @return
	 */
	public static double calculateBusinessBudgetLeftForUser(BusinessCustomer client){
		double budgetLeftForTheUser = 0.0;
		switch(client.getBudgetOfBusinessCustomer()) {
			case DAILY:
				budgetLeftForTheUser = Double.valueOf(df.format(((Double.valueOf(String.valueOf(client.getBudgetMaxAmount())) / Constans.DAYS_IN_MONTH) - client.getBudgetUsed())));
				break;
			case WEEKLY:
				budgetLeftForTheUser = Double.valueOf(df.format(((Double.valueOf(String.valueOf(client.getBudgetMaxAmount())) / Constans.WEEKS_IN_MONTH) - client.getBudgetUsed())));
				break;
			case MONTHLY:
				budgetLeftForTheUser = Double.valueOf(df.format((Double.valueOf(String.valueOf(client.getBudgetMaxAmount())) - client.getBudgetUsed())));
				break;
			default:
				break;
		}
		return budgetLeftForTheUser;
	}
}
