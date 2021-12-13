package query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import communication.Answer;
import communication.Message;
import communication.Task;
import users.Branch;
import users.BudgetType;
import users.BusinessCustomer;
import users.Company;
import users.ConfirmationStatus;
import users.Customer;
import users.HrManager;
import users.PositionType;
import users.User;

/**
 * 
 * @author Mousa, Srour

 * Class description:
 * This class will contain all the queries to DB
 * that relate to Editing users phase.
 * @version 13/12/2021
 */
public class EditUsersQueries {
	
	
	/**
	 * This method gets a branch manager object , and gets all the customers,businesscustomers and hrmanagers that belong to same branch.
	 * @param message
	 * @return list of customers .
	 */
	public static Message getCustomersListFromDb(Message message) {
		Message returnMessageToClient;
		ArrayList<User> customersList = new ArrayList<>();
		Company company;
		ResultSet rs;
		Branch homeBranch = ((User)message.getObject()).getHomeBranch();
		rs = Query.getRowsFromTableInDB("customer","homeBranch = '"+homeBranch.toString()+"'");
		try {
			while(rs.next()) {
				customersList.add(new Customer(rs.getString(1),(ConfirmationStatus.valueOf(rs.getString(2))),rs.getString(3),rs.getString(4),(Branch.valueOf(rs.getString(5))),
						rs.getBoolean(6),rs.getInt(7),rs.getString(8),rs.getString(9),rs.getString(10), rs.getDouble(11)));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		rs= Query.getRowsFromTableInDB("businesscustomer","homeBranch = '"+homeBranch.toString()+"'");
		try {
			while(rs.next()) {
				company = LoginQueries.getCompany(rs.getString(12));
				customersList.add( new BusinessCustomer(rs.getString(1),(ConfirmationStatus.valueOf(rs.getString(2))),rs.getString(3),rs.getString(4),(Branch.valueOf(rs.getString(5))),
						rs.getBoolean(6),rs.getInt(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getDouble(11),company,(BudgetType.valueOf(rs.getString(13))),
						(PositionType.valueOf(rs.getString(14))),rs.getInt(15)));
			}
			rs.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		rs = Query.getRowsFromTableInDB("hrmanager","homeBranch = '"+homeBranch.toString()+"'");
		try {
			while(rs.next()) {
			company = LoginQueries.getCompany(rs.getString(12));
			customersList.add(new HrManager(rs.getString(1),(ConfirmationStatus.valueOf(rs.getString(2))),rs.getString(3),rs.getString(4),(Branch.valueOf(rs.getString(5))),
						rs.getBoolean(6),rs.getInt(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getDouble(11),company, 
						(BudgetType.valueOf(rs.getString(13))),(PositionType.valueOf(rs.getString(14))),rs.getInt(15)));
			}
			rs.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		returnMessageToClient = new Message (Task.DISPLAY_CUSTOMERS_INTO_TABLE,Answer.SUCCEED,customersList);
		return returnMessageToClient;
		
	}

	/**
	 * This method is general for every user type , in the message we sent the userid,the new status as a string and the table name
	 * this method calls the update query method and changes the status.
	 * @param message
	 */
	public static void setCustomerNewStatus(Message message) {
		ArrayList<String> list = (ArrayList<String>) message.getObject();
		String userId= list.get(0);
		String newStatus = list.get(1);
		String tableName = list.get(2);
		//Query.updateOneColumnForTableInDbByPrimaryKey("hrmanager","isLoggedIn"+"="+"0", "userID="+((User) (message.getObject())).getUserId());
		Query.updateOneColumnForTableInDbByPrimaryKey(tableName, "statusInSystem = '"+newStatus+"'", "userID='"+userId+"'");
	}
	
	/**
	 * this method gets a list that contains userId and user type , deletes the user from his table and from login table.
	 * @param message
	 */
	public static void removeUserFromDB(Message message) {
		ArrayList<String> list = (ArrayList<String>) message.getObject();
		String userId = list.get(0);
		String tableName = list.get(1);
		Query.deleteRowFromTableInDbByPrimaryKey(tableName, "userID='"+userId+"'");
		Query.deleteRowFromTableInDbByPrimaryKey("login", "userID='"+userId+"'");
	}
	

}
