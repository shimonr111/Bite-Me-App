package query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import communication.Answer;
import communication.Message;
import users.Branch;
import users.ConfirmationStatus;
import users.Customer;

/**
 * 
 * @author Lior, Guzovsky
 * @author Shimon, Rubin
 * Class description: 
 * This is a class for 
 * order queries which is 
 * used for getting information 
 * related to the DB in the order 
 * process.
 * 
 * @version 13/12/2021
 */
public class OrderQueries {
	
	/**
	 * This is a function for getting all 
	 * the required data about the restaurants 
	 * we need for the customer in the order 
	 * process.
	 * 
	 * @param messageFromClient
	 * @return
	 * @throws SQLException
	 */
	public static Message createRestaurantsForCustomer(Message messageFromClient) throws SQLException{
		Message returnMessageToClient = messageFromClient;
		Customer customer = (Customer) messageFromClient.getObject();
		Branch branchNameForRelevantRestaurants = customer.getHomeBranch();
		ConfirmationStatus status = ConfirmationStatus.CONFIRMED;
		Map<String,String> supplierList = new HashMap<>();
		
		ResultSet rs = Query.getRowsFromTableInDB("supplier","homeBranch='"+branchNameForRelevantRestaurants.toString()+"' AND statusInSystem='"+status.toString()+"'");
		try {
			//If the row doesn't exist in login Table
			if(!rs.isBeforeFirst()) {
				returnMessageToClient.setAnswer(Answer.RESTAURANTS_NOT_EXIST_IN_THIS_BRANCH);
				returnMessageToClient.setObject(null);
				return returnMessageToClient;
			}
			while(rs.next()) {
				supplierList.put(rs.getString(1),rs.getString(9));//add to the hashMap all the data we need
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//set the message back to the customer
		returnMessageToClient.setObject(supplierList);
		returnMessageToClient.setAnswer(Answer.RESTAURANTS_EXIST_FOR_THIS_BRANCH);
		return returnMessageToClient;
	}
}
