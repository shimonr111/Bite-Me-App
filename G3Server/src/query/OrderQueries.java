package query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import communication.Answer;
import communication.Message;
import orders.Item;
import orders.ItemCategory;
import orders.ItemSize;
import users.Branch;
import users.ConfirmationStatus;
import users.Customer;

/**
 * 
 * @author Lior, Guzovsky
 * @author Shimon, Rubin
 * @author Alexander, Martinov
 * Class description: 
 * This is a class for 
 * order queries which is 
 * used for getting information 
 * related to the DB in the order 
 * process.
 * 
 * @version 15/12/2021
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
		
		ResultSet rs = Query.getRowsFromTableInDB("supplier","homeBranch='"+branchNameForRelevantRestaurants.toString()+"'");
		try {
			//If the row doesn't exist in login Table
			if(!rs.isBeforeFirst()) {
				returnMessageToClient.setAnswer(Answer.RESTAURANTS_NOT_EXIST_IN_THIS_BRANCH);
				returnMessageToClient.setObject(null);
				return returnMessageToClient;
			}
			while(rs.next()) {
				supplierList.put(rs.getString(1),rs.getString(2));//add to the hashMap all the data we need
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
	
	public static Message createMenuForSpecificSupplier(Message messageFromClient) throws SQLException{
		Message returnMessageToClient = messageFromClient;
		String supplierId = (String) messageFromClient.getObject();	
		List<Item> itemList = new ArrayList<Item>();
		//check first if the supplier is confirmed in the system
		
		
		//create query for getting all the items of the specific supplier for the users menu		
		ResultSet rs = Query.getRowsFromTableInDB("item_in_menu","supplierId='"+supplierId+"'");
		try {
			//If the row doesn't exist in login Table
			if(!rs.isBeforeFirst()) {
				returnMessageToClient.setAnswer(Answer.SUPPLIER_HAS_NO_MENU);
				returnMessageToClient.setObject(null);
				return returnMessageToClient;
			}
			while(rs.next()) {
				Item itemFromDB = convertToItem(rs);
				if(itemFromDB != null) {
					itemList.add(itemFromDB);
				}
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		returnMessageToClient.setObject(itemList);
		returnMessageToClient.setAnswer(Answer.SUPPLIER_MENU_FOUND);
		return returnMessageToClient;
	}
	
	/**
	 * This is a function
	 * for creating the Item from the DB 
	 * that helps us to create arrayList as
	 * we want to return to the customer.
	 * 
	 * @param rs
	 * @return
	 */
	private static Item convertToItem(ResultSet rs) {
		try {
			String itemName = rs.getString(1);
			String supplierId = null;
			ItemCategory itemCategory = ItemCategory.valueOf(rs.getString(3));
			ItemSize itemSize = ItemSize.valueOf(rs.getString(4));
			String picturePath = rs.getString(5);
			double price =  Double.parseDouble(rs.getString(6));
			return new Item(supplierId,itemName,itemCategory,itemSize,picturePath,price);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		//new Item(rs.getString(2),,rs.getString(6))
	}
}
